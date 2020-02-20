package org.firstinspires.ftc.teamcode.subsystems;

import android.app.Activity;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.commands.core.LynxGetBulkInputDataResponse;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.teamcode.util.Logger;
import org.firstinspires.ftc.teamcode.util.Pose2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class Robot implements OpModeManagerNotifier.Notifications {
  // Subsystems
  public MecanumDrive drive;
  public EncoderTest encoderTest;
  public Lift lift;
  public Intake intake;
  long start;
  List<LynxModule> allHubs;
  // Run the "update" function for every subsytem
  Logger logger;
  private List<Subsystem> subsystems;
  private OpModeManagerImpl opModeManager;
  private ExecutorService subsystemUpdateExecutor;
  private boolean started;
  private List<LynxModule> hubs;
  private boolean bulkDataUpdated = false;
  private Map<LynxModule, LynxGetBulkInputDataResponse> bulkDataResponses;

  private Runnable subsystemUpdateRunnable =
      new Runnable() {
        @Override
        public void run() {
          try {
            Logger logger = new Logger("robotPositions");
          } catch (IOException e) {
            e.printStackTrace();
          }

          while (!Thread.currentThread().isInterrupted()) {
            long startTime = System.currentTimeMillis();

            for (LynxModule module : allHubs) {
              module.clearBulkCache();
            }

            //            for (LynxModule hub : hubs) {
            //              LynxGetBulkInputDataCommand command = new
            // LynxGetBulkInputDataCommand(hub);
            //              try {
            //                bulkDataResponses.put(hub, command.sendReceive());
            //                bulkDataUpdated = true;
            //              } catch (Exception e) {
            //                try {
            //                  Log.e("sadasd", "get bulk data error");
            //                  Log.e("sadasd", e.getLocalizedMessage());
            //                } catch (NullPointerException npe) {
            //                  Log.e("sadasd", "error logging exception");
            //                }
            //                bulkDataUpdated = false;
            //              }
            //            }

            for (Subsystem subsystem : subsystems) {
              try {
                subsystem.update();
              } catch (Exception E) {
                System.out.println("DAMMIT ERROR GOT THROW BUT IT DIDNT CRASH WHOOPEEEE");
              }
            }
            try {
              logger.writePose(drive.getPosition());
            } catch (IOException e) {
              e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            System.out.println("time taken in mili seconds " + (endTime - startTime));
          }
        }
      };

  public Robot(Pose2d ogPos, OpMode opMode, Telemetry telemetry) throws IOException {
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

    bulkDataResponses = new HashMap<>(2);
    hubs = new ArrayList<>(2);

    subsystemUpdateExecutor = ThreadPool.newSingleThreadExecutor("subsystem update");

    this.started = false;

    logger = new Logger("RobotPositions");
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
