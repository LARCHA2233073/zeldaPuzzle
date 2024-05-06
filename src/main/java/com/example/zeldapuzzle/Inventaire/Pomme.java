package com.example.zeldapuzzle.Inventaire;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Pomme extends Entity implements Objet {

    int nombreDePomme = 0;
    Text textNombreDePomme;

    ImageView imageViewPomme;
    public Pomme()  {
        textNombreDePomme = new Text("x" + nombreDePomme);
        textNombreDePomme.setFill(Color.WHITE);

        try {
            imageViewPomme = new ImageView(new Image(new FileInputStream("src/main/resources/assets/textures/PommeBackground.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //utiliser l'objet

    }
    @Override
    public void utiliser() {

    }

    @Override
    public int getNombre() {
        return nombreDePomme;
    }



    @Override
    public void augmenterNombre() {
        nombreDePomme++;
    }

    @Override
    public void diminuerNombre() {
        nombreDePomme--;
    }


    @Override
    public ImageView getImageView() {
        return imageViewPomme;
    }

    @Override
    public Text getText() {

        if (nombreDePomme >= 10){
            textNombreDePomme.setText("x" + nombreDePomme);
        }else{
            textNombreDePomme.setText("x0" + nombreDePomme);
        }
        return textNombreDePomme;
    }


}
