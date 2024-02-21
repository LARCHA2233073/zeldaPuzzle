package com.example.zeldapuzzle;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class Player extends Component {


    private double speed = 2;


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
}
