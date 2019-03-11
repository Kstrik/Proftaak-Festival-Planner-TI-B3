package model.map.world;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class Room {
    private int id;
    private String name;
    private Seat teacherSeat;
    private ArrayList<Seat> studentSeats;

    public Room(@NotNull int id, @NotNull String name){
        this.id = id;
        this.name = name;
        this.studentSeats = new ArrayList<Seat>();
    }

    public int getId() {
        return this.id;
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

    public void setTeacherSeat(@NotNull Seat teacherSeat) {
        this.teacherSeat = teacherSeat;
    }

    public void addStudentSeat(@NotNull Seat seat) {
        this.studentSeats.add(seat);
    }

    public void addStudentSeats(@NotNull ArrayList<Seat> seats) {
        this.studentSeats.addAll(seats);
    }
}
