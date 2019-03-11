package model.ai.entities;

import com.sun.istack.internal.NotNull;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class SimulationCharacter
{
    private Point2D position;
    private double directionAngle;

    private float scale;
    private float movementSpeed;

    private float collisionRadius;

    public SimulationCharacter(@NotNull Point2D position, @NotNull double directionAngle, @NotNull float scale, @NotNull float movementSpeed, @NotNull float collisionRadius) {
        this.position = position;
        this.directionAngle = directionAngle;
        this.scale = scale;
        this.movementSpeed = movementSpeed;
        this.collisionRadius = collisionRadius;
    }

    public void update(double deltaTime) {
        this.position = new Point2D.Double(this.position.getX() + (this.movementSpeed * deltaTime) * Math.cos(directionAngle),
                                            this.position.getY() + (this.movementSpeed * deltaTime) * Math.sin(directionAngle));
    }

    public void draw(FXGraphics2D graphics2D) {
        graphics2D.setColor(Color.BLUE);
        graphics2D.draw(new Ellipse2D.Double(this.position.getX() - (collisionRadius * this.scale),
                this.position.getY() - (collisionRadius * this.scale),
                (collisionRadius * 2) * this.scale,
                (collisionRadius * 2) * this.scale));
    }

    public AffineTransform getTransform() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(this.position.getX(), this.position.getY());
        affineTransform.setToScale(this.scale, this.scale);
        return affineTransform;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public double getDirectionAngle() {
        return this.directionAngle;
    }

    public float getScale() {
        return this.scale;
    }

    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    public float getCollisionRadius() {
        return this.collisionRadius;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setDirectionAngle(double directionAngle) {
        this.directionAngle = directionAngle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setCollisionRadius(float collisionRadius) {
        this.collisionRadius = collisionRadius;
    }
}
