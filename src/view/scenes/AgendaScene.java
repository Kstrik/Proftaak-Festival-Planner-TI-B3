package view.scenes;

import javafx.scene.input.MouseEvent;
import model.ConfigModel;
import model.entity.Agenda;
import model.entity.*;

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
import controller.interfaces.AgendaUpdate;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgendaScene extends BaseScene {

    private HashMap<Rectangle, Item> canvasItemLocations;

    private Agenda agenda;

    private AgendaUpdate observer;
    private Schedule schedule;

    private GridPane scheduleGrid;
    private Canvas canvas;
    private VBox main;

    // main function
    @Override
    public Scene getScene(Stage stage) {

        this.canvasItemLocations = new HashMap<>();
        this.scheduleGrid = new GridPane();
        this.canvas = new Canvas();

        this.main = this.getMain();

        this.setScheduleDateButtons();
        this.setScheduleClassrooms();
        this.setScheduleTime();
        this.setScheduleGrid();

        this.draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));

        Scene scene = new Scene(new ScrollPane(this.main), this.getSceneWidth(), this.getSceneHeight());
        scene.getStylesheets().add("view/style/style.css");

        return scene;
    }

    // setters
    public void setObserver(AgendaUpdate observer) {

        this.observer = observer;
        this.setBaseObserver(observer);
    }

    public void setSchedule(Schedule schedule) {

        this.schedule = schedule;
    }

    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;

        if (this.schedule == null)
            this.schedule = agenda.getFirstSchedule();
    }

    // scene methods
    private void setScheduleDateButtons() {

        HBox hBox = new HBox();
        hBox.getStyleClass().addAll("paddingless", "borderless-bottom");

        for (LocalDateTime date : this.agenda.getAllScheduleDates()) {

            Button button = new Button(getParsedDate(date));
            button.getStyleClass().addAll("button", "schedule-button");
            button.setOnMouseClicked(e -> this.observer.onAgendaSelectByDate(date));

            hBox.getChildren().add(button);
        }

        main.getChildren().add(hBox);
    }

    private void setScheduleClassrooms() {

        ArrayList<Classroom> classrooms = this.agenda.getAllClassrooms();

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
        button.setOnMouseClicked(e -> this.observer.onAgendaItemCreate());

        this.canvas = new Canvas(
            (ConfigModel.BLOCK_WIDTH * this.agenda.getAmountOfClassrooms()),
            (ConfigModel.BLOCK_HEIGHT * (this.schedule.getScheduleLength() + 1))
        );

        GridPane.setConstraints(button, 0, 0);
        GridPane.setConstraints(this.canvas, 1, 1);
        GridPane.setColumnSpan(this.canvas, (this.agenda.getAmountOfClassrooms() == 0) ? 1 : this.agenda.getAmountOfClassrooms());
        GridPane.setRowSpan(this.canvas, (this.schedule.getScheduleLength() + 1));

        this.scheduleGrid.getChildren().addAll(button, this.canvas);
        this.main.getChildren().addAll(this.scheduleGrid);
    }

    // canvas methods
    private void draw(FXGraphics2D graphics) {

        this.drawBorder(graphics);
        this.drawLines(graphics);

        for (int i = 0; i < this.schedule.getAmountOfItems(); i++)
            this.drawItem(graphics, this.schedule.getItem(i));

        this.canvas.setOnMouseClicked(this::canvasOnClick);
    }

    private void drawBorder(FXGraphics2D graphics2D) {

        graphics2D.draw(new Rectangle2D.Double(2, 2, this.canvas.getWidth() - 2, this.canvas.getHeight() - 2));
    }

    private void drawLines(FXGraphics2D graphics) {

        graphics.setColor(Color.GRAY);
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {2}, 0));

        for (int a = 1; a < this.agenda.getAmountOfClassrooms(); a++) {

            graphics.draw(new Line2D.Double((ConfigModel.BLOCK_WIDTH * a) + 1, 3, (ConfigModel.BLOCK_WIDTH * a) + 1, this.canvas.getHeight() - 2));
        }
    }

    private void drawItem(FXGraphics2D graphics, Item item) {

        int x        = getItemX(item);
        int y        = getItemY(item);
        int width    = getItemWidth();
        int height   = getItemHeight(item);

        Rectangle rectangle = new Rectangle(x, y, width, height);
        graphics.setColor(Color.getHSBColor((float) Math.random(), 1, 1));
        graphics.fill(rectangle);

        graphics.setColor(Color.BLACK);
        graphics.drawString(this.getItemString(item), (x + 10), (y + 20));

        this.canvasItemLocations.put(rectangle, item);
    }

    private String getItemString(Item item) {

        StringBuilder itemString = new StringBuilder();

        itemString.append(item.getName());
        itemString.append(" in ");
        itemString.append(this.agenda.getTeacherById(item.getTeacherId()).getName());
        itemString.append("\n");
        itemString.append("Teacher: ");
        itemString.append(this.agenda.getClassroomById(item.getClassroomId()).getName());
        itemString.append("\n");
        itemString.append(item.getParsedStart());
        itemString.append(" - ");
        itemString.append(item.getParsedEnd());

        return itemString.toString();
    }

    private void canvasOnClick(MouseEvent e) {

        for (Map.Entry<Rectangle, Item> entry : this.canvasItemLocations.entrySet())
            if (entry.getKey().contains(e.getX(), e.getY()))
                this.observer.onAgendaItemSelect(entry.getValue());
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

        return (this.canvas.getWidth() < 1000) ? (this.canvas.getWidth() + 68) : 1068;
    }

    private double getSceneHeight() {

        return (this.canvas.getHeight() < 500) ? (this.canvas.getHeight() + 114) : 613;
    }

    private int getItemX(Item item) {

        return ((ConfigModel.BLOCK_WIDTH * this.agenda.getClassRoomKey(item.getClassroomId())) + 9);
    }

    private int getItemY(Item item) {

        return (int) (ConfigModel.BLOCK_HEIGHT * (item.getStartDouble() - this.schedule.getScheduleStart().getHour())) + 2;
    }

    private int getItemWidth() {

        return (ConfigModel.BLOCK_WIDTH - 17);
    }

    private int getItemHeight(Item item) {

        return (item.getLessonDouble() > 1) ? (int) (ConfigModel.BLOCK_HEIGHT * item.getLessonDouble()) : 62;
    }
}
