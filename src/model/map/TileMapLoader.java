package model.map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TileMapLoader {
    private ArrayList<TileLayer> tileLayers;
    private ArrayList<TileSet> tileSets;
    private TileLayer collisionLayer;

    private String mapFilePath;

    public TileMapLoader(String mapFilePath){
        this.tileLayers = new ArrayList<TileLayer>();
        this.tileSets = new ArrayList<TileSet>();
        this.collisionLayer = null;

        this.mapFilePath = mapFilePath;
    }

    public TileMap loadTileMap() {
        loadTileSets(getJsonObject(this.mapFilePath));
        loadTileLayers(getJsonObject(this.mapFilePath));

        TileMap tileMap = new TileMap();
        tileMap.setLayers(this.tileLayers);
        tileMap.setCollisionLayerLayer(this.collisionLayer);

        return tileMap;
    }

    private void loadTileSets(JSONObject jsonMap){
        JSONArray tileSets = (JSONArray)jsonMap.get("tilesets");

        for (Object tileSet : tileSets) {
            JSONObject jsonTileSet = getJsonObject(formatFilePath(((JSONObject)tileSet).get("source").toString()));
            BufferedImage image = loadImage(jsonTileSet.get("image").toString());
            int firstGlobalId = Math.toIntExact((long)((JSONObject)tileSet).get("firstgid"));
            int columnCount = Math.toIntExact((long)jsonTileSet.get("columns"));
            int tileWidth = Math.toIntExact((long)jsonTileSet.get("tilewidth"));

            this.tileSets.add(new TileSet(image, firstGlobalId, columnCount, tileWidth));
        }
    }

    private void loadTileLayers(JSONObject jsonMap) {
        JSONArray tileLayers = (JSONArray)jsonMap.get("layers");

        int tileCountY = Math.toIntExact((long)jsonMap.get("height"));
        int tileWidth =  Math.toIntExact((long)jsonMap.get("tilewidth"));

        for (Object tileLayer : tileLayers) {
            JSONObject jsonTileLayer = (JSONObject)tileLayer;

            int tileCountX = Math.toIntExact((long)jsonTileLayer.get("width"));
            String layerName = jsonTileLayer.get("name").toString();
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
//                    int imageX = ((id % columns) - 1) * tileWidth;
//                    int imageY = ((id - (id % columns)) / columns) * tileWidth;
                    //int tempId = (id > 1) ? (id - tileSet.getFirstGlobalId()) : id;
                    int tempId = id - tileSet.getFirstGlobalId();
                    int imageX = (tempId % columns) * tileWidth;
                    int imageY = ((tempId - (tempId % columns)) / columns) * tileWidth;

                    //BufferedImage image = tileSet.getImage().getSubimage(imageX, imageY, tileWidth, tileWidth);

                    BufferedImage image = null;
                    if(id != 0) {
                        image = tileSet.getImage().getSubimage(imageX, imageY, tileWidth, tileWidth);
                    }
//                    BufferedImage image = null;
//
//                    try{
//                        image = tileSet.getImage().getSubimage(imageX, imageY, tileWidth, tileWidth);
//                    }catch (Exception e){
//                        System.out.println(imageX + " : " + imageY);
//                    }

                    //tiles[y][x] = new Tile(image, x * tileWidth, y * tileWidth, tileWidth, false, (id != 0));
                    tiles[y][x] = new Tile(image, x * tileWidth, y * tileWidth, tileWidth, layerName.toLowerCase().equals("collisionlayer"), ((id != 0) && isVisible));
                }
            }
            if(!layerName.toLowerCase().equals("collisionlayer")){
                this.tileLayers.add(new TileLayer(tiles, tileWidth));
            }else{
                this.collisionLayer = new TileLayer(tiles, tileWidth);
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
            e.printStackTrace();
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
