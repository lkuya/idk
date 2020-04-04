package run;

import interfaces.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Controller("localhost", 12345));
        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
