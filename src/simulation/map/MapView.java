package simulation.map;

import com.sun.istack.internal.NotNull;
import model.entity.Agenda;
import simulation.map.world.TimeDisplay;
import simulation.map.world.World;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MapView {
    private ResizableCanvas canvas;
    private World world;
    private TimeDisplay timer;
    private double simulationSpeed;

    public MapView(@NotNull TileMap tileMap, @NotNull ResizableCanvas canvas, Agenda agenda) {
        this.canvas = canvas;
        this.simulationSpeed = 1.0;
//        if(tileMap != null) {
            this.world = new World(tileMap, canvas, this.simulationSpeed, agenda);
//        }
        this.timer = new TimeDisplay();
    }

    public void update(double deltaTime) {
        this.world.update(deltaTime);
        this.timer.addSeconds(deltaTime * this.simulationSpeed);
    }

    public void draw(FXGraphics2D graphics2D) {
        this.getFXGraphics2DContext().setColor(Color.DARK_GRAY);
        this.getFXGraphics2DContext().clearRect((int)this.getFXGraphics2DContext().getTransform().getTranslateX() - (int)this.canvas.getWidth(),
                                                (int)this.getFXGraphics2DContext().getTransform().getTranslateY() - (int)this.canvas.getWidth(),
                                                (int)this.canvas.getWidth() * 3,
                                                (int)this.canvas.getHeight() * 3);


        if(this.world.getTileMap() != null){
            AffineTransform originalTransform = graphics2D.getTransform();
            graphics2D.setTransform(this.world.getCamera().getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
            graphics2D.translate(-(this.canvas.getWidth() / 2), -(canvas.getHeight() / 2));

            this.world.draw(this.getFXGraphics2DContext());

            int pixelSizeTile = this.world.getTileMap().getLayers().get(0).getTilePixelSize();
//
//            //Draw Distances
//            this.getFXGraphics2DContext().setColor(Color.WHITE);
//            for (int y = 0; y < this.world.getPathMaps().get(0).getHeight(); y++) {
//                for (int x = 0; x < this.world.getPathMaps().get(0).getWidth(); x++) {
//                    double xPos = (x * pixelSizeTile) + (pixelSizeTile / 2);
//                    double yPos = (y * pixelSizeTile) + (pixelSizeTile / 2);
//                    graphics2D.drawString(Double.toString(this.world.getPathMaps().get(0).getNode(x, y).getDistance()), (int)xPos, (int)yPos);
//                }
//            }
//
//            //Draw Rotations
//            for (int y = 0; y < this.world.getPathMaps().get(0).getHeight(); y++) {
//                for (int x = 0; x < this.world.getPathMaps().get(0).getWidth(); x++) {
//                    double xPos = (x * pixelSizeTile) + (pixelSizeTile / 2);
//                    double yPos = (y * pixelSizeTile) + (pixelSizeTile / 2);
//                    graphics2D.setColor(Color.RED);
//                    graphics2D.draw(new Ellipse2D.Double(xPos - 1, yPos - 1, 2, 2));
//                    graphics2D.setColor(Color.BLUE);
//                    graphics2D.drawLine((int)xPos, (int)yPos,
//                            (int)(xPos + (pixelSizeTile / 2) * Math.cos(this.world.getPathMaps().get(0).getNode(x, y).getRotation())),
//                            (int)(yPos + (pixelSizeTile / 2) * Math.sin(this.world.getPathMaps().get(0).getNode(x, y).getRotation())));
//                }
//            }

            graphics2D.setTransform(originalTransform);
        }
        else
        {
            this.getFXGraphics2DContext().setColor(Color.WHITE);
//            graphics2D.drawString("No map loaded", (int)((this.canvas.getWidth() / 2) - this.world.getCamera().getCenterPoint().getX()),
//                                                        (int)((this.canvas.getHeight() / 2) - this.world.getCamera().getCenterPoint().getY()));
            graphics2D.drawString("No map loaded", (int)((this.canvas.getWidth() / 2)), (int)((this.canvas.getHeight() / 2)));
        }

        this.getFXGraphics2DContext().setColor(Color.WHITE);
        this.getFXGraphics2DContext().setFont(new Font(null, Font.BOLD, 15));
        graphics2D.drawString(this.timer.toString(), (int)(this.canvas.getTranslateX() + 10), (int)(this.canvas.getTranslateY() + 20));
    }

    private FXGraphics2D getFXGraphics2DContext(){
        return new FXGraphics2D(this.canvas.getGraphicsContext2D());
    }

    public void setTileMap(TileMap tileMap) {
        this.world.setTileMap(tileMap);
    }
}
