package simulation.ai.pathfinding;

import simulation.map.TileLayer;

import java.awt.geom.Point2D;
import java.util.*;

public class MapGenerator {
    public static PathMap generatePathMap(int width, int height, Point2D destinationPoint, TileLayer collisionLayer){
        PathMap pathMap = new PathMap(width, height);

        Queue<PathNode> openNodes = new LinkedList<PathNode>();
        Set<PathNode> closedNodes = new HashSet<PathNode>();
        if(collisionLayer != null){
            closedNodes.addAll(getCollisionNodes(pathMap, collisionLayer));
        }

        destinationPoint = new Point2D.Double(Math.floor(destinationPoint.getX() / collisionLayer.getTilePixelSize()), Math.floor(destinationPoint.getY() / collisionLayer.getTilePixelSize()));

        PathNode startNode = pathMap.getNode((int)destinationPoint.getX(), (int)destinationPoint.getY());
        startNode.setDistance(0);
        openNodes.add(startNode);

        while(!openNodes.isEmpty()){
            PathNode currentNode = openNodes.poll();
            for(PathNode pathNode : pathMap.getConnectedNeighbours(currentNode)){
                if(!closedNodes.contains(pathNode) && !openNodes.contains(pathNode)){
                    pathNode.setDistance(currentNode.getDistance() + 1);
                    openNodes.add(pathNode);
                }
            }
            closedNodes.add(currentNode);
        }

        for(int y = 0; y < pathMap.getHeight(); y++){
            for(int x = 0; x < pathMap.getWidth(); x++){
                PathNode pathNode = pathMap.getNode(x, y);

                PathNode fastest = null;

                for(PathNode node : pathMap.getAllNeighbours(pathNode)){
                    if(collisionLayer != null && collisionLayer.getTile((int)node.getIndexing().getX(), (int)node.getIndexing().getY()).isCollidable()){
                        continue;
                    }
                    if(fastest == null) {
                        fastest = node;

                    }
                    else if(fastest != null && node.getDistance() < fastest.getDistance()){
                        fastest = node;
                    }
                }

                if(fastest != null){
                    pathNode.setRotation(Math.atan2((fastest.getIndexing().getY() - pathNode.getIndexing().getY()), (fastest.getIndexing().getX() - pathNode.getIndexing().getX())));
                    if(pathNode.getDistance() == 0){
                        pathNode.setRotation(Math.toRadians(90));
                    }
                }
            }
        }

        return pathMap;
    }

    private static Set<PathNode> getCollisionNodes(PathMap pathMap, TileLayer collisionLayer){
        Set<PathNode> collisionNodes = new HashSet<PathNode>();

        for(int y = 0; y < pathMap.getHeight(); y++){
            for(int x = 0; x < pathMap.getWidth(); x++){
                if(collisionLayer.getTile(x, y).isCollidable()){
                    collisionNodes.add(pathMap.getNode(x, y));
                }
            }
        }

        return collisionNodes;
    }
}
