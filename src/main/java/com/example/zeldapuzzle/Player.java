package com.example.zeldapuzzle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.example.zeldapuzzle.Inventaire.Equipement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player extends Component  {


    private double speed = 4;
    
    private int vie = 100;
    
    private int shield = 50;


    @Override
    public void onAdded() {

    }



    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }

    public void moveRight(){
        entity.translateX(speed);

    }

    public void moveleft(){
        entity.translateX(speed * -1);

    }

    public void moveUp(){
        entity.translateY(speed * -1);

    }

    public void moveDown(){
        entity.translateY(speed);

    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getVie() {
        return vie;
    }

    public void équipé(Equipement equipement) {

    }
}
