package model.map;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private int x;
    private int y;
    private int tilePixelSize;
    private boolean isCollidable;
    private boolean isVisible;
    private Point2D centerPosition;

    public Tile(BufferedImage image, int x, int y, int tilePixelSize, boolean isCollidable, boolean isVisible) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.tilePixelSize = tilePixelSize;
        this.isCollidable = isCollidable;
        this.isVisible = isVisible;
        this.centerPosition = new Point2D.Double(x + (tilePixelSize / 2), y + (tilePixelSize / 2));
    }

    public boolean isColliding(Point2D point2D) {
        return (this.isCollidable) ? new Rectangle2D.Double(this.x, this.y, this.tilePixelSize, this.tilePixelSize).contains(point2D) : false;
    }

    public void draw(FXGraphics2D graphics){
        if(this.isVisible && this.image != null){
            graphics.drawImage(this.image, this.getX(), this.getY(), this.tilePixelSize, this.tilePixelSize, null);
        }
    }

    public Point2D getCenterPoint()
    {
        return new Point2D.Double(this.x + (this.tilePixelSize / 2), this.y + (this.tilePixelSize / 2));
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isCollidable() {
        return this.isCollidable;
    }

    public boolean isVisible(){
        return this.isVisible;
    }

    public int getTilePixelSize() {
        return this.tilePixelSize;
    }

    public void setIsVisible(boolean isVisible){
        this.isVisible = isVisible;
    }
}
