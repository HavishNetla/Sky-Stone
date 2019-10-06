package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Robot implements OpModeManagerNotifier.Notifications {
  // Subsystems
  public MecanumDrive drive;

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
            for (Subsystem subsystem : subsystems) {
              subsystem.update();
            }
          }
        }
      };

  public Robot(OpMode opMode) {
    subsystems = new ArrayList<>();

    // Init subsystems
    try {
      drive = new MecanumDrive(opMode.hardwareMap);
      subsystems.add(drive);
    } catch (IllegalArgumentException e) {

    }
    subsystemUpdateExecutor = ThreadPool.newSingleThreadExecutor("subsystem update");
  }

  // Starts subsystem executor
  public void start() {
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
  public void onOpModePreInit(OpMode opMode) {}

  @Override
  public void onOpModePreStart(OpMode opMode) {}

  @Override
  public void onOpModePostStop(OpMode opMode) {
    stop();
    if (opModeManager != null) {
      opModeManager.unregisterListener(this);
      opModeManager = null;
    }
  }
}
