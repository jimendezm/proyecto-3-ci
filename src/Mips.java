import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;

public class Mips {
  private ArrayList<String> dataSegment = new ArrayList<>();

  private ArrayList<String> textSegment = new ArrayList<>();

  private int labelCounter = 0;

  private int frameSizeLocation = 0; // Frame size needing to get patched.

  public HashMap<String, Func> funcTable = new HashMap<>();

  public Func f; // Current function definition.

  public Func fc; // Current function call.

  public int floatCounter = 0;

  public HashMap<String, String> floatsTable = new HashMap<>(); // Float variables.

  private void addData(String s, boolean spacesToTabs) {
    if (spacesToTabs) {
      dataSegment.add(s.replace(" ", "\t"));
    } else {
      dataSegment.add(s);
    }
  }

  private void addData(String s) {
    addData(s, true);
  }

  private void addText(String s, boolean spacesToTabs) {
    if (spacesToTabs) {
      textSegment.add(s.replace(" ", "\t"));
    } else {
      textSegment.add(s);
    }
  }

  private void addText(String s) {
    addText(s, true);
  }

  private String getLabel() {
    return "L" + labelCounter++;
  }

  private String getFloatLabel() {
    return "f" + floatCounter++;
  }

  public void genMainStart() {
    addText(".globl main", false);
    addText("main:");
    addText("  addiu $sp, $sp, -?"); // This is later patched with the actual frame size.
    frameSizeLocation = textSegment.size() - 1;
    addText("  move $fp, $sp");
  }

  public void genMainEnd() {
    int frameSize = f.localVarsCount * 4;
    textSegment.set(frameSizeLocation, "\t\taddiu\t$sp,\t$sp,\t-" + frameSize);
    addText("  li  $v0, 10");
    addText("  syscall");
  }

  public void genFuncStart() {
    String label = getLabel();
    f.label = label;
    addText(label + ": # " + f.funcName, false);
    // Space needed. word_size * (paramsCount + fp + ra).
    // Only 4 parameters are saved because the rest are in stack from caller.
    // TO DO: Handle float parameters.
    f.frameSize = 4 * (Math.min(f.intParamsCount, 4) + 2);
    addText("  addiu $sp, $sp, -" + f.frameSize);
    addText("  sw  $ra, " + (f.frameSize - 4) + "($sp)");
    addText("  sw  $fp, " + (f.frameSize - 8) + "($sp)");
    addText("  move $fp, $sp");
    for (int i = 0; i < Math.min(f.intParamsCount, 4); i++) {
      int offset = f.frameSize - 12 - i * 4;
      addText("  sw  $a" + i + ", " + offset + "($sp)");
    }
  }

  public void genFuncEnd() {
    addText("  lw  $ra, " + (f.frameSize - 4) + "($fp)");
    addText("  lw  $fp, " + (f.frameSize - 8) + "($fp)");
    addText("  addiu $sp, $sp, " + f.frameSize);
    addText("  jr  $ra");
    addText("  nop");
    funcTable.put(f.funcName, f);
  }

  public void genFuncCall() {
    String label = funcTable.get(fc.funcName).label;
    addText("  jal  " + label);
  }

  // Returns first unused register.
  public int getReg() {
    for (int i = 0; i < f.registersUsed.size(); i++) {
      if (!f.registersUsed.get(i)) {
        f.registersUsed.put(i, true);
        return i;
      }
    }
    return -1;
  }

  public void genVarCreation(String reg) {
    // f.intParamsCount++;
    // int offset = 4 * (2 + f.intParamsCount + f.floatParamsCount + f.localVarsOffsetCounter);
    // addText("  sw,  " + reg + ", " + offset + "");
    // f.localVarsOffsetCounter += 4;
  }

  public String primaryInt(String value) {
    int reg = getReg();
    addText("  li  $t" + reg + ", " + value);
    return "t" + reg;
  }

  public void dump(String filename) {
    try {
      PrintWriter writer = new PrintWriter("../" + filename);
      writer.println(".data");
      for (String s : dataSegment) {
        writer.println(s);
      }
      writer.println(".text");
      for (String s : textSegment) {
        writer.println(s);
      }
      writer.close();
    } catch (Exception e) {
      System.out.println("Could't save MIPS assembly file. Error:\n" + e.getMessage());
    }
  }
}
