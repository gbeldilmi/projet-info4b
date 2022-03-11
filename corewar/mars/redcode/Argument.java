package corewar.mars.redcode;

public class Argument {
  private AddressMode addressMode;
  private int value;

  Argument(AddressMode addressMode, int value) {
    this.addressMode = addressMode;
    this.value = value;
  }

  Argument(String arg) {
    addressMode = AddressMode.getAddressMode(arg);
    value = 0;
    try {
      value = Integer.parseInt(addressMode == AddressMode.INDIRECT ? arg : arg.substring(1));
    } catch (Exception e) {
      value = 0;
    }
  }

  public Argument copy() {
    return new Argument(addressMode, value);
  }

  public AddressMode getAddressMode() {
    return addressMode;
  }

  public int getValue() {
    return value;
  }

  public String toString() {
    return addressMode.toString() + value;
  }
}
