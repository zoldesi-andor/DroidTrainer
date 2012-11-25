package zoldesi.andor.droidtrainer.activities;

import zoldesi.andor.droidtrainer.model.ExerciseState;
import zoldesi.andor.droidtrainer.model.ExerciseStateChangeListener;
import zoldesi.andor.droidtrainer.model.RepsAndSetsBasedExercise;

/**
 * Created with IntelliJ IDEA.
 * User: Andor
 * Date: 11/24/12
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pyramids extends RepeaterBase {
    private int hangTimes[] = { 4, 6, 8, 10, 8, 6, 4};

    @Override
    public void initialize(){
        this.view.setTitle("Pyramid Hangs");

        this.model.setHangTime(4);
        this.model.setRestTime(5);
        this.model.setTotalReps(7);
        this.model.setPerSetRestTime(60);
        this.model.setTotalSets(5);

        this.view.getTotalRepsEditText().setEnabled(false);
        this.view.getHangTimeEditText().setEnabled(false);
        this.view.getTotalRestEditText().setEnabled(false);

        this.model.addStateChangeListener(
                new ExerciseStateChangeListener() {
                    @Override
                    public void stateChanged(ExerciseState oldState, ExerciseState newState) {
                        if(oldState == ExerciseState.EXERCISING){
                            int nextRep = model.getCompletedReps();
                            model.setHangTime(hangTimes[nextRep]);
                        }
                    }
                }
        );
    }
}
