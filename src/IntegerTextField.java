import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class IntegerTextField extends TextField {
    private final TextFormatter<String> formatter = new TextFormatter<>(change -> {
        String text = change.getControlNewText();
        if (text.isEmpty()) {
            return change;
        }
        if (text.matches("-?[0-9]+")) {
            return change;
        }
        return null;
    });

    /*
    1 2 3 9
    2 −1 1 8
    3 0 −1 3
     */

    public IntegerTextField() {
        super("0");
        setTextFormatter(formatter);
    }

    public IntegerTextField(int initial) {
        this();
        setText(Integer.toString(initial));
    }
}
