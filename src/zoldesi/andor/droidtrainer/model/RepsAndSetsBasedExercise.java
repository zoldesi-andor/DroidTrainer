package zoldesi.andor.droidtrainer.model;

/**
 * Model class for Sets - Repetitions based exercises (e.g. fingerboard hangs, fingerboard pyramids)
 */
public class RepsAndSetsBasedExercise extends SetsBasedExercise {
////////////////////////////////////////////
// Fields
///////////////////////////////////////////
    protected int totalReps;
    protected int completedReps;
    protected int hangTime;
    protected int completedHangTime;
    protected int restTime;
    protected int completedRestTime;

////////////////////////////////////////////
// Constructor
///////////////////////////////////////////
    public RepsAndSetsBasedExercise(int totalSets, int perSetRestTime, int totalReps, int hangTime, int restTime) {
        super(totalSets, perSetRestTime);
        this.totalReps = totalReps;
        this.hangTime = hangTime;
        this.restTime = restTime;
    }

////////////////////////////////////////////
// Properties
///////////////////////////////////////////
    public int getTotalReps() {
        return totalReps;
    }

    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
        this.notifyObservers();
    }

    public int getCompletedReps() {
        return completedReps;
    }

    public void setCompletedReps(int completedReps) {
        this.completedReps = completedReps;
        this.notifyObservers();
    }

    public int getHangTime() {
        return hangTime;
    }

    public void setHangTime(int hangTime) {
        this.hangTime = hangTime;
        this.notifyObservers();
    }

    public int getCompletedHangTime() {
        return completedHangTime;
    }

    public void setCompletedHangTime(int completedHangTime) {
        this.completedHangTime = completedHangTime;
        this.notifyObservers();
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
        this.notifyObservers();
    }

    public int getCompletedRestTime() {
        return completedRestTime;
    }

    public void setCompletedRestTime(int completedRestTime) {
        this.completedRestTime = completedRestTime;
        this.notifyObservers();
    }

    public int getRemainingReps(){
        return totalReps - completedReps;
    }

    public int getRemainingHangTime(){
        return hangTime - completedHangTime;
    }

    public int getRemainingRestTime(){
        return restTime - completedRestTime;
    }

//////////////////////////////////////////
// Model methods
//////////////////////////////////////////
    @Override
    public void tick(){
        if(state == ExerciseState.EXERCISING){
            this.setCompletedHangTime(completedHangTime + 1);
            if(completedHangTime >= hangTime){
                this.setCompletedReps(completedReps + 1);
                this.setCompletedHangTime(0);
                if(completedReps >= totalReps){
                    this.setCompletedSets(completedSets + 1);
                    this.setState(ExerciseState.SET_RESTING);
                } else {
                    this.setState(ExerciseState.REP_RESTING);
                }
            }
        } else if(state == ExerciseState.REP_RESTING){
            this.setCompletedRestTime(completedRestTime + 1);
            if(completedRestTime >= restTime){
                this.setCompletedRestTime(0);
                this.setState(ExerciseState.EXERCISING);
            }
        } else if(state == ExerciseState.SET_RESTING){
            super.tick();
        }
    }
}
