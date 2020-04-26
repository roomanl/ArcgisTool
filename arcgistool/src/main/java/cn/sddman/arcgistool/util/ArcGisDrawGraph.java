package cn.sddman.arcgistool.util;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import java.util.ArrayList;
import java.util.List;

import cn.sddman.arcgistool.common.Draw;
import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.listener.DrawGraphListener;

public class ArcGisDrawGraph {
    private Context context;
    private MapView mapView;
    private GraphicsOverlay drawGraphicOverlay=null;//绘制面板
    private SimpleMarkerSymbol pointSymbol=null;//点样式
    private PointCollection mPointCollection = null;
    private List<Point> circlePointList;
    private List<Graphic> circleGraphicList=null;
    private List<Graphic> boxGraphicList=null;
    private List<Graphic> pointGraphicList=null;
    private DrawGraphListener drawGraphListener;

    public ArcGisDrawGraph(Context context, MapView mapView) {
        this.context=context;
        this.mapView=mapView;
        init();
    }
    private void init(){
        circlePointList = new ArrayList<>();
        circleGraphicList = new ArrayList<>();
        pointGraphicList = new ArrayList<>();
        boxGraphicList = new ArrayList<>();
        mPointCollection = new PointCollection(mapView.getSpatialReference());
        pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.GRAY, 8);
    }
    public void setDrawGraphListener(DrawGraphListener drawGraphListener){
        this.drawGraphListener=drawGraphListener;
    }
    public void clear(){
        removeAllGraphics(circleGraphicList);
        removeAllGraphics(pointGraphicList);
        removeAllGraphics(boxGraphicList);
    }
    private void drawPoint(Point point){
        if(drawGraphicOverlay==null){
            drawGraphicOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(drawGraphicOverlay);
           // pointGraphic.add(drawPointGraphicOverlay);
        }
        Graphic pointGraphic = new Graphic(point,pointSymbol);
        drawGraphicOverlay.getGraphics().add(pointGraphic);
        pointGraphicList.add(pointGraphic);
    }
    public void drawBox(float x,float y) {
        Point point=screenXYtoPpoint(x,y);
        circlePointList.add(point);
        drawPoint(point);
        if(circlePointList.size()==2){
            drawBox(circlePointList.get(0),circlePointList.get(1));
            circlePointList.clear();
            removeAllGraphics(pointGraphicList);
            if(drawGraphListener!=null){
                drawGraphListener.drawEnd(Variable.GraphType.BOX);
            }
        }
    }
    public void drawBox(Point point1,Point point2) {
        mPointCollection.clear();
        mPointCollection.add(point1);
        mPointCollection.add(new Point(point1.getX(),point2.getY()));
        mPointCollection.add(point2);
        mPointCollection.add(new Point(point2.getX(),point1.getY()));
        boxGraphicList.add(drawPolygon(mPointCollection));
    }
    public void drawCircle(float x,float y) {
        Point point=screenXYtoPpoint(x,y);
        circlePointList.add(point);
        drawPoint(point);
        if(circlePointList.size()==2){
            drawCircle(circlePointList.get(0),circlePointList.get(1));
            circlePointList.clear();
            removeAllGraphics(pointGraphicList);
            if(drawGraphListener!=null){
                drawGraphListener.drawEnd(Variable.GraphType.CIRCLE);
            }
        }
    }
    public void drawCircle(Point point1,Point point2) {
        double radius = 0;
        double x = (point2.getX() - point1.getX());
        double y = (point2.getY() - point1.getY());
        radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        drawCircle(point1, radius);
    }

    public void drawCircle(Point point, double radius) {
        Point[] points = getCirclePoints(point, radius);
        mPointCollection.clear();
        for (Point p : points) {
            mPointCollection.add(p);
        }
        circleGraphicList.add(drawPolygon(mPointCollection));
    }
    public Graphic drawPolygon(PointCollection mPointCollection){
        Polygon polygon = new Polygon(mPointCollection);
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3.0f);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#33e97676"), lineSymbol);
        Graphic graphic = new Graphic(polygon, simpleFillSymbol);
        drawGraphicOverlay.getGraphics().add(graphic);
        return graphic;
    }

    private static Point[] getCirclePoints(Point center, double radius) {
        Point[] points = new Point[50];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 50; i++) {
            sin = Math.sin(Math.PI * 2 * i / 50);
            cos = Math.cos(Math.PI * 2 * i / 50);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }
    private void removeAllGraphics(List<Graphic> go){
        drawGraphicOverlay.getGraphics().removeAll(go);

    }
    protected Point screenXYtoPpoint(float x, float y){
        android.graphics.Point point=new android.graphics.Point(Math.round(x),Math.round(y));
        return mapView.screenToLocation(point);
    }

}
