package cn.sddman.arcgistool.manager;

import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.view.MeasureToolView;

public class ArcgisToolManager {
    private static MeasureToolManager measureToolManager=null;
    private static ArcgisToolManager arcgisToolManager=null;
    private static MapView mMapView;

    public ArcgisToolManager(MapView mMapView) {
        this.mMapView=mMapView;
    }

    public static ArcgisToolManager create(MapView mMapView){
        if(arcgisToolManager==null){
            arcgisToolManager=new ArcgisToolManager(mMapView);
        }
        return arcgisToolManager;
    }

    public MeasureToolManager builderMeasure(MeasureToolView measureToolView){
        if(measureToolManager==null){
            measureToolManager=new MeasureToolManager(measureToolView,mMapView);
        }
        return measureToolManager;
    }
}
