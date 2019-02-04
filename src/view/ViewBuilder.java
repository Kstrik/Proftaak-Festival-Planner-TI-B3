package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class ViewBuilder extends Application {

    private static final int STARTING_HOUR = 6;
    private static final int ENDING_HOUR = 24;

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

        style();

        draw();

        GridPane.setConstraints(this.canvas, 1, 1);
        GridPane.setConstraints(this.vBox, 0, 1);
        GridPane.setConstraints(this.hBox, 1, 0);

        this.gridPane.getChildren().addAll(this.canvas, this.vBox, this.hBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.gridPane);

        Scene scene = new Scene(scrollPane, this.gridPane.getWidth(), 500);

        primaryStage.setTitle("School Agenda Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buildVBox() {

        for (double i = STARTING_HOUR; i < ENDING_HOUR; i += 0.5) {

            double modulo = i % 1;
            int hour = (int)(i - modulo);
            String string = "";

            if (modulo > 0) {

                string += hour + ":30";
            } else {

                string += hour + ":00";
            }

            Label label = new Label(" " + string);
            label.setStyle(
                "-fx-pref-width: 100;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2 0 0 0;" +
                "-fx-border-color: lightgrey;"
            );

            this.vBox.getChildren().add(label);
        }
    }

    private void buildHBox()
    {

        String lokalen[] = new String[] {"Lokaal A", "Lokaal B", "Lokaal C", "Lokaal D", "Lokaal E", "Lokaal F", "Lokaal G"};

        for (String lokaal : lokalen) {

            Label label = new Label(" " + lokaal);
            label.setStyle(
                "-fx-pref-width: 100;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 0 0 0 2;" +
                "-fx-border-color: lightgrey;"
            );

            hBox.getChildren().add(label);
        }
    }

    private void draw()
    {

    }

    private void style()
    {

        this.gridPane.setStyle(
            "-fx-border-style: solid inside;" +
            "-fx-border-color: lightgrey;" +
            "-fx-border-insets: 2;" +
            "-fx-border-width: 2;"
        );

        this.vBox.setStyle(
            "-fx-border-style: solid inside;" +
            "-fx-border-color: lightgrey;" +
            "-fx-border-width: 0 2 0 0;"
        );

        this.hBox.setStyle(
            "-fx-border-style: solid inside;" +
            "-fx-border-color: lightgrey;" +
            "-fx-border-width: 0 0 2 0;"
        );
    }
}
