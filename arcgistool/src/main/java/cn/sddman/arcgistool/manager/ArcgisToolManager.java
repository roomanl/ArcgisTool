package cn.sddman.arcgistool.manager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.view.ArcGisZoomView;
import cn.sddman.arcgistool.view.MeasureToolView;

public class ArcgisToolManager {
    private static MeasureToolManager measureToolManager=null;
    private static ArcgisToolManager arcgisToolManager=null;
    private ArcGisZoomManager arcGisZoomManager=null;
    private MapView mMapView;
    private Context context;
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
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(mapListener!=null) {
                    mapListener.onScroll(e1, e2, distanceX, distanceY);
                }
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
        };
        mMapView.setOnTouchListener(listener);
    }

    public static ArcgisToolManager create(Context context, MapView mMapView){
        if(arcgisToolManager==null){
            arcgisToolManager=new ArcgisToolManager(context,mMapView);
        }
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
}
