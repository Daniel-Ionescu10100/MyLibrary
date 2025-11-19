package launcher2;

import javafx.application.Application;
import javafx.stage.Stage;


public class Launcher  extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginComponentFactory loginComponentFactory = LoginComponentFactory.getInstance(false,primaryStage);
    }
}
