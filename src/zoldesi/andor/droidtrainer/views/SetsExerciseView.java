package zoldesi.andor.droidtrainer.views;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import zoldesi.andor.droidtrainer.R;
import zoldesi.andor.droidtrainer.model.ObservableModel;
import zoldesi.andor.droidtrainer.model.RepsAndSetsBasedExercise;

/**
 * Created with IntelliJ IDEA.
 * User: Andor
 * Date: 11/24/12
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetsExerciseView extends TableLayout implements ObservableModel.ModelListener {
    private RepsAndSetsBasedExercise model;

    public SetsExerciseView(Context context) {
        super(context);
    }

    public SetsExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //////////////////////////////////
    //  Getters setters
    /////////////////////////////////
    public void setModel(RepsAndSetsBasedExercise model) {
        this.model = model;
    }

    public void setTitle(String title){
        ((TextView)this.findViewById(R.id.ExerciseTitle)).setText(title);
    }

    public Button getStartButton(){
        return ((Button)this.findViewById(R.id.StartButton));
    }

    public Button getPauseButton(){
        return ((Button)this.findViewById(R.id.PauseButton));
    }

    public Button getResetButton(){
        return ((Button)this.findViewById(R.id.ResetButton));
    }

    public EditText getTotalRepsEditText(){
        return ((EditText)this.findViewById(R.id.TotalReps));
    }

    public EditText getTotalRestEditText(){
        return ((EditText)this.findViewById(R.id.TotalRest));
    }

    public EditText getTotalSetRestEditText(){
        return ((EditText)this.findViewById(R.id.TotalSetRest));
    }

    public EditText getTotalSetsEditText(){
        return ((EditText)this.findViewById(R.id.TotalSets));
    }

    public EditText getHangTimeEditText(){
        return ((EditText)this.findViewById(R.id.HangTime));
    }

    @Override
    public void modelChanged() {
        if(model != null){
            ((TextView)this.findViewById(R.id.CompletedReps)).setText(String.valueOf(model.getCompletedReps()));
            ((TextView)this.findViewById(R.id.CompletedRest)).setText(String.valueOf(model.getCompletedRestTime()));
            ((TextView)this.findViewById(R.id.CompletedSetRest)).setText(String.valueOf(model.getCompletedPerSetRestTime()));
            ((TextView)this.findViewById(R.id.CompletedSets)).setText(String.valueOf(model.getCompletedSets()));
            ((TextView)this.findViewById(R.id.CompletedHangTime)).setText(String.valueOf(model.getCompletedHangTime()));
            ((EditText)this.findViewById(R.id.TotalReps)).setText(String.valueOf(model.getTotalReps()));
            ((EditText)this.findViewById(R.id.TotalRest)).setText(String.valueOf(model.getRestTime()));
            ((EditText)this.findViewById(R.id.TotalSetRest)).setText(String.valueOf(model.getPerSetRestTime()));
            ((EditText)this.findViewById(R.id.TotalSets)).setText(String.valueOf(model.getTotalSets()));
            ((EditText)this.findViewById(R.id.HangTime)).setText(String.valueOf(model.getHangTime()));
        }
    }
}
