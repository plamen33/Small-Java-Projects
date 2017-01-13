package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    
    @FXML
    private Text output;

    private double number1 = 0; // by default number1 value equals 0
    private String operator = "";
    private boolean start = true;
	private String clearCommand = "";

    private Model model = new Model();

    @FXML
    private void processNumpad(ActionEvent event) {
        if (start) {
            output.setText("");
            start = false;
        }

        String value = ((Button)event.getSource()).getText();  // returns the text of the Button which is on the button
        output.setText(output.getText() + value);  // get the value - we append the value at the end
    }

    @FXML
    private void processOperator(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();

         if (!"=".equals(value) && !"Clear".equals(value)) {  // we check if value does not equal the final equal sign as it is the final calculate sign
            if (!operator.isEmpty()){
                return;
            }
            operator = value;
            number1 = Double.parseDouble(output.getText());
            output.setText("");
        }
		else if (value.equals("Clear")) {
            number1 = 0;
            operator = ""; // clear the operator
            start = true;
            output.setText("");
        }
        else {
            if (operator.isEmpty()){
                return;
            }
            double number2 = Double.parseDouble(output.getText());
            output.setText(String.valueOf(model.calculate(number1, number2, operator)));
            double result = model.calculate(number1, number2, operator);
            number1 = result; // in case one decides to do actions further
            operator = ""; // clear the operator
            start = true;
        }
    }
	//This is done to change the Title of the program window
    @FXML
    private TextField input;
    @FXML
    private void doChangeTitle() {
        Stage primStage = (Stage)input.getScene().getWindow();
        primStage.setTitle(input.getText());
    }
 }
