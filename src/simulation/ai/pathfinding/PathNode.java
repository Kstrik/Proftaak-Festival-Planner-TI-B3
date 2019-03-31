package simulation.ai.pathfinding;

import com.sun.istack.internal.NotNull;

import java.awt.geom.Point2D;

public class PathNode implements Comparable{
    private Point2D indexing;
    private double distance;
    private double rotation;

    public PathNode(@NotNull Point2D indexing){
        this.indexing = indexing;
    }

    public Point2D getIndexing() {
        return this.indexing;
    }

    public double getDistance() {
        return this.distance;
    }

    public double getRotation() {
        return this.rotation;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public int compareTo(Object o) {
        PathNode test = (PathNode)o;
        return (test.getIndexing().getX() == this.getIndexing().getX() && test.getIndexing().getY() == this.getIndexing().getY()) ? 0 : 1;
    }
}
