package zoldesi.andor.droidtrainer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import zoldesi.andor.droidtrainer.R;

/**
 * Created with IntelliJ IDEA.
 * User: Andor
 * Date: 11/25/12
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class OneMinutePullUp extends RepeaterBase {

    @Override
    public void initialize(){
        this.view.setTitle("One Minute Pull Ups");

        this.model.setHangTime(0);
        this.model.setRestTime(0);
        this.model.setTotalReps(0);
        this.model.setPerSetRestTime(60);
        this.model.setTotalSets(20);

        hideRow(R.id.RepsRestRow);
        hideRow(R.id.RepsRow);
        hideRow(R.id.HangsRow);
    }

    private void hideRow(int id){
        ((TableRow)this.findViewById(id)).setVisibility(View.GONE);
    }
}