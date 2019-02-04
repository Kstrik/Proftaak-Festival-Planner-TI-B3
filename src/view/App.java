package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application {

    public App()
    {

        launch(App.class);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent page = FXMLLoader.load(getClass().getResource("App.fxml"));

        primaryStage.setTitle("Application");
        primaryStage.setScene(page.getScene());
        primaryStage.show();
    }
}
