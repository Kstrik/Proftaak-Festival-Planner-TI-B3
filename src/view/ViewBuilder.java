package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewBuilder extends Application {

    private static final int STARTING_HOUR = 7;
    private static final int ENDING_HOUR = 22;

    private GridPane gridPane = new GridPane();

    public void startup()
    {

        launch(ViewBuilder.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        buildHours();
        buildRooms();

        style();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.gridPane);

        Scene scene = new Scene(scrollPane, this.gridPane.getWidth(), this.gridPane.getHeight());

        primaryStage.setTitle("School Agenda Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buildHours() {

        // Dit kan mooier
        for (int i = 0; i < (ENDING_HOUR - STARTING_HOUR) * 2; i++) {

            int modulo = i % 2;
            int hour = (i - modulo) / 2;
            String string = "";

            if (modulo > 0) {

                string += (hour + STARTING_HOUR) + ":30";
            } else {

                string += (hour + STARTING_HOUR) + ":00";
            }

            Label label = new Label(" " + string);
            label.setStyle(
                "-fx-pref-width: 100;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2 2 0 0;" +
                "-fx-border-color: lightgrey;"
            );

            GridPane.setConstraints(label, 0, i + 1);

            this.gridPane.getChildren().add(label);
        }
    }

    private void buildRooms()
    {

        // moet uit de database komen!!!
        String lokalen[] = new String[] {"Lokaal A", "Lokaal B", "Lokaal C", "Lokaal D", "Lokaal E", "Lokaal F", "Lokaal G"};

        for (int i = 0; i < lokalen.length; i++) {

            Label label = new Label(" " + lokalen[i]);
            label.setStyle(
                "-fx-pref-width: 100;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 0 0 2 2;" +
                "-fx-border-color: lightgrey;"
            );

            GridPane.setConstraints(label, i + 1, 0);

            this.gridPane.getChildren().add(label);
        }
    }

    private void style()
    {

        this.gridPane.setStyle(
            "-fx-border-style: solid inside;" +
            "-fx-border-color: lightgrey;" +
            "-fx-border-insets: 2;" +
            "-fx-border-width: 2;"
        );
    }
}
