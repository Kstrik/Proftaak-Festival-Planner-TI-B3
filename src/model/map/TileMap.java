package model.map;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileMap {
    private ArrayList<TileLayer> layers;
    private TileLayer collisionLayer;

    public TileMap() {
        this.layers = new ArrayList<TileLayer>();
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
    }

    public void drawCollisionLayer(FXGraphics2D graphics){
        if(this.collisionLayer != null) {
            for(Tile[] tiles : this.collisionLayer.getTiles()) {
                for(Tile tile : tiles){
                    tile.draw(graphics);
                }
            }
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

    public void setLayers(ArrayList<TileLayer> layers) {
        this.layers = layers;
    }

    public void setCollisionLayerLayer(TileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
