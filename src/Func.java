// Just holds information about a function being translated to MIPS.
public class Func {
  public String funcName = "";
  public String funcType = "";
  public int paramsCount = 0;
  public int savedRegsCount = 0;
  public int frameSize = 0; // In bytes.
}
