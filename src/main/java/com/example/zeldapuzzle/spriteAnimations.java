package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
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
                player.getComponent(AnimationComponent.class).moveRight();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveLeft();
            }
        }, KeyCode.A);
    }

    @Override
    protected void initGame() {
        try {
            player = FXGL.entityBuilder()
                    .at(200, 200)
                    .with(new AnimationComponent())
                    .buildAndAttach();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {

        launch(args);
    }

}
