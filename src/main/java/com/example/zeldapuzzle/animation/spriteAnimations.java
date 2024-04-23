package com.example.zeldapuzzle.animation;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

import java.io.FileNotFoundException;

public class spriteAnimations extends GameApplication {

    private Entity player;
    @Override
    protected void initSettings(GameSettings settings) {
    }
    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveRight();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveLeft();
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveUp();
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponentPlayer.class).moveDown();
            }
        }, KeyCode.S);
    }

    @Override
    protected void initGame() {
        try {
            player = FXGL.entityBuilder()
                    .at(200, 200)
                    .with(new AnimationComponentPlayer())
                    .buildAndAttach();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {

        launch(args);
    }

}
