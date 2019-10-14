package cn.sddman.arcgistool.manager;

import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.listener.ZoomClickListener;
import cn.sddman.arcgistool.view.ArcGisZoomView;

public class ArcGisZoomManager {
    private ArcGisZoomView arcGisZoomView;
    private MapView mMapView;

    public ArcGisZoomManager(ArcGisZoomView arcGisZoomView, MapView mMapView) {
        this.arcGisZoomView = arcGisZoomView;
        this.mMapView = mMapView;
        arcGisZoomView.init(mMapView);
    }
    public ArcGisZoomManager setZoomClickListener(ZoomClickListener zoomClickListener) {
        arcGisZoomView.setZoomClickListener(zoomClickListener);
        return this;
    }
    public ArcGisZoomManager setZoomWidth(int w){
        arcGisZoomView.setZoomWidth(w);
        return this;
    }

    public ArcGisZoomManager setZoomHeight(int h){
        arcGisZoomView.setZoomHeight(h);
        return this;
    }

    public ArcGisZoomManager setZoomBackground(int bg){
        arcGisZoomView.setZoomBackground(bg);
        return this;
    }

    public ArcGisZoomManager isHorizontal(boolean horizontal) {
        arcGisZoomView.isHorizontal(horizontal);
        return this;
    }


    public ArcGisZoomManager setZoomInNum(int num){
        arcGisZoomView.setZoomInNum(num);
        return this;
    }

    public ArcGisZoomManager setZoomOutNum(int num){
        arcGisZoomView.setZoomOutNum(num);
        return this;
    }

    public ArcGisZoomManager setZoomInImage(int zoomInImage) {
        arcGisZoomView.setZoomInImage(zoomInImage);
        return this;
    }

    public ArcGisZoomManager setZoomOutImage(int zoomOutImage) {
        arcGisZoomView.setZoomOutImage(zoomOutImage);
        return this;
    }

    public ArcGisZoomManager setShowText(boolean showText) {
        arcGisZoomView.setShowText(showText);
        return this;
    }

    public ArcGisZoomManager setZoomInText(String zoomInText) {
        arcGisZoomView.setZoomInText(zoomInText);
        return this;
    }

    public ArcGisZoomManager setZoomOutText(String zoomOutText) {
        arcGisZoomView.setZoomOutText(zoomOutText);
        return this;
    }

    public ArcGisZoomManager setFontSize(int fontSize) {
        arcGisZoomView.setFontSize(fontSize);
        return this;
    }

    public ArcGisZoomManager setFontColor(int fontColor) {
        arcGisZoomView.setFontColor(fontColor);
        return this;
    }
}
