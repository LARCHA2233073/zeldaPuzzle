package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.example.zeldapuzzle.animation.AnimationComponent;
import javafx.scene.input.KeyCode;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MainGameApp extends GameApplication {

    private Entity player;
    private Entity platform;
    private Entity dungeonEntry;
    private Entity dungeon;

    private Entity background;
    private Viewport viewport;

    private PhysicsComponent physics;

    @Override
    protected void initSettings(GameSettings settings) {
    settings.setWidth(800);
    settings.setHeight(800);
    settings.setTitle("Zelda 2D Game");
    settings.setVersion("1");
    settings.setIntroEnabled(false);
    settings.setMainMenuEnabled(true);
    settings.setDeveloperMenuEnabled(true);


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
//        dungeonEntry.setX(850);
//        dungeonEntry.setY(-130);
        platform = spawn("platform");
        player = spawn("player");
        viewport = getGameScene().getViewport();
        viewport.bindToEntity(player,player.getX(), player.getY());
        FileInputStream fileInputStream;
//        try {
//             fileInputStream = new FileInputStream("assets/levels/BeginingMap3.tmx");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        TMXLevelLoader tmxLevelLoader = new TMXLevelLoader();
//        tmxLevelLoader.parse(fileInputStream);



        //Position
    }

    @Override
    protected void initPhysics(){

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                super.onCollisionBegin(player, door);
                shoot((int) door.getX(), (int) door.getY());
                player.getComponent(PhysicsComponent.class).pause();
            }
        });
        FXGL.getPhysicsWorld().setGravity(0,0);
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
        PLAYER,DOOR,PLATFORM,SMALLTREE
    }

    private void shoot(int x, int y) {
        Vec2 arrowVecteur = new Vec2(1200,600);
        getGameWorld().addEntity(new Arrow(arrowVecteur,x,y));
    }

    public static void main(String[] args) {
        launch(args);
    }





}
