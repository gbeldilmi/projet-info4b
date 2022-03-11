package corewar.mars.redcode;

public enum AddressMode {
  IMMEDIATE, DIRECT, INDIRECT;

  public static AddressMode getAddressMode(String arg) {
    if (arg.length() >= 1) {
      switch (arg.charAt(0)) {
        case '#':
          return IMMEDIATE;
        case '@':
          return DIRECT;
        default:
      }
    }
    return INDIRECT;
  }

  public String toString() {
    switch (this) {
      case IMMEDIATE:
        return "#";
      case DIRECT:
        return "@";
      default:
        return "";
    }
  }
}
