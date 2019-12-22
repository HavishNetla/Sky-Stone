package org.firstinspires.ftc.teamcode.subsystems;

import android.app.Activity;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Robot implements OpModeManagerNotifier.Notifications {
    // Subsystems
    public MecanumDrive drive;
    public EncoderTest encoderTest;
    // public Intake intake;

    private List<Subsystem> subsystems;
    private OpModeManagerImpl opModeManager;
    private ExecutorService subsystemUpdateExecutor;

    private boolean started;

    // Run the "update" function for every subsytem
    private Runnable subsystemUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                for (Subsystem subsystem : subsystems) {
                    subsystem.update();
                }

                System.out.println("statut: " + Thread.currentThread().isInterrupted());
            }
        }
    };

    public Robot(OpMode opMode, Telemetry telemetry) {
        subsystems = new ArrayList<>();

        // Init subsystems
        drive = new MecanumDrive(opMode.hardwareMap, telemetry);
        subsystems.add(drive);

        encoderTest = new EncoderTest(opMode.hardwareMap);
        subsystems.add(encoderTest);

        //    intake = new Intake(opMode.hardwareMap);
        //    subsystems.add(encoderTest);

        Activity activity = (Activity) opMode.hardwareMap.appContext;
        opModeManager = OpModeManagerImpl.getOpModeManagerOfActivity(activity);
        if (opModeManager != null) {
            opModeManager.registerListener(this);
        }

        subsystemUpdateExecutor = ThreadPool.newSingleThreadExecutor("subsystem update");

        this.started = false;
    }

    // Starts subsystem executor
    public void start() {
        if (!started) {
            subsystemUpdateExecutor.execute(subsystemUpdateRunnable);

            started = true;
        }
    }

    // Shuts down subsystem executor
    public void stop() {
        System.out.println("statut: STOPED THE ROBOT");
        if (subsystemUpdateExecutor != null) {
            subsystemUpdateExecutor.shutdownNow();
            subsystemUpdateExecutor = null;
        }
    }

    // ===============================================

    @Override
    public void onOpModePreInit(OpMode opMode) {
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        System.out.println("statut: STOPEED THE DAMN ROBOT");
        stop();
        if (opModeManager != null) {
            opModeManager.unregisterListener(this);
            opModeManager = null;
        }
    }
}
