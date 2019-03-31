package simulation.map;

import java.awt.image.BufferedImage;

public class TileSet {
    private BufferedImage image;
    private int firstGlobalId;
    private int columnCount;
    private int tileWidth;

    public TileSet(BufferedImage image, int firstGlobalId, int columnCount, int tileWidth){
        this.image = image;
        this.firstGlobalId = firstGlobalId;
        this.columnCount = columnCount;
        this.tileWidth = tileWidth;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getFirstGlobalId() {
        return this.firstGlobalId;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }
}
