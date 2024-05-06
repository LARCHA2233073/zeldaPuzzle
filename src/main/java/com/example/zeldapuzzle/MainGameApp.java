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
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.example.zeldapuzzle.Inventaire.Banane;
import com.example.zeldapuzzle.Inventaire.CaseInventaire;
import com.example.zeldapuzzle.Inventaire.Objet;
import com.example.zeldapuzzle.Inventaire.Pomme;
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MainGameApp extends GameApplication {

    private Entity mobPassive;
    private Entity player;
    private Entity platform;
    private Entity dungeonEntry;
    private Entity dungeon;

    private Entity background;

    private Entity arrow;
    private Viewport viewport;

    private PhysicsComponent physics =  new PhysicsComponent();;

    private GameEntityFactory gameEntityFactory = new GameEntityFactory();
    private AnimationComponentPlayer animation = new AnimationComponentPlayer();

    private  int numberOfTarget = 5;

    private Pomme pomme;

    private Banane banane;

    private CaseInventaire caseInventaire;


    private GridPane gridPane;

    private GridPane gridPaneNombre;

    Objet[][] tabObjets  = new Objet[40][4];

    private int placeInventaireX = 0; //de 0 a 9

    private int placeInventaireY = 0; //de 0 a 3


    private boolean isPomme = false;
    private boolean isBanane = false;
    private StackPane stackPane;
    private int positionDeImageViewX;
    private int positionDeImageViewY;

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
                ajoutInventaire();
            }

            @Override
            protected void onActionEnd() {
                gridPane.getChildren().clear();
                gridPaneNombre.getChildren().clear();
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
            getGameWorld().addEntityFactory(gameEntityFactory));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //initialiser les objet
        pomme = new Pomme();
        caseInventaire = new CaseInventaire();
        banane = new Banane();

        //utiliser l'objet
        pomme.getText().setOnMouseClicked(mouseEvent -> {
            pomme.diminuerNombre();
            gridPane.getChildren().clear();
            gridPaneNombre.getChildren().clear();
            ajoutInventaireObjet();
        });

        banane.getText().setOnMouseClicked(mouseEvent -> {
            banane.diminuerNombre();
            gridPane.getChildren().clear();
            gridPaneNombre.getChildren().clear();
            ajoutInventaireObjet();
        });

        getGameWorld().addEntityFactory(gameEntityFactory);

        FXGL.setLevelFromMap("StartingMap.tmx");
        dungeonEntry = spawn("dungeonEntry");

        //spawn des objets
        FXGL.spawn("pomme");
        FXGL.spawn("pomme").setPosition(50,0);
        FXGL.spawn("banane").setPosition(100,0);

        player = spawn("player");
        mobPassive = spawn("mobPassive");
        viewport = getGameScene().getViewport();
        viewport.bindToEntity(player,player.getX(), player.getY());
        FileInputStream fileInputStream;

        Runnable helloRunnable = new Runnable() {
            public void run() {
                gameEntityFactory.getAnimationComponentMobPassive().setSpeedy(0);
                gameEntityFactory.getAnimationComponentMobPassive().setSpeedx(0);
                gameEntityFactory.getAnimationComponentMobPassive().stopMovement();
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, (long) 5 , (long) 2, TimeUnit.SECONDS);

        Runnable helloRunnable1 = new Runnable() {
            public void run() {
                mobMovement(gameEntityFactory.getAnimationComponentMobPassive());
            }
        };
        ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(1);
        executor1.scheduleAtFixedRate(helloRunnable1, 0, 4, TimeUnit.SECONDS);

        getPhysicsWorld().setGravity(0,0);


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
                spawnEntityMondeDepart();
                player = spawn("player");
                viewport.bindToEntity(player,player.getX(), player.getY());
                player.setPosition(400,100);
                getGameScene().setBackgroundColor(Color.WHITE);
                getPhysicsWorld().setGravity(0,0);
                setPlayer(player);

            }
        });



        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.POMME) {
            @Override
            protected void onCollisionBegin(Entity player, Entity pommeEntity) {
                //repenser a ameliorer le systeme pour avoir plusieurs pommes
                if (placeInventaireY != 4 ) {
                    for (int i = 0; i <= 3; i++) {
                        for (int j = 0; j <= 9; j++) {
                            if (tabObjets[j][i].getImageView().getImage() == pomme.getImageView().getImage()) {
                                isPomme = true;
                                if (tabObjets[j][i].getNombre() != 10) {
                                        tabObjets[j][i].augmenterNombre();
                                }
                            }
                        }
                    }

                    if (!isPomme){
                        //mettre a 0 pour avoir  la case la plus proche
                        placeInventaireY = 0;
                        placeInventaireX = 0;

                        while (tabObjets[placeInventaireX][placeInventaireY].getImageView().getImage() != caseInventaire.getImageView().getImage()) {
                            System.out.println(tabObjets[placeInventaireX][placeInventaireY].getImageView().getImage() + "  " + caseInventaire.getImageView().getImage());
                            placeInventaireX++;

                            if(placeInventaireX == 10){
                                placeInventaireX = 0;
                                placeInventaireY++;
                            }

                        }
                        //ajout de la pomme
                        tabObjets[placeInventaireX][placeInventaireY] = pomme;
                        tabObjets[placeInventaireX][placeInventaireY].augmenterNombre();

                        if (placeInventaireX == 9 && placeInventaireY == 3 ){
                            placeInventaireY = 4;
                        }
                    }
                }

                pommeEntity.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.BANANE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity bananeEntity) {
                //repenser a ameliorer le systeme pour avoir plusieurs bananes
                if (placeInventaireY != 4 ) {
                    for (int i = 0; i <= 3; i++) {
                        for (int j = 0; j <= 9; j++) {
                            if (tabObjets[j][i].getImageView().getImage() == banane.getImageView().getImage()) {
                                isBanane = true;
                                if (tabObjets[j][i].getNombre() != 10) {
                                    tabObjets[j][i].augmenterNombre();
                                }
                            }
                        }
                    }

                    if (!isBanane){
                        //mettre a 0 pour avoir  la case la plus proche
                        placeInventaireY = 0;
                        placeInventaireX = 0;

                        while (tabObjets[placeInventaireX][placeInventaireY].getImageView().getImage() != caseInventaire.getImageView().getImage()) {
                            System.out.println(tabObjets[placeInventaireX][placeInventaireY].getImageView().getImage() + "  " + caseInventaire.getImageView().getImage());
                            placeInventaireX++;

                            if(placeInventaireX == 10){
                                placeInventaireX = 0;
                                placeInventaireY++;
                            }

                        }
                        //ajout de la banane
                        tabObjets[placeInventaireX][placeInventaireY] = banane;
                        tabObjets[placeInventaireX][placeInventaireY].augmenterNombre();

                        if (placeInventaireX == 9 && placeInventaireY == 3 ){
                            placeInventaireY = 4;
                        }
                    }
                }

            }
        });



    }

    @Override
    protected void initUI(){
        //ajouter les objets case inventaire dans un tableau format(colonnes,lignes)
        for (int i = 0; i <=3; i++){
            for (int j = 0; j <=9; j ++){
                tabObjets[j][i] = caseInventaire;
            }
        }


        //creation borderPane et gridPane et stackPane (initialisation des variable)
        BorderPane borderPane = new BorderPane();
        gridPane = new GridPane();
        stackPane = new StackPane();
        gridPaneNombre = new GridPane();

        //parametre du gridPane
        gridPane.setPadding(new Insets(95,10,10,50  ));
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.setAlignment(Pos.CENTER_RIGHT);
        gridPane.setGridLinesVisible(true);

        //parametre du gridPaneNombre
        gridPaneNombre.setPadding(new Insets(100,22,0,0));
        gridPaneNombre.setHgap(13);
        gridPaneNombre.setVgap(16);
        gridPaneNombre.setAlignment(Pos.CENTER_RIGHT);


        //ajout du gridPane dans le stackPane
        stackPane.getChildren().add(gridPane);
        stackPane.getChildren().add(gridPaneNombre);


        //barre de vie
        ProgressBar barreDeVie = new ProgressBar();
        barreDeVie.setProgress(1);
        barreDeVie.setStyle("-fx-accent: red;");
        barreDeVie.setPrefWidth(225);
        barreDeVie.setPrefHeight(21);

        //BorderPane
        borderPane.setLeft(barreDeVie);
        borderPane.setCenter(stackPane);

        getGameScene().addUINode(borderPane);
        getGameScene().getRoot().setCenterShape(true);

    }


