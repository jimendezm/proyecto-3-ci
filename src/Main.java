import java.io.*;
import java_cup.runtime.ComplexSymbolFactory;

public class Main {
  // Receives the name of the source code file as the first parameter.
  public static void main(String[] args) throws Exception {
    if (args[0].length() == 0) {
      System.out.println("Please enter filename as first parameter.");
      return;
    }

    FileReader f = new FileReader("../test/" + args[0]);
    parser p = new parser(new Lexer(f));
    try {
      p.parse();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
