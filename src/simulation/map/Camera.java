package simulation.map;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import simulation.ai.entities.SimulationCharacter;
import simulation.map.world.World;
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

	private Point2D getTransformedCursorPosition(Point2D cursorPosition)
	{
		AffineTransform tx = new AffineTransform();
		tx.translate((this.canvas.getWidth()/2) + cursorPosition.getX(), (this.canvas.getHeight()/2) + cursorPosition.getY());
		tx.scale(zoom, zoom);
		tx.translate(centerPoint.getX(), centerPoint.getY());
		tx.rotate(rotation);

		return new Point2D.Double(tx.getTranslateX(), tx.getTranslateY());
	}

	public void mouseScroll(ScrollEvent e) {
		zoom *= (1 + e.getDeltaY()/250.0f);
		resizable.draw(g2d);
	}

	public void mousePressed(MouseEvent e) {
		lastMousePos = new Point2D.Double(e.getX(), e.getY());
        if(e.getButton() == MouseButton.PRIMARY && this.world != null) {
//			AffineTransform tx = new AffineTransform();
//			tx.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);
//			tx.scale(this.zoom, this.zoom);
//			tx.translate(-((this.canvas.getWidth()/2) - (e.getX() * this.zoom)), -((this.canvas.getHeight()/2) - (e.getY() * this.zoom)));
//			//tx.translate(-this.centerPoint.getX() + (e.getX() * this.zoom), -this.centerPoint.getY() + (e.getY() * this.zoom));
//			tx.translate(-this.centerPoint.getX(), -this.centerPoint.getY());

//			Point2D cursorPosition = getTransformedCursorPosition(new Point2D.Double(e.getX(), e.getY()));

//            Point2D cursorPosition = new Point2D.Double(e.getX(), e.getY());

//			SimulationCharacter simulationCharacter = new SimulationCharacter(new Point2D.Double(
//					e.getX() - this.centerPoint.getX(),
//					e.getY() - this.centerPoint.getY()),
//					Math.toRadians(45), 1.0f,
//					this.world.getTileMap().getLayers().get(0).getTile(0,0).getTilePixelSize() * 2,
//					this.world.getTileMap().getLayers().get(0).getTile(0,0).getTilePixelSize() * 2,
//					this.world.getTileMap().getLayers().get(0).getTile(0,0).getTilePixelSize() / 2, this.world);

//			SimulationCharacter simulationCharacter = new SimulationCharacter(cursorPosition,
//					Math.toRadians(45), 1.0f,
//					this.world.getTileMap().getLayers().get(0).getTile(0,0).getTilePixelSize() * 2,
//					this.world.getTileMap().getLayers().get(0).getTile(0,0).getTilePixelSize() * 2,
//					this.world.getTileMap().getLayers().get(0).getTile(0,0).getTilePixelSize() / 2, this.world);
//
//			simulationCharacter.getAIComponent().setActivePathfinderMap(this.world.getPathMaps().get(0));
//			this.world.addCharacter(simulationCharacter);
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
