package cn.sddman.gis;

import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.DrawEntity;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.listener.ZoomClickListener;
import cn.sddman.arcgistool.util.ArcGisDraw;
import cn.sddman.arcgistool.util.ArcGisMeasure;
import cn.sddman.arcgistool.view.ArcGisZoomView;
import cn.sddman.arcgistool.view.MapRotateView;
import cn.sddman.arcgistool.view.MeasureToolView;

public class MainActivity extends AppCompatActivity {
    private MapView mMapView;
    private String url="http://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.mapViewLayout);
        ArcGISTiledLayer tiledLayerBaseMap = new ArcGISTiledLayer(url);
        Basemap basemap = new Basemap(tiledLayerBaseMap);
        ArcGISMap map = new ArcGISMap(basemap);
        Envelope mInitExtent = new Envelope(12152397.115334747, 2780298.008156988, 12204603.605653452, 2804643.2016657833,
                SpatialReference.create(102100));
        Viewpoint vp = new Viewpoint(mInitExtent);
        map.setInitialViewpoint(vp);
        mMapView.setMap(map);

        ArcGisZoomView zoomBtn=(ArcGisZoomView)findViewById(R.id.arcgis_zoom_btn);
        zoomBtn.init(mMapView);
        zoomBtn.setZoomHeight(35);
        zoomBtn.setZoomWidth(60);
        zoomBtn.setZoomBackground(R.drawable.round_corner);
        zoomBtn.isHorizontal(true);
        zoomBtn.setZoomInNum(2);
        zoomBtn.setZoomOutNum(2);
        zoomBtn.setZoomOutImage(R.drawable.sddman_zoomout);
        zoomBtn.setZoomInImage(R.drawable.sddman_zoomin);
        zoomBtn.setShowText(true);
        zoomBtn.setZoomOutText("缩小");
        zoomBtn.setZoomInText("放大");
        zoomBtn.setFontSize(12);
        zoomBtn.setFontColor(R.color.colorMain);

        MeasureToolView measureToolView=(MeasureToolView)findViewById(R.id.measure_tool);
        measureToolView.init(mMapView);
        measureToolView.setButtonWidth(60);
        measureToolView.setButtonHeight(40);
        measureToolView.setMeasureBackground(R.color.colorAccent);
        measureToolView.setSohwText(true);
        measureToolView.setFontSize(12);
        measureToolView.setFontColor(R.color.color444);
        measureToolView.setMeasurePrevStr("撤销");
        measureToolView.setMeasureNextStr("恢复");
        measureToolView.setMeasureLengthStr("测距");
        measureToolView.setMeasureAreaStr("测面积");
        measureToolView.setMeasureClearStr("清除");
        measureToolView.setMeasureEndStr("完成");
        measureToolView.setMeasurePrevImage(R.drawable.sddman_measure_prev);
        measureToolView.setMeasureNextImage(R.drawable.sddman_measure_next);
        measureToolView.setMeasureLengthImage(R.drawable.sddman_measure_length);
        measureToolView.setMeasureAreaImage(R.drawable.sddman_measure_area);
        measureToolView.setMeasureClearImage(R.drawable.sddman_measure_clear);
        measureToolView.setMeasureEndImage(R.drawable.sddman_measure_end);
        measureToolView.setSpatialReference(SpatialReference.create(3857));
        measureToolView.setLengthType(Variable.Measure.KM);
        measureToolView.setAreaType(Variable.Measure.KM2);

        MapRotateView mapRotateView=(MapRotateView)findViewById(R.id.map_rotate_view);
        mapRotateView.init(mMapView);

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
