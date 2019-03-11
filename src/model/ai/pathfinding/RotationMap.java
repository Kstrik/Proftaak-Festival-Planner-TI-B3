package model.ai.pathfinding;

public class RotationMap {
    private double[][] rotationNodes;
    private int width;
    private int height;

    public RotationMap(int width, int height){
        this.rotationNodes = new double[height][width];
        this.width = width;
        this.height = height;
    }

    private double getRotationFromNode(int x, int y){
        return this.rotationNodes[y][x];
    }

    public void setRotationNode(int x, int y, double distance){
        this.rotationNodes[y][x] = distance;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
