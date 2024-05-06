package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


import java.io.FileNotFoundException;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MainGameApp extends GameApplication {

    private Entity playerMapPrincipal;

    private Entity playerMapDongeon;

    private Entity ascenceur;

    private String positionAscenceur = "bas";
    private Entity platform;
    private Entity dungeonEntry;
    private Entity dungeon;

    private Entity background;

    private Entity arrow;
    private Viewport viewport;

    private PhysicsComponent physics =  new PhysicsComponent();;

    private AnimationComponentPlayer animation = new AnimationComponentPlayer();

    private int numberOfTarget = 5;

    private int vieDuPersonnage = 100;


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
            protected void onAction() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveRight();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.D);
        input.addAction(new UserAction("Move left") {
            @Override
            protected void onAction() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveLeft();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                playerMapPrincipal.getComponent(AnimationComponentPlayer.class).moveUp();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                physics = playerMapPrincipal.getComponent(PhysicsComponent.class);
                physics.getBody().setType(BodyType.STATIC);
                physics.getBody().setType(BodyType.DYNAMIC);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
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
            }
        }, KeyCode.S);

    }

    @Override
    protected void initGame(){

        //Création du game entity factory
        try {
            getGameWorld().addEntityFactory(new GameEntityFactory());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        FXGL.setLevelFromMap("StartingMap.tmx");
        dungeonEntry = spawn("dungeonEntry");
        playerMapPrincipal = spawn("playerMapPrincipal");
        viewport = getGameScene().getViewport();
        viewport.bindToEntity(playerMapPrincipal, playerMapPrincipal.getX(), playerMapPrincipal.getY());
        getPhysicsWorld().setGravity(0,0);
    }

    @Override
    protected void initPhysics(){

        //Entrer du dongeon

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPPRINCIPAL,EntityType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity playerMapPrincipal, Entity door) {
                //changer de niveau
                FXGL.setLevelFromMap("leDongeon.tmx");
                playerMapDongeon = spawn("playerMapDongeon");
                viewport.bindToEntity(playerMapDongeon,400,400);
                viewport.setZoom(1.5);
                playerMapDongeon.setPosition(195,400);
                ascenceur = spawn("ascenceur");
                getGameScene().setBackgroundColor(Color.BLACK);
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


        //Boucle de jeu du dongeon
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
                    viewport.bindToEntity(playerMapPrincipal,320, 500);
                    playerMapPrincipal.setPosition(561.333,817.333);
                    getGameScene().setBackgroundColor(Color.BLACK);
                    getPhysicsWorld().setGravity(0,1500);
                    setPlayerMapPrincipal(playerMapPrincipal);
                }

            }
        });

        //Contact avec les pièges
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.DANGER) {
            @Override
            protected void onCollisionBegin(Entity playerDongeon, Entity danger) {
                setVieDuPersonnage(getVieDuPersonnage() - 10);
                System.out.println(getVieDuPersonnage());

            }
        });

        // Faire descendre l'ascenceur
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


        // Faire monter l'ascenceur
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

        // Arêter l'ascenceur

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






        //Retour a la map
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYERMAPDONGEON,EntityType.STATUE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity statue) {
                FXGL.setLevelFromMap("StartingMap.tmx");
                playerMapPrincipal = spawn("playerMapPrincipal");
                viewport.bindToEntity(player,300, 500);
                player.setPosition(400,100);
                getGameScene().setBackgroundColor(Color.WHITE);
                getPhysicsWorld().setGravity(0,0);
                setPlayerMapPrincipal(playerMapPrincipal);

            }
        });
    }

    @Override
    protected void initUI(){


    }

    private void setLevel(Level level){
        getGameWorld().getEntities().forEach(e -> e.removeFromWorld());
    }

    public void setVieDuPersonnage(int vieDuPersonnage) {
        this.vieDuPersonnage = vieDuPersonnage;
    }

    public int getVieDuPersonnage() {
        return vieDuPersonnage;
    }

    public enum EntityType {
        DOOR,SMALLTREE,CIBLE,BOITE,STATUE,TRIANGLE,STATIONTIRE,ARROW,ARROWMOVE,MOBPASSIVE, MUR, PLAYERMAPPRINCIPAL, MURTRAVERSE, DANGER, PLAYERMAPDONGEON, POSITIONHAUT, POSITIONBAS, ASCENSEURD, ASCENSEURM, ASCENSEUR, POMME
    }

    public void setPlayerMapPrincipal(Entity playerMapPrincipal) {
        this.playerMapPrincipal = playerMapPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }





}
