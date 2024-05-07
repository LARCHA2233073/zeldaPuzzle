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
import com.almasb.fxgl.time.TimerAction;
import com.example.zeldapuzzle.Inventaire.Banane;
import com.example.zeldapuzzle.Inventaire.CaseInventaire;
import com.example.zeldapuzzle.Inventaire.Objet;
import com.example.zeldapuzzle.Inventaire.Pomme;
import com.example.zeldapuzzle.animation.AnimationComponentMobPassive;
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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
import javafx.util.Duration;


import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MainGameApp extends GameApplication {

    private Entity mobPassive;
    private boolean isDonjon = false;
    private boolean bougePas;
    private Entity playerMapPrincipal;

    private Entity playerMapDongeon;

    private Entity ascenceur;



    private String positionAscenceur = "bas";
    private Entity platform;
    private Entity dungeon;
    private boolean isAuSol;
    private Entity background;

    private Entity arrow;
    private Viewport viewport;

    private PhysicsComponent physics =  new PhysicsComponent();;

    private GameEntityFactory gameEntityFactory = new GameEntityFactory();
    private AnimationComponentPlayer animation = new AnimationComponentPlayer();

    private  int numberOfTarget = 3;

    private Pomme pomme;

    private Banane banane;
    private int vieDuPersonnage = 100;

    private CaseInventaire caseInventaire;


    private boolean entrer = true;

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
    TimerAction timerActionFeu;
    private static ProgressBar barreDeVie;

    public MainGameApp() throws FileNotFoundException {
        physics.setBodyType(BodyType.DYNAMIC);

    }

    @Override
    protected void initSettings(GameSettings settings) {
    settings.setWidth(800);
    settings.setHeight(800);
    settings.setTitle("Zelda 2D Game");
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
                System.out.println("X : " + playerMapPrincipal.getX() + " Y : " + playerMapPrincipal.getY());
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
            protected void onActionBegin() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
            @Override
            protected void onAction() {
                if (!bougePas) {
                    playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveRight();
                }
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move left") {
            @Override
            protected void onActionBegin() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
            @Override
            protected void onAction() {
                if (!bougePas) {
                    playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveLeft();
                }
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onActionBegin() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
            @Override
            protected void onAction() {
                if (!isDonjon)
                    playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveUp();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onActionBegin() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
            @Override
            protected void onAction() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveDown();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Jump") {
            @Override
            protected void onAction() {
                if (isDonjon && isAuSol) {
                    Vec2 force = new Vec2(0, 4);
                    Vec2 point = new Vec2(playerMapPrincipal.getX(), playerMapPrincipal.getY());
                    playerMapPrincipal.getComponent(PhysicsComponent.class).applyBodyLinearImpulse(force, point, false);

                }
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).startAnimIdle();
            }
        }, KeyCode.SPACE);

    }


//    @Override
//    protected void initAssets() throws Exception {
//
//    }

    @Override
    protected void initGame(){

        //EntityFactory
        getGameWorld().addEntityFactory(gameEntityFactory);

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
            pomme.utiliser();
        });

        banane.getText().setOnMouseClicked(mouseEvent -> {
            banane.diminuerNombre();
            gridPane.getChildren().clear();
            gridPaneNombre.getChildren().clear();
            ajoutInventaireObjet();
            banane.utiliser();
        });

        FXGL.setLevelFromMap("mapFinal.tmx");
        getGameScene().setBackgroundColor(Color.BLACK);

        //position du perso
        playerMapPrincipal = spawn("playerMapPrincipal");
        Point2D positionPerso = new Point2D(2088.0,2365.0);
        playerMapPrincipal.getComponent(PhysicsComponent.class).overwritePosition(positionPerso);

        //position mobPassive
        mobPassive = spawn("mobPassive");
        Point2D positionMob = new Point2D(2300.0,2365.0);
        mobPassive.getComponent(PhysicsComponent.class).overwritePosition(positionMob);

        viewport = getGameScene().getViewport();
        System.out.println("X : " + playerMapPrincipal.getX() + "y :" + playerMapPrincipal.getY());  ;
        viewport.bindToEntity(playerMapPrincipal, playerMapPrincipal.getX(), playerMapPrincipal.getY());
        getPhysicsWorld().setGravity(0,0);

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

        //timeractions
         timerActionFeu = getGameScene().getTimer().runAtInterval(() -> {
            barreDeVie.setProgress(barreDeVie.getProgress() - 0.1);
        }, Duration.seconds(1));
         timerActionFeu.pause();


    }

    @Override
    protected void initPhysics(){

        //colission mob personnage

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.MUR) {
            @Override
            protected void onCollisionBegin(Entity playerMapDongeon, Entity mur) {
                //changer de bodytype
                isAuSol = true;
            }
            @Override
            protected void onCollisionEnd(Entity playerMapDongeon, Entity mur) {
                isAuSol = false;
            }
        });

        //Entrer du dongeon
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPPRINCIPAL,EntityType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity playerMapPrincipal, Entity door) {
                //changer de niveau
                FXGL.setLevelFromMap("leDongeon.tmx");
                Entity cible1 = spawn("cible");
                Entity cible2 = spawn("cible");
                Entity cible3 = spawn("cible");
                isDonjon = true;
                cible1.setPosition(1600, 520);
                cible2.setPosition(1400, 450);
                cible3.setPosition(1680, 400);
                playerMapDongeon = spawn("playerMapDongeon");
                viewport.bindToEntity(playerMapDongeon,320,500);
                viewport.setZoom(1.5);
                playerMapDongeon.setPosition(195,400);
                ascenceur = spawn("ascenceur");
                getPhysicsWorld().setGravity(0,1500);
                setPlayerMapPrincipal(playerMapDongeon);



            }
        });

        //Station de tir dans le dongeon
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.STATIONTIRE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity stationTire) {
                arrow = spawn("arrow");
                arrow.setX(stationTire.getX());
                arrow.setY(stationTire.getY());

            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.MUR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity stationTire) {
                bougePas = false;
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity stationTire) {
                bougePas = true;
            }
        });


        //Boucle de jeu du dongeon
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.CIBLE,EntityType.ARROWMOVE) {
            @Override
            protected void onCollisionBegin(Entity cible, Entity arrowMove) {
                //enlever la cible
                numberOfTarget--;
                cible.removeFromWorld();

                //message pour le joueur
                String message = "nombre de cible restante " + numberOfTarget + "/3";
                FXGL.getNotificationService().pushNotification(message);

                //enlever la fleche
                arrowMove.removeFromWorld();

                //changer de carte

                if (numberOfTarget == 0){
                    isDonjon = false;
                    FXGL.setLevelFromMap("mapFinal.tmx");
                    playerMapPrincipal = spawn("playerMapPrincipal");
                    viewport.bindToEntity(playerMapPrincipal,400,100);
                    playerMapPrincipal.setPosition(3000,2500);
                    getPhysicsWorld().setGravity(0,0);
                    setPlayerMapPrincipal(playerMapPrincipal);


                    mobPassive = spawn("mobPassive");
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Point2D positionMob = new Point2D(2300.0,2365.0);
                            mobPassive.getComponent(PhysicsComponent.class).overwritePosition(positionMob);
                        }
                    };
                    mobPassive.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);
                }

            }
        });

        //Contact avec les pièges
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.DANGER) {
            @Override
            protected void onCollisionBegin(Entity playerDongeon, Entity danger) {
            }
        });

        // Faire descendre l'ascenceur
        /*
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.ASCENSEURD) {
            @Override
            protected void onCollisionBegin(Entity playerDongeon, Entity ascenseurD) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Vec2 vec2 = new Vec2(0,-10);
                        System.out.println("oui");
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.STATIC);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.KINEMATIC);
                        ascenceur.getComponent(PhysicsComponent.class).setBodyLinearVelocity(vec2);
                    }
                };
                ascenceur.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);

            }
        });

         */


        // Faire monter l'ascenceur
        /*
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.ASCENSEURM) {
            @Override
            protected void onCollisionBegin(Entity playerDongeon, Entity ascenceurM) {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Vec2 vec2 = new Vec2(0,10);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.STATIC);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.KINEMATIC);
                        ascenceur.getComponent(PhysicsComponent.class).setBodyLinearVelocity(vec2);
                    }
                };
                ascenceur.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);
            }
        });

         */

        /*
        // Toucher l'ascenceur
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.ASCENSEUR) {
            @Override
            protected void onCollisionBegin(Entity playerDongeon, Entity ascenceur) {
                int x = 0;
                if(positionAscenceur == "bas")
                   x = 10;
                else if(positionAscenceur == "haut")
                    x = -10;

                int finalX = x;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Vec2 vec2 = new Vec2(0, finalX);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.STATIC);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.KINEMATIC);
                        ascenceur.getComponent(PhysicsComponent.class).setBodyLinearVelocity(vec2);
                    }
                };
                ascenceur.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);
                Vec2 vec2 = new Vec2(0, finalX);
                System.out.println("oui");
                ascenceur.getComponent(PhysicsComponent.class).setBodyLinearVelocity(vec2);
                positionAscenceur = "enMouvement";
            }
        });

         */

        // Arêter l'ascenceur

        /*
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ASCENSEUR,EntityType.POSITIONBAS) {
            @Override
            protected void onCollisionEnd(Entity playerDongeon, Entity ascenceur) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Vec2 vec2 = new Vec2(0, 0);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.STATIC);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.KINEMATIC);
                        ascenceur.getComponent(PhysicsComponent.class).setBodyLinearVelocity(vec2);
                    }
                };
                ascenceur.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);
                positionAscenceur = "bas";
            }
        });

         */
