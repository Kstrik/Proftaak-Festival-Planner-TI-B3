package simulation.map.world;

import com.sun.istack.internal.NotNull;
import model.entity.*;
import simulation.ai.entities.SimulationCharacter;
import simulation.ai.pathfinding.MapGenerator;
import simulation.ai.pathfinding.PathMap;
import simulation.map.Camera;
import simulation.map.Tile;
import simulation.map.TileMap;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class World {
    private ArrayList<SimulationCharacter> characters;
    private HashMap<String, PathMap> pathMaps;
    private TileMap tileMap;
    private Camera camera;
    private double simulationSpeed;

    private Agenda agenda;

    public World(TileMap tileMap, ResizableCanvas canvas, double simulationSpeed, Agenda agenda){
        this.characters = new ArrayList<SimulationCharacter>();
        this.tileMap = tileMap;
        this.pathMaps = new HashMap<String, PathMap>();
        this.camera = new Camera(canvas, g -> draw(g), new FXGraphics2D(canvas.getGraphicsContext2D()), this);
        this.simulationSpeed = simulationSpeed;
        this.agenda = agenda;
        this.setupPathmaps();
        this.setupCharacters();
    }

    private void setupPathmaps() {
        if(tileMap != null){
            for(Room room : this.tileMap.getRooms().values())
            {
                this.pathMaps.put(room.getName(), MapGenerator.generatePathMap(tileMap.getLayers().get(0).getWidthInTiles(), tileMap.getLayers().get(0).getHeightInTiles(),
                        room.getCenterPoint(), tileMap.getCollisionLayer()));
            }
        }
    }

    private void setupCharacters()
    {
        Random random = new Random();

        for(Group group : this.agenda.getAllGroups())
        {
            if(group.getSchedules().size() != 0)
            {
                Schedule schedule = group.getSchedules().get(0);

                for(Person person : group.getMembers())
                {
                    SimulationCharacter simulationCharacter = new SimulationCharacter(new Point2D.Double(this.tileMap.getSpawnPoint().getX(), this.tileMap.getSpawnPoint().getY()),
                            (Math.PI / 2) * 3, 1.0f,
                            this.tileMap.getLayers().get(0).getTile(0,0).getTilePixelSize() * 2,
                            this.tileMap.getLayers().get(0).getTile(0,0).getTilePixelSize() * 2,
                            (this.tileMap.getLayers().get(0).getTile(0,0).getTilePixelSize() / 4) * 1.5f, this, this.pathMaps, this.tileMap.getRooms(),
                            this.agenda, schedule.getItems(), person.isTeacher());

                    double angle = getRandomDirectionInRange(random);
                    simulationCharacter.setPosition(new Point2D.Double(this.tileMap.getSpawnPoint().getX() + getRandomLengthInRange(random) * Math.cos(angle),
                            this.tileMap.getSpawnPoint().getY() + getRandomLengthInRange(random) * Math.sin(angle)));
                    this.characters.add(simulationCharacter);
                }
            }
        }
    }

    protected double getRandomDirectionInRange(Random random)
    {
        double a = Math.PI / 2;
        double b = (Math.PI / 2) * 3;
        return (random.nextDouble() * (b - a) + a);
    }

    protected double getRandomLengthInRange(Random random)
    {
        double a = 2;
        double b = 40;
        return (random.nextDouble() * (b - a) + a);
    }

    public void update(double deltaTime){
        deltaTime *= this.simulationSpeed;

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

    public void reset() {
        this.camera.reset();
        this.characters.clear();
        this.pathMaps.clear();
    }

    public void draw(FXGraphics2D graphics2D){
        if(this.tileMap != null){
            this.tileMap.draw(graphics2D);
        }

        try{
            Collections.sort(this.characters);
        }
        catch (IllegalArgumentException e){ }

//        ((PathMap)this.pathMaps.values().toArray()[1]).drawDistances(graphics2D, this.tileMap.getCollisionLayer().getTilePixelSize());
//        ((PathMap)this.pathMaps.values().toArray()[1]).drawRotations(graphics2D, this.tileMap.getCollisionLayer().getTilePixelSize());
//        ((PathMap)this.pathMaps.values().toArray()[0]).drawIndexes(graphics2D, this.tileMap.getCollisionLayer().getTilePixelSize());

        for(SimulationCharacter character : this.characters){
            character.draw(graphics2D);

//            //Draw character map collision points
//            for(int i = 0; i < 4; i++) {
//                AffineTransform affineTransform = new AffineTransform();
//                affineTransform.translate(character.getPosition().getX(), character.getPosition().getY());
//                affineTransform.rotate(Math.toRadians(i * 90));
//                affineTransform.translate(0, character.getCollisionRadius());
//                Point2D angledPoint = new Point2D.Double(affineTransform.getTranslateX(), affineTransform.getTranslateY());
//                graphics2D.setColor(Color.RED);
//                graphics2D.draw(new Ellipse2D.Double(angledPoint.getX() - (2.5 * character.getScale()),
//                                                        angledPoint.getY() - (2.5 * character.getScale()),
//                                                        5 * character.getScale(),
//                                                        5 * character.getScale()));
//            }
        }

        //Draw rooms and seats
//        for(Room room : this.tileMap.getRooms().values()){
//            graphics2D.setColor(Color.BLUE);
//            graphics2D.fill(room.getShape());
//
//            if(room.getTeacherSeat() != null){
//                graphics2D.setColor(Color.ORANGE);
//                graphics2D.fill(room.getTeacherSeat().getShape());
//            }
//
//            graphics2D.setColor(Color.GREEN);
//
//            for(Seat seat : room.getStudentSeats()){
//                graphics2D.fill(seat.getShape());
//            }
//
//            graphics2D.setColor(Color.MAGENTA);
//            graphics2D.fill(new Ellipse2D.Double(room.getCenterPoint().getX() - 5, room.getCenterPoint().getY() - 5, 10, 10));
//        }
    }

    public void addCharacter(@NotNull SimulationCharacter character){
        this.characters.add(character);
    }

    public void addCharacters(@NotNull ArrayList<SimulationCharacter> characters){
        this.characters.addAll(characters);
    }

    public HashMap<String, PathMap> getPathMaps() {
        return this.pathMaps;
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public double getSimulationSpeed() {
        return this.simulationSpeed;
    }

    public void setTileMap(TileMap tileMap) {
        this.reset();
        this.tileMap = tileMap;

        this.pathMaps.clear();
        this.setupPathmaps();
//        this.pathMaps.add(MapGenerator.generatePathMap(tileMap.getLayers().get(0).getWidthInTiles(), tileMap.getLayers().get(0).getHeightInTiles(),
//                            new Point2D.Double(14, 10), tileMap.getCollisionLayer()));

//        this.pathMap = MapGenerator.generatePathMap(tileMap.getLayers().get(0).getWidthInTiles(), tileMap.getLayers().get(0).getHeightInTiles(),
//                new Point2D.Double(14, 10), tileMap.getCollisionLayer());
    }

    public void setSimulationSpeed(double simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }
}
