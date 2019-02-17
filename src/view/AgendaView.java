package view;

import model.agendaEntity.*;
import model.AgendaModel;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AgendaView extends Application {

    private static final int BLOCK_WIDTH = 200;
    private static final int BLOCK_HEIGHT = 75;

    private Agenda agenda;
    private Schedule schedule;

    private GridPane scheduleGrid;
    private Canvas canvas;
    private VBox main;

    public void startup() {

        launch(AgendaView.class);
    }

    @Override
    public void start(Stage primaryStage) {

        AgendaModel agendaModel = new AgendaModel();
        this.agenda = agendaModel.getAgendaWithJSONFile("testAgenda");
        this.schedule = this.agenda.getFirstSchedule();

        this.buildSchedule();

        Scene scene = new Scene(new ScrollPane(this.main), this.getSceneWidth(), this.getSceneHeight());
        scene.getStylesheets().add("view/style/agenda.css");

        primaryStage.setTitle("School Agenda Manager: " + this.agenda.getName());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // scene methods
    private void buildSchedule() {

        this.scheduleGrid = new GridPane();
        this.canvas = new Canvas();
        this.main = new VBox();

        this.setScheduleDateButtons();
        this.setScheduleClassrooms();
        this.setScheduleTime();
        this.setScheduleGrid();

        this.draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
    }

    private void setScheduleDateButtons() {

        HBox hBox = new HBox();

        for (LocalDateTime date : this.agenda.getScheduleDates()) {

            Button button = new Button(getParsedDate(date));
            button.setOnMouseClicked(e -> {

                this.schedule = this.agenda.getScheduleByDate(date);
                this.buildSchedule();
            });

            hBox.getChildren().add(button);
        }

        main.getChildren().add(hBox);
        main.getStyleClass().add("main");
    }

    private void setScheduleClassrooms() {

        ArrayList<Classroom> classrooms = this.schedule.getAllClassrooms();

        for (int i = 0; i < classrooms.size(); i++) {

            Label label = new Label(" " + classrooms.get(i).getName());
            label.getStyleClass().add("classroom-label");

            GridPane.setConstraints(label, i + 1, 0);

            this.scheduleGrid.getChildren().add(label);
        }
    }

    private void setScheduleTime() {

        LocalDateTime start = this.schedule.getScheduleStart();
        LocalDateTime end = this.schedule.getScheduleEnd();

        for (int i = start.getHour(); i <= end.getHour(); i++) {

            VBox vBox = new VBox();
            vBox.getChildren().addAll(new Label(" " + i + ":00"), new Pane());

            GridPane.setConstraints(vBox, 0, (i - start.getHour()) + 1);

            this.scheduleGrid.getChildren().add(vBox);
        }
    }

    private void setScheduleGrid() {

        Button button = new Button("+");
        button.getStyleClass().add("add-button");

        this.canvas = new Canvas(
            (BLOCK_WIDTH * this.schedule.getAmountOfClassrooms()),
            (BLOCK_HEIGHT * (this.schedule.getScheduleLength() + 1))
        );

        GridPane.setConstraints(button, 0, 0);
        GridPane.setConstraints(this.canvas, 1, 1);
        GridPane.setColumnSpan(this.canvas, this.schedule.getAmountOfClassrooms());
        GridPane.setRowSpan(this.canvas, (this.schedule.getScheduleLength() + 1));

        this.scheduleGrid.getChildren().addAll(button, this.canvas);
        this.main.getChildren().addAll(this.scheduleGrid);
    }

    // canvas methods
    private void draw(FXGraphics2D graphics) {

        this.drawLines(graphics);

        for (int i = 0; i < this.schedule.getAmountOfScheduleItems(); i++)
            this.drawScheduleItem(graphics, this.schedule.getScheduleItem(i));
    }

    private void drawLines(FXGraphics2D graphics) {

        graphics.setColor(Color.GRAY);
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {2}, 0));

        for (int a = 1; a < this.schedule.getAmountOfClassrooms(); a++) {

            graphics.draw(new Line2D.Double((BLOCK_WIDTH * a) + 2, 0, (BLOCK_WIDTH * a) + 2, this.canvas.getHeight()));
        }
    }

    private void drawScheduleItem(FXGraphics2D graphics, ScheduleItem item) {

        double x = ((BLOCK_WIDTH * this.schedule.getClassRoomKey(item.getClassroom())) + 10);
        double y = (BLOCK_HEIGHT * (item.getStartDouble() - this.schedule.getScheduleStart().getHour()));
        double height = (item.getLessonDouble() > 1) ? (BLOCK_HEIGHT * item.getLessonDouble()) : 62;

        graphics.setColor(Color.getHSBColor((float) Math.random(), 1, 1));
        graphics.fill(new Rectangle2D.Double(x, y, BLOCK_WIDTH - 17, height));

        graphics.setColor(Color.BLACK);
        graphics.drawString(item.getString(), (int) (x + 10), (int) (y + 20));
    }

    // other methods
    private String getParsedDate(LocalDateTime date) {

        return
            date.getDayOfWeek()     + " ( " +
            date.getYear()          + "-" +
            date.getMonthValue()    + "-" +
            date.getDayOfMonth()    + " )"
        ;
    }

    private double getSceneWidth() {

        return (this.canvas.getWidth() < 1000) ? this.canvas.getWidth() + 63 : 1063;
    }

    private double getSceneHeight() {

        return (this.canvas.getHeight() < 500) ? this.canvas.getHeight() + 30 : 530;
    }
}
