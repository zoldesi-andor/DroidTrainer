package zoldesi.andor.droidtrainer.activities;

import android.app.Activity;
import android.os.Bundle;
import zoldesi.andor.droidtrainer.model.RepsAndSetsBasedExercise;

/**
 * Created with IntelliJ IDEA.
 * User: Andor
 * Date: 11/24/12
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardRepeater extends RepeaterBase {

    @Override
    public RepsAndSetsBasedExercise createModel() {
        return new RepsAndSetsBasedExercise(10, 20, 5, 5, 5);
    }
}