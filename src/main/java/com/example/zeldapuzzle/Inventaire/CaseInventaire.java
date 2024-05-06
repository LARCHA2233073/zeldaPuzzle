package com.example.zeldapuzzle.Inventaire;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CaseInventaire implements Objet{
    Image imageInventaire;

    public CaseInventaire()  {
        try {
            imageInventaire = new Image(new FileInputStream("src/main/resources/assets/textures/caseInventaire.png"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void utiliser() {

    }

    @Override
    public int getNombre() {
        return 0;
    }

    @Override
    public void augmenterNombre() {

    }


    @Override
    public void diminuerNombre() {

    }


    @Override
    public ImageView getImageView() {
        return new ImageView(imageInventaire);
    }

    @Override
    public Text getText() {
        Text textCaseInventaire = new Text("x00");
        textCaseInventaire.setFill(Color.WHITE);
        return textCaseInventaire;
    }


}
