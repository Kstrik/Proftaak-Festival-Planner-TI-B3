package model.map;

import com.sun.istack.internal.NotNull;
import model.map.world.World;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MapView {
    private TileMap tileMap;
    private ResizableCanvas canvas;
    private Camera camera;
    private World world;

    public MapView(@NotNull TileMap tileMap, @NotNull ResizableCanvas canvas) {
        this.canvas = canvas;
        if(tileMap != null) {
            this.tileMap = tileMap;
            this.world = new World(tileMap);
        }
        this.camera = new Camera(this.canvas, g -> draw(g), this.getFXGraphics2DContext(), this.world);
    }

    public void update(double deltaTime) {
        if(this.tileMap != null){
            this.world.update(deltaTime);
        }
    }

    public void resetMapTranformation() {
        this.camera.reset();
    }

    public void draw(FXGraphics2D graphics2D) {
        this.getFXGraphics2DContext().setColor(Color.DARK_GRAY);
        this.getFXGraphics2DContext().clearRect((int)this.getFXGraphics2DContext().getTransform().getTranslateX() - (int)this.canvas.getWidth(),
                                                (int)this.getFXGraphics2DContext().getTransform().getTranslateY() - (int)this.canvas.getWidth(),
                                                (int)this.canvas.getWidth() * 3,
                                                (int)this.canvas.getHeight() * 3);

        AffineTransform originalTransform = graphics2D.getTransform();
        graphics2D.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics2D.translate(-(this.canvas.getWidth() / 2), -(canvas.getHeight() / 2));

        if(this.tileMap != null){
            this.tileMap.draw(this.getFXGraphics2DContext());
            this.world.draw(this.getFXGraphics2DContext());
        }
        else
        {
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("No map loaded", (int)canvas.getWidth() / 2, (int)canvas.getHeight() / 2);
        }

        graphics2D.setTransform(originalTransform);
    }

    private FXGraphics2D getFXGraphics2DContext(){
        return new FXGraphics2D(this.canvas.getGraphicsContext2D());
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
        if(tileMap != null) {
            this.world = new World(tileMap);
        }
        else{
            this.world = null;
        }

        this.camera.setWorld(this.world);
        this.resetMapTranformation();
    }
}
