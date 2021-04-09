package stef.asteroidchallenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen, InputProcessor {
    //a stage for action
    protected Stage mainStage;
    //a stage for UI elements, status, info...
    protected Stage uiStage;
    //a table to organize elements in the UI stage
    protected Table uiTable;



    public AbstractScreen(){
        //initialize the stages
        mainStage = new Stage();
        uiStage = new Stage();
        //initialize the table and add it to ui stage
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        //template method to be overridden in each screen individually
        initialize();
    }
    //template method to be overridden in each screen individually
    public abstract void initialize();

    @Override
    public void render(float dt){
        //calling act on stages also calls act on each individual actor in them
        uiStage.act(dt);
        mainStage.act(dt);

        //template method to be overridden in each screen individually
        update(dt);

        //prepare for rendering
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //draw the stages
        mainStage.draw();
        uiStage.draw();
    }
    //template method to be overridden in each screen individually
    public abstract void update(float dt);

    /**
     * @param event - the event to be evaluated
     * @return - true if the specified event's type is touchDown
     */
    public boolean isTouchDownEvent(Event event){
        return (event instanceof InputEvent) && ((InputEvent)event).getType().equals(InputEvent.Type.touchDown);
    }

    //methods required by the screen interface
    public void resize(int width, int height){}
    public void pause(){}
    public void resume(){}
    public void dispose(){}
    public void show(){
        //when the screen is visible register for the input
        //InputMultiplexer has been set as the input processor in AsteroidGame class
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }
    public void hide(){
        //when screen is not visible de-register
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }
    //methods for InputProcessor interface
    public boolean keyDown(int keycode){
        return false;
    }
    public boolean keyUp(int keycode){
        return false;
    }
    public boolean keyTyped(char c){
        return false;
    }
    public boolean mouseMoved(int screenX, int screenY){
        return false;
    }
    public boolean scrolled(float horizontal, float vertical){
        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        return false;
    }
    public boolean touchDragged(int screenX, int screenY, int pointer){
        return false;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        return false;
    }
}




