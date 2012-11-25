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
    public void initialize(){
        this.model.setHangTime(5);
        this.model.setRestTime(5);
        this.model.setTotalReps(10);
        this.model.setPerSetRestTime(180);
        this.model.setTotalSets(10);
    }
}