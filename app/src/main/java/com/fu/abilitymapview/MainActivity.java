package com.fu.abilitymapview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private AbilityMapView abilitymapview;
    private AppCompatSeekBar seekbarkill;
    private AppCompatSeekBar seekbarsurvival;
    private AppCompatSeekBar seekbarassist;
    private AppCompatSeekBar seekbarad;
    private AppCompatSeekBar seekbarap;
    private AppCompatSeekBar seekbardefense;
    private AppCompatSeekBar seekbarmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initListener();
        abilitymapview.setData(new AbilityBean(65, 70, 80, 70, 80, 80, 80));
        seekbarkill.setProgress(65);
        seekbarsurvival.setProgress(70);
        seekbarassist.setProgress(80);
        seekbarad.setProgress(70);
        seekbarap.setProgress(80);
        seekbardefense.setProgress(80);
        seekbarmoney.setProgress(80);
    }

    private void initUI() {
        this.abilitymapview = (AbilityMapView) findViewById(R.id.ability_map_view);
        this.seekbarkill = (AppCompatSeekBar) findViewById(R.id.seekbar_kill);
        this.seekbarsurvival = (AppCompatSeekBar) findViewById(R.id.seekbar_survival);
        this.seekbarassist = (AppCompatSeekBar) findViewById(R.id.seekbar_assist);
        this.seekbarad = (AppCompatSeekBar) findViewById(R.id.seekbar_ad);
        this.seekbarap = (AppCompatSeekBar) findViewById(R.id.seekbar_ap);
        this.seekbardefense = (AppCompatSeekBar) findViewById(R.id.seekbar_defense);
        this.seekbarmoney = (AppCompatSeekBar) findViewById(R.id.seekbar_money);
    }

    private void initListener() {
        this.seekbarkill.setOnSeekBarChangeListener(this);
        this.seekbarsurvival.setOnSeekBarChangeListener(this);
        this.seekbarassist.setOnSeekBarChangeListener(this);
        this.seekbarad.setOnSeekBarChangeListener(this);
        this.seekbarap.setOnSeekBarChangeListener(this);
        this.seekbardefense.setOnSeekBarChangeListener(this);
        this.seekbarmoney.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar_kill:
                abilitymapview.changeProgress("击杀", progress);
                break;
            case R.id.seekbar_survival:
                abilitymapview.changeProgress("生存", progress);
                break;
            case R.id.seekbar_assist:
                abilitymapview.changeProgress("助攻", progress);
                break;
            case R.id.seekbar_ad:
                abilitymapview.changeProgress("物理", progress);
                break;
            case R.id.seekbar_ap:
                abilitymapview.changeProgress("魔法", progress);
                break;
            case R.id.seekbar_defense:
                abilitymapview.changeProgress("防御", progress);
                break;
            case R.id.seekbar_money:
                abilitymapview.changeProgress("金钱", progress);
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //开始拖动的时候调用

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //停止拖动的时候调用

    }
}
