package zoldesi.andor.droidtrainer.model;

import java.util.ArrayList;

/**
 * Base class for all the set - rest based exercises.
 */
public class SetsBasedExercise extends ObservableModel {
////////////////////////////////////////////
// Fields
///////////////////////////////////////////
    private ArrayList<ExerciseStateChangeListener> stateChangeListeners = new ArrayList<ExerciseStateChangeListener>();

    protected int totalSets;
    protected int completedSets;
    protected int perSetRestTime;
    protected int completedPerSetRestTime;
    protected ExerciseState state;

////////////////////////////////////////////
// Constructor
///////////////////////////////////////////
    public SetsBasedExercise(int totalSets, int perSetRestTime) {
        this.totalSets = totalSets;
        this.perSetRestTime = perSetRestTime;
    }

////////////////////////////////////////////
// Properties
///////////////////////////////////////////
    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
        this.notifyObservers();
    }

    public int getCompletedSets() {
        return completedSets;
    }

    public void setCompletedSets(int completedSets) {
        this.completedSets = completedSets;
        this.notifyObservers();
    }

    public int getPerSetRestTime() {
        return perSetRestTime;
    }

    public void setPerSetRestTime(int perSetRestTime) {
        this.perSetRestTime = perSetRestTime;
        this.notifyObservers();
    }

    public int getCompletedPerSetRestTime() {
        return completedPerSetRestTime;
    }

    public void setCompletedPerSetRestTime(int completedPerSetRestTime) {
        this.completedPerSetRestTime = completedPerSetRestTime;
        this.notifyObservers();
    }

    public int getRemainingSets(){
        return totalSets - completedSets;
    }

    public int getRemainingPerSetRestTime(){
        return perSetRestTime - completedPerSetRestTime;
    }

    public ExerciseState getState() {
        return state;
    }

    public void setState(ExerciseState state) {
        notifyStateChangeListeners(this.state, state);
        this.state = state;
        this.notifyObservers();
    }

//////////////////////////////////////////
// Model methods
//////////////////////////////////////////
    public void tick(){
        if(this.state == ExerciseState.SET_RESTING){
            this.setCompletedPerSetRestTime(completedPerSetRestTime + 1);
            if(completedPerSetRestTime >= perSetRestTime){
                this.setCompletedPerSetRestTime(0);
                this.setState(ExerciseState.EXERCISING);
            }
        }
    }

    public void startNextSet(){
        this.setCompletedPerSetRestTime(0);
        this.setState(ExerciseState.EXERCISING);
    }

    public void startNextSetRest(){
        this.setCompletedSets(completedSets + 1);
        this.setCompletedPerSetRestTime(0);
        this.setState(ExerciseState.SET_RESTING);
    }

    public void addStateChangeListener(ExerciseStateChangeListener listener){
        stateChangeListeners.add(listener);
    }

    public void removeStateChangeListener(ExerciseStateChangeListener listener){
        stateChangeListeners.remove(listener);
    }

    protected void notifyStateChangeListeners(ExerciseState oldState, ExerciseState newState){
        for(ExerciseStateChangeListener listener : stateChangeListeners){
            listener.stateChanged(oldState, newState);
        }
    }
}
