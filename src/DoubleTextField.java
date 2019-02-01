import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class DoubleTextField extends TextField {
    private final TextFormatter<String> formatter = new TextFormatter<>(change -> {
        String text = change.getControlNewText();
        if (text.isEmpty()) {
            return change;
        }
        if (text.matches("-?[0-9]+(\\.[0-9]*)?")) {
            return change;
        }
        return null;
    });

    public DoubleTextField() {
        super("0");
        setTextFormatter(formatter);
    }

}
