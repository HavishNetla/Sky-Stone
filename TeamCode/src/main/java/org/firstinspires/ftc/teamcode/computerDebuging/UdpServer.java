package org.firstinspires.ftc.teamcode.computerDebuging;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Semaphore;

public class UdpServer implements Runnable {
  public static boolean kill = false;
  private final int clientPort;
  private Semaphore sendLock = new Semaphore(1);
  private long lastSendMillis = 0;
  private String lastUpdate = "";
  private String currentUpdate = "";

  public UdpServer(int clientPort) {
    this.clientPort = clientPort;
  }

  /**
   * This runs repeatedly (it's own thread). It looks to see if there are any messages to send and
   * if so which to send.
   */
  @Override
  public void run() {
    while (true) {
      if (kill) {
        break;
      }
      try {
        // never send data too fast
        if (System.currentTimeMillis() - lastSendMillis < 100) {
          continue;
        }
        // set the last send time
        lastSendMillis = System.currentTimeMillis();

        // wait for semaphore to be available
        sendLock.acquire();

        // We will send either the current update or the last update depending on
        // if we are using the currentUpdate String or not
        if (currentUpdate.length() > 0) {
          // send the current update
          sendUdpRAW(currentUpdate);
          // now we scrap everything in currentUpdate to flag it is empty
          currentUpdate = "";
        }
        // release the semaphore
        sendLock.release();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void splitAndSend(String message) {
    String[] splitted = message.split("%");
    for (String x : splitted) {
      sendUdpRAW(x);
    }
  }

  /**
   * This is a prate method to actually send a message over the udp protocol
   *
   * @param message the message you wish to send
   */
  private void sendUdpRAW(String message) {
    try (DatagramSocket serverSocket = new DatagramSocket()) {
      DatagramPacket datagramPacket =
          new DatagramPacket(
              message.getBytes(), message.length(), InetAddress.getByName("192.168.49.189"), clientPort);
      //192.168.49.221
      serverSocket.send(datagramPacket);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
