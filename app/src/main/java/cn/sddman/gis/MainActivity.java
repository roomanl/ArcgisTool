package cn.sddman.gis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.manager.ArcgisToolManager;
import cn.sddman.arcgistool.manager.MeasureToolManager;
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

        MapRotateView mapRotateView=(MapRotateView)findViewById(R.id.map_rotate_view);
        mapRotateView.init(mMapView);

        MeasureToolView measureToolView=(MeasureToolView)findViewById(R.id.measure_tool);
        ArcgisToolManager.create(mMapView).builderMeasure(measureToolView)
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
            .setAreaType(Variable.Measure.KM2);
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
