package com.example.zeldapuzzle;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.KeyInputBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.example.zeldapuzzle.Inventaire.Equipement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class Player extends Component {

    private double speed = 4;

    private int vie = 100;

    private int shield = 50;

    private PhysicsComponent physics;

    @Override
    public void onAdded() {

    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }

    public void moveRight(){
        physics = entity.getComponent(PhysicsComponent.class);
        Vec2 vec2 =new Vec2(4,0);
//        physics.setVelocityX(100);
        physics.setBodyLinearVelocity(vec2);

    }

    public void moveleft(){
        physics = entity.getComponent(PhysicsComponent.class);
        Vec2 vec2 =new Vec2(-4,0);
//        physics.setVelocityX(-100);
        physics.setBodyLinearVelocity(vec2);

    }

    public void moveUp(){
        physics = entity.getComponent(PhysicsComponent.class);
        Vec2 vec2 =new Vec2(0,4);
//        physics.setVelocityX(-100);
        physics.setBodyLinearVelocity(vec2);

    }

    public void moveDown(){
        physics = entity.getComponent(PhysicsComponent.class);
        Vec2 vec2 =new Vec2(0,-4);
//        physics.setVelocityX(-100);
        physics.setBodyLinearVelocity(vec2);

    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getVie() {
        return vie;
    }



}
