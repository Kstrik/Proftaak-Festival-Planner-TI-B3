package simulation.map;

import simulation.map.world.Room;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class TileMap {
    private ArrayList<TileLayer> layers;
    private TileLayer collisionLayer;
    private HashMap<String, Room> rooms;
    private Point2D spawnPoint;

    public TileMap() {
        this.layers = new ArrayList<TileLayer>();
        this.rooms = new HashMap<String, Room>();
        this.spawnPoint = null;
    }

    public void addLayer(TileLayer tileLayer){
        this.layers.add(tileLayer);
    }

    public void draw(FXGraphics2D graphics){
        if(!this.layers.isEmpty()) {
            for(TileLayer tileLayer : this.layers){
                tileLayer.draw(graphics);
            }
        }
        drawCollisionLayer(graphics);
    }

    public void drawCollisionLayer(FXGraphics2D graphics){
        if(this.collisionLayer != null) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            this.collisionLayer.draw(graphics);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    public void toggleCollisionVisibility() {
        if(this.collisionLayer != null) {
            for(Tile[] tiles : this.collisionLayer.getTiles()) {
                for(Tile tile : tiles){
                    tile.setIsVisible(!tile.isVisible());
                }
            }
        }
    }

    public ArrayList<TileLayer> getLayers() {
        return this.layers;
    }

    public TileLayer getCollisionLayer() {
        return this.collisionLayer;
    }

    public HashMap<String, Room> getRooms() {
        return this.rooms;
    }

    public Point2D getSpawnPoint() {
        return this.spawnPoint;
    }

    public void setLayers(ArrayList<TileLayer> layers) {
        this.layers = layers;
    }

    public void setCollisionLayerLayer(TileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public void setSpawnPoint(Point2D spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}
