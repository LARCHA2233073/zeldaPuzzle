package com.example.zeldapuzzle.Inventaire;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public interface Objet  {
     void utiliser();
     int getNombre();
     void augmenterNombre();
     void diminuerNombre();
     ImageView getImageView();
     Text getText();


}
