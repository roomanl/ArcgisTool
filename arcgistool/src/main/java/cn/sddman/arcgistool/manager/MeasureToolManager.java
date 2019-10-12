package cn.sddman.arcgistool.manager;

import android.view.MotionEvent;

import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.view.MeasureToolView;

public class MeasureToolManager {

    private MeasureToolView measureToolView;
    private MapView mMapView;


    public MeasureToolManager(MeasureToolView measureToolView,MapView mMapView) {
        this.measureToolView = measureToolView;
        this.mMapView=mMapView;
        measureToolView.init(mMapView);
    }

    public void onMapSingleTapUp(MotionEvent e){
        measureToolView.onMapSingleTapUp(e);
    }
    public MeasureToolManager setMeasureClickListener(MeasureClickListener measureClickListener) {
        measureToolView.setMeasureClickListener(measureClickListener);
        return this;
    }
    public MeasureToolManager setButtonWidth(int buttonWidth){
        measureToolView.setButtonWidth(buttonWidth);
        return this;
    }
    public MeasureToolManager setButtonHeight(int buttonHeight) {
        measureToolView.setButtonHeight(buttonHeight);
        return this;
    }
    public MeasureToolManager setMeasureBackground(int bg) {
        measureToolView.setMeasureBackground(bg);
        return this;
    }
    public MeasureToolManager setSohwText(boolean showText) {
        measureToolView.setSohwText(showText);
        return this;
    }
    public MeasureToolManager setFontSize(int fontSize) {
        measureToolView.setFontSize(fontSize);
        return this;
    }
    public MeasureToolManager setFontColor(int fontColor) {
        measureToolView.setFontColor(fontColor);
        return this;
    }
    public MeasureToolManager setMeasurePrevStr(String measurePrevStr) {
        measureToolView.setMeasurePrevStr(measurePrevStr);
        return this;
    }
    public MeasureToolManager setMeasureNextStr(String measureNextStr) {
        measureToolView.setMeasureNextStr(measureNextStr);
        return this;
    }

    public MeasureToolManager setMeasureLengthStr(String measureLengthStr) {
        measureToolView.setMeasureLengthStr(measureLengthStr);
        return this;
    }

    public MeasureToolManager setMeasureAreaStr(String measureAreaStr) {
        measureToolView.setMeasureAreaStr(measureAreaStr);
        return this;
    }

    public MeasureToolManager setMeasureClearStr(String measureClearStr) {
        measureToolView.setMeasureClearStr(measureClearStr);
        return this;
    }

    public MeasureToolManager setMeasureEndStr(String measureEndStr) {
        measureToolView.setMeasureEndStr(measureEndStr);
        return this;
    }
    public MeasureToolManager setMeasurePrevImage(int measurePrevImage) {
        measureToolView.setMeasurePrevImage(measurePrevImage);
        return this;
    }

    public MeasureToolManager setMeasureNextImage(int measureNextImage) {
        measureToolView.setMeasureNextImage(measureNextImage);
        return this;
    }

    public MeasureToolManager setMeasureLengthImage(int measureLengthImage) {
        measureToolView.setMeasureLengthImage(measureLengthImage);
        return this;
    }

    public MeasureToolManager setMeasureAreaImage(int measureAreaImage) {
        measureToolView.setMeasureAreaImage(measureAreaImage);
        return this;
    }

    public MeasureToolManager setMeasureClearImage(int measureClearImage) {
        measureToolView.setMeasureClearImage(measureClearImage);
        return this;
    }

    public MeasureToolManager setMeasureEndImage(int measureEndImage) {
        measureToolView.setMeasureEndImage(measureEndImage);
        return this;
    }
    public MeasureToolManager setSpatialReference(SpatialReference spatialReference) {
        measureToolView.setSpatialReference(spatialReference);
        return this;
    }

    public MeasureToolManager setLengthType(Variable.Measure type) {
        measureToolView.setLengthType(type);
        return this;
    }

    public MeasureToolManager setAreaType(Variable.Measure type) {
        measureToolView.setAreaType(type);
        return this;
    }
}
