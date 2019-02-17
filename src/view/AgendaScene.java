package view;

import controller.AgendaUpdate;
import model.agendaEntity.*;

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

public class AgendaScene {

    private static final int BLOCK_WIDTH = 200;
    private static final int BLOCK_HEIGHT = 75;

    private AgendaUpdate observer;
    private Schedule schedule;
    private Agenda agenda;

    private GridPane scheduleGrid;
    private HBox buttonBox;
    private Canvas canvas;
    private VBox main;

    public Scene getScene(Stage primaryStage) {

        this.buildSchedule();

        this.buttonBox.getStyleClass().add("button-box");

        VBox vBox = new VBox();
        vBox.getStyleClass().add("borderless");
        vBox.getChildren().addAll(this.main, this.buttonBox);

        Scene scene = new Scene(new ScrollPane(vBox), this.getSceneWidth(), this.getSceneHeight());
        scene.getStylesheets().add("view/style/style.css");

        primaryStage.setTitle("School Agenda Manager: " + this.agenda.getName());

        return scene;
    }

    // setters
    public void setObserver(AgendaUpdate observer) {

        this.observer = observer;
    }

    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    public void setSchedule(Schedule schedule) {

        this.schedule = schedule;
    }

    // scene methods
    private void buildSchedule() {

        this.scheduleGrid = new GridPane();
        this.buttonBox = new HBox();
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
        hBox.getStyleClass().addAll("paddingless", "borderless");

        for (LocalDateTime date : this.agenda.getScheduleDates()) {

            Button button = new Button(getParsedDate(date));
            button.getStyleClass().add("button");
            button.setOnMouseClicked(e -> this.observer.onScheduleSelectByDate(date));

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
        button.getStyleClass().addAll("button", "add-button");
        button.setOnMouseClicked(e -> this.observer.onAgendaScheduleItemCreate());

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

        this.drawBorder(graphics);
        this.drawLines(graphics);

        for (int i = 0; i < this.schedule.getAmountOfScheduleItems(); i++) {

            this.drawScheduleItem(graphics, this.schedule.getScheduleItem(i));
            this.drawButtons(this.schedule.getScheduleItem(i));
        }
    }

    private void drawBorder(FXGraphics2D graphics2D) {

        graphics2D.draw(new Rectangle2D.Double(2, 2, this.canvas.getWidth() - 2, this.canvas.getHeight() - 2));
    }

    private void drawLines(FXGraphics2D graphics) {

        graphics.setColor(Color.GRAY);
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {2}, 0));

        for (int a = 1; a < this.schedule.getAmountOfClassrooms(); a++) {

            graphics.draw(new Line2D.Double((BLOCK_WIDTH * a) + 1, 3, (BLOCK_WIDTH * a) + 1, this.canvas.getHeight() - 2));
        }
    }

    private void drawScheduleItem(FXGraphics2D graphics, ScheduleItem scheduleItem) {

        double x        = getScheduleItemX(scheduleItem);
        double y        = getScheduleItemY(scheduleItem);
        double height   = getScheduleItemHeight(scheduleItem);

        graphics.setColor(Color.getHSBColor((float) Math.random(), 1, 1));
        graphics.fill(new Rectangle2D.Double(x, y, BLOCK_WIDTH - 17, height));

        graphics.setColor(Color.BLACK);
        graphics.drawString(scheduleItem.getString(), (int) (x + 10), (int) (y + 20));
    }

    private void drawButtons(ScheduleItem scheduleItem) {

        Button edit = new Button("Edit");
        edit.getStyleClass().addAll("button", "scheduleItem-button");
        edit.setOnMouseClicked(e -> this.observer.onAgendaScheduleItemRead(scheduleItem));
        edit.setTranslateX(this.getButtonX(scheduleItem));
        edit.setTranslateY(this.getButtonY(scheduleItem));

        Button delete = new Button("Delete");
        delete.getStyleClass().addAll("button", "scheduleItem-button");
        delete.setOnMouseClicked(e -> this.observer.onAgendaScheduleItemDelete(scheduleItem));
        delete.setTranslateX(this.getButtonX(scheduleItem) - 45);
        delete.setTranslateY(this.getButtonY(scheduleItem) + 31);

        this.buttonBox.getChildren().addAll(edit, delete);
    }

    // other methods
    private String getParsedDate(LocalDateTime date) {

        return
            date.getDayOfWeek()     + " ( " +
            date.getYear()          + " - " +
            date.getMonthValue()    + " - " +
            date.getDayOfMonth()    + " )"
        ;
    }

    private double getSceneWidth() {

        return /*(this.canvas.getWidth() < 1000) ? (this.canvas.getWidth() + 68) : 1068;*/ 1000;
    }

    private double getSceneHeight() {

        return /*(this.canvas.getHeight() < 500) ? (this.canvas.getHeight() + 99) : 599;*/ 500;
    }

    private double getScheduleItemX(ScheduleItem item) {

        return ((BLOCK_WIDTH * this.schedule.getClassRoomKey(item.getClassroom())) + 9);
    }

    private double getScheduleItemY(ScheduleItem item) {

        return (BLOCK_HEIGHT * (item.getStartDouble() - this.schedule.getScheduleStart().getHour())) + 2;
    }

    private double getScheduleItemHeight(ScheduleItem item) {

        return (item.getLessonDouble() > 1) ? (BLOCK_HEIGHT * item.getLessonDouble()) : 62;
    }

    private double getButtonX(ScheduleItem item) {

        return (this.getScheduleItemX(item) + 189) - (78 * this.schedule.getClassRoomKey(item.getClassroom()));
    }

    private double getButtonY(ScheduleItem item) {

        return -((this.canvas.getHeight() - this.getScheduleItemY(item)) + 3);
    }
}
