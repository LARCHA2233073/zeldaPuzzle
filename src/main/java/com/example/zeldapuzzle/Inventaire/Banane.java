package com.example.zeldapuzzle.Inventaire;

import com.almasb.fxgl.entity.Entity;
import com.example.zeldapuzzle.MainGameApp;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Banane extends Entity implements Objet{
    int nombreDeBanane = 0;
    Text textNombreDeBanane;
    ImageView imageViewBanane;

    public Banane(){
        textNombreDeBanane= new Text("x" + nombreDeBanane);
        textNombreDeBanane.setFill(Color.WHITE);

        try {
            imageViewBanane = new ImageView(new Image(new FileInputStream("src/main/resources/assets/textures/bananeBackground.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //utiliser l'objet
        textNombreDeBanane.setOnMouseClicked(mouseEvent -> {
            diminuerNombre();
        });
    }
    @Override
    public void utiliser() {
        ProgressBar barDeVie = MainGameApp.getBarreDeVieVieDuPersonnage();
        barDeVie.setProgress(barDeVie.getProgress() + 0.1);
    }

    @Override
    public int getNombre() {
        return nombreDeBanane;
    }

    @Override
    public void augmenterNombre() {
    nombreDeBanane++;
    }

    @Override
    public void diminuerNombre() {
    nombreDeBanane--;
    }

    @Override
    public ImageView getImageView() {
        return imageViewBanane;
    }

    @Override
    public Text getText() {
        if (nombreDeBanane >= 10){
            textNombreDeBanane.setText("x" + nombreDeBanane);
        }else{
            textNombreDeBanane.setText("x0" + nombreDeBanane);
        }
        return textNombreDeBanane;
    }
}
