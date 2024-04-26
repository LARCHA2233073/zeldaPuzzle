package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.entity.level.tiled.TiledMap;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.example.zeldapuzzle.animation.AnimationComponentMobPassive;
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MainGameApp extends GameApplication {

    private Entity player;
    private Entity platform;
    private Entity dungeonEntry;
    private Entity dungeon;

    private Entity background;

    private Entity arrow;
    private Viewport viewport;

    private PhysicsComponent physics =  new PhysicsComponent();;

    private AnimationComponentPlayer animation = new AnimationComponentPlayer();

    private  int numberOfTarget = 5;

    private Entity pomme;

    private GridPane gridPane;

    ImageView[][] imageViewTab = new ImageView[40][4];


    public MainGameApp() throws FileNotFoundException {
        physics.setBodyType(BodyType.DYNAMIC);

    }

    @Override
    protected void initSettings(GameSettings settings) {
    settings.setWidth(800);
    settings.setHeight(800);
    settings.setTitle("Zelda 2D Game par ماستن");
    settings.setVersion("1");
    settings.setIntroEnabled(false);
    settings.setMainMenuEnabled(true);
    settings.setDeveloperMenuEnabled(true);
    settings.setFullScreenAllowed(true);

    }

    @Override
    protected void initInput() {
        Input input = getInput();
        input.addAction(new UserAction("inventaire") {
            @Override
            protected void onActionBegin() {
                //ajout dans le gridpane format(colonnes,lignes)
                for (int i = 0; i <=3; i++){
                    for (int j = 0; j <=9; j ++){
                        gridPane.add(imageViewTab[j][i] ,j,i);
                    }
                }
            }

            @Override
            protected void onActionEnd() {
                gridPane.getChildren().clear();
            }

        }, KeyCode.TAB);
        input.addAction(new UserAction("getPosition") {
            @Override
            protected void onAction() {
                System.out.println("X : " + player.getX() + " Y : " + player.getY());;
            }

        }, KeyCode.P);
        input.addAction(new UserAction("zoom+") {
            @Override
            protected void onAction() {viewport.setZoom(viewport.getZoom()+0.05);
            }
        }, KeyCode.UP);
        input.addAction(new UserAction("zoom-") {
            @Override
            protected void onAction() {viewport.setZoom(viewport.getZoom()-0.05);
            }
        }, KeyCode.DOWN);
        input.addAction(new UserAction("resetZoom") {
            @Override
            protected void onAction() {viewport.setZoom(1.2);
            }
        }, KeyCode.DIGIT0);
        input.addAction(new UserAction("Move right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveRight();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = player.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveLeft();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = player.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveUp();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = player.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveDown();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = player.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.S);

    }


//    @Override
//    protected void initAssets() throws Exception {
//
//    }

    @Override
    protected void initGame(){

        //EntityFactory
        try {
            getGameWorld().addEntityFactory(new GameEntityFactory());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        FXGL.setLevelFromMap("StartingMap.tmx");
        dungeonEntry = spawn("dungeonEntry");
        player = spawn("player");
        viewport = getGameScene().getViewport();
        viewport.bindToEntity(player,player.getX(), player.getY());
        FileInputStream fileInputStream;
        getPhysicsWorld().setGravity(0,0);
        pomme = spawn("pomme");

    }

    @Override
    protected void initPhysics(){

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                //changer de niveau
                FXGL.setLevelFromMap("donjonPasFini.tmx");
                player = spawn("player");
                viewport.bindToEntity(player,320, 500);
                player.setPosition(100,850);
                getGameScene().setBackgroundColor(Color.BLACK);
                getPhysicsWorld().setGravity(0,1500);
                setPlayer(player);

            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.STATIONTIRE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity stationTire) {
                arrow = spawn("arrow");
                arrow.setX(stationTire.getX());
                arrow.setY(stationTire.getY());

            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.CIBLE,EntityType.ARROWMOVE) {
            @Override
            protected void onCollisionBegin(Entity cible, Entity arrowMove) {
                //enlever la cible
                numberOfTarget--;
                cible.removeFromWorld();

                //message pour le joueur
                String message = "nombre de cible restante " + numberOfTarget + "/5";
                FXGL.getNotificationService().pushNotification(message);

                //enlever la fleche
                arrowMove.removeFromWorld();

                //changer de carte
                if (numberOfTarget == 0){
                    FXGL.setLevelFromMap("donjonFini.tmx");
                    player = spawn("player");
                    viewport.bindToEntity(player,320, 500);
                    player.setPosition(561.333,817.333);
                    getGameScene().setBackgroundColor(Color.BLACK);
                    getPhysicsWorld().setGravity(0,1500);
                    setPlayer(player);
                }

            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.STATUE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity statue) {
                FXGL.setLevelFromMap("StartingMap.tmx");
                player = spawn("player");
                viewport.bindToEntity(player,320, 500);
                player.setPosition(400,100);
                getGameScene().setBackgroundColor(Color.WHITE);
                getPhysicsWorld().setGravity(0,0);
                setPlayer(player);

            }
        });
    }

    @Override
    protected void initUI(){

        //case

        //sourcede l'image
        Image image1;
        try {
             image1 = new Image(new FileInputStream("src/main/resources/assets/textures/caseInventaire.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //case (node)
//        ImageView imageView1_0 = new ImageView(image1);
//        ImageView imageView2_0= new ImageView(image1);
//        ImageView imageView3_0 = new ImageView(image1);
//        ImageView imageView4_0 = new ImageView(image1);
//        ImageView imageView5_0 = new ImageView(image1);
//        ImageView imageView6_0 = new ImageView(image1);
//        ImageView imageView7_0 = new ImageView(image1);
//        ImageView imageView8_0 = new ImageView(image1);
//        ImageView imageView9_0 = new ImageView(image1);
//        ImageView imageView10_0 = new ImageView(image1);
//
//        ImageView imageView1_1 = new ImageView(image1);
//        ImageView imageView2_1= new ImageView(image1);
//        ImageView imageView3_1 = new ImageView(image1);
//        ImageView imageView4_1 = new ImageView(image1);
//        ImageView imageView5_1 = new ImageView(image1);
//        ImageView imageView6_1 = new ImageView(image1);
//        ImageView imageView7_1 = new ImageView(image1);
//        ImageView imageView8_1 = new ImageView(image1);
//        ImageView imageView9_1 = new ImageView(image1);
//        ImageView imageView10_1 = new ImageView(image1);
//
//        ImageView imageView1_2= new ImageView(image1);
//        ImageView imageView2_2= new ImageView(image1);
//        ImageView imageView3_2 = new ImageView(image1);
//        ImageView imageView4_2 = new ImageView(image1);
//        ImageView imageView5_2 = new ImageView(image1);
//        ImageView imageView6_2 = new ImageView(image1);
//        ImageView imageView7_2 = new ImageView(image1);
//        ImageView imageView8_2 = new ImageView(image1);
//        ImageView imageView9_2 = new ImageView(image1);
//        ImageView imageView10_2 = new ImageView(image1);
//
//        ImageView imageView1_3 = new ImageView(image1);
//        ImageView imageView2_3= new ImageView(image1);
//        ImageView imageView3_3 = new ImageView(image1);
//        ImageView imageView4_3 = new ImageView(image1);
//        ImageView imageView5_3 = new ImageView(image1);
//        ImageView imageView6_3 = new ImageView(image1);
//        ImageView imageView7_3 = new ImageView(image1);
//        ImageView imageView8_3 = new ImageView(image1);
//        ImageView imageView9_3 = new ImageView(image1);
//        ImageView imageView10_3= new ImageView(image1);

        //ajouter les imageviews dans un tableau format(colonnes,lignes)
        for (int i = 0; i <=3; i++){
            for (int j = 0; j <=9; j ++){
                imageViewTab[j][i] = new ImageView(image1);
            }
        }



        BorderPane borderPane = new BorderPane();

        gridPane= new GridPane();

//        //ajout dans le gridpane format(colonnes,lignes)
//        for (int i = 0; i <=3; i++){
//            System.out.println("y= " + i);
//
//            for (int j = 0; j <=9; j ++){
//                System.out.println("x= " + j );
//                gridPane.add(imageViewTab[j][i] ,j,i);
//            }
//        }


        //parametre du GridPane
        gridPane.setPadding(new Insets(95,10,10,50  ));
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.setAlignment(Pos.CENTER_RIGHT);
        gridPane.setGridLinesVisible(true);


        Button button = new Button("");
        button.setStyle("-fx-background-color: MediumSeaGreen");

        //barre de vie
        ProgressBar barreDeVie = new ProgressBar();
        barreDeVie.setProgress(1);
        barreDeVie.setStyle("-fx-accent: red;");
        barreDeVie.setPrefWidth(225);
        barreDeVie.setPrefHeight(21);

        //BorderPane
        borderPane.setLeft(barreDeVie);
        borderPane.setCenter(gridPane);

        getGameScene().addUINode(borderPane);
        getGameScene().getRoot().setCenterShape(true);

    }


//    @Override
//    protected void onUpdate(){
//
//    }

    private void setLevel(Level level){
        getGameWorld().getEntities().forEach(e -> e.removeFromWorld());
    }

    public enum EntityType {
        PLAYER,DOOR,PLATFORM,SMALLTREE,CIBLE,BOITE,STATUE,TRIANGLE,STATIONTIRE,ARROW,ARROWMOVE,
        SWORD,MOBPASSIVE,POMME
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public static void main(String[] args) {
        launch(args);
    }





}
