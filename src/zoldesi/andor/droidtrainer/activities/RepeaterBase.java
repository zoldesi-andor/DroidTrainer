package zoldesi.andor.droidtrainer.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
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

    private Timer timer;
    private ExerciseState pausedState = null;

    SetsExerciseView view;
    RepsAndSetsBasedExercise model = new RepsAndSetsBasedExercise();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = (SetsExerciseView) LayoutInflater.from(this).inflate(R.layout.exercise, null, false);
        this.setContentView(view);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        highBeep = MediaPlayer.create(this, R.raw.beephigh);
        lowBeep = MediaPlayer.create(this, R.raw.beeplow);
        doubleBeep = MediaPlayer.create(this, R.raw.beepdouble);

        this.model.addObserver(view);
        this.view.setModel(model);

        this.model.addStateChangeListener(
                new ExerciseStateChangeListener() {
                    @Override
                    public void stateChanged(ExerciseState oldState, ExerciseState newState) {
                        doubleBeep.start();
                    }
                }
        );

        this.view.getStartButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepeaterBase.this.startExercise();

                RepeaterBase.this.view.getStartButton().setVisibility(View.GONE);
                RepeaterBase.this.view.getResetButton().setVisibility(View.GONE);
                RepeaterBase.this.view.getPauseButton().setVisibility(View.VISIBLE);
            }
        });

        this.view.getPauseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepeaterBase.this.pauseExercise();

                RepeaterBase.this.view.getStartButton().setVisibility(View.VISIBLE);
                RepeaterBase.this.view.getResetButton().setVisibility(View.VISIBLE);
                RepeaterBase.this.view.getPauseButton().setVisibility(View.GONE);
            }
        });

        this.view.getResetButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepeaterBase.this.resetExercise();

                RepeaterBase.this.view.getStartButton().setVisibility(View.VISIBLE);
                RepeaterBase.this.view.getResetButton().setVisibility(View.GONE);
                RepeaterBase.this.view.getPauseButton().setVisibility(View.GONE);
            }
        });

        this.view.getTotalSetsEditText().addTextChangedListener(new TotalSetsWatcher());
        this.view.getHangTimeEditText().addTextChangedListener(new HangTimWatcher());
        this.view.getTotalRepsEditText().addTextChangedListener(new TotalRepsWatcher());
        this.view.getTotalRestEditText().addTextChangedListener(new RestTimeWatcher());
        this.view.getTotalSetRestEditText().addTextChangedListener(new PerSetRestTimeWatcher());

        this.initialize();
    }

    @Override
    public void onPause(){
        super.onPause();
        if(timer != null){
            timer.cancel();
        }
        lowBeep.release();
        highBeep.release();
        doubleBeep.release();
        this.finish();
    }

    public void initialize() {};

    public void startExercise(){
        if(timer != null){
            timer.cancel();
        }

        timer = new Timer();
        if(this.pausedState != null){
            this.model.setState(this.pausedState);
            this.pausedState = null;
        }else{
            this.model.setState(ExerciseState.EXERCISING);
        }
        Toast.makeText(this, "Starting in 5 seconds", Toast.LENGTH_LONG).show();

        timer.schedule(
                new TimerTask() {
                    int delay = 0;

                    @Override
                    public void run() {
                        if(delay < 5){
                            delay++;
                            lowBeep.start();
                            return;
                        }

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

    public void resetExercise(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }

        this.pausedState = null;

        this.model.setState(ExerciseState.PENDING);
        this.model.setCompletedReps(0);
        this.model.setCompletedHangTime(0);
        this.model.setCompletedRestTime(0);
        this.model.setCompletedPerSetRestTime(0);
        this.model.setCompletedSets(0);
    }

    public void pauseExercise(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }

        this.pausedState = this.model.getState();
        this.model.setState(ExerciseState.PENDING);
    }

    private abstract class EditTextWatcher implements TextWatcher{
        
        private Integer safeParse(Object s){
            Integer result = null;
            try{
                result = Integer.parseInt(s.toString());
            }catch (NumberFormatException nfe){ }
            return result;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            Integer value = safeParse(editable);
            setValue(value);
        }

        abstract void setValue(Integer value);
    }

    private class TotalSetsWatcher extends EditTextWatcher{
         void setValue(Integer value){
             model.setTotalSets(value != null ? value : model.getTotalSets());
         }
    }

    private class PerSetRestTimeWatcher extends EditTextWatcher{
        void setValue(Integer value){
            model.setPerSetRestTime(value != null ? value : model.getPerSetRestTime());
        }
    }

    private class TotalRepsWatcher extends EditTextWatcher{
        void setValue(Integer value){
            model.setTotalReps(value != null ? value : model.getTotalReps());
        }
    }

    private class RestTimeWatcher extends EditTextWatcher{
        void setValue(Integer value){
            model.setRestTime(value != null ? value : model.getRestTime());
        }
    }

    private class HangTimWatcher extends EditTextWatcher{
        void setValue(Integer value){
            model.setHangTime(value != null ? value : model.getHangTime());
        }
    }
}
