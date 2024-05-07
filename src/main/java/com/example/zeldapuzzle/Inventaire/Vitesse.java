package com.example.zeldapuzzle.Inventaire;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Vitesse extends Entity implements Objet {
    int nombreDeVitesse = 0;
    Text textNombreDeVitesse;
    ImageView imageViewVitesse;
    @Override
    public void utiliser() {
        textNombreDeVitesse= new Text("x" + nombreDeVitesse);
        textNombreDeVitesse.setFill(Color.WHITE);

        try {
            imageViewVitesse = new ImageView(new Image(new FileInputStream("src/main/resources/assets/textures/bananeBackground.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //utiliser l'objet
        textNombreDeVitesse.setOnMouseClicked(mouseEvent -> {
            diminuerNombre();
        });
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
        return textNombreDeVitesse;
    }
}
