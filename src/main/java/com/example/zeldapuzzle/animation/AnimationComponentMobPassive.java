package com.example.zeldapuzzle.animation;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//essayer de regler le probleme de vitesse en changeant le speedy avec les vecteurs etc.

public class AnimationComponentMobPassive extends Component {
    Point2D vecY = new Point2D(0, -4);
    Point2D vecX = new Point2D(-4, 0);

    //private PhysicsComponent physics = getEntity().getComponent(PhysicsComponent.class);
    private PhysicsComponent physics;
    boolean trueIfVertical;
    private int speedx = 0;
    private int speedy = 0;

    private AnimatedTexture texture;
    private AnimationChannel animWalkUp, animIdle, animWalkLeft, animWalkDown, animWalkRight;

    public AnimationComponentMobPassive() throws FileNotFoundException {

        animIdle = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/mobPassiveWalkLeft.png")), 9, 64, 64, Duration.seconds(1), 0, 0);
        animWalkLeft = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/mobPassiveWalkLeft.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkRight = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/mobPassiveWalkRight.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkUp = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/mobPassiveWalkUp.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkDown = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/mobPassiveWalkDown.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        texture = new AnimatedTexture(animIdle);

    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

    }
/*
    @Override
    public void onUpdate(double tpf) {

        if (trueIfVertical) {
            entity.translateY( (speedy) * tpf);

            //up
            if (speedy < 0)
            {
                if (texture.getAnimationChannel() == animIdle) {
                    texture.loopAnimationChannel(animWalkUp);
                }

            }
            //down
            else {
                if (texture.getAnimationChannel() == animIdle) {
                    texture.loopAnimationChannel(animWalkDown);
                }

            }
            speedy = (int) (speedy * 0.9);
            if (FXGLMath.abs(speedy) < 1) {
                speedy = 0;
                texture.loopAnimationChannel(animIdle);
            }

        }
        else {

            entity.translateX((speedx) * tpf);

            //left
            if (speedx < 0)
            {
                if (texture.getAnimationChannel() == animIdle) {
                    texture.loopAnimationChannel(animWalkLeft);
                }

            }
            //right
            else {
                if (texture.getAnimationChannel() == animIdle) {
                    texture.loopAnimationChannel(animWalkRight);
                }

            }
            speedx = (int) (speedx * 0.9);
            if (FXGLMath.abs(speedx) < 1) {
                speedx = 0;
                texture.loopAnimationChannel(animIdle);
            }

        }
    }

 */

    public void moveRight() {
        speedx = 150;
        trueIfVertical = false;


        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(1.5, 0);
        physics.setBodyLinearVelocity(vec2);

        texture.loopAnimationChannel(animWalkRight);


    }
    public void moveLeft() {
        speedx = -150;
        trueIfVertical = false;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(-1.5, 0);
        physics.setBodyLinearVelocity(vec2);

        texture.loopAnimationChannel(animWalkLeft);
    }
    public void moveUp() {
        speedy = -150;
        trueIfVertical = true;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(0, 1.5);
        physics.setBodyLinearVelocity(vec2);

        texture.loopAnimationChannel(animWalkUp);
    }
    public void moveDown() {
        speedy = 150;
        trueIfVertical = true;

        physics = getEntity().getComponent(PhysicsComponent.class);
        Vec2 vec2 = new Vec2(0, -1.5);
        physics.setBodyLinearVelocity(vec2);

        texture.loopAnimationChannel(animWalkDown);
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }

    public void setSpeedx(int speedx) {
        this.speedx = speedx;
    }
    public void setSpeedy(int speedx) {
        this.speedy = speedx;
    }
    public void stopMovement() {
        physics = getEntity().getComponent(PhysicsComponent.class);
        physics.setBodyLinearVelocity(new Vec2(0,0));
        physics.setBodyType(BodyType.STATIC);
        texture.loopAnimationChannel(animIdle);
}
    public void changeBodyType(BodyType bodyType) {
        physics = getEntity().getComponent(PhysicsComponent.class);
        physics.setBodyType(bodyType);
    }
}
