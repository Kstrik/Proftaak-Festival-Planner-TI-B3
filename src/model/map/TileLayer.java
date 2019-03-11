package model.map;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TileLayer {
    private Tile[][] tiles;
    private int tilePixelSize;
    private int pixelWidth;
    private int pixelHeight;

    public TileLayer(Tile[][] tiles, int tilePixelSize) {
        this.tiles = tiles;
        this.tilePixelSize = tilePixelSize;
        this.pixelWidth = tiles[0].length * tilePixelSize;
        this.pixelHeight = tiles.length * tilePixelSize;
    }

    public void draw(FXGraphics2D graphics){
        for(Tile[] tiles : this.tiles) {
            for(Tile tile : tiles){
                tile.draw(graphics);
            }
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public Tile getTile(int xIndex, int yIndex){
        if(xIndex >= 0 && xIndex < this.tiles[0].length && yIndex >= 0 && yIndex < this.tiles.length) {
            return this.tiles[yIndex][xIndex];
        }
        else{
            return null;
        }
    }

    public Tile getTileWithinCoordinates(Point2D coördinates) {
        if(coördinates.getX() >= 0 && coördinates.getX() <= this.pixelWidth && coördinates.getY() >= 0 && coördinates.getY() <= this.pixelHeight){
            return this.tiles[(int)Math.floor(coördinates.getY() / this.tilePixelSize)][(int)Math.floor(coördinates.getX() / this.tilePixelSize)];
        }
        else{
            return null;
        }
    }
}
