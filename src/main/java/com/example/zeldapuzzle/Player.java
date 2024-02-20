package com.example.zeldapuzzle;

import com.almasb.fxgl.entity.component.Component;

public class Player extends Component {
    private double speed = 2;

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