//    @Override
//    protected void onUpdate(){
//
//    }

    private void spawnEntityMondeDepart(){
        FXGL.spawn("pomme");
        FXGL.spawn("pomme").setPosition(50,0);
        FXGL.spawn("banane").setPosition(100,0);
    }

    private void ajoutInventaire(){
        //ajout dans le gridpane des imageviews des cases et du text format(colonnes,lignes)
        for (int i = 0; i <=3; i++){
            for (int j = 0; j <=9; j ++){
                gridPane.add(tabObjets[j][i].getImageView() ,j,i);
                if (tabObjets[j][i].getImageView().getImage() != caseInventaire.getImageView().getImage()){
                    gridPaneNombre.add(tabObjets[j][i].getText(),j,i);

                }else {
                    gridPaneNombre.add(caseInventaire.getText() ,j,i);
                }
            }
        }
    }

    private void ajoutInventaireObjet(){
        //ajout dans le gridpane des imageviews des cases et du text format(colonnes,lignes)
        for (int i = 0; i <=3; i++){
            for (int j = 0; j <=9; j ++){
                if (tabObjets[j][i].getImageView().getImage() != caseInventaire.getImageView().getImage()){
                    if (tabObjets[j][i].getNombre() ==0 ){
                        tabObjets[j][i] = caseInventaire;
                    }
                    gridPane.add(tabObjets[j][i].getImageView() ,j,i);
                    gridPaneNombre.add(tabObjets[j][i].getText(),j,i);

                }else {
                    gridPane.add(tabObjets[j][i].getImageView() ,j,i);
                    gridPaneNombre.add(caseInventaire.getText() ,j,i);
                }
            }
        }
    }

    public enum EntityType {
        PLAYER,DOOR,PLATFORM,SMALLTREE,CIBLE,BOITE,STATUE,TRIANGLE,STATIONTIRE,ARROW,ARROWMOVE,
        SWORD,MOBPASSIVE,POMME,BANANE
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void mobMovement (AnimationComponentMobPassive animationComponentMobPassive) {
      //  animationComponentMobPassive.changeBodyType(BodyType.DYNAMIC);

            int random = (int) ((Math.random() * 3) + 1);

            switch (random) {
                case 0 :  animationComponentMobPassive.moveDown();
                    break;
                case 1 :  animationComponentMobPassive.moveUp();
                    break;
                case 2 :  animationComponentMobPassive.moveLeft();
                    break;
                case 3 :  animationComponentMobPassive.moveRight();
                    break;
            }

    }
}


