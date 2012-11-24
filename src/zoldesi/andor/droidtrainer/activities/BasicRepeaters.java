package zoldesi.andor.droidtrainer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import zoldesi.andor.droidtrainer.R;
import zoldesi.andor.droidtrainer.model.RepsAndSetsBasedExercise;
import zoldesi.andor.droidtrainer.views.SetsExerciseView;

import java.util.Timer;
import java.util.TimerTask;

public class BasicRepeaters extends Activity {

    SetsExerciseView view;
    RepsAndSetsBasedExercise model = new RepsAndSetsBasedExercise(10, 360, 5, 4, 5);

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = (SetsExerciseView) LayoutInflater.from(this).inflate(R.layout.setsexercise, null, false);
        setContentView(view);

        model.addObserver(view);
        view.setModel(model);
        model.startNextSet();

        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        BasicRepeaters.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BasicRepeaters.this.model.tick();
                            }
                        });
                    }
                },
                0,
                1000
        );


    }
}