/*
        // Arêter l'ascenceur
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ASCENSEUR,EntityType.POSITIONHAUT) {
            @Override
            protected void onCollisionEnd(Entity playerDongeon, Entity ascenceur) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Vec2 vec2 = new Vec2(0, 0);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.STATIC);
                        ascenceur.getComponent(PhysicsComponent.class).getBody().setType(BodyType.KINEMATIC);
                        ascenceur.getComponent(PhysicsComponent.class).setBodyLinearVelocity(vec2);
                    }
                };
                ascenceur.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);
                positionAscenceur = "haut";
            }
        });

 */






        //Retour a la map
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.STATUE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity statue) {
                FXGL.setLevelFromMap("StartingMap.tmx");
                playerMapPrincipal = spawn("playerMapPrincipal");
                viewport.bindToEntity(playerMapDongeon,400,100);
                player.setPosition(400,100);
                getPhysicsWorld().setGravity(0,0);
                setPlayerMapPrincipal(playerMapPrincipal);

            }
        });



        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPPRINCIPAL,EntityType.POMME) {
            @Override
            protected void onCollisionBegin(Entity player, Entity pommeEntity) {
                //repenser a ameliorer le systeme pour avoir plusieurs pommes
                if (placeInventaireY != 4 ) {
                    for (int i = 0; i <= 3; i++) {
                        for (int j = 0; j <= 9; j++) {
                            if (tabObjets[j][i].getImageView().getImage() == pomme.getImageView().getImage()) {

                                if (tabObjets[j][i].getNombre() != 10) {
                                        tabObjets[j][i].augmenterNombre();
                                }
                            }
                        }
                    }

                    if (pomme.getNombre() == 0){
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

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPPRINCIPAL,EntityType.BANANE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity bananeEntity) {
                //repenser a ameliorer le systeme pour avoir plusieurs bananes
                if (placeInventaireY != 4 ) {
                    for (int i = 0; i <= 3; i++) {
                        for (int j = 0; j <= 9; j++) {
                            if (tabObjets[j][i].getImageView().getImage() == banane.getImageView().getImage()) {

                                if (tabObjets[j][i].getNombre() != 10) {
                                    tabObjets[j][i].augmenterNombre();
                                }
                            }
                        }
                    }

                    if (banane.getNombre() == 0){
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
                bananeEntity.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPPRINCIPAL,EntityType.PORTEMAISONPLAYER) {
            @Override
            protected void onCollisionBegin(Entity player, Entity porteMaisonPlayer) {
                if (entrer) {
                    //dire que nous sommes rentrer
                    entrer = false;

                    FXGL.setLevelFromMap("interieurMaisonPlayer1.tmx");
                    playerMapPrincipal = spawn("playerMapPrincipal");
                    getPhysicsWorld().setGravity(0, 0);
                    setPlayerMapPrincipal(playerMapPrincipal);
                    viewport.bindToEntity(playerMapPrincipal,400,100);

                    //position Player
                    Point2D positionPlayer = new Point2D(650, 346);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            playerMapPrincipal.getComponent(PhysicsComponent.class).overwritePosition(positionPlayer);
                        }
                    };
                    playerMapPrincipal.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);

                }
                else {
                    //dire que nous somme sortie
                    entrer = true;

                    FXGL.setLevelFromMap("mapFinal.tmx");
                    playerMapPrincipal = spawn("playerMapPrincipal");
                    getPhysicsWorld().setGravity(0, 0);
                    setPlayerMapPrincipal(playerMapPrincipal);
                    viewport.bindToEntity(playerMapPrincipal,400,100);

                    //position Player
                    Point2D positionPlayer = new Point2D(2088.0,2365.0);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            playerMapPrincipal.getComponent(PhysicsComponent.class).overwritePosition(positionPlayer);
                        }
                    };
                    playerMapPrincipal.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable);

                    mobPassive = spawn("mobPassive");
                    Runnable runnable1 = new Runnable() {
                        @Override
                        public void run() {
                            Point2D positionMob = new Point2D(2300.0,2365.0);
                            mobPassive.getComponent(PhysicsComponent.class).overwritePosition(positionMob);
                        }
                    };
                    mobPassive.getComponent(PhysicsComponent.class).setOnPhysicsInitialized(runnable1);
                }

            }

        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPPRINCIPAL,EntityType.FEU) {


            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                timerActionFeu.resume();

            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                timerActionFeu.pause();
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
        gridPaneNombre.setPadding(new Insets(100,10,0,0));
        gridPaneNombre.setHgap(10);
        gridPaneNombre.setVgap(16);
        gridPaneNombre.setAlignment(Pos.CENTER_RIGHT);


        //ajout du gridPane dans le stackPane
        stackPane.getChildren().add(gridPane);
        stackPane.getChildren().add(gridPaneNombre);


        //barre de vie
        barreDeVie = new ProgressBar();
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


    @Override
    protected void onUpdate(double tpf){

    }
private void setLevel(Level level){
    getGameWorld().getEntities().forEach(e -> e.removeFromWorld());
}

    public static ProgressBar getBarreDeVieVieDuPersonnage() {
        return barreDeVie;
    }

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
        PLAYER,DOOR,PLATFORM,OBJETDEHORS,CIBLE,BOITE,STATUE,TRIANGLE,STATIONTIRE,ARROW,ARROWMOVE,
        SWORD,MOBPASSIVE,POMME,BANANE, MUR, PLAYERMAPPRINCIPAL, MURTRAVERSE, DANGER, PLAYERMAPDONGEON, POSITIONHAUT, POSITIONBAS, ASCENSEURD, ASCENSEURM, ASCENSEUR,
        MAISONOBJET,PORTEMAISONPLAYER,FEU
    }

    public void setPlayerMapPrincipal(Entity playerMapPrincipal) {
        this.playerMapPrincipal = playerMapPrincipal;
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


