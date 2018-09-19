package cn.sddman.arcgistool.common;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PolygonBuilder;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;

import java.util.ArrayList;
import java.util.List;

import cn.sddman.arcgistool.R;
import cn.sddman.arcgistool.entity.DrawEntity;

public class Draw {
    private Context context;
    private MapView mapView;
    private SpatialReference spatialReference;
    private GraphicsOverlay drawTextGraphicOverlay=null;//绘制面板
    private GraphicsOverlay drawPointGraphicOverlay=null;
    private GraphicsOverlay drawLineGraphicOverlay=null;
    private GraphicsOverlay drawPolygonGraphicOverlay=null;
    private List<GraphicsOverlay> textGraphic=null;//文字集合
    private List<GraphicsOverlay> polygonGraphic=null;//面集合
    private List<GraphicsOverlay> lineGraphic=null;//线集合
    private List<GraphicsOverlay> pointGraphic=null;//点集合
    private TextSymbol textSymbol=null;//标注样式
    private SimpleMarkerSymbol pointSymbol=null;//点样式
    private SimpleLineSymbol lineSymbol=null;//线样式
    private SimpleFillSymbol polygonSymbol =null;//面样式
    private List<List<Point>>  pointGroup=null;//绘制点的集合的集合
    private List<Point> pointList=null;//每次绘制点的集合
    private List<Point> tmpPointList=null;//撤销、恢复临时点集合
    private List<TextSymbol> textList=null;//每次绘制文字的集合
    private List<TextSymbol> tmpTextList=null;//撤销、恢复临时文字集合
    private List<Graphic> textPointList=null;//每次绘制文字的集合
    private List<Graphic> tmpTextPointList=null;//撤销、恢复临时文字集合
    private Variable.DrawType drawType=null;//绘制类型
    private boolean isNext=false,isPrev=false;//是否在恢复或者撤销
    public Draw(Context context, MapView mapView){
        this.context=context;
        this.mapView=mapView;
        init();
    }

