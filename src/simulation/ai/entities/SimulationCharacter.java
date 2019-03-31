package simulation.ai.entities;

import animation.animations.SpriteAnimation;
import animation.statelogic.AnimationConstraint;
import animation.statelogic.AnimationManager;
import animation.statelogic.SpriteAnimationState;
import animation.statelogic.constraintvalues.RotationConstraintValue;
import animation.statelogic.transitionvalues.RotationClamp;
import com.sun.istack.internal.NotNull;
import model.entity.Agenda;
import model.entity.Classroom;
import model.entity.Item;
import simulation.ai.pathfinding.PathMap;
import simulation.ai.pathfinding.Pathfinder;
import simulation.map.world.Room;
import simulation.map.world.World;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulationCharacter implements Comparable
{
    private Point2D position;
    private double directionAngle;
    private float scale;
    private float maxMovementSpeed;
    private float movementSpeed;
    private float collisionRadius;
    private World world;
    private AIComponent aiComponent;

    //Animation
    private RotationConstraintValue rotationConstraintValue;
    private AnimationManager animationManager;

    public SimulationCharacter(@NotNull Point2D position, @NotNull double directionAngle, @NotNull float scale, @NotNull float maxMovementSpeed, @NotNull float movementSpeed, @NotNull float collisionRadius, @NotNull World world,
                               HashMap<String, PathMap> pathMaps, HashMap<String, Room> rooms, Agenda agends, ArrayList<Item> scheduleItems, boolean isTeacher) {
        this.position = position;
        this.directionAngle = directionAngle;
        this.scale = scale;
        this.maxMovementSpeed = maxMovementSpeed;
        this.movementSpeed = movementSpeed;
        this.collisionRadius = collisionRadius;
        this.world = world;
        if(this.world != null){
            this.aiComponent = new AIComponent(this, new Pathfinder(this, world.getTileMap().getLayers().get(0).getTilePixelSize()), pathMaps, rooms,
                    agends, scheduleItems, isTeacher);
        }
        this.rotationConstraintValue = new RotationConstraintValue(this.directionAngle);
        this.setupAnimationManager();
    }

    public void update(double deltaTime) {
        if(this.aiComponent != null){
            this.aiComponent.update(deltaTime);
        }
        this.position = new Point2D.Double(this.position.getX() + (this.movementSpeed * deltaTime) * Math.cos(this.directionAngle),
                                            this.position.getY() + (this.movementSpeed * deltaTime) * Math.sin(this.directionAngle));
        this.animationManager.update(deltaTime);
    }

    public void draw(FXGraphics2D graphics2D) {
//        //Draw collision boundry
//        graphics2D.setColor(Color.BLUE);
//        graphics2D.draw(new Ellipse2D.Double(this.position.getX() - (this.collisionRadius * this.scale),
//                this.position.getY() - (this.collisionRadius * this.scale),
//                (this.collisionRadius * 2) * this.scale,
//                (this.collisionRadius * 2) * this.scale));
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        graphics2D.setColor(Color.BLACK);
        Ellipse2D ellipse2D = new Ellipse2D.Double(this.position.getX() - (this.collisionRadius * this.scale),
                this.position.getY() - (this.collisionRadius * this.scale),
                (this.collisionRadius * 2) * this.scale,
                (this.collisionRadius * 2) * this.scale);
        graphics2D.fill(ellipse2D);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        BufferedImage image = this.animationManager.getAnimationStateMachine().getLastFrame();
        double width = (this.collisionRadius * 2);
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double height = (imageHeight / 100) * (width / (imageWidth / 100));
        graphics2D.drawImage(image,(int)(this.position.getX() - ((width * this.scale) / 2)), (int)(this.position.getY() - ((height * this.scale) - (this.collisionRadius * this.scale))), (int)(width * this.scale), (int)(height * this.scale), null);
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

    public float getMaxMovementSpeed() {
        return this.maxMovementSpeed;
    }

    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    public float getCollisionRadius() {
        return this.collisionRadius;
    }

    public AIComponent getAIComponent() {
        return this.aiComponent;
    }

    public AnimationManager getAnimationManager() {
        return this.animationManager;
    }

    public World getWorld() {
        return this.world;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setDirectionAngle(double directionAngle) {
        this.directionAngle = directionAngle;
        this.rotationConstraintValue.setValue(directionAngle);
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

//    private void setupAnimationManager(){
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File("src/resources/images/pokemon_sprites2.0.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        SpriteAnimation idleAnimation =                         new SpriteAnimation(1.0f, image, 6, 8, 18, 18, true);
//        SpriteAnimation walkLeftAnimation =                     new SpriteAnimation(1.0f, image, 6, 8, 6, 9, true);
//        SpriteAnimation walkTopAnimation =                      new SpriteAnimation(1.0f, image, 6, 8, 12, 15, true);
//        SpriteAnimation walkRightAnimation =                    new SpriteAnimation(1.0f, image, 6, 8, 0, 3, true);
//        SpriteAnimation walkDownAnimation =                     new SpriteAnimation(1.0f, image, 6, 8, 18, 21, true);
//
//        SpriteAnimationState idleState =                        new SpriteAnimationState(idleAnimation);
//        SpriteAnimationState walkLeftState =                    new SpriteAnimationState(walkLeftAnimation);
//        SpriteAnimationState walkTopState =                     new SpriteAnimationState(walkTopAnimation);
//        SpriteAnimationState walkRightState =                   new SpriteAnimationState(walkRightAnimation);
//        SpriteAnimationState walkDownState =                    new SpriteAnimationState(walkDownAnimation);
//
//        RotationClamp idleToWalkLeftClamp =                     new RotationClamp(157.5, -157.5, true, "Left");
//        RotationClamp idleToWalkTopClamp =                      new RotationClamp(-112.5, -67.5, true, "Top");
//        RotationClamp idleToWalkRightClamp =                    new RotationClamp(-22.5, 22.5, true, "Right");
//        RotationClamp idleToWalkDownClamp =                     new RotationClamp(67.5, 112.5, true, "Down");
//
//        RotationClamp walkLeftToIdleClamp =                     new RotationClamp(157.5, -157.5, false, "Left");
//        RotationClamp walkTopToIdleClamp =                      new RotationClamp(-112.5, -67.5, false, "Top");
//        RotationClamp walkRightToIdleClamp =                    new RotationClamp(-22.5, 22.5, false, "Right");
//        RotationClamp walkDownToIdleClamp =                     new RotationClamp(67.5, 112.5, false, "Down");
//
//        AnimationConstraint idleToWalkLeftConstraint =          new AnimationConstraint(idleState, walkLeftState, this.rotationConstraintValue, idleToWalkLeftClamp);
//        AnimationConstraint idleToWalkTopConstraint =           new AnimationConstraint(idleState, walkTopState, this.rotationConstraintValue, idleToWalkTopClamp);
//        AnimationConstraint idleToWalkRightConstraint =         new AnimationConstraint(idleState, walkRightState, this.rotationConstraintValue, idleToWalkRightClamp);
//        AnimationConstraint idleToWalkDownConstraint =          new AnimationConstraint(idleState, walkDownState, this.rotationConstraintValue, idleToWalkDownClamp);
//
//        AnimationConstraint walkLeftToIdleConstraint =          new AnimationConstraint(walkLeftState, idleState, this.rotationConstraintValue, walkLeftToIdleClamp);
//        AnimationConstraint walkTopToIdleConstraint =           new AnimationConstraint(walkTopState, idleState, this.rotationConstraintValue, walkTopToIdleClamp);
//        AnimationConstraint walkRightToIdleConstraint =         new AnimationConstraint(walkRightState, idleState, this.rotationConstraintValue, walkRightToIdleClamp);
//        AnimationConstraint walkDownToIdleConstraint =          new AnimationConstraint(walkDownState, idleState, this.rotationConstraintValue, walkDownToIdleClamp);
//
//        idleState.addConstraints(idleToWalkLeftConstraint, idleToWalkTopConstraint, idleToWalkRightConstraint, idleToWalkDownConstraint);
//        walkLeftState.addConstraint(walkLeftToIdleConstraint);
//        walkTopState.addConstraint(walkTopToIdleConstraint);
//        walkRightState.addConstraint(walkRightToIdleConstraint);
//        walkDownState.addConstraint(walkDownToIdleConstraint);
//
//        this.animationManager = new AnimationManager(image, idleState);
//        this.animationManager.addAnimationState("idle", idleState);
//        this.animationManager.addAnimationState("walkLeft", walkLeftState);
//        this.animationManager.addAnimationState("walkTop", walkTopState);
//        this.animationManager.addAnimationState("walkRight", walkRightState);
//        this.animationManager.addAnimationState("walkDown", walkDownState);
//    }

    private void setupAnimationManager(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/resources/images/walk_template.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SpriteAnimation idleAnimation =                         new SpriteAnimation(1.0f, image, 8, 8, 8, 16, true);
        SpriteAnimation walkLeftAnimation =                     new SpriteAnimation(1.0f, image, 8, 8, 24, 32, true);
        SpriteAnimation walkLeftTopAnimation =                  new SpriteAnimation(1.0f, image, 8, 8, 40, 48, true);
        SpriteAnimation walkTopAnimation =                      new SpriteAnimation(1.0f, image, 8, 8, 48, 56, true);
        SpriteAnimation walkRightTopAnimation =                 new SpriteAnimation(1.0f, image, 8, 8, 56, 64, true);
        SpriteAnimation walkRightAnimation =                    new SpriteAnimation(1.0f, image, 8, 8, 32, 40, true);
        SpriteAnimation walkRightDownAnimation =                new SpriteAnimation(1.0f, image, 8, 8, 16, 24, true);
        SpriteAnimation walkDownAnimation =                     new SpriteAnimation(1.0f, image, 8, 8, 8, 16, true);
        SpriteAnimation walkLeftDownAnimation =                 new SpriteAnimation(1.0f, image, 8, 8, 0, 8, true);

        SpriteAnimationState idleState =                        new SpriteAnimationState(idleAnimation);
        SpriteAnimationState walkLeftState =                    new SpriteAnimationState(walkLeftAnimation);
        SpriteAnimationState walkLeftTopState =                 new SpriteAnimationState(walkLeftTopAnimation);
        SpriteAnimationState walkTopState =                     new SpriteAnimationState(walkTopAnimation);
        SpriteAnimationState walkRightTopState =                new SpriteAnimationState(walkRightTopAnimation);
        SpriteAnimationState walkRightState =                   new SpriteAnimationState(walkRightAnimation);
        SpriteAnimationState walkRightDownState =               new SpriteAnimationState(walkRightDownAnimation);
        SpriteAnimationState walkDownState =                    new SpriteAnimationState(walkDownAnimation);
        SpriteAnimationState walkLeftDownState =                new SpriteAnimationState(walkLeftDownAnimation);

        RotationClamp idleToWalkLeftClamp =                     new RotationClamp(157.5, -157.5, true, "Left");
        RotationClamp idleToWalkLeftTopClamp =                  new RotationClamp(-157.5, -112.5, true, "LeftTop");
        RotationClamp idleToWalkTopClamp =                      new RotationClamp(-112.5, -67.5, true, "Top");
        RotationClamp idleToWalkRightTopClamp =                 new RotationClamp(-67.5, -22.5, true, "RightTop");
        RotationClamp idleToWalkRightClamp =                    new RotationClamp(-22.5, 22.5, true, "Right");
        RotationClamp idleToWalkRightDownClamp =                new RotationClamp(22.5, 67.5, true, "RightDown");
        RotationClamp idleToWalkDownClamp =                     new RotationClamp(67.5, 112.5, true, "Down");
        RotationClamp idleToWalkLeftDownClamp =                 new RotationClamp(112.5, 157.5, true, "LeftDown");

        RotationClamp walkLeftToIdleClamp =                     new RotationClamp(157.5, -157.5, false, "Left");
        RotationClamp walkLeftTopToIdleClamp =                  new RotationClamp(-157.5, -112.5, false, "LeftTop");
        RotationClamp walkTopToIdleClamp =                      new RotationClamp(-112.5, -67.5, false, "Top");
        RotationClamp walkRightTopToIdleClamp =                 new RotationClamp(-67.5, -22.5, false, "RightTop");
        RotationClamp walkRightToIdleClamp =                    new RotationClamp(-22.5, 22.5, false, "Right");
        RotationClamp walkRightDownToIdleClamp =                new RotationClamp(22.5, 67.5, false, "RightDown");
        RotationClamp walkDownToIdleClamp =                     new RotationClamp(67.5, 112.5, false, "Down");
        RotationClamp walkLeftDownToIdleClamp =                 new RotationClamp(112.5, 157.5, false, "LeftDown");

        AnimationConstraint idleToWalkLeftConstraint =          new AnimationConstraint(idleState, walkLeftState, this.rotationConstraintValue, idleToWalkLeftClamp);
        AnimationConstraint idleToWalkLeftTopConstraint =       new AnimationConstraint(idleState, walkLeftTopState, this.rotationConstraintValue, idleToWalkLeftTopClamp);
        AnimationConstraint idleToWalkTopConstraint =           new AnimationConstraint(idleState, walkTopState, this.rotationConstraintValue, idleToWalkTopClamp);
        AnimationConstraint idleToWalkRightTopConstraint =      new AnimationConstraint(idleState, walkRightTopState, this.rotationConstraintValue, idleToWalkRightTopClamp);
        AnimationConstraint idleToWalkRightConstraint =         new AnimationConstraint(idleState, walkRightState, this.rotationConstraintValue, idleToWalkRightClamp);
        AnimationConstraint idleToWalkRightDownConstraint =     new AnimationConstraint(idleState, walkRightDownState, this.rotationConstraintValue, idleToWalkRightDownClamp);
        AnimationConstraint idleToWalkDownConstraint =          new AnimationConstraint(idleState, walkDownState, this.rotationConstraintValue, idleToWalkDownClamp);
        AnimationConstraint idleToWalkLeftDownConstraint =      new AnimationConstraint(idleState, walkLeftDownState, this.rotationConstraintValue, idleToWalkLeftDownClamp);

        AnimationConstraint walkLeftToIdleConstraint =          new AnimationConstraint(walkLeftState, idleState, this.rotationConstraintValue, walkLeftToIdleClamp);
        AnimationConstraint walkLeftTopToIdleConstraint =       new AnimationConstraint(walkLeftTopState, idleState, this.rotationConstraintValue, walkLeftTopToIdleClamp);
        AnimationConstraint walkTopToIdleConstraint =           new AnimationConstraint(walkTopState, idleState, this.rotationConstraintValue, walkTopToIdleClamp);
        AnimationConstraint walkRightTopToIdleConstraint =      new AnimationConstraint(walkRightTopState, idleState, this.rotationConstraintValue, walkRightTopToIdleClamp);
        AnimationConstraint walkRightToIdleConstraint =         new AnimationConstraint(walkRightState, idleState, this.rotationConstraintValue, walkRightToIdleClamp);
        AnimationConstraint walkRightDownToIdleConstraint =     new AnimationConstraint(walkRightDownState, idleState, this.rotationConstraintValue, walkRightDownToIdleClamp);
        AnimationConstraint walkDownToIdleConstraint =          new AnimationConstraint(walkDownState, idleState, this.rotationConstraintValue, walkDownToIdleClamp);
        AnimationConstraint walkLeftDownToIdleConstraint =      new AnimationConstraint(walkLeftDownState, idleState, this.rotationConstraintValue, walkLeftDownToIdleClamp);

        idleState.addConstraints(idleToWalkLeftConstraint, idleToWalkLeftTopConstraint, idleToWalkTopConstraint, idleToWalkRightTopConstraint, idleToWalkRightConstraint, idleToWalkRightDownConstraint,
                                idleToWalkDownConstraint, idleToWalkLeftDownConstraint);
        walkLeftState.addConstraint(walkLeftToIdleConstraint);
        walkLeftTopState.addConstraint(walkLeftTopToIdleConstraint);
        walkTopState.addConstraint(walkTopToIdleConstraint);
        walkRightTopState.addConstraint(walkRightTopToIdleConstraint);
        walkRightState.addConstraint(walkRightToIdleConstraint);
        walkRightDownState.addConstraint(walkRightDownToIdleConstraint);
        walkDownState.addConstraint(walkDownToIdleConstraint);
        walkLeftDownState.addConstraint(walkLeftDownToIdleConstraint);

        this.animationManager = new AnimationManager(image, idleState);
        this.animationManager.addAnimationState("idle", idleState);
        this.animationManager.addAnimationState("walkLeft", walkLeftState);
        this.animationManager.addAnimationState("walkLeftTop", walkLeftTopState);
        this.animationManager.addAnimationState("walkTop", walkTopState);
        this.animationManager.addAnimationState("walkRightTop", walkRightTopState);
        this.animationManager.addAnimationState("walkRight", walkRightState);
        this.animationManager.addAnimationState("walkRightDown", walkRightDownState);
        this.animationManager.addAnimationState("walkDown", walkDownState);
        this.animationManager.addAnimationState("walkLeftDown", walkLeftDownState);
    }

    @Override
    public int compareTo(Object o) {
        if(this.position.getY() < ((SimulationCharacter)o).getPosition().getY())
            return -1;
        else if(this.position.getY() > ((SimulationCharacter)o).getPosition().getY())
            return 1;
        else
            return 0;
    }
}
