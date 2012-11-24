package zoldesi.andor.droidtrainer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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

    public void setModel(RepsAndSetsBasedExercise model) {
        this.model = model;
    }

    @Override
    public void modelChanged() {
        if(model != null){
            ((TextView)this.findViewById(R.id.CompletedReps)).setText(String.valueOf(model.getCompletedReps()));
            ((TextView)this.findViewById(R.id.CompletedRest)).setText(String.valueOf(model.getCompletedRestTime()));
            ((TextView)this.findViewById(R.id.CompletedSetRest)).setText(String.valueOf(model.getCompletedPerSetRestTime()));
            ((TextView)this.findViewById(R.id.CompletedSets)).setText(String.valueOf(model.getCompletedSets()));
            ((TextView)this.findViewById(R.id.CompletedHangTime)).setText(String.valueOf(model.getCompletedHangTime()));
            ((TextView)this.findViewById(R.id.TotalReps)).setText(String.valueOf(model.getTotalReps()));
            ((TextView)this.findViewById(R.id.TotalRest)).setText(String.valueOf(model.getRestTime()));
            ((TextView)this.findViewById(R.id.TotalSetRest)).setText(String.valueOf(model.getPerSetRestTime()));
            ((TextView)this.findViewById(R.id.TotalSets)).setText(String.valueOf(model.getTotalSets()));
            ((TextView)this.findViewById(R.id.HangTime)).setText(String.valueOf(model.getHangTime()));
        }
    }
}
