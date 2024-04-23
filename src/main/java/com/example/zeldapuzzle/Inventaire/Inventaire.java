package com.example.zeldapuzzle.Inventaire;

import com.almasb.fxgl.app.scene.SceneFactory;
import javafx.scene.Parent;
import javafx.scene.SubScene;

import java.util.ArrayList;

public class Inventaire extends SubScene {

    ArrayList<Objet> inventaire = new ArrayList<>();

    public Inventaire(Parent parent, double v, double v1) {
        super(parent, v, v1);
    }

}
