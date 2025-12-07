import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;

public class Mips {
  private ArrayList<String> dataSegment = new ArrayList<>();

  private ArrayList<String> textSegment = new ArrayList<>();

  private int labelCounter = 0;

  public HashMap<String, Func> funcTable = new HashMap<>();

  public Func f; // Current function.

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

  public void genMainStart() {
    addText(".globl main", false);
    addText("main:");
  }

  public void genMainEnd() {
    addText("  li  $v0, 10");
    addText("  syscall");
  }

  public void genFuncStart() {
    String label = getLabel();
    f.label = label;
    addText(label + ": # " + f.funcName, false);
    // Space needed. word_size * (paramsCount + fp + ra).
    // 1 is added to point to the next free word.
    // Only 4 parameters are saved because the rest are in stack from caller.
    // TO DO: Handle float parameters.
    f.frameSize = 4 * (Math.min(f.intParamsCount, 4) + 2 + 1);
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
    String label = funcTable.get(f.funcName).label;
    addText("  jal " + label);
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
