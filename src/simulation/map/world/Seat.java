package simulation.map.world;

import com.sun.istack.internal.NotNull;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Seat {
    private Point2D position;
    private int width;
    private int height;
    private boolean isTaken;

    public Seat(@NotNull Point2D position, int width, int height){
        this.position = position;
        this.width = width;
        this.height = height;
        this.isTaken = false;
    }

    public boolean isOnSeat(Point2D point2D){
        return this.getShape().contains(point2D);
    }

    public Rectangle2D getShape(){
        return new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
    }

    public Point2D getCenterPoint(){
        return new Point2D.Double(this.position.getX() + (this.width / 2), this.position.getY() + (this.height / 2));
    }


    public Point2D getPosition(){
        return this.position;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isTaken() {
        return this.isTaken;
    }

    public void setTaken(boolean taken) {
        this.isTaken = taken;
    }
}
