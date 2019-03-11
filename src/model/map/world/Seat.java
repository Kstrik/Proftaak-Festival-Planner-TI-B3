package model.map.world;

import com.sun.istack.internal.NotNull;
import javafx.geometry.Point2D;

public class Seat {
    private Point2D position;

    public Seat(@NotNull Point2D position){
        this.position = position;
    }

    public Point2D getPosition(){
        return this.position;
    }
}
