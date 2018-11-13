package ba.unsa.etf.rpr.tutorijal05;

import java.math.BigDecimal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class Main extends Application {

    private BigDecimal left;
    private String selectedOperator;
    private boolean numberInputting;

    @FXML
    private TextField display;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculator");
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });
        stage.setResizable(false);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("digitron.fxml"))));
        stage.show();
    }

    @FXML
    protected void handleOnAnyButtonClicked(ActionEvent evt) {
        Button button = (Button)evt.getSource(); // koja je tipka
        final String buttonText = button.getText(); // uzmi naziv tipke da mozes traziti dolje

        if (buttonText.matches("[0-9\\.]")) { // ako je pritisnut broj
            if (!numberInputting) {
                numberInputting = true;
                display.clear();
            }
            display.appendText(buttonText);
            return;
        }
        if (buttonText.matches("[＋－×/%]")) { // ako je pritisnuta tipka
            left = new BigDecimal(display.getText());
            selectedOperator = buttonText;
            numberInputting = false;
            return;
        }
        if (buttonText.equals("=")) { // ako je jednako
            final BigDecimal right = numberInputting ? new BigDecimal(display.getText()) : left;
            left = calculate(selectedOperator, left, right);
            display.setText(left.toString());
            numberInputting = false;
            return;
        }
    }

    static BigDecimal calculate(String operator, BigDecimal left, BigDecimal right) { // gdje se svo racunanje vrsi
        switch (operator) {
            case "＋":
                return left.add(right);
            case "－":
                return left.subtract(right);
            case "×":
                return left.multiply(right);
            case "/":
                return left.divide(right);
            case "%":
                return left.remainder(right);
            default:
        }
        return right;
    }

    public static void main(String[] args) {
        launch(args);
    }

}