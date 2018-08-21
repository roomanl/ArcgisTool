package cn.sddman.arcgistool.util;

import android.content.Context;

import com.esri.arcgisruntime.geometry.GeographicTransformation;
import com.esri.arcgisruntime.geometry.GeographicTransformationStep;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PolygonBuilder;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;

import cn.sddman.arcgistool.common.Draw;
import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.DrawEntity;

public class ArcGisMeasure extends Draw {
    private Context context;
    private MapView mapView;
    private Variable.DrawType drawType=null;
    private Variable.Measure measureLengthType=Variable.Measure.M;
    private Variable.Measure measureAreaType=Variable.Measure.M2;
    private double lineLength=0;
    private List<Double> lengthList;
    private List<Double> tmpLengthList;
    public ArcGisMeasure(Context context, MapView mapView) {
        super(context, mapView);
        this.context=context;
        this.mapView=mapView;
        lengthList=new ArrayList<>();
        tmpLengthList=new ArrayList<>();
    }

    public void startMeasuredLength(float screenX,float screenY){
        if(drawType==null) {
            super.startLine();
            drawType=Variable.DrawType.LINE;
        }
        drawScreenXY(screenX,screenY);
    }

    public void startMeasuredLength(android.graphics.Point screenPoint){
        if(drawType==null) {
            super.startLine();
            drawType=Variable.DrawType.LINE;
        }
        drawScreenPoint(screenPoint);
    }
    public void startMeasuredArea(float screenX,float screenY){
        if(drawType==null) {
            super.startPolygon();
            drawType=Variable.DrawType.POLYGON;
        }
        drawScreenXY(screenX,screenY);
    }
    public void startMeasuredArea(android.graphics.Point screenPoint){
        if(drawType==null) {
            super.startPolygon();
            drawType=Variable.DrawType.POLYGON;
        }
        drawScreenPoint(screenPoint);
    }



    public boolean prevDraw(){
        if(lengthList.size()>1) {
            lengthList.remove(lengthList.size() - 1);
            lineLength=lengthList.get(lengthList.size()-1);
        }else{
            lengthList.clear();
            lineLength=0;
        }
        return super.prevDraw();
    }

    public boolean nextDraw(){
        if(lengthList.size()>0 && lengthList.size()<tmpLengthList.size()) {
            lengthList.add(tmpLengthList.get(lengthList.size()));
            lineLength=lengthList.get(lengthList.size()-1);
        }
        return super.nextDraw();
    }

    public DrawEntity endMeasure(){
        drawType=null;
        lineLength=0;
        tmpLengthList.clear();
        lengthList.clear();
        return super.endDraw();
    }
    public DrawEntity clearMeasure(){
        drawType=null;
        lineLength=0;
        tmpLengthList.clear();
        lengthList.clear();
        return super.clear();
    }
    public void setSpatialReference(SpatialReference spatialReference) {
        super.setSpatialReference(spatialReference);
    }

    public void setLengthType(Variable.Measure type){
        this.measureLengthType=type;
    }
    public void setAreaType(Variable.Measure type){
        this.measureAreaType=type;
    }
    private void drawScreenXY(float x, float y){
        Point point=super.screenXYtoPpoint(x,y);
        if(mapView.getSpatialReference().getWkid()==4490 || mapView.getSpatialReference().getWkid()==4326){
            point = (Point) GeometryEngine.project(point ,SpatialReference.create(102100));
            super.setSpatialReference(SpatialReference.create(102100));
        }
        if( drawType==Variable.DrawType.LINE){
            PolylineBuilder line=(PolylineBuilder)super.drawByGisPoint(point);
            showLength(line,point);
        }else if(drawType==Variable.DrawType.POLYGON){
            PolygonBuilder polygon=(PolygonBuilder)super.drawByGisPoint(point);
            showArea(polygon);
        }

    }
    private void drawScreenPoint(android.graphics.Point screenPoint){
        Point point=super.screenXYtoPpoint(screenPoint.x,screenPoint.y);
        if( drawType==Variable.DrawType.LINE){
            PolylineBuilder line=(PolylineBuilder)super.drawByScreenPoint(screenPoint);
            showLength(line,point);
        }else if(drawType==Variable.DrawType.POLYGON){
            PolygonBuilder polygon=(PolygonBuilder)super.drawByScreenPoint(screenPoint);
            showArea(polygon);
        }
    }
    private void showLength(PolylineBuilder line,Point point){
        if(line!=null) {
            double length = GeometryEngine.length(line.toGeometry());
            lineLength+=length;
            lengthList.add(lineLength);
            tmpLengthList.clear();
            tmpLengthList.addAll(lengthList);
            String s=Util.forMatDouble(Math.abs(Util.lengthChange(lineLength,measureLengthType)));
            super.drawText(point,s+Util.lengthEnameToCname(measureLengthType),false);
        }
    }

    private void showArea(PolygonBuilder polygon){
        if(polygon!=null) {
            double area = GeometryEngine.area(polygon.toGeometry());
            String s=Util.forMatDouble(Math.abs(Util.areaChange(area,measureAreaType)));
            super.drawText(polygon.toGeometry().getExtent().getCenter(),s+Util.lengthEnameToCname(measureAreaType),true);
        }
    }


}
