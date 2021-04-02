package stef.asteroidchallenge.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RootActor extends Group{

    private static Rectangle worldBounds;

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float minSpeed;
    private float deceleration;


    public RootActor(float x, float y, Stage s) {
        this(x, y);
        s.addActor(this);
    }

    public RootActor(float x, float y) {
        super();
        setPosition(x, y);
        animation = null;

        //keeping track of how long the animation has been playing
        //therefore which image should be displayed
        elapsedTime = 0;
        animationPaused = false;

        //initialize properties used for movement
        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        maxSpeed = 1000;
        minSpeed = 0;
        deceleration = 0;
    }


    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        elapsedTime = 0;
        //get the first image of animation
        TextureRegion tr = animation.getKeyFrame(0);
        float width = tr.getRegionWidth();
        float height = tr.getRegionHeight();
        //update actor size to match the animation size (set to the width and height of the first image of animation)
        this.setSize(width, height);
        //point around which the actor should be rotated
        this.setOrigin(width / 2, height / 2);
    }


    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }

    public void addForwardAcceleration() {
        accelerationVec.add(new Vector2(acceleration, 0).setAngleDeg(this.getRotation()));
    }

    /**
     * Internal method that handles movement of the actor
     * @param dt - elapsed time since the previous game loop
     */
    public void applyMovement(float dt) {
        //apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);
//        System.out.println(accelerationVec.x + " "  + accelerationVec.y);
        float speed = getSpeed();

        //decrease speed(decelerate) when not accelerating
        if (accelerationVec.len() == 0)
            speed -= deceleration * dt;

        //keep speed within set bounds
        speed = MathUtils.clamp(speed, minSpeed, maxSpeed);

        //update velocity
        setSpeed(speed);

        //apply velocity
        this.moveBy(velocityVec.x * dt, velocityVec.y * dt);

        //reset acceleration
        accelerationVec.set(0, 0);
    }

    /**
     * Treat the game world as a 'globe'.
     * For example: if a player leaves the screen on the left edge,
     * it will appear on the right edge, as it has 'circled around' the map and vice versa
     */
    public void wrapAroundWorld() {
        if (getX() + getWidth() < 0)
            setX(worldBounds.width);

        if (getX() > worldBounds.width)
            setX(-getWidth());

        if (getY() + getHeight() < 0)
            setY(worldBounds.height);

        if (getY() > worldBounds.height)
            setY(-getHeight());
    }

    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }
    public void setMinSpeed(float ms) {
        minSpeed = ms;
    }

    public void setSpeed(float speed) {
        //adding this to give initial movement
        //otherwise actors would stand in place until given initial push
        if (velocityVec.len() == 0)
            velocityVec.set(speed, 0);
        else
        velocityVec.setLength(speed);
    }

    public float getSpeed() {
        return velocityVec.len();
    }
    public void setMotionAngle(float angle) {
        velocityVec.setAngleDeg(angle);
    }



    /**
     * Multiplies the actors size by the given factor.
     */
    public void resize(float factor) {
        setSize(getWidth() * factor, getHeight() * factor);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        //advance the animation if it's not paused
        if (!animationPaused)
            elapsedTime += dt;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //apply color tint if color changed
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        //not all of these change all the time, they are there in case they get changed
        super.draw(batch, parentAlpha);
    }

    public static void setWorldBounds(RootActor background) {
        setWorldBounds(background.getWidth(), background.getHeight());
    }

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }




}
