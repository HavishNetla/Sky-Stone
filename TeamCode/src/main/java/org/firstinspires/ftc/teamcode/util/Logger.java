package org.firstinspires.ftc.teamcode.util;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
  String fileName;
  FileWriter fileWriter;

  public Logger(String fileName) throws IOException {
    this.fileName = fileName;

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();

    String concatFileName = fileName + " " + dateFormat.format(date) + ".csv";

    File myObj = new File(Environment.getExternalStorageDirectory(), concatFileName);

    try {
      myObj.createNewFile();
    } catch (Exception e) {
      System.out.println("havish error: " + e);
    }
    this.fileWriter = new FileWriter(myObj);
    fileWriter.write("x,y,c,");
  }

  public void writePose(Pose2d pose) throws IOException {
    fileWriter.write(pose.getX() + "," + pose.getY() + "," + pose.getHeading() + "\n");
  }
}
