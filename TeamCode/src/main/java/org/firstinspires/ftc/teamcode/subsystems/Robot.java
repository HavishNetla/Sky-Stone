package org.firstinspires.ftc.teamcode.subsystems;

import android.app.Activity;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.teamcode.util.Pose2d;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Robot implements OpModeManagerNotifier.Notifications {
  // Subsystems
  public MecanumDrive drive;
  public EncoderTest encoderTest;
  public Lift lift;
  public Intake intake;
  long start;
  List<LynxModule> allHubs;
  private List<Subsystem> subsystems;
  private OpModeManagerImpl opModeManager;
  private ExecutorService subsystemUpdateExecutor;
  private boolean started;
  // Run the "update" function for every subsytem
  private Runnable subsystemUpdateRunnable =
      new Runnable() {
        @Override
        public void run() {
          while (!Thread.currentThread().isInterrupted()) {
            //  long startTime = System.currentTimeMillis();

            for (LynxModule module : allHubs) {
              module.clearBulkCache();
            }

            for (Subsystem subsystem : subsystems) {
              subsystem.update();
            }

            // long endTime = System.currentTimeMillis();
            // System.out.println("time taken in nano seconds" + (endTime-startTime));
          }
        }
      };

  public Robot(Pose2d ogPos, OpMode opMode, Telemetry telemetry) {
    subsystems = new ArrayList<>();
    this.allHubs = opMode.hardwareMap.getAll(LynxModule.class);

    // Init subsystems
    drive = new MecanumDrive(ogPos, opMode.hardwareMap, telemetry);
    subsystems.add(drive);

    lift = new Lift(opMode.hardwareMap, telemetry);
    subsystems.add(lift);

    intake = new Intake(opMode.hardwareMap);
    subsystems.add(intake);

    Activity activity = (Activity) opMode.hardwareMap.appContext;
    opModeManager = OpModeManagerImpl.getOpModeManagerOfActivity(activity);
    if (opModeManager != null) {
      opModeManager.registerListener(this);
    }

    for (LynxModule module : allHubs) {
      module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
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
  public void onOpModePreInit(OpMode opMode) {}

  @Override
  public void onOpModePreStart(OpMode opMode) {}

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
