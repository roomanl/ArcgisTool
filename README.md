# ArcGisTool
封装Arcgis Runtime for Android 100.3.0地图基本操作。 <br>
包括：测量工具控件及测量接口，放大缩小控件及放大缩小接口，地图旋转控件及地图旋转接口。 <br>
![](https://github.com/roomanl/ArcgisTool/blob/master/GIF.gif?raw=true)
## 引用：
[![](https://jitpack.io/v/roomanl/ArcgisTool.svg)](https://jitpack.io/#roomanl/ArcgisTool)
```gradle
implementation 'com.github.roomanl:ArcgisTool:1.4'
或者
implementation project(':arcgistool')
```
## 更新日志：
#### 2019/10/12 V1.4
新增ArcgisToolManager类，用此类来统一管理MeasureToolView，JAVA设置属性修改为用ArcgisToolManager类链式调用设置
#### 2018/09/19 V1.3
1、修复WGS84坐标下测量不正确的问题<br>
2、优化测量工具控件的使用<br>
3、封装设置底图、初始范围等接口<br>
4、封装在底图上叠加、移除图层的接口<br>
5、新增了很多接口，没时间写具体使用说明，有时间再写了<br>
## 测量工具：
### MeasureToolView使用
控件的功能包括，测距、测面积、撤销、恢复、清除、完成六个功能。 <br>

测距：在地图上绘制线段进行长度测量 <br>

测面积：在地图上绘制一个面，进行面积测量 <br>

撤销：撤销到上一步绘制，只能撤销未完成的测量 <br>

恢复：恢复到下一步绘制，只能恢复未完成的测量 <br>

清除：清空测量内容并结束测量，再次点击地图时不会进行测量 <br>

完成：结束本次测量，本次测量将不能撤销和恢复，已绘制的图形不会被清除，如需进行下一段测量请再次点击测距或测面积按钮 <br>
#### 最简单的基本用法：
界面代码
```xml
    <cn.sddman.arcgistool.view.MeasureToolView
        android:id="@+id/measure_tool"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
    </cn.sddman.arcgistool.view.MeasureToolView>
```
java代码
```java
   MeasureToolView measureToolView=(MeasureToolView)findViewById(R.id.measure_tool);
   ArcgisToolManager.create(mMapView).builderMeasure(measureToolView);
```
注意：请不要在ArcgisToolManager.create()之后给mMapView设置点击事件，不然会覆盖掉MeasureToolView的地图点击事件，如需要在地图点击之后做一些自己的操作，请看下面设置回调的说明。<br>

以上代码将会显示默认的控件样式，下图是默认样式
![](https://github.com/roomanl/ArcgisTool/blob/master/1.jpg?raw=true)

MeasureToolView支持样式设置，可以设置成自己需要的样式，下图是自定义样式
![](https://github.com/roomanl/ArcgisTool/blob/master/2.jpg?raw=true)
### MeasureToolView属性样式设置
#### 在界面设置属性
```xml
 <cn.sddman.arcgistool.view.MeasureToolView
        android:id="@+id/measure_tool"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:button_width="60do"//设置每一个按钮宽度;默认35
        app:button_height="40dp"//设置每一个按钮高度;默认35
        app:is_horizontal="true"//是否水平显示，true水平,false垂直;默认true
        app:view_background="@drawable/sddman_shadow_bg"//设置整个控件背景，默认白色圆角矩形
        app:show_text="true"//是否显示文字；默认false
        app:font_color="@color/colorAccent"//设置文字颜色；默认gray
        app:font_size="12dp"//设置文字大小；默认12dp
        app:measure_prev_text="撤销"//设置撤销按钮文字；默认“撤销”
        app:measure_next_text="恢复"//设置恢复按钮文字；默认“恢复”
        app:measure_length_text="测距"//设置测距按钮文字；默认“测距”
        app:measure_area_text="测面积"//设置测面积按钮文字；默认“测面积”
        app:measure_clear_text="清除"//设置清除按钮文字；默认“清除”
        app:measure_end_text="完成"//设置完成按钮文字；默认“完成”
        app:measure_prev_image="@drawable/sddman_measure_prev"//设置撤销按钮图标
        app:measure_next_image="@drawable/sddman_measure_next"//设置恢复按钮图标
        app:measure_length_image="@drawable/sddman_measure_length"//设置测距按钮图标
        app:measure_area_image="@drawable/sddman_measure_area"//设置测面积按钮图标
        app:measure_clear_image="@drawable/sddman_measure_clear"//设置清除按钮图标
        app:measure_end_image="@drawable/sddman_measure_end"//设置完成按钮图标
        >
    </cn.sddman.arcgistool.view.MeasureToolView>
```
#### java代码设置属性
```java
MeasureToolView measureToolView=(MeasureToolView)findViewById(R.id.measure_tool);
ArcgisToolManager.create(mMapView).builderMeasure(measureToolView)
        .setButtonWidth(60)//设置每一个按钮宽度;默认35
        .setButtonHeight(40)//设置每一个按钮高度;默认35
        .setMeasureBackground(R.color.colorAccent)//设置整个控件背景，默认白色圆角矩形
        .setSohwText(true)//是否显示文字；默认false
        .setFontSize(12)//设置文字大小；默认12dp
        .setFontColor(R.color.color444)//设置字体颜色，默认#808080
        .setMeasurePrevStr("撤销")//设置撤销按钮文字；默认“撤销”
        .setMeasureNextStr("恢复")//设置恢复按钮文字；默认“恢复”
        .setMeasureLengthStr("测距")//设置测距按钮文字；默认“测距”
        .setMeasureAreaStr("测面积")//设置测面积按钮文字；默认“测面积”
        .setMeasureClearStr("清除")//设置清除按钮文字；默认“清除”
        .setMeasureEndStr("完成")//设置完成按钮文字；默认“完成”
        .setMeasurePrevImage(R.drawable.sddman_measure_prev)//设置撤销按钮图标
        .setMeasureNextImage(R.drawable.sddman_measure_next)//设置恢复按钮图标
        .setMeasureLengthImage(R.drawable.sddman_measure_length)//设置测距按钮图标
        .setMeasureAreaImage(R.drawable.sddman_measure_area)//设置测面积按钮图标
        .setMeasureClearImage(R.drawable.sddman_measure_clear)//设置清除按钮图标
        .setMeasureEndImage(R.drawable.sddman_measure_end)//设置完成按钮图标
        .setSpatialReference(SpatialReference.create(3857))//设置坐标参考系,默认从mMapView获取SpatialReference
        .setLengthType(Variable.Measure.KM)//设置测量长度单位,默认Variable.Measure.M(米))；其它：Variable.Measure.KM(千米)；Variable.Measure.M(米)；Variable.Measure.KIM(公里)
        .setAreaType(Variable.Measure.KM2);//设置测量面积单位,默认Variable.Measure.M2(平方米)；其它：Variable.Measure.KM2(平方千米)；Variable.Measure.M2(平方米)；Variable.Measure.HM2(公顷)；Variable.Measure.A2(亩)；
```
#### 设置地图点击回调
```java
//注意：setMapClickCallBack()要在create()之后,builderMeasure()之前调用
ArcgisToolManager.create(this,mMapView)
            .setMapClickCallBack(new DefaultMapViewOnTouchListener(this,mMapView){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Toast.makeText(MainActivity.this,"onSingleTapUp",Toast.LENGTH_SHORT).show();
                    return super.onSingleTapUp(e);
                }
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Toast.makeText(MainActivity.this,"onDoubleTap",Toast.LENGTH_SHORT).show();
                    return super.onDoubleTap(e);
                }
                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    Toast.makeText(MainActivity.this,"onScroll",Toast.LENGTH_SHORT).show();
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
                @Override
                public boolean onRotate(MotionEvent event, double rotationAngle) {
                    Toast.makeText(MainActivity.this,"onRotate",Toast.LENGTH_SHORT).show();
                    return super.onRotate(event, rotationAngle);
                }
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    Toast.makeText(MainActivity.this,"onScale",Toast.LENGTH_SHORT).show();
                    return super.onScale(detector);
                }
            })
            .builderMeasure(measureToolView)
```
#### 设置测量工具按钮点击回调
```java
    ArcgisToolManager.create(mMapView).builderMeasure(measureToolView)
            .setMeasureClickListener(new MeasureClickListener() {
                    @Override
                    public void prevClick(boolean hasPrev) {
                         //撤销回调，hasPrev是否还能撤销
                        Toast.makeText(MainActivity.this,"MeasureToolView prevClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nextClick(boolean hasNext) {
                        //恢复回调，hasPrev是否还能恢复
                        Toast.makeText(MainActivity.this,"MeasureToolView nextClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void lengthClick() {
                        //长度测量点击
                        Toast.makeText(MainActivity.this,"MeasureToolView lengthClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void areaClick() {
                        //面积测量点击
                        Toast.makeText(MainActivity.this,"MeasureToolView areaClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void clearClick(DrawEntity draw) {
                        //清除点击，返回DrawEntity所有绘制的文字、点、线、面的集合
                        Toast.makeText(MainActivity.this,"MeasureToolView clearClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void endClick(DrawEntity draw) {
                         //完成点击，返回DrawEntity所有绘制的文字、点、线、面的集合
                        Toast.makeText(MainActivity.this,"MeasureToolView endClick",Toast.LENGTH_SHORT).show();
                    }
                });
```

### 测量开放接口，ArcGisMeasure
MeasureToolView控件均由调用ArcGisMeasure开放接口实现
```java
    ArcGisMeasure arcgisMeasure=new ArcGisMeasure(context,mMapView);
    //默认从mMapView获取SpatialReference
    arcgisMeasure.setSpatialReference(SpatialReference.create(3857));
    //Variable.Measure.KM(千米)；Variable.Measure.M(米)；Variable.Measure.KIM(公里)
     //默认Variable.Measure.M(米))；
   arcgisMeasure.setLengthType(Variable.Measure.KM);
   //Variable.Measure.KM2(平方千米)；Variable.Measure.M2(平方米)；Variable.Measure.HM2(公顷)；Variable.Measure.A2(亩)；
    //默认Variable.Measure.M2(平方米)；
   arcgisMeasure.setAreaType(Variable.Measure.KM2);
   //撤销
   boolean hasPrev=arcgisMeasure.prevDraw();
   //恢复
   boolean hasNext=arcgisMeasure.nextDraw();
   //开始测长度，传入屏幕坐标x,y或者传入android.graphics.Point
   arcgisMeasure.startMeasuredLength(e.getX(), e.getY());
   //或者
    arcgisMeasure.startMeasuredLength(android.graphics.Point);
    //开始测面积，传入屏幕坐标x,y或者传入android.graphics.Point
   arcgisMeasure.startMeasuredArea(e.getX(), e.getY());
   //或者
   arcgisMeasure.startMeasuredArea(android.graphics.Point);
   //清除测量，返回DrawEntity所有绘制的文字、点、线、面的集合
   DrawEntity draw=arcgisMeasure.clearMeasure();
    //结束测量，返回DrawEntity所有绘制的文字、点、线、面的集合
   DrawEntity draw=arcgisMeasure.endMeasure();
```