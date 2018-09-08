package com.kas4.flipcardbattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kas4.flipcardbattle.fragment.BattleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            BattleFragment fragment = BattleFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();

        }
    }
}
