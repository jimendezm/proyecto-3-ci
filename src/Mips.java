import java.util.ArrayList;
import java.io.PrintWriter;

public class Mips {
  private ArrayList<String> dataSegment = new ArrayList<>();

  private ArrayList<String> textSegment = new ArrayList<>();

  private int labelCounter = 0;

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

  public String funcName = "";

  public String funcType = "";

  public int paramsCount = 0;

  public int savedRegsCount = 0;

  public int frameSize = 0; // In bytes.

  public void genMainStart() {
    addText(".globl main", false);
    addText("main:");
  }

  public void genMainEnd() {
    addText("  li $v0, 10");
    addText("  syscall");
  }

  public void genFuncStart() {
    addText(getLabel() + ": # " + funcName, false);
    // Space needed. word_size * (paramsCount + fp + ra).
    // 1 is added to point to the next free word.
    // Only 4 parameters are saved because the rest are in stack from caller.
    frameSize = 4 * (Math.min(paramsCount, 4) + 2 + 1);
    addText("  addiu $sp, $sp, -" + frameSize);
    addText("  sw $ra, " + (frameSize - 4) + "($sp)");
    addText("  sw $fp, " + (frameSize - 8) + "($sp)");
    addText("  move $fp, $sp");
    for (int i = 0; i < Math.min(paramsCount, 4); i++) {
      int offset = frameSize - 12 - i * 4;
      addText("  sw $a" + i + ", " + offset + "($sp)");
    }
  }

  public void genFuncEnd() {
    addText("  lw $ra, " + (frameSize - 4) + "($sp)");
    addText("  lw $fp, " + (frameSize - 8) + "($sp)");
    addText("  addiu $sp, $sp, " + frameSize);
    addText("  jr $ra");
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
