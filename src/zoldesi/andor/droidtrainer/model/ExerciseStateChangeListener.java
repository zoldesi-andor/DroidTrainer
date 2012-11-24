package zoldesi.andor.droidtrainer.model;

/**
 * Interface for state change notifications
 */
public interface ExerciseStateChangeListener {
    public void stateChanged(ExerciseState oldState, ExerciseState newState);
}
