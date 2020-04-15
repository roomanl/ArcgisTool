package cn.sddman.gis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.Arrays;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.DrawEntity;
import cn.sddman.arcgistool.listener.MapRotateClickListener;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.listener.ZoomClickListener;
import cn.sddman.arcgistool.manager.ArcgisToolManager;
import cn.sddman.arcgistool.manager.MeasureToolManager;
import cn.sddman.arcgistool.view.ArcGisZoomView;
import cn.sddman.arcgistool.view.MapRotateView;
import cn.sddman.arcgistool.view.MeasureToolView;

public class MainActivity extends AppCompatActivity {
    private MapView mMapView;
    private String url="https://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer";
    private String templateUri="http://mt{subDomain}.google.cn/vt?lyrs=m&scale=1&hl=zh-CN&gl=cn&x={col}&y={row}&z={level}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.mapViewLayout);
        ArcGISTiledLayer tiledLayerBaseMap = new ArcGISTiledLayer(url);
        WebTiledLayer webTiledLayer=new WebTiledLayer(templateUri, Arrays.asList("0","1","2","3"));
        Basemap basemap = new Basemap(webTiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        Envelope mInitExtent = new Envelope(12152397.115334747, 2780298.008156988, 12204603.605653452, 2804643.2016657833,
                SpatialReference.create(102100));
        Viewpoint vp = new Viewpoint(mInitExtent);
        map.setInitialViewpoint(vp);
        mMapView.setMap(map);

        MeasureToolView measureToolView=(MeasureToolView)findViewById(R.id.measure_tool);
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
                .setButtonWidth(60)
                .setButtonHeight(40)
                .setMeasureBackground(R.color.colorAccent)
                .setSohwText(true)
                .setFontSize(12)
                .setFontColor(R.color.color444)
                .setMeasurePrevStr("撤销")
                .setMeasureNextStr("恢复")
                .setMeasureLengthStr("测距")
                .setMeasureAreaStr("测面积")
                .setMeasureClearStr("清除")
                .setMeasureEndStr("完成")
                .setMeasurePrevImage(R.drawable.sddman_measure_prev)
                .setMeasureNextImage(R.drawable.sddman_measure_next)
                .setMeasureLengthImage(R.drawable.sddman_measure_length)
                .setMeasureAreaImage(R.drawable.sddman_measure_area)
                .setMeasureClearImage(R.drawable.sddman_measure_clear)
                .setMeasureEndImage(R.drawable.sddman_measure_end)
                .setSpatialReference(SpatialReference.create(3857))
                .setLengthType(Variable.Measure.KM)
                .setAreaType(Variable.Measure.KM2)
                .setMeasureClickListener(new MeasureClickListener() {
                    @Override
                    public void prevClick(boolean hasPrev) {
                        Toast.makeText(MainActivity.this,"MeasureToolView prevClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nextClick(boolean hasNext) {
                        Toast.makeText(MainActivity.this,"MeasureToolView nextClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void lengthClick() {
                        Toast.makeText(MainActivity.this,"MeasureToolView lengthClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void areaClick() {
                        Toast.makeText(MainActivity.this,"MeasureToolView areaClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void clearClick(DrawEntity draw) {
                        Toast.makeText(MainActivity.this,"MeasureToolView clearClick",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void endClick(DrawEntity draw) {
                        Toast.makeText(MainActivity.this,"MeasureToolView endClick",Toast.LENGTH_SHORT).show();
                    }
                });
        ArcGisZoomView zoomBtn=(ArcGisZoomView)findViewById(R.id.arcgis_zoom_btn);
        ArcgisToolManager.create(this,mMapView).builderZoomView(zoomBtn)
            .setZoomHeight(35)
            .setZoomWidth(60)
            .setZoomBackground(R.drawable.round_corner)
            .isHorizontal(true)
            .setZoomOutImage(R.drawable.sddman_zoomout)
            .setZoomInImage(R.drawable.sddman_zoomin)
            .setShowText(true)
            .setZoomOutText("缩小")
            .setZoomInText("放大")
            .setFontSize(12)
            .setFontColor(R.color.colorMain)
            .setZoomClickListener(new ZoomClickListener() {
                @Override
                public void zoomInClick(View view) {
                    Toast.makeText(MainActivity.this,"zoom in",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void zoomOutClick(View view) {
                    Toast.makeText(MainActivity.this,"zoom out",Toast.LENGTH_SHORT).show();
                }
            });

        MapRotateView mapRotateView=(MapRotateView)findViewById(R.id.map_rotate_view);
        ArcgisToolManager.create(this,mMapView).builderRotateView(mapRotateView)
        .setHeight(40)
        .setWidth(60)
        .setBackground(R.drawable.round_corner)
        .setRotateNum(-45)
        .setRotateImage(R.drawable.sddman_measure_prev)
        .setRotateText("旋转")
        .setShowText(true)
        .setFontSize(16)
        .setFontColor(R.color.colorMain)
        .setMapRotateClickListener(new MapRotateClickListener() {
            @Override
            public void mapRotateClick(View view) {
                Toast.makeText(MainActivity.this,"Map Rotate",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }
}
