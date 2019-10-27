package org.firstinspires.ftc.teamcode.subsystems;

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

  private List<Subsystem> subsystems;
  private OpModeManagerImpl opModeManager;
  private ExecutorService subsystemUpdateExecutor;

  private boolean started;
  private Telemetry telemetry;
  private int count = 0;
  // Run the "update" function for every subsytem
  private Runnable subsystemUpdateRunnable =
          new Runnable() {
            @Override
            public void run() {
              telemetry.addData("f", count);
              while (!Thread.currentThread().isInterrupted()) {
                for (Subsystem subsystem : subsystems) {
                  subsystem.update();
                }
                telemetry.addData("f", count);
                count++;
              }
            }
          };

  public Robot(OpMode opMode, Telemetry telemetry) {
    subsystems = new ArrayList<>();

    // Init subsystems
    drive = new MecanumDrive(opMode.hardwareMap);
    subsystems.add(drive);

    encoderTest = new EncoderTest(opMode.hardwareMap);
    subsystems.add(encoderTest);

    subsystemUpdateExecutor = ThreadPool.newSingleThreadExecutor("subsystem update");
    this.telemetry = telemetry;
  }

  // Starts subsystem executor
  public void start() {
    telemetry.addData("f", "f");
    if (!started) {
      subsystemUpdateExecutor.submit(subsystemUpdateRunnable);
      started = true;
    }
  }

  // Shuts down subsystem executor
  private void stop() {
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
    stop();
    if (opModeManager != null) {
      opModeManager.unregisterListener(this);
      opModeManager = null;
    }
  }
}
