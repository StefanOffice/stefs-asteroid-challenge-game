package stef.asteroidchallenge.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.actor.Asteroid;
import stef.asteroidchallenge.actor.Explosion;
import stef.asteroidchallenge.actor.Laser;
import stef.asteroidchallenge.actor.RootActor;
import stef.asteroidchallenge.actor.SpaceShip;
import stef.asteroidchallenge.util.ActorCollector;
import stef.asteroidchallenge.util.AnimationCreator;

public class LevelScreen extends AbstractScreen {

    private static final int STARTING_ASTEROIDS = 8;
    private SpaceShip spaceship;

    private boolean playing;
    private RootActor msgWin;
    private RootActor msgGameOver;


    @Override
    public void initialize() {
        playing = true;

        RootActor space = new RootActor(0, 0, mainStage);
        space.setAnimation(AnimationCreator.loadTexture("space-1.png"));
        space.setSize(1280, 720);
        RootActor.setWorldBounds(space);

        spaceship = new SpaceShip(1280 / 2f, 720 / 2f, mainStage);

        //create the starting asteroids
        for (int i = 0; i < STARTING_ASTEROIDS; i++) {
            float startX;
            float startY;
            do {
                //randomly generate starting position
                startX = (float) (Math.random() * space.getWidth());
                startY = (float) (Math.random() * space.getHeight());
                //if the starting position is too close to the ship on either x or y axis,
                // recalculate the starting position of the asteroid
            } while ((startX < spaceship.getX() + 200 && startX > spaceship.getX() - 200)
                    || (startY < spaceship.getY() + 200 && startY > spaceship.getY() - 200));

            new Asteroid(startX, startY, mainStage);
        }

        //will be displayed in case the playere completes the level
        msgWin = new RootActor(0, 0);
        msgWin.setAnimation(AnimationCreator.loadTexture("msg-lvl-complete.png"));
        msgWin.setVisible(false);

        //will be displayed in case player is destroyed
        msgGameOver = new RootActor(0, 0);
        msgGameOver.setAnimation(AnimationCreator.loadTexture("msg-gameover-red.png"));

        uiTable.add(msgWin).center();
    }

    @Override
    public void update(float dt) {

        for (Asteroid asteroid : ActorCollector.getActiveInstanceList(mainStage, Asteroid.class)) {
            //if player gets hit by the asteroid
            if (asteroid.overlaps(spaceship)) {

                Explosion explosion = new Explosion(0, 0, mainStage);

                if (spaceship.getShield().getPower() <= 0) {
                    //create an explosion and center it at the spaceship
                    explosion.resize(2f);
                    explosion.centerAtActor(spaceship);
                    //remove the spaceship from the stage as it was destroyed by the asteroid
                    showEndMsg(false);
                    spaceship.remove();
                    spaceship.setPosition(-1000, -1000);
                } else {
                    //if player still has a shield, damage it
                    spaceship.getShield().modifyPower(-34);
                    explosion.centerAtActor(asteroid);
                    asteroid.remove();
                }
            }

            for (Laser laser : ActorCollector.getActiveInstanceList(mainStage, Laser.class)) {
                //if the laser hits the asteroid
                if (laser.overlaps(asteroid)) {
                    //create and explosion
                    Explosion explosion = new Explosion(0, 0, mainStage);
                    explosion.resize(1.5f);
                    explosion.centerAtActor(asteroid);
                    laser.remove();
                    asteroid.remove();
                }
            }
        }

        //check if all asteroids are destroyed - level complete
        if (playing && ActorCollector.getActiveInstanceCount(mainStage, Asteroid.class) == 0) {
            showEndMsg(true);
        }
    }

    @Override
    //all the user controls are defined here
    public boolean keyDown(int keycode) {

        // if the player presses 'space' on the keyboard, fire a laser
        if (playing && keycode == Input.Keys.SPACE)
            spaceship.shoot();

        return false;
    }

    /**
     * @param playerWon - true if the player completed the level
     */
    private void showEndMsg(boolean playerWon) {
        playing = false;
        //get the correct msg based on game status
        RootActor msgToShow = playerWon ? msgWin : msgGameOver;

        //put the msg in the slot
        uiTable.getCell(msgWin).setActor(msgToShow);
        //make the fade in effect
        msgToShow.setOpacity(0);
        msgToShow.addAction(Actions.fadeIn(2));
        msgToShow.setVisible(true);
    }


}
