package com.example.zeldapuzzle;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.example.zeldapuzzle.animation.AnimationComponentMobPassive;

import java.io.FileNotFoundException;
import java.util.Timer;

import static java.lang.System.nanoTime;

public class SensorComponent extends Component {
    private PhysicsComponent physics = new PhysicsComponent();
    MainGameApp mainGameApp = new MainGameApp();
    boolean trueIfVertical;
    int speedx;
    int speedy;
    public SensorComponent() throws FileNotFoundException {}

    @Override
    public void onUpdate(double tpf) {
        if (nanoTime() % 500 == 0) {
           int random = 1;
           if (random == 0)
               moveLeft();
           if (random == 1)
               moveRight();
           if (random == 2)
               moveDown();
           if (random == 3)
               moveUp();
        }
    }
    public void moveRight() {
        speedx = 150;
        trueIfVertical = false;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(4, 0);
        physics.setBodyLinearVelocity(vec2);
    }
    public void moveLeft() {
        speedx = -150;
        trueIfVertical = false;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(-4, 0);
        physics.setBodyLinearVelocity(vec2);
    }
    public void moveUp() {
        speedy = -150;
        trueIfVertical = true;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(0, 4);
        physics.setBodyLinearVelocity(vec2);
    }
    public void moveDown() {
        speedy = 150;
        trueIfVertical = true;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(0, -4);
        physics.setBodyLinearVelocity(vec2);
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }

    public int getSpeedx() {
        return speedx;
    }

    public int getSpeedy() {
        return speedy;
    }

    public boolean isTrueIfVertical() {
        return trueIfVertical;
    }
}
