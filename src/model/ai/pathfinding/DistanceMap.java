package model.ai.pathfinding;

public class DistanceMap {
    private double[][] distanceNodes;
    private int width;
    private int height;

    public DistanceMap(int width, int height){
        this.distanceNodes = new double[height][width];
        this.width = width;
        this.height = height;
    }

    private double getDistanceFromNode(int x, int y){
        return this.distanceNodes[y][x];
    }

    public void setDistanceNode(int x, int y, double distance){
        this.distanceNodes[y][x] = distance;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
