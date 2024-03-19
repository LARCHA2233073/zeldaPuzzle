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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Arrow extends Entity{
    public Arrow(Vec2 vec2,int x,int y){
        Rectangle rectangle = new Rectangle(200,200, Color.RED);
        rectangle.setX(x);
        rectangle.setY(y);
        this.getViewComponent().addChild(rectangle);

        PhysicsComponent physics = new PhysicsComponent();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                physics.applyBodyForce(vec2,vec2);
            }
        };
        physics.setOnPhysicsInitialized(runnable);
        physics.setBodyType(BodyType.DYNAMIC);
        FixtureDef fd = new FixtureDef();
        fd.setDensity(0.7f);
        fd.setRestitution(0.3f);
        physics.setFixtureDef(fd);
        this.addComponent(physics);
    }



}
