package com.example.zeldapuzzle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class MainGameApp extends GameApplication {

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

    }

//    @Override
//    protected void initAssets() throws Exception {
//
//    }

    @Override
    protected void initGame(){

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
