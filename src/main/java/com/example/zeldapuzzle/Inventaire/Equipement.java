package com.example.zeldapuzzle.Inventaire;

import com.example.zeldapuzzle.Player;

public abstract class Equipement implements Objet {

    public void Équiper(Player player) {
        player.équipé(this);
    }
}
