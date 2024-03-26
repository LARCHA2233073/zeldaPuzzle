package com.example.zeldapuzzle;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
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

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Arrow extends Entity{

    ArrayList<Double> listeDonne = new ArrayList<>();

    PhysicsComponent physics = new PhysicsComponent();

    Vec2 vec2;

    Rectangle arrow;
    public Arrow(Vec2 vec2,int x,int y)  {

        //Creation du rectangle
        arrow = new Rectangle(100,100,Color.RED);
        this.getViewComponent().addChild(arrow);
        this.vec2=vec2;
        this.setX(x);
        this.setY(y);
        desactivéPhysique();

        arrow.setOnMousePressed(event -> {
            System.out.println("Bravo");
            listeDonne.add(event.getSceneX());
            listeDonne.add(event.getSceneY());
        });

        arrow.setOnMouseReleased(event -> {
            this.activerPhysique();
            System.out.println("t bon");
            System.out.println(listeDonne);
            getGameScene().update(0);

        });
    }

    private void activerPhysique() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                physics.applyBodyForce(vec2,vec2);

            }
        };
        physics.setOnPhysicsInitialized(runnable);
        FixtureDef fd = new FixtureDef();
        fd.setDensity(0.7f);
        fd.setRestitution(0.3f);
        physics.setFixtureDef(fd);
        physics.setBodyType(BodyType.DYNAMIC);
        this.addComponent(physics);
    }

    private void desactivéPhysique() {
        physics.setBodyType(BodyType.STATIC);

    }




}
