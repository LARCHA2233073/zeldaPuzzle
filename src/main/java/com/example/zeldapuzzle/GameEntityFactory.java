package com.example.zeldapuzzle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class GameEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity spawnPlayer(SpawnData data){
        Image imagePlayer;
        try {
            imagePlayer = new Image(new FileInputStream("src/main/resources/assets/textures/zelda3.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imagePlayer);

        //Physics
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        HitBox hitBox = new HitBox(BoundingShape.box(5,5));
        return FXGL.entityBuilder()
                .type(MainGameApp.EntityType.PLAYER)
                .at(100,100)
                .viewWithBBox(imageView)
                .scale(0.2,0.2)
                .with(new Player())
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Spawns("background")
    public Entity background(SpawnData data) {
        Image imageMap;
        try {
            imageMap = new Image(new FileInputStream("src/main/resources/assets/textures/BeginingMap.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imageMap);
        return entityBuilder(data)
                .view(imageView)
                .buildAndAttach();


    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("dungeonEntry")
    public Entity dungeonEntry(SpawnData data) {
        Image dungeonImage;
        try {
            dungeonImage = new Image(new FileInputStream("src/main/resources/assets/textures/Map.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(dungeonImage);
        Rectangle rectangle = new Rectangle(150,150,Color.BLUE);
        return entityBuilder(data)
                .type(MainGameApp.EntityType.DOOR)
                .viewWithBBox(rectangle)
                .with(new CollidableComponent(true))
                .buildAndAttach();


    }
    @Spawns("smallTree")
    public Entity smallTree(SpawnData data) {

        return entityBuilder(data)
                .from(data)
                .type(MainGameApp.EntityType.SMALLTREE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .buildAndAttach();


    }
}
