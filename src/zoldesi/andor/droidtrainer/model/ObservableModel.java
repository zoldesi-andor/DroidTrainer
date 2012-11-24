package zoldesi.andor.droidtrainer.model;

import java.util.ArrayList;

/**
 * Base class for Observable models. Supports observer registration and change notification with the ModelListener
 * interface
 */
public class ObservableModel {
    private ArrayList<ModelListener> observers = new ArrayList<ModelListener>();

    /**
     * Interface to receive change notifications from the model
     */
    interface ModelListener {
        void modelChanged();
    }

    /**
     * Registers new Observer
     * @param listener
     */
    public void addObserver(ModelListener listener){
           observers.add(listener);
    }

    /**
     * Removes registered Observer
     * @param listener
     */
    public void removeObserver(ModelListener listener){
        observers.remove(listener);
    }

    /**
     * Notifies all registered observers to refresh
     */
    protected void notifyObservers(){
        for(ModelListener listener : observers){
            listener.modelChanged();
        }
    }
}
