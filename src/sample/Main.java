package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    }


//    public static void main(String[] args) throws Exception {
////        launch(args);
//        String urlString = "https://jsonplaceholder.typicode.com/todos/1";
//        URL url = new URL(urlString);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        String line;
//        while((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//        reader.close();
//    }

    public static void main(String[] args) throws Exception {
//        launch(args);
        String urlString = "https://jsonplaceholder.typicode.com/todos/1";
        URL url = new URL(urlString);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())))
        {
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
