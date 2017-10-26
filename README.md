# AbilityMapView
仿LOL掌盟能力七星图控件

源码解析博客地址：[自定义View之LOL能力七星图](http://blog.csdn.net/fu908323236/article/details/78356344)

预览图

![预览图](https://github.com/qq908323236/AbilityMapView/blob/master/image/pre_img.png)


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
