package cn.sddman.arcgistool.util;

import com.esri.arcgisruntime.mapping.view.MapView;

public class ArcGisControl {
    private MapView mMapView;
    public ArcGisControl(MapView mMapView) {
        this.mMapView=mMapView;
    }

    public void zoomIn(double scale){
        double scale2 = mMapView.getMapScale();
        mMapView.setViewpointScaleAsync(scale2 * (1.0/scale));
    }

    public void zoomOut(double scale){
        double scale2 = mMapView.getMapScale();
        mMapView.setViewpointScaleAsync(scale2 * scale);
    }

    public void mapRotate(double rotate){
        double rotate2=mMapView.getMapRotation();
        mMapView.setViewpointRotationAsync(rotate2-rotate);
    }
}
