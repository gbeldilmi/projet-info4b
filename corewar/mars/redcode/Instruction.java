package corewar.mars.redcode;

public class Instruction {
  private OpCode op;
  private Argument arg1, arg2;
  private int ownerId;

  public Instruction(OpCode op, Argument arg1, Argument arg2, int ownerId) {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.ownerId = ownerId;
  }

  public Instruction(String instruction, int ownerId) {
    String[] args = instruction.split(" ");
    try {
      op = OpCode.getOpCode(args[0]);
      arg1 = new Argument(args[1]);
      arg2 = new Argument(args[2]);
    } catch (Exception e) {
      op = OpCode.UNKNOWN;
      arg1 = new Argument("");
      arg2 = new Argument("");
    }
    this.ownerId = ownerId;
  }

  public Instruction(Instruction instruction, int ownerId) {
    this(instruction.getOpCode(), instruction.getArg1().copy(), instruction.getArg2().copy(), ownerId);
  }

  public Instruction copy(int ownerId) {
    return new Instruction(this, ownerId);
  }

  public OpCode getOpCode() {
    return op;
  }

  public Argument getArg1() {
    return arg1;
  }

  public Argument getArg2() {
    return arg2;
  }

  public int getOwnerId() {
    return ownerId;
  }

  public String toString() {
    return op.toString() + " " + arg1.toString() + " " + arg2.toString();
  }
}
