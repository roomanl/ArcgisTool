package cn.sddman.arcgistool.manager;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.view.ArcGisZoomView;
import cn.sddman.arcgistool.view.DrawGraphView;
import cn.sddman.arcgistool.view.MapRotateView;
import cn.sddman.arcgistool.view.MeasureToolView;

public class ArcgisToolManager {
    private static MeasureToolManager measureToolManager=null;
    private static ArcgisToolManager arcgisToolManager=null;
    private ArcGisZoomManager arcGisZoomManager=null;
    private MapRotateViewManager mapRotateViewManager=null;
    private DrawGraphManager drawGraphManager=null;
    private MapView mMapView;
    private ArcGISMap arcGISMap;
    private Context context;
    private Viewpoint viewpoint=null;
    private DefaultMapViewOnTouchListener mapListener;

    public ArcgisToolManager(Context context,MapView mMapView) {
        this.mMapView=mMapView;
        this.context=context;
        DefaultMapViewOnTouchListener listener=new DefaultMapViewOnTouchListener(context,mMapView){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if(measureToolManager!=null){
                    measureToolManager.onMapSingleTapUp(e);
                }
                if(mapListener!=null){
                    mapListener.onSingleTapUp(e);
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(mapListener!=null) {
                    mapListener.onDoubleTap(e);
                }
                return super.onDoubleTap(e);
            }
            @Override
            public boolean onDoubleTouchDrag(MotionEvent e) {
                return super.onDoubleTouchDrag(e);
            }
            @Override
            public boolean  onFling(MotionEvent e1,MotionEvent e2,float velocityX, float velocityY) {
                return super.onFling(e1,e2,velocityX,velocityY);
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(mapListener!=null) {
                    mapListener.onScroll(e1, e2, distanceX, distanceY);
                }
                if(drawGraphManager!=null){
                    if(viewpoint!=null && arcGISMap!=null){
                        arcGISMap.setInitialViewpoint(viewpoint);
                    }
                    drawGraphManager.onScroll(e1,e2,distanceX,distanceY);
                }
                Log.e("e1========>",e1.getX()+"");
                Log.e("e1========>",e2.getX()+"");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onRotate(MotionEvent event, double rotationAngle) {
                if(mapListener!=null) {
                    mapListener.onRotate(event, rotationAngle);
                }
                return super.onRotate(event, rotationAngle);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if(mapListener!=null) {
                    mapListener.onScale(detector);
                }
                return super.onScale(detector);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                if(viewpoint==null && arcGISMap!=null){
                    viewpoint=arcGISMap.getInitialViewpoint();
                }
                return super.onDown(e);
            }

            @Override
            public boolean onUp(MotionEvent e) {
                viewpoint=null;
                Log.e("onUp========>",e.getX()+"");
                return super.onUp(e);
            }

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                return super.onTouch(view, event);
            }
        };
        mMapView.setOnTouchListener(listener);
    }

    public static ArcgisToolManager create(Context context, MapView mMapView){
        if(arcgisToolManager==null){
            arcgisToolManager=new ArcgisToolManager(context,mMapView);
        }
        return arcgisToolManager;
    }
    public static ArcgisToolManager create(Context context, MapView mMapView, ArcGISMap arcGISMap){
        if(arcgisToolManager==null){
            arcgisToolManager=new ArcgisToolManager(context,mMapView);
        }
        arcgisToolManager.arcGISMap=arcGISMap;
        return arcgisToolManager;
    }
    public ArcgisToolManager setMapClickCallBack(DefaultMapViewOnTouchListener mapListener){
        this.mapListener=mapListener;
        return arcgisToolManager;
    }

    public MeasureToolManager builderMeasure(MeasureToolView measureToolView){
        if(measureToolManager==null){
            measureToolManager=new MeasureToolManager(measureToolView,mMapView);
        }
        return measureToolManager;
    }
    public ArcGisZoomManager builderZoomView(ArcGisZoomView arcGisZoomView){
        if(arcGisZoomManager==null){
            arcGisZoomManager=new ArcGisZoomManager(arcGisZoomView,mMapView);
        }
        return arcGisZoomManager;
    }
    public MapRotateViewManager builderRotateView(MapRotateView mapRotateView){
        if(mapRotateViewManager==null){
            mapRotateViewManager=new MapRotateViewManager(mapRotateView,mMapView);
        }
        return mapRotateViewManager;
    }
    public DrawGraphManager builderDrawGraphView(DrawGraphView drawGraphView){
        if(drawGraphManager==null){
            drawGraphManager=new DrawGraphManager(drawGraphView,mMapView);
        }
        return drawGraphManager;
    }
}
