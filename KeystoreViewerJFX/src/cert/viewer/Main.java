package cert.viewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Keystore Viewer");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/cert/fxml/certviewer.fxml"));
        Parent fxmlMain = fxmlLoader.load();

        Scene scene = new Scene(fxmlMain, 950, 600);
        scene.getStylesheets().add(getClass().getResource("/cert/css/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource("/cert/images/cert.png").toExternalForm()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
