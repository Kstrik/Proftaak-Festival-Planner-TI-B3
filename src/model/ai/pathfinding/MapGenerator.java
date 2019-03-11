package model.ai.pathfinding;

import java.awt.geom.Point2D;

public class MapGenerator {
    public static DistanceMap generateDistanceMap(int width, int height, Point2D destiationPoint){
        DistanceMap distanceMap = new DistanceMap(width, height);
        return distanceMap;
    }

    public static RotationMap generateRotationMap(DistanceMap distanceMap){
        RotationMap rotationMap = new RotationMap(distanceMap.getWidth(), distanceMap.getHeight());
        return rotationMap;
    }
}
