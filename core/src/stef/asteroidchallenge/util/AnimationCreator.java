package stef.asteroidchallenge.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationCreator {

    /**
     * Creates an animation from a single sprite sheet
     * @param fileName - relative path to the file containing the sprite sheet for animation
     * @param rowsOnSheet - number of rows on the sprite sheet
     * @param columnsOnSheet - number of column on the sprite sheet
     * @param frameDuration - how long should pass between switching frames in the animation
     * @param loop - determines wether to create a continuous animation
     * @return - The animation created based on passed in parameters
     */
    public static Animation<TextureRegion> createAnimationFromSheet(String fileName, int rowsOnSheet, int columnsOnSheet, float frameDuration, boolean loop) {
        //Create a texture from the specified file
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //calculate width and height of a single frame based on the amount of rows and columns on the sheet
        int frameWidth = texture.getWidth() / columnsOnSheet;
        int frameHeight = texture.getHeight() / rowsOnSheet;
        //Split the texture into individual frames
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        //all all the frames to an array
        Array<TextureRegion> textureArray = new Array<>();
        for (int r = 0; r < rowsOnSheet; r++) {
            for (int c = 0; c < columnsOnSheet; c++)
                textureArray.add(temp[r][c]);
        }

        //use the array to create the animation
        Animation<TextureRegion> animation = new Animation<>(frameDuration, textureArray);

        //turn on looping if required
        if (loop)
            animation.setPlayMode(Animation.PlayMode.LOOP);
        else
            animation.setPlayMode(Animation.PlayMode.NORMAL);

        //give back the ready animation
        return animation;
    }


    /**
     * Creates an 'animation' from single image file
     * @param fileName - relative path to the file containing the image for the actor
     * @return - The animation created based on passed in parameters
     */
    public static Animation<TextureRegion> loadTexture(String fileName) {
        //frame duration and playback style do not matter in this case, passing basic values
        return createAnimationFromSheet(fileName, 1, 1, 1, true);
    }
}
