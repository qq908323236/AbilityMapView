# AbilityMapView
仿LOL掌盟能力七星图控件

预览图



xml中
```xml
    <com.fu.abilitymapview.AbilityMapView
        android:id="@+id/ability_map_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

MainActivity中
```Java
        this.abilitymapview = (AbilityMapView) findViewById(R.id.ability_map_view);
        abilitymapview.setData(new AbilityBean(65, 70, 80, 70, 80, 80, 80));
```
