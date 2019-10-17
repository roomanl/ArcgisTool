package cn.sddman.arcgistool.manager;

import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.listener.MapRotateClickListener;
import cn.sddman.arcgistool.view.MapRotateView;

public class MapRotateViewManager {

    private MapRotateView mapRotateView;
    private MapView mMapView;

    public MapRotateViewManager(MapRotateView mapRotateView, MapView mMapView) {
        this.mapRotateView = mapRotateView;
        this.mMapView = mMapView;
        mapRotateView.init(mMapView);
    }

    public MapRotateViewManager setWidth(int w){
        mapRotateView.setWidth(w);
        return this;
    }
    public MapRotateViewManager setHeight(int h){
        mapRotateView.setHeight(h);
        return this;
    }

    public MapRotateViewManager setBackground(int bg){
        mapRotateView.setBackground(bg);
        return this;
    }

    public MapRotateViewManager setRotateNum(double num){
        mapRotateView.setRotateNum(num);
        return this;
    }

    public MapRotateViewManager setRotateImage(int zoomRotateImage) {
        mapRotateView.setRotateImage(zoomRotateImage);
        return this;
    }

    public MapRotateViewManager setShowText(boolean showText) {
        mapRotateView.setShowText(showText);
        return this;
    }

    public MapRotateViewManager setRotateText(String zoomRotateText) {
        mapRotateView.setRotateText(zoomRotateText);
        return this;
    }

    public MapRotateViewManager setFontSize(int fontSize) {
        mapRotateView.setFontSize(fontSize);
        return this;
    }

    public MapRotateViewManager setFontColor(int fontColor) {
        mapRotateView.setFontColor(fontColor);
        return this;
    }
    public MapRotateViewManager setMapRotateClickListener(MapRotateClickListener mapRotateClickListener) {
        mapRotateView.setMapRotateClickListener(mapRotateClickListener);
        return this;
    }
}
