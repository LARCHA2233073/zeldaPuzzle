package com.example.zeldapuzzle.Inventaire;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import com.example.zeldapuzzle.MainGameApp;
import com.example.zeldapuzzle.animation.AnimationComponentMobPassive;
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Vitesse extends Entity implements Objet {
    int nombreDeVitesse = 0;
    Text textNombreDeVitesse;
    ImageView imageViewVitesse;

    public Vitesse() {
        textNombreDeVitesse= new Text("x" + nombreDeVitesse);
        textNombreDeVitesse.setFill(Color.WHITE);

        //image Background
        try {
            imageViewVitesse = new ImageView(new Image(new FileInputStream("src/main/resources/assets/textures/vitesseBackground.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //utiliser l'objet
        textNombreDeVitesse.setOnMouseClicked(mouseEvent -> {
            diminuerNombre();
        });
    }

    @Override
    public void utiliser() {
        AnimationComponentPlayer.setIntVec(20);
        TimerAction timerAction = MainGameApp.getGameScene().getTimer().runOnceAfter(() ->{
            AnimationComponentPlayer.setIntVec(4);
        }, Duration.seconds(2));
        timerAction.resume();
    }

    @Override
    public int getNombre() {
        return nombreDeVitesse;
    }

    @Override
    public void augmenterNombre() {
        nombreDeVitesse++;
    }

    @Override
    public void diminuerNombre() {
        nombreDeVitesse--;
    }

    @Override
    public ImageView getImageView() {
        return imageViewVitesse;
    }

    @Override
    public Text getText() {
        if (nombreDeVitesse >= 10){
            textNombreDeVitesse.setText("x" + nombreDeVitesse);
        }else{
            textNombreDeVitesse.setText("x0" + nombreDeVitesse);
        }
        return textNombreDeVitesse;
    }
}
