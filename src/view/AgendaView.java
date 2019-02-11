package view;

import entity.AgendaEntity;
import entity.LessonEntity;
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

    private static final int BLOCK_WIDTH = 150;
    private static final int BLOCK_HEIGHT = 75;

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


        primaryStage.setTitle("School Agenda Manager: " + this.agenda.getName());
        primaryStage.setScene(setScene());
        primaryStage.show();

        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
    }

    private void draw(FXGraphics2D graphics) {

        for (int i = 0; i < this.agenda.getAmountOfLessons(); i++) {

            LessonEntity lesson = this.agenda.getLesson(i);

            double x = (BLOCK_WIDTH * this.agenda.getClassRoomKey(lesson.getClassRoom()));
            double y = (BLOCK_HEIGHT * (lesson.getStartTime() - this.agenda.getAgendaStartTime()));
            double height = (lesson.getLessonLength() > 0.5) ? (BLOCK_HEIGHT * lesson.getLessonLength()) : 40;

            graphics.setColor(Color.RED);
            graphics.draw(new Rectangle2D.Double(x, y, BLOCK_WIDTH, height));
            graphics.setColor(Color.BLACK);
            graphics.drawString(
                lesson.getName() + " - " + lesson.getTeacher() + "\n" +
                lesson.getClassRoom() + " - " + lesson.getGroup() + "\n" +
                lesson.getParsedStartTime() + " - " + lesson.getParsedEndTime(),
                (int) x + 10, (int) y + 20
            );
        }
    }

    private void setTime() {

        for (int i = this.agenda.getAgendaStartTime(); i < this.agenda.getAgendaEndTime(); i++) {

            Label label = new Label(" " + i + ":00");

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
                "-fx-pref-height: " + BLOCK_HEIGHT + ";" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2 2 0 0;" +
                "-fx-border-color: lightgrey;"
            );
            vBox.getChildren().addAll(label, pane);

            GridPane.setConstraints(vBox, 0, (i - this.agenda.getAgendaStartTime()) + 1);

            this.gridPane.getChildren().add(vBox);
        }
    }

    private void setClassRooms()
    {

        List<String> classRooms = this.agenda.getAllClassRooms();

        for (int i = 0; i < classRooms.size(); i++) {

            Label label = new Label(" " + classRooms.get(i));
            label.setStyle(
                "-fx-pref-width: " + BLOCK_WIDTH + ";" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 0 0 2 2;" +
                "-fx-border-color: lightgrey;"
            );

            GridPane.setConstraints(label, i + 1, 0);

            this.gridPane.getChildren().add(label);
        }
    }

    private Scene setScene() {

        this.canvas = new Canvas(
            (BLOCK_WIDTH * this.agenda.getAmountOfClassRooms()),
            (BLOCK_HEIGHT * this.agenda.getAgendaLength())
        );

        GridPane.setConstraints(this.canvas, 1, 1);
        GridPane.setColumnSpan(this.canvas, this.agenda.getAmountOfClassRooms());
        GridPane.setRowSpan(this.canvas, this.agenda.getAgendaLength());

        this.gridPane.getChildren().add(this.canvas);
        this.gridPane.setStyle(
            "-fx-border-style: solid inside;" +
            "-fx-border-color: lightgrey;" +
            "-fx-border-insets: 2;" +
            "-fx-border-width: 2;"
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.gridPane);

        return new Scene(
            scrollPane,
/*          (this.canvas.getWidth() < BLOCK_WIDTH0) ? this.canvas.getWidth() + 63 : 1050, */ 1000,
/*          (this.canvas.getHeight() < 500) ? this.canvas.getHeight() + 30 : 530 */  500
        );
    }
}
