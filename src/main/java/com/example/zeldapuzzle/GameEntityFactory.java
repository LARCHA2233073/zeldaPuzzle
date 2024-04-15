package com.example.zeldapuzzle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsUnitConverter;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.example.zeldapuzzle.animation.AnimationComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class GameEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity spawnPlayer(SpawnData data) throws FileNotFoundException {
        /*
        Image imagePlayer;
        try {
            imagePlayer = new Image(new FileInputStream("src/main/resources/assets/textures/zelda3.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imagePlayer);


         */
        Image imagePlayer = new Image(new FileInputStream("src/main/resources/assets/textures/character.png"));
        //Physics

        HitBox box = new HitBox(BoundingShape.polygon(20,33,25,45,33,55,43,40,42,15,41,10,19.5,7));
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
//        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));
        return FXGL.entityBuilder()
                .type(MainGameApp.EntityType.PLAYER)
                .at(400, 100)
                .scale(2, 2)
                .bbox(box)
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .with(physics)
                .buildAndAttach();

        /*
        return FXGL.entityBuilder()
                .type(MainGameApp.EntityType.PLAYER)
                .at(100,100)
                .viewWithBBox(imageView)
                .scale(0.2,0.2)
                .with(new Player())
//                .with(new CollidableComponent(true))
                .with(physics)
                .buildAndAttach();

         */
    }

    @Spawns("Tree")
    public Entity Tree(SpawnData data) {

        return entityBuilder(data)
                .type(MainGameApp.EntityType.SMALLTREE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();


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





    @Spawns("plateform")
    public Entity plateform(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("triangle")
    public Entity triangle(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.TRIANGLE)
                .bbox(new HitBox(BoundingShape.polygon(0,0,65.3333,-21.111,62.6667,4)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }


    @Spawns("cible")
    public Entity cible(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.CIBLE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("stationTire")
    public Entity stationTire(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.STATIONTIRE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("boite")
    public Entity boite(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.BOITE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("statue")
    public Entity statue(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.STATUE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
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
                .at(850,-130)
                .type(MainGameApp.EntityType.DOOR)
                .viewWithBBox(rectangle)
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .buildAndAttach();

    }
    @Spawns("dungeon")
    public Entity dungeon(SpawnData data) {
        Image dungeon;
        try {
            dungeon = new Image(new FileInputStream("src/main/resources/assets/textures/dongeon.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(dungeon);
        return entityBuilder(data)
                .view(imageView)
                .buildAndAttach();
    }


}
