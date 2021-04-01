package stef.asteroidchallenge.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RootActor extends Actor{

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;


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
    }


    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        elapsedTime = 0;
        //get the first image of animation
        TextureRegion tr = animation.getKeyFrame(0);
        float width = tr.getRegionWidth();
        float height = tr.getRegionHeight();
        //actor size (set to the width and height of the first image of animation)
        this.setSize(width, height);
        //point around which the actor should be rotated
        this.setOrigin(width / 2, height / 2);
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


}
