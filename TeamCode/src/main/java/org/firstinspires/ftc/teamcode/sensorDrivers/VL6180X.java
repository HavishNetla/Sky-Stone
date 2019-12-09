package org.firstinspires.ftc.teamcode.sensorDrivers;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;

@I2cSensor(name = "VL6180X Laser TOF Sensor", description = "TOF from Adafruit", xmlTag = "VL6180X")
public class VL6180X extends I2cDeviceSynchDevice<I2cDeviceSynch> {
  private static final I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(29);

  protected VL6180X(I2cDeviceSynch deviceClient) {
    super(deviceClient, true);
    this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

    super.registerArmingStateCallback(false);
    this.deviceClient.engage();
  }

  @Override
  protected boolean doInitialize() {
    return true;
  }

  @Override
  public Manufacturer getManufacturer() {
    return Manufacturer.Adafruit;
  }

  @Override
  public String getDeviceName() {
    return "Adafruit VL6180X";
  }

  public enum Register {
      IDENTIFICATION__MODEL_ID(0x0),
    SYSRANGE__THRESH_HIGH(0x019),
      SYSRANGE__THRESH_LOW(0x01A),
      RESULT__RANGE_VAL(0x062),
    ;
    public int bVal;

    Register(int bVal) {
      this.bVal = bVal;
    }
  }
}
