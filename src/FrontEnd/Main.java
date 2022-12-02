package FrontEnd;

import BackEnd.InOutOperations;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
        stage.setScene(new Scene(root, 800,600));
        stage.show();
    }

    //log bug data to Data/log.txt
    private static PrintWriter bugLogger;

    public static void main(String[] args){
        try{
            bugLogger = new PrintWriter("Data/log.txt");
            InOutOperations.customerIn();
            InOutOperations.rentalIn();
            InOutOperations.contractIn();

            Application.launch(args);
        }
        catch (IOException ioException){
            ioException.printStackTrace(bugLogger);
        }
    }
}
