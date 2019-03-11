package model.map.world;

import com.sun.istack.internal.NotNull;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_DIFFERENCEPeer;
import model.ai.entities.SimulationCharacter;
import model.map.Tile;
import model.map.TileMap;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class World {
    private ArrayList<SimulationCharacter> characters;
    public TileMap tileMap;

    public World(TileMap tileMap){
        this.characters = new ArrayList<SimulationCharacter>();
        this.tileMap = tileMap;
    }

    public void update(double deltaTime){
        for(SimulationCharacter character : this.characters){
            checkCollisionCharacterWithCharacters(character);
            checkCollisionCharacterWithMap(character);
            character.update(deltaTime);
        }
    }

    private void checkCollisionCharacterWithMap(SimulationCharacter character){
        if(this.tileMap.getCollisionLayer() != null){
            for(int i = 0; i < 4; i++) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.translate(character.getPosition().getX(), character.getPosition().getY());
                affineTransform.rotate(Math.toRadians(i * 90));
                affineTransform.translate(0, character.getCollisionRadius());
                Point2D angledPoint = new Point2D.Double(affineTransform.getTranslateX(), affineTransform.getTranslateY());

                Tile tile = this.tileMap.getCollisionLayer().getTileWithinCoordinates(angledPoint);

                if(tile != null && tile.isColliding(angledPoint)) {
                    character.setDirectionAngle(character.getDirectionAngle() + Math.toRadians(90));

//                    Point2D difference = new Point2D.Double(character.getPosition().getX() - tile.getCenterPoint().getX(), character.getPosition().getY() - tile.getCenterPoint().getY());
//                    double magnitute = Math.sqrt(Math.pow(difference.getX(), 2) + Math.pow(difference.getY(), 2));
//                    Point2D direction = new Point2D.Double(difference.getX() / magnitute, difference.getY() / magnitute);
//                    double moveDistance = magnitute - (character.getCollisionRadius() + ((tile.getTilePixelSize() / 2) + 8));
//
//                    character.setPosition(new Point2D.Double(character.getPosition().getX() + (-direction.getX() * moveDistance),
//                            character.getPosition().getY() + (-direction.getY() * moveDistance)));

                    double moveDistance = character.getCollisionRadius() + (tile.getTilePixelSize() / 2) + 2;

                    switch(i){
                        case 0:{
                            character.setPosition(new Point2D.Double(character.getPosition().getX(),
                                    tile.getCenterPoint().getY() - moveDistance));
                           break;
                        }
                        case 1:{
                            character.setPosition(new Point2D.Double(tile.getCenterPoint().getX() + moveDistance,
                                    character.getPosition().getY()));
                            break;
                        }
                        case 2:{
                            character.setPosition(new Point2D.Double(character.getPosition().getX(),
                                    tile.getCenterPoint().getY() + moveDistance));
                            break;
                        }
                        case 3:{
                            character.setPosition(new Point2D.Double(tile.getCenterPoint().getX() - moveDistance,
                                    character.getPosition().getY()));
                            break;
                        }
                    }
                }
            }
        }
    }

    private void checkCollisionCharacterWithCharacters(SimulationCharacter character){
        for(SimulationCharacter simulationCharacter : this.characters){
            if(simulationCharacter != character){
                if(character.getPosition().distance(simulationCharacter.getPosition()) < character.getCollisionRadius() + simulationCharacter.getCollisionRadius()){
                    character.setDirectionAngle(character.getDirectionAngle() + Math.toRadians(90));

                    Point2D difference = new Point2D.Double(character.getPosition().getX() - simulationCharacter.getPosition().getX(), character.getPosition().getY() - simulationCharacter.getPosition().getY());
                    double magnitute = Math.sqrt(Math.pow(difference.getX(), 2) + Math.pow(difference.getY(), 2));
                    Point2D direction = new Point2D.Double(difference.getX() / magnitute, difference.getY() / magnitute);
                    double moveDistance = magnitute - (character.getCollisionRadius() + simulationCharacter.getCollisionRadius());

                    character.setPosition(new Point2D.Double(character.getPosition().getX() + (-direction.getX() * moveDistance),
                                                            character.getPosition().getY() + (-direction.getY() * moveDistance)));
                }
            }
        }
    }

    public void draw(FXGraphics2D graphics2D){
        for(SimulationCharacter character : this.characters){
            character.draw(graphics2D);

            for(int i = 0; i < 4; i++) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.translate(character.getPosition().getX(), character.getPosition().getY());
                affineTransform.rotate(Math.toRadians(i * 90));
                affineTransform.translate(0, character.getCollisionRadius());
                Point2D angledPoint = new Point2D.Double(affineTransform.getTranslateX(), affineTransform.getTranslateY());
                graphics2D.setColor(Color.RED);
                graphics2D.draw(new Ellipse2D.Double(angledPoint.getX() - (2.5 * character.getScale()),
                                                        angledPoint.getY() - (2.5 * character.getScale()),
                                                        5 * character.getScale(),
                                                        5 * character.getScale()));
            }
        }
    }

    public void addCharacter(@NotNull SimulationCharacter character){
        this.characters.add(character);
    }

    public void addCharctaers(@NotNull ArrayList<SimulationCharacter> characters){
        this.characters.addAll(characters);
    }
}
