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
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import com.example.zeldapuzzle.animation.AnimationComponentMobPassive;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGL.*;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;


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
            protected void onAction() {viewport.setZoom(1);
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
        getGameWorld().addEntityFactory(gameEntityFactory);
        FXGL.setLevelFromMap("StartingMap.tmx");
        dungeonEntry = spawn("dungeonEntry");
//        dungeonEntry.setX(850);
//        dungeonEntry.setY(-130);
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
        executor.scheduleAtFixedRate(helloRunnable, (long) 4 , (long) 2, TimeUnit.SECONDS);

        Runnable helloRunnable1 = new Runnable() {
            public void run() {
                mobMovement(gameEntityFactory.getAnimationComponentMobPassive());
            }
        };
        ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(1);
        executor1.scheduleAtFixedRate(helloRunnable1, 0, 3, TimeUnit.SECONDS);

        getPhysicsWorld().setGravity(0,0);
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                FXGL.setLevelFromMap("donjonPasFini.tmx");
                player = spawn("player");
                player.setScaleUniform(1.3);
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
                cible.removeFromWorld();
                System.out.println("OK");
                arrowMove.removeFromWorld();

            }
        });
    }

    @Override
    protected void initUI(){
//        Rectangle grass = new Rectangle(500,500,Color.GREEN);
//        Rectangle grassRed = new Rectangle(300,300,Color.RED);
//        grass.viewOrderProperty().set(1.0);
//        grassRed.viewOrderProperty().set(0.5);
//        FXGL.getGameScene().addUINode(grass);
//        FXGL.getGameScene().addUINode(grassRed);
//        FXGL.getGameScene().setUIMouseTransparent(true);
//        FXGL.getGameScene().setBackgroundColor(Color.GREEN);
//        FXGL.getUIFactoryService().newText("OKKK");
    }


//    @Override
//    protected void onUpdate(){
//
//    }

    private void setLevel(Level level){
        getGameWorld().getEntities().forEach(e -> e.removeFromWorld());
    }

    public enum EntityType {
        PLAYER,DOOR,PLATFORM,SMALLTREE,CIBLE,BOITE,STATUE,TRIANGLE,STATIONTIRE,ARROW,ARROWMOVE,MOBPASSIVE
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Entity getMobPassive() {
        return mobPassive;
    }
    public void mobMovement (AnimationComponentMobPassive animationComponentMobPassive) {
      //  animationComponentMobPassive.changeBodyType(BodyType.DYNAMIC);

            int random = (int) ((Math.random() * 5));

            if (random == 0) {
                animationComponentMobPassive.moveDown();
                if (currentTimeMillis() % 2000 == 0) {
                   // animationComponentMobPassive.setSpeedy(0);
                  //  animationComponentMobPassive.setSpeedx(0);
                }
            }
            if (random == 1) {
                animationComponentMobPassive.moveLeft();
                if (currentTimeMillis() % 2000 == 0) {
                   // animationComponentMobPassive.setSpeedy(0);
                   // animationComponentMobPassive.setSpeedx(0);
                }
            }
            if (random == 2) {
                animationComponentMobPassive.moveUp();
                if (currentTimeMillis() % 2000 == 0) {
                   // animationComponentMobPassive.setSpeedy(0);
                   // animationComponentMobPassive.setSpeedx(0);
                }
            }
            if (random == 3) {
                animationComponentMobPassive.moveRight();
                if (currentTimeMillis() % 2000 == 0) {
                  //  animationComponentMobPassive.setSpeedy(0);
                   // animationComponentMobPassive.setSpeedx(0);
                }
            }
    }

}
