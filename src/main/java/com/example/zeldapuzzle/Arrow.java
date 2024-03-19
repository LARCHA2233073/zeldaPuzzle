package com.example.zeldapuzzle;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Arrow extends Entity{

    ArrayList<Double> listeDonne = new ArrayList<>();

    Rectangle arrow;
    public Arrow(Vec2 vec2,int x,int y){

        //Creation du rectangle
        arrow = new Rectangle(200,200, Color.RED);
        this.getViewComponent().addChild(arrow);
        arrow.setX(x);
        arrow.setY(y);
        arrow.setVisible(true);

        //Creation de sa physique
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        FixtureDef fd = new FixtureDef();
        fd.setDensity(0.7f);
        fd.setRestitution(0.3f);
        physics.setFixtureDef(fd);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                physics.applyBodyForce(vec2,vec2);

            }
        };

        arrow.setOnMousePressed(event -> {
            System.out.println("Bravo");
            listeDonne.add(event.getSceneX());
            listeDonne.add(event.getSceneY());
        });

        arrow.setOnMouseReleased(event -> {
            System.out.println("t bon");
            listeDonne.add(event.getSceneX());
            listeDonne.add(event.getSceneY());

            this.addComponent(physics);
            physics.setOnPhysicsInitialized(runnable);

        });
    }



}