    private void init(){
        pointList=new ArrayList<>();
        tmpPointList=new ArrayList<>();
        textList=new ArrayList<>();
        tmpTextList=new ArrayList<>();
        textPointList=new ArrayList<>();
        tmpTextPointList=new ArrayList<>();
        textGraphic=new ArrayList<>();
        polygonGraphic=new ArrayList<>();
        lineGraphic=new ArrayList<>();
        pointGraphic=new ArrayList<>();
        pointGroup=new ArrayList<>();
        pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.GRAY, 8);
        lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2);
        //lineSymbol.setMarkerStyle(SimpleLineSymbol.MarkerStyle.ARROW);
        polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.argb(40,0,0,0), null);
        textSymbol = new TextSymbol(12,"", R.color.colorMain, TextSymbol.HorizontalAlignment.LEFT,TextSymbol.VerticalAlignment.BOTTOM);
    }
    protected void startPoint(){
        drawType=Variable.DrawType.POINT;
    }
    protected void startLine(){
        drawType=Variable.DrawType.LINE;
    }
    protected void startPolygon(){
        drawType=Variable.DrawType.POLYGON;
    }
    protected void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    private SpatialReference getSpatialReference() {
        if(spatialReference==null){
            return mapView.getSpatialReference();
        }
        return spatialReference;
    }

    protected Object drawByScreenPoint(android.graphics.Point point){
        if(drawType==Variable.DrawType.POINT){
            return drawPointByScreenPoint(point);
        }else if(drawType==Variable.DrawType.LINE){
            return drawLineByScreenPoint(point);
        }else if(drawType==Variable.DrawType.POLYGON){
            return drawPolygonByScreenPoint(point);
        }
        return null;
    }

    protected Object drawByGisPoint(Point point){
        if(drawType==Variable.DrawType.POINT){
            return drawPointByGisPoint(point);
        }else if(drawType==Variable.DrawType.LINE){
            return drawLineByGisPoint(point);
        }else if(drawType==Variable.DrawType.POLYGON){
            return drawPolygonByGisPoint(point);
        }
        return null;
    }

    protected Object drawByScreenXY(float x,float y){
        if(drawType==Variable.DrawType.POINT){
            return drawPointByScreenXY(x,y);
        }else if(drawType==Variable.DrawType.LINE){
            return drawLineByScreenXY(x,y);
        }else if(drawType==Variable.DrawType.POLYGON){
            return drawPolygonByScreenXY(x, y);
        }
        return null;
    }
    protected void drawPointByGisXY(float x,float y){
        if(drawType==Variable.DrawType.POINT){
            drawPointByGisXY(x,y);
        }else if(drawType==Variable.DrawType.LINE){
            drawLineByGisXY(x,y);
        }else if(drawType==Variable.DrawType.POLYGON){
            drawPolygonByGisXY(x, y);
        }
    }
    private void drawText(Point point){
        if(drawTextGraphicOverlay==null){
            drawTextGraphicOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(drawTextGraphicOverlay);
            textGraphic.add(drawTextGraphicOverlay);
        }
        Graphic pointGraphic = new Graphic(point,textSymbol);
        drawTextGraphicOverlay.getGraphics().add(pointGraphic);
        if(!isPrev) {
            textList.add(textSymbol);
        }
        if(!isNext && !isPrev) {
            tmpTextList.clear();
            tmpTextList.addAll(textList);
        }
    }

    private void drawPoint(Point point){
        if(drawPointGraphicOverlay==null){
            drawPointGraphicOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(drawPointGraphicOverlay);
            pointGraphic.add(drawPointGraphicOverlay);
        }
        Graphic pointGraphic = new Graphic(point,pointSymbol);
        drawPointGraphicOverlay.getGraphics().add(pointGraphic);
        pointList.add(point);
        if(!isNext) {
            tmpPointList.clear();
            tmpPointList.addAll(pointList);
        }
    }

    private PolylineBuilder drawLine(Point point1,Point point2){
        //绘制面板为空，说明重新绘制一个linr，在地图和线集合里添加一个新line
        if(drawLineGraphicOverlay==null){
            drawLineGraphicOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(drawLineGraphicOverlay);
            lineGraphic.add(drawLineGraphicOverlay);
        }

        PolylineBuilder lineGeometry = new PolylineBuilder(getSpatialReference());
        lineGeometry.addPoint(point1);
        lineGeometry.addPoint(point2);
        Graphic lineGraphic = new Graphic(lineGeometry.toGeometry(),lineSymbol);
        drawLineGraphicOverlay.getGraphics().add(lineGraphic);
        return lineGeometry;
    }
    private PolygonBuilder drawPolygon(){
        //绘制面板为空，说明重新绘制一个Polyline，在地图和面集合里添加一个新Polyline
        if(drawPolygonGraphicOverlay==null){
            drawPolygonGraphicOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(drawPolygonGraphicOverlay);
            polygonGraphic.add(drawPolygonGraphicOverlay);
        }
        PolygonBuilder polygonGeometry = new PolygonBuilder(getSpatialReference());
        for(Point point:pointList){
            polygonGeometry.addPoint(point);
        }
        drawPolygonGraphicOverlay.getGraphics().clear();
        Graphic polygonGraphic = new Graphic(polygonGeometry.toGeometry(),polygonSymbol);
        drawPolygonGraphicOverlay.getGraphics().add(polygonGraphic);
        return polygonGeometry;

    }
    private Point drawPointByScreenPoint(android.graphics.Point point){
        Point center = mapView.screenToLocation(point);
        this.drawPoint(center);
        return center;
    }

    private Point drawPointByGisPoint(Point point){
        this.drawPoint(point);
        return point;
    }
    private Point drawPointByScreenXY(float x,float y){
        android.graphics.Point point=new android.graphics.Point(Math.round(x),Math.round(y));
        Point center = mapView.screenToLocation(point);
        this.drawPoint(center);
        return center;
    }
    private Point drawPointByGisXY(double x,double y){
        Point center = new Point(x, y);
        this.drawPoint(center);
        return center;
    }
    private PolylineBuilder drawLineByScreenPoint(android.graphics.Point point){
        Point nextPoint=this.drawPointByScreenPoint(point);
        if(getPointSize()>1) {
            Point prvPoint=getLastPoint();
            return this.drawLine(prvPoint,nextPoint);
        }
        return null;
    }
    private PolylineBuilder drawLineByGisPoint(Point point){
        Point nextPoint=this.drawPointByGisPoint(point);
        if(getPointSize()>1) {
            Point prvPoint=getLastPoint();
            return this.drawLine(prvPoint,nextPoint);
        }
        return null;
    }
    private PolylineBuilder drawLineByGisPoint(Point point1,Point point2){
        return this.drawLine(point1,point2);
    }

    private PolylineBuilder drawLineByScreenXY(float x,float y){
        Point nextPoint=this.drawPointByScreenXY(x,y);
        if(getPointSize()>1) {
            Point prvPoint=getLastPoint();
            return this.drawLine(prvPoint,nextPoint);
        }
        return null;
    }
    private PolylineBuilder drawLineByGisXY(double x,double y){
        Point nextPoint=this.drawPointByGisXY(x,y);
        if(getPointSize()>1) {
            Point prvPoint=getLastPoint();
           return this.drawLine(prvPoint,nextPoint);
        }
        return null;
    }

    private PolygonBuilder drawPolygonByScreenXY(float x, float y){
        drawLineByScreenXY(x,y);
        if(getPointSize()>=3) {
            return drawPolygon();
        }
        return null;
    }

    private PolygonBuilder drawPolygonByScreenPoint(android.graphics.Point point){
        drawLineByScreenPoint(point);
        if(getPointSize()>=3) {
            return drawPolygon();
        }
        return null;
    }

    private PolygonBuilder drawPolygonByGisXY(double x, double y){
        drawLineByGisXY(x,y);
        if(getPointSize()>=3) {
           return drawPolygon();
        }
        return null;
    }

    private PolygonBuilder drawPolygonByGisPoint(Point point){
        drawLineByGisPoint(point);
        if(getPointSize()>=3) {
           return drawPolygon();
        }
        return null;
    }
    protected Point screenXYtoPpoint(float x, float y){
        android.graphics.Point point=new android.graphics.Point(Math.round(x),Math.round(y));
        return mapView.screenToLocation(point);
    }

    protected void drawText(Point point,String text,boolean replaceOld){
        textSymbol = getTextSymbol();
        if(replaceOld) {
            textSymbol.setHorizontalAlignment(TextSymbol.HorizontalAlignment.CENTER);
            if(drawTextGraphicOverlay!=null)
                drawTextGraphicOverlay.getGraphics().clear();
        }
        textSymbol.setText(text);
        drawText(point);
    }
    protected boolean prevDraw(){
        if(pointList.size()>1){
            pointList.remove(pointList.size()-1);
            if(textList.size()>0) {
                textList.remove(textList.size() - 1);
            }
            removePrevGraphics(drawPointGraphicOverlay);
            removePrevGraphics(drawLineGraphicOverlay);
            if(drawType==Variable.DrawType.LINE){
                removePrevGraphics(drawTextGraphicOverlay);
            }else if(drawType==Variable.DrawType.POLYGON){
                if(textList.size()>1) {
                    textSymbol = tmpTextList.get(textList.size() - 1);
                }
                PolygonBuilder pb=drawPolygon();
                isPrev=true;
                if(drawTextGraphicOverlay!=null)
                    drawTextGraphicOverlay.getGraphics().clear();
                if(pointList.size()>2) {
                    drawText(pb.toGeometry().getExtent().getCenter());
                }
                isPrev=false;
            }
        }
        return pointList.size()>1?true:false;
    }
    protected boolean nextDraw(){
        isNext=true;
        if(pointList.size()>0 && pointList.size()<tmpPointList.size()){
            int index=pointList.size();
            textSymbol=tmpTextList.get(textList.size());
            if(drawType==Variable.DrawType.LINE) {
                drawLineByGisPoint(tmpPointList.get(index));
                drawText(tmpPointList.get(index));
            }else if(drawType==Variable.DrawType.POLYGON){
                PolygonBuilder pb=drawPolygonByGisPoint(tmpPointList.get(index));
                if(drawTextGraphicOverlay!=null)
                    drawTextGraphicOverlay.getGraphics().clear();
                if(pointList.size()>2) {
                    drawText(pb.toGeometry().getExtent().getCenter());
                }
            }

        }
        isNext=false;
        return (pointList.size()>0 && pointList.size()<tmpPointList.size())?true:false;
    }
    protected DrawEntity endDraw(){
        if(drawType==Variable.DrawType.POLYGON) {
            if (getPointSize() >= 3) {
                this.drawLineByGisPoint(getEndPoint(), getFristPoint());
            }
        }
        if(pointList.size()>0) {
            pointGroup.add(pointList);
        }
        DrawEntity de=allDraw();
        drawType=null;
        drawPolygonGraphicOverlay=null;
        drawLineGraphicOverlay=null;
        drawPointGraphicOverlay=null;
        drawTextGraphicOverlay=null;
        pointList=new ArrayList<>();
        return de;
    }

    protected DrawEntity clear(){
        DrawEntity de=allDraw();
        removeAllGraphics(lineGraphic);
        removeAllGraphics(polygonGraphic);
        removeAllGraphics(pointGraphic);
        removeAllGraphics(textGraphic);
        drawPolygonGraphicOverlay=null;
        drawLineGraphicOverlay=null;
        drawPointGraphicOverlay=null;
        drawTextGraphicOverlay=null;
        drawType=null;
        pointGroup.clear();
        pointList.clear();
        lineGraphic.clear();
        pointGraphic.clear();
        textGraphic.clear();
        pointGraphic.clear();
        pointGroup=new ArrayList<>();
        pointList=new ArrayList<>();
        lineGraphic=new ArrayList<>();
        pointGraphic=new ArrayList<>();
        textGraphic=new ArrayList<>();
        pointGraphic=new ArrayList<>();
        return de;
    }

    private DrawEntity allDraw(){
        DrawEntity de=new DrawEntity();
        de.setLineGraphic(lineGraphic);
        de.setPointGraphic(pointGraphic);
        de.setTextGraphic(textGraphic);
        de.setPolygonGraphic(polygonGraphic);
        de.setPointGroup(pointGroup);
        return de;
    }

    private void removeAllGraphics(List<GraphicsOverlay> go){
        if(go.size()>0){
            for(GraphicsOverlay graphics:go){
                mapView.getGraphicsOverlays().remove(graphics);
            }
        }
    }
    private void removePrevGraphics(GraphicsOverlay gho){
        gho.getGraphics().remove(gho.getGraphics().size()-1);
    }
    /*protected void prevDraw(){
        if(pointGraphic.size()==0 && textGraphic.size()==0 && lineGraphic.size()==0){
            endDraw();
            return;
        }
        removePrevGraphics(pointGraphic,Variable.DrawType.POINT);
        removePrevGraphics(textGraphic,Variable.DrawType.TEXT);
        removePrevGraphics(lineGraphic,Variable.DrawType.LINE);
        if(pointList.size()>0){
            if (pointList.size() > 0) {
                pointList.remove(pointList.size() - 1);
            }
        }else if(pointGroup.size()>0) {
            pointList=pointGroup.get(pointGroup.size()-1);
            pointGroup.remove(pointGroup.size()-1);
        }
        if(drawType==Variable.DrawType.POLYGON){
            drawPolygon();
        }
    }
    private void removePrevGraphics(List<GraphicsOverlay> gos, Variable.DrawType type){
        if(gos.size()>0) {
            GraphicsOverlay go=gos.get(gos.size()-1);
            List<Graphic> tg=go.getGraphics();
            if (tg.size() > 0) {
                tg.remove(tg.size() - 1);
            }
            if (tg.size() == 0){
                mapView.getGraphicsOverlays().remove(gos.get(gos.size()-1));
                gos.remove(gos.size()-1);
                if(drawType==Variable.DrawType.LINE && type==Variable.DrawType.LINE){
                    removePrevGraphics(pointGraphic,Variable.DrawType.POINT);
                }
                if(drawType==Variable.DrawType.LINE && type==Variable.DrawType.POINT){
                    pointList.clear();
                }
                if(gos.size()>0) {
                    if (type == Variable.DrawType.LINE) {
                        drawLineGraphicOverlay = gos.get(gos.size() - 1);
                    } else if (type == Variable.DrawType.POINT) {
                        drawPointGraphicOverlay = gos.get(gos.size() - 1);
                    } else if (type == Variable.DrawType.TEXT) {
                        drawTextGraphicOverlay = gos.get(gos.size() - 1);
                    }
                }else{
                    if (type == Variable.DrawType.LINE) {
                        drawLineGraphicOverlay = null;
                    } else if (type == Variable.DrawType.POINT) {
                        drawPointGraphicOverlay = null;
                    } else if (type == Variable.DrawType.TEXT) {
                        drawTextGraphicOverlay = null;
                    }
                }
            }
        }
    }*/

    private TextSymbol getTextSymbol(){
        TextSymbol textSymbol = new TextSymbol(12,"", Color.BLACK, TextSymbol.HorizontalAlignment.LEFT,TextSymbol.VerticalAlignment.BOTTOM);
        textSymbol.setColor(Color.WHITE);
        textSymbol.setHaloColor(Color.WHITE);
        textSymbol.setHaloWidth(1);
        textSymbol.setOutlineColor(ContextCompat.getColor(context,R.color.color444));
        textSymbol.setOutlineWidth(1);
        return textSymbol;
    }
    private int getPointSize(){
        return pointList.size();
    }

    public Point getEndPoint(){
        int index=getPointSize()>1?getPointSize()-1:0;
        Point point=pointList.get(index);
        return point;
    }

    private Point getLastPoint(){
        int index=getPointSize()>1?getPointSize()-2:0;
        Point point=pointList.get(index);
        return point;
    }
    private Point getFristPoint(){
        if(getPointSize()==0) return null;
        Point point=pointList.get(0);
        return point;
    }

}
