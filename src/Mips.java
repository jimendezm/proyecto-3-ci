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

  public void genMainStart() {
    addText(".globl main", false);
    addText("main:");
  }

  public void genMainEnd() {
    addText("  li $v0, 10");
    addText("  syscall");
  }

  public void genFuncStart(String name) {
    addText(getLabel() + ": # " + name, false);
  }

  public void genFuncEnd() {
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
