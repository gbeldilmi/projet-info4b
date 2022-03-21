package corewar.mars.redcode;

public enum AddressMode {
  IMMEDIATE, DIRECT, INDIRECT;

  public static AddressMode getAddressMode(String arg) {
    if (arg.length() >= 1) {
      switch (arg.charAt(0)) {
        case '#':
          return IMMEDIATE;
        case '@':
          return INDIRECT;
        default:
      }
    }
    return DIRECT;
  }

  public static AddressMode getAddressMode(int code) {
    return AddressMode.values()[code % (AddressMode.values().length)];
  }

  public int getCode() {
    return ordinal();
  }

  public String toString() {
    switch (this) {
      case IMMEDIATE:
        return "#";
      case INDIRECT:
        return "@";
      default:
        return "";
    }
  }
}
