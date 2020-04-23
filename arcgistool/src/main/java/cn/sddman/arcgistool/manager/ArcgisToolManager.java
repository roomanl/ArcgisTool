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
                    return mapListener.onSingleTapUp(e);
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
                if(mapListener!=null) {
                    return mapListener.onDoubleTouchDrag(e);
                }
                return super.onDoubleTouchDrag(e);
            }
            @Override
            public boolean  onFling(MotionEvent e1,MotionEvent e2,float velocityX, float velocityY) {
                if(mapListener!=null) {
                    return mapListener.onFling(e1,e2,velocityX,velocityY);
                }
                return super.onFling(e1,e2,velocityX,velocityY);
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(drawGraphManager!=null){
                    if(viewpoint!=null && arcGISMap!=null){
                        arcGISMap.setInitialViewpoint(viewpoint);
                    }
                    drawGraphManager.onScroll(e1,e2,distanceX,distanceY);
                }
                if(mapListener!=null) {
                    return mapListener.onScroll(e1, e2, distanceX, distanceY);
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onRotate(MotionEvent event, double rotationAngle) {
                if(mapListener!=null) {
                    return mapListener.onRotate(event, rotationAngle);
                }
                return super.onRotate(event, rotationAngle);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if(mapListener!=null) {
                    return mapListener.onScale(detector);
                }
                return super.onScale(detector);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                if(viewpoint==null && arcGISMap!=null){
                    viewpoint=arcGISMap.getInitialViewpoint();
                }
                if(mapListener!=null) {
                    return mapListener.onDown(e);
                }
                return super.onDown(e);
            }

            @Override
            public boolean onUp(MotionEvent e) {
                viewpoint=null;
                if(mapListener!=null) {
                    return mapListener.onUp(e);
                }
                return super.onUp(e);
            }

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(mapListener!=null) {
                    return mapListener.onTouch(view, event);
                }
                return super.onTouch(view, event);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if(mapListener!=null) {
                    mapListener.onLongPress(e);
                }
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                if(mapListener!=null) {
                    return mapListener.onDoubleTapEvent(e);
                }
                return super.onDoubleTapEvent(e);
            }

            @Override
            public boolean onMultiPointerTap(MotionEvent event) {
                if(mapListener!=null) {
                    return mapListener.onMultiPointerTap(event);
                }
                return super.onMultiPointerTap(event);
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                if(mapListener!=null) {
                    return mapListener.onScaleBegin(detector);
                }
                return super.onScaleBegin(detector);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if(mapListener!=null) {
                    return mapListener.onSingleTapConfirmed(e);
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                if(mapListener!=null) {
                    mapListener.onScaleEnd(detector);
                }
                super.onScaleEnd(detector);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                if(mapListener!=null) {
                    mapListener.onShowPress(e);
                }
                super.onShowPress(e);
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
