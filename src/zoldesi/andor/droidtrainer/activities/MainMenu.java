package zoldesi.andor.droidtrainer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import zoldesi.andor.droidtrainer.R;

/**
 * Created with IntelliJ IDEA.
 * User: Andor
 * Date: 11/25/12
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainMenu extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mainmenu);

        this.getButton(R.id.PyramidOptionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Pyramids.class);
            }
        });

        this.getButton(R.id.HangsOptionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(StandardRepeater.class);
            }
        });
    }

    private void startActivity(Class c){
        Intent i = new Intent(this, c);
        this.startActivity(i);
    }

    private Button getButton(int id){
        return (Button)this.findViewById(id);
    }
}