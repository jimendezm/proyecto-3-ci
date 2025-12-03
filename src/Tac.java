import java.util.ArrayList;
import java_cup.runtime.Symbol;

public class Tac {
  private ArrayList<String> code = new ArrayList<>();

  private int temp_counter = 1;

  public String newTemp() {
    return "t" + temp_counter++;
  }

  public void resetCounter() {
    temp_counter = 1;
  }

  public String primary(Object value) {
    String i;
    String t = newTemp();
    if (value instanceof String) {
      i = String.format("%s = %s", t, value);
    } else {
      i = String.format("%s = %s", t, ((Symbol)value).toString());
    }
    code.add(i);
    return t;
  }

  public String inc(String id) {
    // Note: This is a postorder increment, so the original value is the one
    // returned to be used in the expression.
    String original = newTemp();
    String increment = newTemp(); // Just to hold the 1 used in the sum.
    String incremented = newTemp();
    code.add(String.format("%s = %s", original, id));
    code.add(String.format("%s = %s", increment, "1"));
    code.add(String.format("%s = %s + %s", incremented, original, increment));
    code.add(String.format("%s = %s", id, incremented));
    return original;
  }

  public String dec(String id) {
    // Note: This is a postorder decrement, so the original value is the one
    // returned to be used in the expression.
    String original = newTemp();
    String decrement = newTemp(); // Just to hold the 1 used in the subtraction.
    String decremented = newTemp();
    code.add(String.format("%s = %s", original, id));
    code.add(String.format("%s = %s", decrement, "1"));
    code.add(String.format("%s = %s - %s", decremented, original, decrement));
    code.add(String.format("%s = %s", id, decremented));
    return original;
  }

  public String power(String base, String exp) {
    String t = newTemp();
    code.add(String.format("param %s", base));
    code.add(String.format("param %s", exp));
    code.add(String.format("%s = call pow, 2", t));
    return t;
  }

  public void dump() {
    for (String i : code) {
      System.out.println(i);
    }
  }

}
