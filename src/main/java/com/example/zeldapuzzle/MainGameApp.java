package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenInvisibleComponent;
import com.almasb.fxgl.dsl.components.OffscreenPauseComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        Image image = new Image("https://w7.pngwing.com/pngs/810/275/png-transparent-the-legend-of-zelda-a-link-to-the-past-princess-zelda-zelda-ii-the-adventure-of-link-pixel-toys-rectangle-symmetry-video-game.png");
        Image image3 = null;
        try {
            image3 = new Image(new FileInputStream("src/main/resources/assets/textures/zelda1.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image2 = new Image("https://i.seadn.io/gae/kpoyimC6bMVwC27nbg13G7vHxIj4adgVK9gCSnZQOCIgeuOsuxSXboKOWpqnI4KDFOIiYNx6Woci6Oh9VK2YzdIRdi347nc5KI9vbg?auto=format&dpr=1&w=1000");
        ImageView imageView = new ImageView(image3);
    player = FXGL.entityBuilder()
            .at(100,100)
            .view(imageView)
            .scale(0.2,0.2)
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
