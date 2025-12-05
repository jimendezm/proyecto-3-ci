import java.util.ArrayList;
import java.io.PrintWriter;

public class Mips {
  private ArrayList<String> dataSegment = new ArrayList<>();

  private ArrayList<String> textSegment = new ArrayList<>();

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
      dataSegment.add(s.replace(" ", "\t"));
    } else {
      dataSegment.add(s);
    }
  }

  private void addText(String s) {
    addText(s, true);
  }

  public void genMain() {
    addText(".text");
    addText(".globl main", false);
    addText("main:");
    addText(" li $v0, 10");
    addText(" syscall");
  }

  public void dump(String filename) {
    try {
      PrintWriter writer = new PrintWriter("../" + filename);
      for (String s : dataSegment) {
        writer.println(s);
      }
      for (String s : textSegment) {
        writer.println(s);
      }
      writer.close();
    } catch (Exception e) {
      System.out.println("Could't save MIPS assembly file. Error:\n" + e.getMessage());
    }
  }
}
