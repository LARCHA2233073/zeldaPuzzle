package com.example.zeldapuzzle.animation;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AnimationComponentPlayer extends Component {

    private PhysicsComponent physics = new PhysicsComponent();
    boolean trueIfVertical;
    private int speedx = 0;
    private int speedy = 0;
    private Vec2 vec;
    boolean isDead = false;
    private static float intVec;


    private AnimatedTexture texture;
    private AnimationChannel animWalkUp, animIdle, animWalkLeft, animWalkDown, animWalkRight, animDeath;

    public AnimationComponentPlayer() throws FileNotFoundException {
        intVec = 4;
        animIdle = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterWalk.png")), 9, 64, 64, Duration.seconds(1), 0, 0);
        animWalkLeft = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterWalk2.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkRight = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterRight.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkUp = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterUp.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkDown = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterDown.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animDeath = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterDeath.png")), 6, 64, 64, Duration.seconds(0.95), 0, 5);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {

        if (!isDead) {
        if (trueIfVertical) {
         //   entity.translateY(speedy * tpf);

            //up
            if (speedy < 0) {
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
            speedy = (int) (speedy * 0.3);
            if (FXGLMath.abs(speedy) < 1) {
                speedy = 0;
                texture.loopAnimationChannel(animIdle);

            }

        }
        else {

       //     entity.translateX(speedx * tpf);

            //left
            if (speedx < 0) {
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
            speedx = (int) (speedx * 0.3);
            if (FXGLMath.abs(speedx) < 1) {
                speedx = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
        }
    }


    public void moveRight() {
        speedx = 75;
        trueIfVertical = false;

        physics = getEntity().getComponent(PhysicsComponent.class);
        vec = new Vec2(intVec, 0);
        physics.setBodyLinearVelocity(vec);


    }

    public void moveLeft() {
        speedx = -75;
        trueIfVertical = false;

        physics = getEntity().getComponent(PhysicsComponent.class);
        vec = new Vec2(-intVec, 0);
        physics.setBodyLinearVelocity(vec);


    }
    public void moveUp() {
        speedy = -75;
        speedx = 0;
        trueIfVertical = true;

        physics = getEntity().getComponent(PhysicsComponent.class);
        vec = new Vec2(0, intVec);
        physics.setBodyLinearVelocity(vec);


    }
    public void moveDown() {
        speedy = 75;
        speedx = 0;
        trueIfVertical = true;

        physics = getEntity().getComponent(PhysicsComponent.class);
        vec = new Vec2(0, -intVec);
        physics.setBodyLinearVelocity(vec);


    }
    public void startAnimIdle() {
        texture.loopAnimationChannel(animIdle);
    }

    public static void setIntVec(float intVec) {
        AnimationComponentPlayer.intVec = intVec;
    }
    public void startDeath() {
        isDead = true;
        texture.playAnimationChannel(animDeath);
    }

    public static void augmenterIntVec() {
        intVec = 20;
    }



    public PhysicsComponent getPhysics() {
        return physics;
    }
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

}
