package org.firstinspires.ftc.teamcode.computerDebuging;

import org.firstinspires.ftc.teamcode.path.PathSegment;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ComputerDebugging {
  static StringBuilder messageBuilder;
  private static UdpServer udpServer;
  private static DecimalFormat df = new DecimalFormat("#.00");

  public ComputerDebugging() {
    udpServer.kill = false;
    udpServer = new UdpServer(8221);
    messageBuilder = new StringBuilder();
    Thread runner = new Thread(udpServer);
    runner.start();

    sendInit();
  }

  public static void sendInit() {
    messageBuilder.append("CLEARINIT:%");
  }

  public static void sendRobotLocation(Pose2d pose) {
    // Create the string that shows the robot position
    // It will look like this "ROBOT:0.0,0.0,0.0"
    messageBuilder.append("ROBOT:");
    messageBuilder.append(df.format(pose.getX()));
    messageBuilder.append(",");
    messageBuilder.append(df.format(pose.getY()));
    messageBuilder.append(",");
    messageBuilder.append(pose.getHeading());
    messageBuilder.append("%");

    // udpServer.addMessage(messageBuilder.toString());
  }

  public static void sendPaths(ArrayList<PathSegment> path) {
    messageBuilder.append("LINE:");
    messageBuilder.append(path.get(0).start);
    messageBuilder.append(",");
    messageBuilder.append(path.get(0).end);

    for (int i = 1; i < path.size(); i++) {
        messageBuilder.append(",");
        messageBuilder.append(path.get(i).end);

        if(i == path.size() - 1) {
          messageBuilder.append("%");
          System.out.println("ffff");
          sendPaths1(path.get(i));
        }
    }
  }

  public static void sendPaths1(PathSegment segment) {
    messageBuilder.append("LINECOLOR:");
    messageBuilder.append(segment.start);
    messageBuilder.append(",");
    messageBuilder.append(segment.end);

    messageBuilder.append("%");
  }

  public static void sendPoint(Vector2d point) {
    messageBuilder.append("POINT:");
    messageBuilder.append(point.toString());

    messageBuilder.append("%");
  }

  public static void sendNoClear(Vector2d point) {
    messageBuilder.append("POINT1:");
    messageBuilder.append(point.toString());

    messageBuilder.append("%");
  }

  public static void sendLog(String message) {
    messageBuilder.append("DEBUG:");
    messageBuilder.append(message);
    messageBuilder.append("%");
  }

  public static void sendPacket() {
    messageBuilder.append("CLEAR:%");

    udpServer.splitAndSend(messageBuilder.toString());
    messageBuilder = new StringBuilder();
  }
}
