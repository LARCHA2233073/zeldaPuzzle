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
import com.example.zeldapuzzle.animation.AnimationComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    private AnimationComponent animation = new AnimationComponent();

    private  int numberOfTarget = 5;



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
                player.getComponent(AnimationComponent.class).moveRight();
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
                player.getComponent(AnimationComponent.class).moveLeft();
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
                player.getComponent(AnimationComponent.class).moveUp();
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
                player.getComponent(AnimationComponent.class).moveDown();
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
        getGameWorld().addEntityFactory(new GameEntityFactory());
        FXGL.setLevelFromMap("StartingMap.tmx");
        dungeonEntry = spawn("dungeonEntry");
        player = spawn("player");
        viewport = getGameScene().getViewport();
        viewport.bindToEntity(player,player.getX(), player.getY());
        FileInputStream fileInputStream;
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
        SWORD
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public static void main(String[] args) {
        launch(args);
    }





}
