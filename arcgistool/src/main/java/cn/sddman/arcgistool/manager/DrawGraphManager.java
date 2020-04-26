package cn.sddman.arcgistool.manager;

import android.view.MotionEvent;

import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.view.DrawGraphView;

public class DrawGraphManager {

    private DrawGraphView drawGraphView;
    private MapView mMapView;


    public DrawGraphManager(DrawGraphView drawGraphView, MapView mMapView) {
        this.drawGraphView = drawGraphView;
        this.mMapView=mMapView;
        drawGraphView.init(mMapView);
    }

    public void onMapSingleTapUp(MotionEvent e){
        drawGraphView.onMapSingleTapUp(e);
    }
    public void onScroll(MotionEvent e1,MotionEvent e2,float distanceX, float distanceY){
        drawGraphView.onScroll(e1,e2,distanceX,distanceY);
    }
    public DrawGraphManager setMeasureClickListener(MeasureClickListener measureClickListener) {
        drawGraphView.setMeasureClickListener(measureClickListener);
        return this;
    }
    public DrawGraphManager setButtonWidth(int buttonWidth){
        drawGraphView.setButtonWidth(buttonWidth);
        return this;
    }
    public DrawGraphManager setButtonHeight(int buttonHeight) {
        drawGraphView.setButtonHeight(buttonHeight);
        return this;
    }
    public DrawGraphManager setBackground(int bg) {
        drawGraphView.setBackground(bg);
        return this;
    }
    public DrawGraphManager setSohwText(boolean showText) {
        drawGraphView.setSohwText(showText);
        return this;
    }
    public DrawGraphManager setFontSize(int fontSize) {
        drawGraphView.setFontSize(fontSize);
        return this;
    }
    public DrawGraphManager setFontColor(int fontColor) {
        drawGraphView.setFontColor(fontColor);
        return this;
    }
    
    public DrawGraphManager setdrawingLineStr(String drawingLineStr) {
        drawGraphView.setdrawingLineStr(drawingLineStr);
        return this;
    }
    
    public DrawGraphManager setdrawingPolygonStr(String drawingPolygonStr) {
        drawGraphView.setdrawingPolygonStr(drawingPolygonStr);
        return this;
    }
    
    public DrawGraphManager setdrawingOrthogonStr(String drawingOrthogonStr) {
        drawGraphView.setdrawingOrthogonStr(drawingOrthogonStr);
        return this;
    }
    
    public DrawGraphManager setdrawingCircleStr(String drawingCircleStr) {
        drawGraphView.setdrawingCircleStr(drawingCircleStr);
        return this;
    }
    
    public DrawGraphManager setdrawingEllipseStr(String drawingEllipseStr) {
        drawGraphView.setdrawingEllipseStr(drawingEllipseStr);
        return this;
    }
    
    public DrawGraphManager setdrawingRhombusStr(String drawingRhombusStr) {
        drawGraphView.setdrawingRhombusStr(drawingRhombusStr);
        return this;
    }
    
    public DrawGraphManager setClearDrawStr(String clearDrawStr) {
        drawGraphView.setClearDrawStr(clearDrawStr);
        return this;
    }
    
    public DrawGraphManager setdrawingLineImage(int drawingLineImage) {
        drawGraphView.setdrawingLineImage(drawingLineImage);
        return this;
    }
    
    public DrawGraphManager setdrawingPolygonImage(int drawingPolygonImage) {
        drawGraphView.setdrawingPolygonImage(drawingPolygonImage);
        return this;
    }
    
    public DrawGraphManager setdrawingOrthogonImage(int drawingOrthogonImage) {
        drawGraphView.setdrawingOrthogonImage(drawingOrthogonImage);
        return this;
    }
    
    public DrawGraphManager setdrawingCircleImage(int drawingCircleImage) {
        drawGraphView.setdrawingCircleImage(drawingCircleImage);
        return this;
    }
    
    public DrawGraphManager setdrawingEllipseImage(int drawingEllipseImage) {
        drawGraphView.setdrawingEllipseImage(drawingEllipseImage);
        return this;
    }
    
    public DrawGraphManager setdrawingRhombusImage(int drawingRhombusImage) {
        drawGraphView.setdrawingRhombusImage(drawingRhombusImage);
        return this;
    }
}
