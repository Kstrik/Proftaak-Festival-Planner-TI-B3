package model.map;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.ai.entities.SimulationCharacter;
import model.map.world.World;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera {
	private Point2D centerPoint = new Point2D.Double(0,0);
	private double zoom = 1;
	private double rotation = 0;
	private Point2D lastMousePos;
	private Canvas canvas;
	private Resizable resizable;
	private FXGraphics2D g2d;
    private World world;

	public Camera(Canvas canvas, Resizable resizable, FXGraphics2D g2d, World world) {
		this.canvas = canvas;
		this.resizable = resizable;
		this.g2d = g2d;

		this.world = world;

		canvas.setOnMousePressed(e -> mousePressed(e));
		canvas.setOnMouseReleased(e -> mouseReleased(e));
		canvas.setOnMouseDragged(e -> mouseDragged(e));
		canvas.setOnScroll(e-> mouseScroll(e));
	}

	public void reset(){
	    this.zoom = 1;
	    this.rotation = 0;
	    this.lastMousePos = null;
	    this.centerPoint = new Point2D.Double(0,0);
    }

	public AffineTransform getTransform(int windowWidth, int windowHeight) {
		AffineTransform tx = new AffineTransform();
		tx.translate(windowWidth/2, windowHeight/2);
		tx.scale(zoom, zoom);
		tx.translate(centerPoint.getX(), centerPoint.getY());
		tx.rotate(rotation);
		return tx;
	}

	public void mouseDragged(MouseEvent e) {
		if(e.getButton() == MouseButton.SECONDARY) {
			centerPoint = new Point2D.Double(
					centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
					centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
			);
			lastMousePos = new Point2D.Double(e.getX(), e.getY());
			resizable.draw(g2d);

			this.canvas.setCursor(Cursor.MOVE);
		}
	}

	public void mouseScroll(ScrollEvent e) {
		zoom *= (1 + e.getDeltaY()/250.0f);
		resizable.draw(g2d);
	}

	public void mousePressed(MouseEvent e) {
		lastMousePos = new Point2D.Double(e.getX(), e.getY());
        if(e.getButton() == MouseButton.PRIMARY && this.world != null) {
        	for(int i = 0; i < 100; i++){
				this.world.addCharacter(new SimulationCharacter(new Point2D.Double(
						e.getX() - this.centerPoint.getX(),
						e.getY() - this.centerPoint.getY()),
						Math.toRadians(45), 1.0f,
						this.world.tileMap.getCollisionLayer().getTile(0,0).getTilePixelSize(),
						this.world.tileMap.getCollisionLayer().getTile(0,0).getTilePixelSize() / 4));
			}

//			double distance = Math.sqrt(Math.pow(this.canvas.getGraphicsContext2D().getTransform().getTx(), 2) +
//											Math.pow(this.canvas.getGraphicsContext2D().getTransform().getTy(), 2));
//
//			this.world.addCharacter(new SimulationCharacter(new Point2D.Double(
//					e.getX() - this.centerPoint.getX() + this.canvas.getGraphicsContext2D().getTransform().getTx(),
//					e.getY() - this.centerPoint.getY() + this.canvas.getGraphicsContext2D().getTransform().getTy()),
//					Math.toRadians(45), 1.0f, 100,
//					this.world.tileMap.getCollisionLayer().getTile(0,0).getTilePixelSize() / 4));
        }
	}

	public void mouseReleased(MouseEvent e) {
		this.canvas.setCursor(Cursor.DEFAULT);
	}

	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

	public double getZoom() {
		return this.zoom;
	}

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
