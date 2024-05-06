package com.example.zeldapuzzle;

import com.almasb.fxgl.core.math.Vec2;
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
import com.example.zeldapuzzle.animation.AnimationComponentPlayer;
import com.example.zeldapuzzle.animation.AnimationComponentMobPassive;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class GameEntityFactory implements EntityFactory {
    ArrayList<Double> listeDonne = new ArrayList<>();
    Entity arrowMove;


    public GameEntityFactory() throws FileNotFoundException {
    }

    @Spawns("playerMapPrincipal")
    public Entity spawnPlayerMapPrincipal(SpawnData data) throws FileNotFoundException {
        HitBox box = new HitBox(BoundingShape.polygon(20,33,25,45,33,55,43,40,42,15,41,10,19.5,7));
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .type(MainGameApp.EntityType.PLAYERMAPPRINCIPAL)
                .at(400, 100)
                .scale(1.3, 1.3)
                .bbox(box)
                .with(new AnimationComponentPlayer())
                .with(new CollidableComponent(true))
                .with(physics)
                .buildAndAttach();
    }

    @Spawns("playerMapDongeon")
    public Entity spawnPlayerMapDongeon(SpawnData data) throws FileNotFoundException {
        HitBox box = new HitBox(BoundingShape.polygon(20,33,25,45,33,55,43,40,42,15,41,10,19.5,7));
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .type(MainGameApp.EntityType.PLAYERMAPDONGEON)
                .at(400, 100)
                .scale(1, 1)
                .bbox(box)
                .with(new AnimationComponentPlayer())
                .with(new CollidableComponent(true))
                .with(physics)
                .buildAndAttach();
    }

    @Spawns("Tree")
    public Entity Tree(SpawnData data) {

        return entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();


    }

    @Spawns("pomme")
    public Entity pomme(SpawnData data) {
        Image imagePomme;
        try {
            imagePomme = new Image(new FileInputStream("src/main/resources/assets/textures/PommeTransparente.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imagePomme);
        return entityBuilder(data)
                .type(MainGameApp.EntityType.POMME)
                .viewWithBBox(imageView)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("banane")
    public Entity banane(SpawnData data) {
        Image imageBanane;
        try {
            imageBanane = new Image(new FileInputStream("src/main/resources/assets/textures/bananeTransparente.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imageBanane);
        return entityBuilder(data)
                .type(MainGameApp.EntityType.BANANE)
                .viewWithBBox(imageView)
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

    @Spawns("mur")
    public Entity mur(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.MUR)
                .bbox(new HitBox(BoundingShape.polygonFromDoubles(data.<Polygon>get("polygon").getPoints())))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("maisonObjet")
    public Entity maisonObjet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.MAISONOBJET)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("porteMaisonPlayer")
    public Entity porteMaisonPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.PORTEMAISONPLAYER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("positionHaut")
    public Entity positionHaut(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.POSITIONHAUT)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(false))
                .build();

    }

    @Spawns("positionBas")
    public Entity positionBas(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.POSITIONBAS)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(false))
                .build();

    }


    @Spawns("ascenseurD")
    public Entity ascenseurD(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.ASCENSEURD)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();

    }
    @Spawns("ascenseurM")
    public Entity ascenseurM(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.ASCENSEURM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("ascenceur")
    public Entity ascenceur(SpawnData data) {

        Image imageAscenceur;
        PhysicsComponent physicsComponent = new PhysicsComponent();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                physicsComponent.getBody().setType(BodyType.KINEMATIC);
            }
        };
        physicsComponent.setOnPhysicsInitialized(runnable);
        try {
            imageAscenceur = new Image(new FileInputStream("src/main/resources/assets/textures/Ascenceur.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imageAscenceur);
        return FXGL.entityBuilder(data)
                .at(800,608)
                .type(MainGameApp.EntityType.ASCENSEUR)
                .viewWithBBox(imageView)
                .with(physicsComponent)
                .with(new CollidableComponent(true))
                .build();
    }


    @Spawns("murTraverse")
    public Entity murTraverse(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.MURTRAVERSE)
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

        Image imageCible;
        try {
            imageCible = new Image(new FileInputStream("src/main/resources/assets/textures/Cible.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(imageCible);
        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.CIBLE)
                .viewWithBBox(imageView)
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
    @Spawns("danger")
    public Entity danger(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.DANGER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(false))
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

    @Spawns("feu")
    public Entity feu(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.FEU)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();

    }



    @Spawns("entrerDonjon")
    public Entity entrerDonjon(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();

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

    AnimationComponentMobPassive animationComponentMobPassive = new AnimationComponentMobPassive();
    @Spawns("mobPassive")
    public Entity mobPassive(SpawnData data) throws FileNotFoundException {

        HitBox box = new HitBox(BoundingShape.polygon(20,33,25,45,33,55,43,40,42,15,41,10,19.5,7));
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        //movement AI
        return FXGL.entityBuilder()
                .type(MainGameApp.EntityType.MOBPASSIVE)
                .at(400, 100)
                .scale(1.3, 1.3)
                .bbox(box)
                .with(animationComponentMobPassive)
                .with(new CollidableComponent(true))
                .with(physics)
                .buildAndAttach();
    }
    @Spawns("objetsDehors")
    public Entity objetsDehors(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(MainGameApp.EntityType.OBJETDEHORS)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();


    }

    @Spawns("arrow")
    public Entity arrow(SpawnData data) {

        Rectangle arrow = new Rectangle(40,10,Color.RED);
        arrow.setOnMousePressed(event -> {
            listeDonne.add(event.getSceneX());
            listeDonne.add(event.getSceneY());
        });

        arrow.setOnMouseReleased(event -> {
            listeDonne.add(event.getSceneX());
            listeDonne.add(event.getSceneY());
            arrowMove = spawn("arrowMove");

        });

        return entityBuilder(data)
                .type(MainGameApp.EntityType.ARROW)
                .viewWithBBox(arrow)
                .buildAndAttach();
    }

    @Spawns("arrowMove")
    public Entity arrowMove(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        Vec2 arrowVecteur = new Vec2((listeDonne.get(0) - listeDonne.get(2))*10,(listeDonne.get(1) - listeDonne.get(3)) * -10);
        listeDonne.clear();
        arrow(data).removeFromWorld();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                physics.applyBodyForce(arrowVecteur,arrowVecteur);

            }
        };
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setOnPhysicsInitialized(runnable);
        Rectangle arrow = new Rectangle(40,10,Color.RED);
        return entityBuilder(data)
                .at(561.333,817.333)
                .viewWithBBox(arrow)
                .type(MainGameApp.EntityType.ARROWMOVE)
                .collidable()
                .with(physics)
                .buildAndAttach();
    }

    public AnimationComponentMobPassive getAnimationComponentMobPassive() {
        return animationComponentMobPassive;
    }
}
