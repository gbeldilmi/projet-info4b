package corewar.mars.redcode;

public enum AddressMode {
  IMMEDIATE, DIRECT, INDIRECT;

  /*
   * Traduit un mode d'adresse sous forme de chaine de caractères en élément de l'énumération
   */
  public static AddressMode getAddressMode(char c) {
    switch (c) {
      case '#':
        return IMMEDIATE;
      case '@':
        return INDIRECT;
      default:
        return DIRECT;
    }
  }

  /*
   * Donne un élément de l'énumération à partir de son nombre correspondant
   */
  public static AddressMode getAddressMode(int code) {
    return AddressMode.values()[code % (AddressMode.values().length)];
  }

  /*
   * Donne l'entier correspondant au mode d'adressage
   */
  public int getCode() {
    return ordinal();
  }

  /*
   * Donne la représentation du mode d'adressage
   * sous forme de chaine de caractères
   */
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
