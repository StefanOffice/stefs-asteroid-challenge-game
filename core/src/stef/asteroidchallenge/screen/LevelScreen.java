package stef.asteroidchallenge.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import java.util.Date;

import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.Player;
import stef.asteroidchallenge.actor.Asteroid;
import stef.asteroidchallenge.actor.Background;
import stef.asteroidchallenge.actor.Explosion;
import stef.asteroidchallenge.actor.Laser;
import stef.asteroidchallenge.actor.RootActor;
import stef.asteroidchallenge.actor.SpaceShip;
import stef.asteroidchallenge.util.ActorCollector;
import stef.asteroidchallenge.util.AnimationCreator;
import stef.asteroidchallenge.util.ScoreManager;

public class LevelScreen extends AbstractScreen {

    private static final int STARTING_ASTEROIDS = 50;
    private SpaceShip spaceship;

    private boolean playing;
    private RootActor msgWin;
    private RootActor msgGameOver;

    private int score = 0;
    private Label scoreLabel;

    private TextField playerNameTextField;
    private Label infoLabel;
    private Label errorLabel;

    @Override
    public void initialize() {
        playing = true;

        Background layer1 = new Background("backgrounds/space-1.png", mainStage, true);
        Background layer2 = new Background("backgrounds/starfield.png", mainStage, false);
        layer2.addFadeInAndOut(0.5f, 0, 4, 0.6f, 5);

        //initialize the spaceship at the center of the screen
        spaceship = new SpaceShip(AsteroidGame.getGameWidth() / 2f, AsteroidGame.getGameHeight() / 2f, mainStage);

        //create the starting asteroids
        for (int i = 0; i < STARTING_ASTEROIDS; i++) {
            float startX;
            float startY;
            do {
                //randomly generate starting position
                startX = (float) (Math.random() * AsteroidGame.getGameWidth());
                startY = (float) (Math.random() * AsteroidGame.getGameHeight());
                //if the starting position is too close to the ship on either x or y axis,
                // recalculate the starting position of the asteroid
            } while ((startX < spaceship.getX() + 200 && startX > spaceship.getX() - 200)
                    || (startY < spaceship.getY() + 200 && startY > spaceship.getY() - 200));

            new Asteroid(startX, startY, mainStage);
        }

        //will be displayed in case the player completes the level
        msgWin = new RootActor(0, 0);
        msgWin.setAnimation(AnimationCreator.loadTexture("text/msg-lvl-complete.png"));
        msgWin.setVisible(false);

        //will be displayed in case player is destroyed
        msgGameOver = new RootActor(0, 0);
        msgGameOver.setAnimation(AnimationCreator.loadTexture("text/msg-gameover-red.png"));

        //upper right corner display of current score
        scoreLabel = new Label("Score: 0", AsteroidGame.labelStyle);

        //label to be displayed if player gets a best score
        infoLabel = new Label("Congratulations! New HighScore!\nPlease enter your name below and press 'Enter'.", AsteroidGame.labelStyle);
        infoLabel.setWrap(true);
        infoLabel.setAlignment(Align.center);
        infoLabel.setVisible(false);

        //field for player to enter the name for highscore
        playerNameTextField = new TextField("", AsteroidGame.skin);
        playerNameTextField.setVisible(false);

        //in case player get's a highscore and doesn't enter a name
        errorLabel = new Label("", AsteroidGame.labelStyle);
        errorLabel.setVisible(false);

        //Add all UI elements to the table
        uiTable.add(scoreLabel).right().expandX().pad(10);

        uiTable.row();
        uiTable.add().expandY();

        uiTable.row();
        uiTable.add(msgWin).center();

        uiTable.row();
        uiTable.pack();
        float tableWidth = uiTable.getWidth();
        uiTable.add(infoLabel).width(tableWidth * 3 / 4);

        uiTable.row();
        uiTable.add(playerNameTextField).padTop(20);

        uiTable.row();
        uiTable.add().expandY();
        uiTable.debugAll();
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
                    score += 110;
                    //create and explosion
                    Explosion explosion = new Explosion(0, 0, mainStage);
                    explosion.resize(1.5f);
                    explosion.centerAtActor(asteroid);
                    //spawn new asteroids if the destroyed asteroids was large enough to break
                    if (asteroid.getWidth() > 32) {
                        Asteroid asteroid1 = new Asteroid(asteroid, laser.getMotionAngle(), mainStage);
                        Asteroid asteroid2 = new Asteroid(asteroid, laser.getMotionAngle() - 45, mainStage);
                        Asteroid asteroid3 = new Asteroid(asteroid, laser.getMotionAngle() + 45, mainStage);
                    }
                    laser.remove();
                    asteroid.remove();
                }
            }
        }

        //update the score label
        scoreLabel.setText("Score: " + score);

        //check if all asteroids are destroyed - level complete
        if (playing && ActorCollector.getActiveInstanceCount(mainStage, Asteroid.class) == 0) {
            showEndMsg(true);
        }
    }

    @Override
    //all the user controls are defined here
    public boolean keyDown(int keycode) {

        // if the player presses 'space' on the keyboard, fire a laser
        if (playing && keycode == Input.Keys.SPACE) {
            //reduce score for each laser used
            score -= 10;
            spaceship.shoot();
        }

        if (playing && keycode == Input.Keys.R) {
                score -= 50;
                spaceship.warp();
        }

        //if the game is over and esc key is pressed go back to the main menu
        if (!playing && !playerNameTextField.isVisible()
                && Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            AsteroidGame.setActiveScreen(new MenuScreen());

        //if the player achieved a highscore and pressed enter
        if (!playing && Gdx.input.isKeyPressed(Input.Keys.ENTER))
            //and didn't enter a valid name
            if (playerNameTextField.isVisible() && (playerNameTextField.getText().trim().equals("") || playerNameTextField.getText() == null)) {
                //display the error label
                errorLabel.setVisible(true);
                errorLabel.setText("Please enter your name, then press 'Enter'.");
            } else {
                //otherwise save it to the leaderboard and go back to main menu
                saveHighScore();
                AsteroidGame.setActiveScreen(new MenuScreen());
            }

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

        //in the case that player achieved a highscore display a text field for the player name
        if (ScoreManager.isHighScore(score)) {
            playerNameTextField.setVisible(true);
        } else
            infoLabel.setText("Press 'ESC' to go back to main menu.");

        infoLabel.setVisible(true);
    }

    /**
     * Add the current player to the leaderboard
     */
    private void saveHighScore() {
        ScoreManager.addPlayerToLeaderBoard(
                new Player(playerNameTextField.getText().replace(";", " ").trim(), score, new Date()));
    }


}
