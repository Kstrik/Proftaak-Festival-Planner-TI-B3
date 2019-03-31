package simulation.map.world;

import com.sun.istack.internal.NotNull;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Room {
    private String name;
    private Seat teacherSeat;
    private ArrayList<Seat> studentSeats;
    private Point2D position;
    private int width;
    private int height;

    public Room(@NotNull String name, @NotNull Point2D position, int width, int height){
        this.name = name;
        this.studentSeats = new ArrayList<Seat>();
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public boolean isInsideRoom(Point2D point2D){
        return this.getShape().contains(point2D);
    }

    public Rectangle2D getShape(){
        return new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
    }

    public String getName() {
        return this.name;
    }

    public Seat getTeacherSeat() {
        return this.teacherSeat;
    }

    public ArrayList<Seat> getStudentSeats() {
        return this.studentSeats;
    }

    public Point2D getCenterPoint(){
        return new Point2D.Double(this.position.getX() + (this.width / 2), this.position.getY() + (this.height / 2));
    }

    public void setTeacherSeat(@NotNull Seat teacherSeat) {
        this.teacherSeat = teacherSeat;
    }

    public void addStudentSeat(@NotNull Seat seat) {
        this.studentSeats.add(seat);
    }

    public void addStudentSeats(@NotNull ArrayList<Seat> seats) {
        this.studentSeats.addAll(seats);
    }

    public Point2D getPosition() {
        return this.position;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
