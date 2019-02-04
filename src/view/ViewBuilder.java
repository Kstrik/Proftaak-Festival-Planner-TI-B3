package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewBuilder extends Application {

    private GridPane gridPane = new GridPane();
    private Canvas canvas = new Canvas();
    private VBox vBox = new VBox();
    private HBox hBox = new HBox();

    public void startup()
    {

        launch(ViewBuilder.class);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        buildVBox();
        buildHBox();

        GridPane.setConstraints(this.canvas, 1, 1);
        GridPane.setConstraints(this.vBox, 0, 1);
        GridPane.setConstraints(this.hBox, 1, 0);

        this.gridPane.getChildren().addAll(this.canvas, this.vBox, this.hBox);

        Scene scene = new Scene(this.gridPane);

        primaryStage.setTitle("School Agenda Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buildVBox() {

        this.vBox.setSpacing(2);
        this.vBox.setMaxHeight(500);

        for (double i = 0; i < 24; i += 0.5) {

            double modulo = i % 1;
            int hour = (int)(i - modulo);
            String string = "";

            if (modulo > 0) {

                string += hour + ":30";
            } else {

                string += hour + ":00";
            }

            this.vBox.getChildren().add(new Label(string));
        }
    }

    private void buildHBox()
    {

        String lokalen[] = new String[] {"Lokaal A", "Lokaal B", "Lokaal C", "Lokaal D", "Lokaal E"};

        this.hBox.setSpacing(10);

        for (String lokaal : lokalen) {

            Label label = new Label(lokaal);
            label.minWidth(200);

            hBox.getChildren().add(label);
        }
    }
}
