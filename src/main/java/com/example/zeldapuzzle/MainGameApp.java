package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenInvisibleComponent;
import com.almasb.fxgl.dsl.components.OffscreenPauseComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;


import static com.almasb.fxgl.dsl.FXGL.*;

public class MainGameApp extends GameApplication {

    private Entity player;
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

    player = entityBuilder()
            .at(100,100)
            .viewWithBBox("com/example/zeldapuzzle/zelda.png")
            .with(new Player())
            .buildAndAttach();
    }

    @Override
    protected void initPhysics(){

    }

    @Override
    protected void initUI(){

    }


//    @Override
//    protected void onUpdate(){
//
//    }

    public static void main(String[] args) {
        launch(args);
    }





}
