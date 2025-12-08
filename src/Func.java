// Just holds information about a function being translated to MIPS.

import java.util.HashMap;

public class Func {
  public String funcName = "";
  public String funcType = "";
  public String label = "";
  public int intParamsCount = 0;
  public int floatParamsCount = 0;
  public int localVarsCount = 0;
  public int savedRegsCount = 0;
  public int frameSize = 0; // In bytes.
  public int localVarsOffsetCounter = 0;
  public HashMap<String, Integer> localVarsOffsets = new HashMap<>();
  // Holds <temporaryRegNumber, usedOrNot>.
  public HashMap<Integer, Boolean> registersUsed = new HashMap<>();

  public Func() {
    resetRegTable();
  }

  public void resetRegTable() {
    for (int i = 0; i < 8; i++) {
      registersUsed.put(i, false);
    }
  }
}
