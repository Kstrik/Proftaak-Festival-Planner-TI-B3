package simulation.map;

import simulation.map.world.Room;
import simulation.map.world.Seat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TileMapLoader {
    private ArrayList<TileLayer> tileLayers;
    private ArrayList<TileSet> tileSets;
    private TileLayer collisionLayer;
    private HashMap<String, Room> rooms;
    private Point2D spawnPoint;

    private String mapFilePath;

    public TileMapLoader(String mapFilePath){
        this.tileLayers = new ArrayList<TileLayer>();
        this.tileSets = new ArrayList<TileSet>();
        this.collisionLayer = null;
        this.rooms = new HashMap<String, Room>();
        this.spawnPoint = null;

        this.mapFilePath = mapFilePath;
    }

    public TileMap loadTileMap() {
        loadTileSets(getJsonObject(this.mapFilePath));
        loadTileLayers(getJsonObject(this.mapFilePath));

        TileMap tileMap = new TileMap();
        tileMap.setLayers(this.tileLayers);
        tileMap.setCollisionLayerLayer(this.collisionLayer);
        tileMap.setSpawnPoint(this.spawnPoint);
        if(this.rooms.size() != 0){
            tileMap.setRooms(this.rooms);
        }

        if(this.tileLayers.isEmpty()){
            tileMap = null;
        }

        return tileMap;
    }

    private void loadTileSets(JSONObject jsonMap){
        JSONArray tileSets = (JSONArray)jsonMap.get("tilesets");

        if(tileSets != null) {
            for (Object tileSet : tileSets) {
                JSONObject jsonTileSet = getJsonObject(formatFilePath(((JSONObject)tileSet).get("source").toString()));
                BufferedImage image = loadImage(jsonTileSet.get("image").toString());
                int firstGlobalId = Math.toIntExact((long)((JSONObject)tileSet).get("firstgid"));
                int columnCount = Math.toIntExact((long)jsonTileSet.get("columns"));
                int tileWidth = Math.toIntExact((long)jsonTileSet.get("tilewidth"));

                this.tileSets.add(new TileSet(image, firstGlobalId, columnCount, tileWidth));
            }
        }
    }

    private void loadTileLayers(JSONObject jsonMap) {
        JSONArray tileLayers = (JSONArray)jsonMap.get("layers");

        if(tileLayers != null){
            int tileCountY = Math.toIntExact((long)jsonMap.get("height"));
            int tileWidth =  Math.toIntExact((long)jsonMap.get("tilewidth"));

            for (Object tileLayer : tileLayers) {
                JSONObject jsonTileLayer = (JSONObject)tileLayer;

                String layerName = jsonTileLayer.get("name").toString();
                String layerType = jsonTileLayer.get("type").toString();

                if(layerType.equals("tilelayer")){
                    int tileCountX = Math.toIntExact((long)jsonTileLayer.get("width"));
                    boolean isVisible = (boolean)jsonTileLayer.get("visible");

                    Tile[][] tiles = new Tile[tileCountY][tileCountX];
                    JSONArray jsonArray =  (JSONArray)jsonTileLayer.get("data");
                    ArrayList<Integer> tileIds = new ArrayList<Integer>();

                    for(Object value : jsonArray) {
                        tileIds.add(Math.toIntExact((long)value));
                    }

                    for(int y = 0; y < tileCountY; y++) {
                        for(int x = 0; x < tileCountX; x++) {
                            int id = tileIds.get(((y * tileCountY)) + x);
                            TileSet tileSet = getTileSetContainingGlobalId(id);
                            int columns = tileSet.getColumnCount();
                            int tempId = id - tileSet.getFirstGlobalId();
                            int imageX = (tempId % columns) * tileWidth;
                            int imageY = ((tempId - (tempId % columns)) / columns) * tileWidth;
                            BufferedImage image = null;
                            if(id != 0) {
                                try {
                                    image = tileSet.getImage().getSubimage(imageX, imageY, tileWidth, tileWidth);
                                }
                                catch(Exception e){
                                    System.out.println("Layername: " + layerName);
                                    System.out.println("ImageX: " + imageX + " ImageY: " + imageY + " TileWidth: " + tileWidth + " TileWidth: " + tileWidth);
                                }
                            }

                            tiles[y][x] = new Tile(image, x * tileWidth, y * tileWidth, tileWidth, layerName.toLowerCase().equals("collisionlayer") && id != 0, ((id != 0) && isVisible));
                        }
                    }
                    if(!layerName.toLowerCase().equals("collisionlayer")){
                        this.tileLayers.add(new TileLayer(tiles, tileWidth));
                    }else{
                        this.collisionLayer = new TileLayer(tiles, tileWidth);
                    }
                }
                else if(layerType.equals("objectgroup")){
                    JSONArray objects = (JSONArray)jsonTileLayer.get("objects");

                    for (Object object : objects) {
                        JSONObject jsonObject = (JSONObject)object;

                        int positionX = Math.toIntExact((long)jsonObject.get("x"));
                        int positionY = Math.toIntExact((long)jsonObject.get("y"));

                        int width = Math.toIntExact((long)jsonObject.get("width"));
                        int height = Math.toIntExact((long)jsonObject.get("height"));

                        String objectName = jsonObject.get("name").toString().toLowerCase();

                        if(layerName.toLowerCase().equals("roomlayer")){
                            this.rooms.put(objectName, new Room(objectName, new Point2D.Double(positionX, positionY), width, height));
                        }
                        else if(layerName.toLowerCase().equals("seatlayer")){
                            Seat seat = new Seat(new Point2D.Double(positionX, positionY), width, height);
                            Room room = null;

                            if(objectName.toLowerCase().endsWith("_d")){
                                room = this.rooms.get(objectName.substring(0, objectName.length() - 2));
                                room.setTeacherSeat(seat);
                            }
                            else{
                                room = this.rooms.get(objectName);
                                room.addStudentSeat(seat);
                            }
                        }
                        else if(layerName.toLowerCase().equals("spawnlayer"))
                        {
                            this.spawnPoint = new Point2D.Double(positionX + (width / 2), positionY + (height / 2));
                        }
                    }
                }
            }
        }
    }

    private TileSet getTileSetContainingGlobalId(int globalId){
        TileSet lastTileSet = null;

        for(TileSet tileSet : this.tileSets) {
            if(lastTileSet != null) {
                if(!(globalId < tileSet.getFirstGlobalId()) && (globalId >= lastTileSet.getFirstGlobalId())){
                    lastTileSet = tileSet;
                }
            } else{
                lastTileSet = tileSet;
            }
        }

        return lastTileSet;
    }

    private BufferedImage loadImage(String imageFilePath){
        try {
            return ImageIO.read(new File(formatFilePath(imageFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getJsonObject(String filePath) {
        JSONParser jsonParser = new JSONParser();
        Path path =  Paths.get(filePath);

        try {
            return (JSONObject)jsonParser.parse(new FileReader(path.toFile()));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    private String formatFilePath(String filePath){
        String output = "";
        boolean hasBeginning = false;

        for(Character character : filePath.toCharArray()) {
            if(hasBeginning){
                output += character;
            }

            if(character == '/'){
                hasBeginning = true;
            }
        }

        return output;
    }
}
