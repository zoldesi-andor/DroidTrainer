package zoldesi.andor.droidtrainer.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;
import zoldesi.andor.droidtrainer.R;
import zoldesi.andor.droidtrainer.model.ExerciseState;
import zoldesi.andor.droidtrainer.model.ExerciseStateChangeListener;
import zoldesi.andor.droidtrainer.model.RepsAndSetsBasedExercise;
import zoldesi.andor.droidtrainer.views.SetsExerciseView;

import java.util.Timer;
import java.util.TimerTask;

public abstract class RepeaterBase extends Activity {

    MediaPlayer highBeep;
    MediaPlayer lowBeep;
    MediaPlayer doubleBeep;

    Timer timer;

    SetsExerciseView view;
    RepsAndSetsBasedExercise model;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = (SetsExerciseView) LayoutInflater.from(this).inflate(R.layout.setsexercise, null, false);
        this.setContentView(view);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        highBeep = MediaPlayer.create(this, R.raw.beephigh);
        lowBeep = MediaPlayer.create(this, R.raw.beeplow);
        doubleBeep = MediaPlayer.create(this, R.raw.beepdouble);

        this.model = this.createModel();
        this.model.addObserver(view);
        this.view.setModel(model);
        this.model.startNextSet();
        this.model.addStateChangeListener(
                new ExerciseStateChangeListener() {
                    @Override
                    public void stateChanged(ExerciseState oldState, ExerciseState newState) {
                        doubleBeep.start();
                    }
                }
        );

        this.initialize();

        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        switch (model.getState()){
                            case EXERCISING: highBeep.start(); break;
                            case REP_RESTING: lowBeep.start(); break;
                            case SET_RESTING: if(model.getRemainingPerSetRestTime() < 10) lowBeep.start(); break;
                        }
                        RepeaterBase.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RepeaterBase.this.model.tick();
                            }
                        });
                    }
                },
                0,
                1000
        );


    }

    @Override
    public void onPause(){
        super.onPause();
        timer.cancel();
        lowBeep.release();
        highBeep.release();
        doubleBeep.release();
        this.finish();
    }

    public void initialize() {};

    public abstract RepsAndSetsBasedExercise createModel();
}
