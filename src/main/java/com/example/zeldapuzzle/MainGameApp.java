package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoaderKt;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;


import java.net.MalformedURLException;
import java.net.URL;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MainGameApp extends GameApplication {

    private Entity player;
    private Entity platform;
    private Entity dungeonEntry;
    private Entity background;
    private Viewport viewport;
    @Override
    protected void initSettings(GameSettings settings) {
    settings.setWidth(800);
    settings.setHeight(800);
    settings.setTitle("Zelda 2D Game");
    settings.setVersion("1");
    settings.setIntroEnabled(false);
    settings.setMainMenuEnabled(true);

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
                player.getComponent(Player.class).moveRight();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move left") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).moveleft();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).moveUp();
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).moveDown();
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
        background = spawn("background");
        dungeonEntry = spawn("dungeonEntry");
        dungeonEntry.setX(850);
        dungeonEntry.setY(-130);
        player = spawn("player");
        viewport = getGameScene().getViewport();
        viewport.bindToEntity(player,player.getX(), player.getY());






        //Position





    }

    @Override
    protected void initPhysics(){

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                super.onCollisionBegin(player, door);
                door.removeFromWorld();
//                background.removeFromWorld();
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
        PLAYER,DOOR,PLATFORM,SMALLTREE
    }

    public static void main(String[] args) {
        launch(args);
    }





}
