package simulation.ai.pathfinding;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;

public class PathMap {
    private PathNode[][] pathNodes;
    private int width;
    private int height;

    public PathMap(int width, int height){
        this.pathNodes = new PathNode[height][width];
        this.width = width;
        this.height = height;
        initialize();
    }

    private void initialize(){
        for (int y = 0; y < this.width; y++) {
            for (int x = 0; x < this.height; x++) {
                this.pathNodes[y][x] = new PathNode(new Point2D.Double(x, y));
            }
        }
    }

    public PathNode getNode(int x, int y){
        return this.pathNodes[y][x];
    }

    public PathNode getNodeWithinCoordinates(Point2D coördinates, int tilePixelSize) {
        if(coördinates.getX() >= 0 && coördinates.getX() <= this.width * tilePixelSize && coördinates.getY() >= 0 && coördinates.getY() <= this.height * tilePixelSize){
            return this.pathNodes[(int)Math.floor(coördinates.getY() / tilePixelSize)][(int)Math.floor(coördinates.getX() / tilePixelSize)];
        }
        else{
            return null;
        }
    }

    public Queue<PathNode> getConnectedNeighbours(PathNode pathNode){
        Queue<PathNode> neighbours = new LinkedList<PathNode>();

        Point2D[] neigbouringPoints = {new Point2D.Double(0, -1), new Point2D.Double(1, 0), new Point2D.Double(0, 1), new Point2D.Double(-1, 0)};

        for (int i = 0; i < neigbouringPoints.length; i++) {
            int xIndex = (int)(pathNode.getIndexing().getX() + neigbouringPoints[i].getX());
            int yIndex = (int)(pathNode.getIndexing().getY() + neigbouringPoints[i].getY());

            if(xIndex >= 0 && xIndex < this.width && yIndex >= 0 && yIndex < this.height){
                neighbours.add(this.pathNodes[yIndex][xIndex]);
            }
        }

        return neighbours;
    }

    public Queue<PathNode> getAllNeighbours(PathNode pathNode){
        Queue<PathNode> neighbours = new LinkedList<PathNode>();

        Point2D[] neigbouringPoints = {new Point2D.Double(0, -1), new Point2D.Double(1, -1),
                                        new Point2D.Double(1, 0), new Point2D.Double(1, 1),
                                        new Point2D.Double(0, 1), new Point2D.Double(-1, 1),
                                        new Point2D.Double(-1, 0), new Point2D.Double(-1, -1)};

        for (int i = 0; i < neigbouringPoints.length; i++) {
            int xIndex = (int)(pathNode.getIndexing().getX() + neigbouringPoints[i].getX());
            int yIndex = (int)(pathNode.getIndexing().getY() + neigbouringPoints[i].getY());

            if(xIndex >= 0 && xIndex < this.width && yIndex >= 0 && yIndex < this.height){
                neighbours.add(this.pathNodes[yIndex][xIndex]);
            }
        }

        return neighbours;
    }

    public void drawDistances(FXGraphics2D graphics2D, int tilePixelSize) {
        graphics2D.setColor(Color.WHITE);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                double xPos = (x * tilePixelSize) + (tilePixelSize / 2);
                double yPos = (y * tilePixelSize) + (tilePixelSize / 2);
                graphics2D.drawString(Double.toString(this.pathNodes[y][x].getDistance()), (int)xPos, (int)yPos);
            }
        }
    }

    public void drawRotations(FXGraphics2D graphics2D, int tilePixelSize) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                double xPos = (x * tilePixelSize) + (tilePixelSize / 2);
                double yPos = (y * tilePixelSize) + (tilePixelSize / 2);
                graphics2D.setColor(Color.RED);
                graphics2D.draw(new Ellipse2D.Double(xPos - 1, yPos - 1, 2, 2));
                graphics2D.setColor(Color.BLUE);
                graphics2D.drawLine((int)xPos, (int)yPos,
                        (int)(xPos + (tilePixelSize / 2) * Math.cos(this.pathNodes[y][x].getRotation())),
                        (int)(yPos + (tilePixelSize / 2) * Math.sin(this.pathNodes[y][x].getRotation())));
            }
        }
    }

    public void drawIndexes(FXGraphics2D graphics2D, int tilePixelSize) {
        graphics2D.setColor(Color.WHITE);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                double xPos = (x * tilePixelSize) + (tilePixelSize / 2);
                double yPos = (y * tilePixelSize) + (tilePixelSize / 2);
                graphics2D.drawString("{" + x + ":" + y + "}", (int)xPos, (int)yPos);
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getDistanceFromNode(int x, int y){
        return this.pathNodes[y][x].getDistance();
    }

    public double getRotationFromNode(int x, int y){
        return this.pathNodes[y][x].getRotation();
    }

    public void setDistanceNode(int x, int y, double distance){
        this.pathNodes[y][x].setDistance(distance);
    }

    public void setRotationNode(int x, int y, double rotation){
        this.pathNodes[y][x].setRotation(rotation);
    }
}
