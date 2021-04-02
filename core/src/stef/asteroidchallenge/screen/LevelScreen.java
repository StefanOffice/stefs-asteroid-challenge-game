package stef.asteroidchallenge.screen;


import stef.asteroidchallenge.actor.Asteroid;
import stef.asteroidchallenge.actor.RootActor;
import stef.asteroidchallenge.actor.SpaceShip;
import stef.asteroidchallenge.util.AnimationCreator;

public class LevelScreen extends AbstractScreen {

    private SpaceShip spaceship;

    @Override
    public void initialize() {
        RootActor space = new RootActor(0,0, mainStage);
        space.setAnimation(AnimationCreator.loadTexture("space-1.png"));
        space.setSize(1280, 720);
        RootActor.setWorldBounds(space);

        spaceship = new SpaceShip(1280/2f,720/2f, mainStage);

        new Asteroid(1000, 600, mainStage);
        new Asteroid(700, 600, mainStage);
        new Asteroid(400, 600, mainStage);
        new Asteroid(200, 600, mainStage);
        new Asteroid(1000, 100, mainStage);
        new Asteroid(700, 100, mainStage);
        new Asteroid(400, 100, mainStage);
        new Asteroid(200, 100, mainStage);
    }

    @Override
    public void update(float dt){

    }
}
