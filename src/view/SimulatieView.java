package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.map.MapView;
import model.map.TileMap;
import model.map.TileMapLoader;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.io.File;

public class SimulatieView extends Application {
    private MapView mapView;

    public void startup() {
        launch(AgendaView.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //TileMap tileMap = new TileMapLoader("src/files/testmap/Test.json").loadTileMap();
        //TileMap tileMap = new TileMapLoader("src/files/testmap2/Map.json").loadTileMap();
        //TileMap tileMap = new TileMapLoader("src/files/testmapcollision/Map.json").loadTileMap();
        //TileMap tileMap = new TileMapLoader("src/files/lasttest/Map.json").loadTileMap();
        //tileMap.toggleCollisionVisibility();
        TileMap tileMap = new TileMapLoader("src/files/DemoMap/tilemap_LD.json").loadTileMap();

        //tileMap = null;

        BorderPane borderPane = new BorderPane();
        ResizableCanvas resizableCanvas = new ResizableCanvas(g -> draw(g), borderPane);

        MenuBar menuBar = new MenuBar();
        Menu mapMenu = new Menu("Maps");
        Menu settingsMenu = new Menu("Settings");
        menuBar.getMenus().addAll(mapMenu, settingsMenu);

        borderPane.setTop(menuBar);
        borderPane.setCenter(resizableCanvas);
        borderPane.setRight(setupSideMenuMaps(primaryStage));

        TabPane tabPane = new TabPane();
        Tab simulatieEditorTab = new Tab("Simulatie Editor");
        simulatieEditorTab.setContent(borderPane);
        simulatieEditorTab.setClosable(false);
        Tab simulatieTab = new Tab("Simulator");
        simulatieTab.setClosable(false);
        tabPane.getTabs().addAll(simulatieEditorTab, simulatieTab);

        this.mapView = new MapView(tileMap, resizableCanvas);

        FXGraphics2D graphics2D = new FXGraphics2D(resizableCanvas.getGraphicsContext2D());

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(graphics2D);
            }
        }.start();

        Scene scene = new Scene(tabPane, 1300, 600);

        scene.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.C){
                tileMap.toggleCollisionVisibility();
            }
        });

        primaryStage.setTitle("Simulatie");
        primaryStage.setScene(scene);
        primaryStage.show();

        draw(graphics2D);
    }

    public void update(double deltaTime) {
        this.mapView.update(deltaTime);
    }

    private void draw(FXGraphics2D graphics) {
        this.mapView.draw(graphics);
    }

    private GridPane setupSideMenuMaps(Stage stage){
        GridPane gridPane = new GridPane();
        gridPane.setPrefWidth(300);
        gridPane.setPadding(new Insets(10));

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open map");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        Label label = new Label("Map Toevoegen");
        VBox vbox = new VBox(label, new Separator());
        Button openMapButton = new Button("Bladeren");

        openMapButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(stage);

            if(file != null){
                TileMap tileMap = new TileMapLoader(file.getPath()).loadTileMap();

                if(tileMap != null) {
                    this.mapView.setTileMap(tileMap);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fout Inladen Map");
                    alert.setHeaderText("Fout Inladen Map");
                    alert.setContentText("Er is iets fout gegaan tijdens het inladen van de geselecteerde map!");
                    alert.showAndWait();
                }
            }
        });

        gridPane.add(vbox,0,0,2,1);
        gridPane.add(openMapButton,0,2);

        return gridPane;
    }
}
