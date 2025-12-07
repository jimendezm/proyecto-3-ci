// Just holds information about a function being translated to MIPS.
public class Func {
  public String funcName = "";
  public String funcType = "";
  public String label = "";
  public int intParamsCount = 0;
  public int floatParamsCount = 0;
  public int localVarsCount = 0;
  public int savedRegsCount = 0;
  public int frameSize = 0; // In bytes.
}
