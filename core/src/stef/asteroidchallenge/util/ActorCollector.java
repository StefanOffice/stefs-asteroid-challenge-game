package stef.asteroidchallenge.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import stef.asteroidchallenge.actor.RootActor;

public class ActorCollector {

    /**
     * Creates the list of all the objects belonging to the specified class that are currently present at the specified stage
     * @return - the created list
     */
    public static <E extends RootActor> ArrayList<E> getActiveInstanceList(Stage stage, Class<E> requiredClass) {
        ArrayList<E> list = new ArrayList<>();

        for (Actor currentActor : stage.getActors()) {
            if(requiredClass.isInstance(currentActor)) {
                @SuppressWarnings("unchecked")
                        //safe to suppress because we are checking if the current actor is an instance of E in the above line
                        //so only actual 'E' types will make it to this line
                E requiredActor = (E) currentActor;
                list.add(requiredActor);
            }
        }
        return list;
    }


}
