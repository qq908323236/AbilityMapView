package com.fu.abilitymapview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AbilityMapView abilitymapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.abilitymapview = (AbilityMapView) findViewById(R.id.ability_map_view);
        abilitymapview.setData(new AbilityBean(65, 70, 80, 70, 80, 80, 80));
    }
}
