package com.example.zeldapuzzle.animation;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AnimationComponent extends Component {

    private PhysicsComponent physics;
    boolean trueIfVertical;
    private int speedx = 0;
    private int speedy = 0;

    private AnimatedTexture texture;
    private AnimationChannel animWalkUp, animIdle, animWalkLeft, animWalkDown, animWalkRight;

    public AnimationComponent() throws FileNotFoundException {
        animIdle = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterWalk.png")), 9, 64, 64, Duration.seconds(1), 0, 0);
        animWalkLeft = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterWalk2.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkRight = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterRight.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkUp = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterUp.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        animWalkDown = new AnimationChannel(new Image(new FileInputStream("src/main/resources/assets/textures/characterDown.png")), 9, 64, 64, Duration.seconds(1), 0, 8);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {

        if (trueIfVertical) {
          //  entity.translateY(speedy * tpf);

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
            speedy = (int) (speedy * 0.9);
            if (FXGLMath.abs(speedy) < 1) {
                speedy = 0;
                texture.loopAnimationChannel(animIdle);
            }

        }
        else {

           // entity.translateX(speedx * tpf);

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
            speedx = (int) (speedx * 0.9);
            if (FXGLMath.abs(speedx) < 1) {
                speedx = 0;
                texture.loopAnimationChannel(animIdle);
            }

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
}
