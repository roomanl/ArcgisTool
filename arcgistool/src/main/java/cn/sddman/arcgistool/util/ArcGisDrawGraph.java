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

public class ArcGisDrawGraph {
    private Context context;
    private MapView mapView;
    private GraphicsOverlay mGraphicsOverlay;
    private PointCollection mPointCollection = new PointCollection(SpatialReferences.getWebMercator());
    private List<Point> mPointList;

    public ArcGisDrawGraph(Context context, MapView mapView) {
        this.context=context;
        this.mapView=mapView;
        mGraphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(mGraphicsOverlay);
        mPointList = new ArrayList<>();
    }
    public void drawCircle(MotionEvent e1,MotionEvent e2,float velocityX, float velocityY) {
        double radius = 0;
        Point point = mapView.screenToLocation(new android.graphics.Point(Math.round(e2.getX()), Math.round(e2.getY())));
        mPointList.add(point);
        if (mPointList.size() == 2) {
            double x = (mPointList.get(1).getX() - mPointList.get(0).getX());
            double y = (mPointList.get(1).getY() - mPointList.get(0).getY());
            radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }

        getCircle(mPointList.get(0), radius);
    }

    private void getCircle(Point point, double radius) {
        //        polygon.setEmpty();
        Point[] points = getPoints(point, radius);
        mPointCollection.clear();
        for (Point p : points) {
            mPointCollection.add(p);
        }

        Polygon polygon = new Polygon(mPointCollection);

        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);

        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3.0f);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#33e97676"), lineSymbol);

        Graphic graphic = new Graphic(polygon, simpleFillSymbol);

        mGraphicsOverlay.getGraphics().add(graphic);
    }

    /**
     * 通过中心点和半径计算得出圆形的边线点集合
     *
     * @param center
     * @param radius
     * @return
     */
    private static Point[] getPoints(Point center, double radius) {
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

}
