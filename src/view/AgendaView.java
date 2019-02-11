package view;

import entity.AgendaEntity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AgendaModel;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class AgendaView extends Application {

    private AgendaEntity agenda;
    private GridPane gridPane = new GridPane();
    private Canvas canvas = new Canvas();

    public void startup() {

        launch(AgendaView.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AgendaModel agendaModel = new AgendaModel();
        this.agenda = agendaModel.getAgendaWithJSONFile("testAgenda");

        setTime();
        setClassRooms();

        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));

        primaryStage.setTitle("School Agenda Manager");
        primaryStage.setScene(setScene());
        primaryStage.show();
    }

    public void draw(FXGraphics2D graphics) {

        graphics.setColor(Color.RED);
        graphics.draw(new Rectangle2D.Double(0, 0, 1000, 1000));
    }

    private void setTime() {

        int startTime = (int) Math.floor(this.agenda.getEarliestLessonStartTime());
        int endTime = (int) Math.ceil(this.agenda.getLatestLessonEndTime());

        for (int hour = startTime; hour < endTime; hour++) {

            Label label = new Label(" " + hour + ":00");

            Pane pane = new Pane();
            pane.setStyle(
                "-fx-pref-height: 20;" +
                "-fx-border-style: dashed inside;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-color: lightgrey;"
            );

            VBox vBox = new VBox();
            vBox.setStyle(
                "-fx-pref-width: 40;" +
                "-fx-pref-height: 75;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2 2 0 0;" +
                "-fx-border-color: lightgrey;"
            );
            vBox.getChildren().addAll(label, pane);

            GridPane.setConstraints(vBox, 0, (hour - startTime) + 1);

            this.gridPane.getChildren().add(vBox);
        }
    }

    private void setClassRooms()
    {

        List<String> classRooms = this.agenda.getAllClassRooms();

        for (int i = 0; i < classRooms.size(); i++) {

            Label label = new Label(" " + classRooms.get(i));
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

    private Scene setScene() {

        GridPane.setConstraints(this.canvas, 1, 1);
        GridPane.setColumnSpan(this.canvas, this.agenda.getAmountOfClassRooms());
        GridPane.setRowSpan(this.canvas, (int) (this.agenda.getLatestLessonEndTime() - this.agenda.getEarliestLessonStartTime()));

        this.gridPane.getChildren().add(this.canvas);
        this.gridPane.setStyle(
            "-fx-border-style: solid inside;" +
            "-fx-border-color: lightgrey;" +
            "-fx-border-insets: 2;" +
            "-fx-border-width: 2;"
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.gridPane);

        return new Scene(scrollPane, this.gridPane.getWidth(), this.gridPane.getHeight());
    }
}
