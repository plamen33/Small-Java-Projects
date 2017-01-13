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
      @FXML
      private void processNumpad(ActionEvent event){
      }
      @FXML
      private void processOperator(ActionEvent event){
      }
    //This is done to change the Title of the program window
      @FXML
      private TextField input;
      @FXML private void doChangeTitle() {
          Stage primStage = (Stage) input.getScene().getWindow();
        primStage.setTitle(input.getText());
    }
}