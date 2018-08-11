package cn.sddman.arcgistool.util;

import android.content.Context;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Draw;
import cn.sddman.arcgistool.entity.DrawEntity;

public class ArcGisDraw extends Draw {
    private Context context;
    private MapView mapView;

    public ArcGisDraw(Context context, MapView mapView){
        super(context,mapView);
        this.context=context;
        this.mapView=mapView;

    }

    public void startPoint(){
        super.startPoint();
    }

    public void startLine(){
        super.startLine();
    }

    public void startPolygon(){
        super.startPolygon();
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        super.setSpatialReference(spatialReference);
    }

    public Object drawByScreenPoint(android.graphics.Point point){
        return super.drawByScreenPoint(point);
    }

    public Object drawByGisPoint(Point point){
        return super.drawByGisPoint(point);
    }

    public Object drawByScreenXY(float x, float y){
        return super.drawByScreenXY(x, y);
    }

    public void drawPointByGisXY(float x,float y){
        super.drawPointByGisXY(x, y);
    }

    /*
    public void drawLineByScreenPoint(android.graphics.Point point1,android.graphics.Point point2){
        Point center1 = mapView.screenToLocation(point1);
        Point center2 = mapView.screenToLocation(point2);
        this.drawLine(center1,center2);
    }
    public void drawLineByScreenXY(float x1,float y1,float x2,float y2){
        android.graphics.Point point1=new android.graphics.Point(Math.round(x1),Math.round(y1));
        android.graphics.Point point2=new android.graphics.Point(Math.round(x2),Math.round(y2));
        Point center1 = mapView.screenToLocation(point1);
        Point center2 = mapView.screenToLocation(point2);
        this.drawLine(center1,center2);
    }
    public void drawLineByGisXY(double x1,double y1,double x2,double y2){
        Point center1 = new Point(x1, y1);
        Point center2 = new Point(x2, y2);
        this.drawLine(center1,center2);
    }
*/

    public DrawEntity endDraw(){
      return super.endDraw();
    }
    
    public DrawEntity clear() {
       return super.clear();
    }
}
