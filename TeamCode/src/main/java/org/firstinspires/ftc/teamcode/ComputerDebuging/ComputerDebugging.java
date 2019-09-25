package org.firstinspires.ftc.teamcode.ComputerDebuging;

import org.firstinspires.ftc.teamcode.Path.PathSegment;
import org.firstinspires.ftc.teamcode.Util.Pose2d;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

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
  }

  public static void sendRobotLocation(Pose2d pose) {
    // Create the string that shows the robot position
    // It will look like this "ROBOT:0.0,0.0,0.0"
    messageBuilder.append("ROBOT:");
    messageBuilder.append(df.format(pose.x));
    messageBuilder.append(",");
    messageBuilder.append(df.format(pose.y));
    messageBuilder.append(",");
    messageBuilder.append(pose.heading);
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
    }
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

  public static void sendPacket() {
    messageBuilder.append("CLEAR:%");

    udpServer.splitAndSend(messageBuilder.toString());
    messageBuilder = new StringBuilder();
  }
}
