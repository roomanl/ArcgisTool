package cn.sddman.arcgistool.layer;

import android.content.Context;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.internal.jni.CoreRequest;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.layers.WmtsLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.ogc.wmts.WmtsService;
import com.esri.arcgisruntime.ogc.wmts.WmtsServiceInfo;

import java.util.ArrayList;
import java.util.List;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.BaseMapEntity;
import cn.sddman.arcgistool.listener.MapLoadingListener;

public class ArcGisBaseMap {
    private Context context;
    private  MapView mapView;
    private ArcGISMap map=null;
    private Envelope envelope=null;
    private MapLoadingListener listener=null;
    private List<BaseMapEntity> baseMaps;
    public ArcGisBaseMap(Context context, MapView mapView) {
        this.context=context;
        this.mapView=mapView;
        map=new ArcGISMap();
        this.mapView.setMap(map);
        baseMaps=new ArrayList<>();
    }
    public void setMapLoadingListener(MapLoadingListener listener){
        this.listener=listener;
    }
    public void addBaseMap(String url, String layerKey,boolean show, Variable.LayerType type){
        BaseMapEntity bme=new BaseMapEntity();
        bme.setUrl(url);
        bme.setLayerKey(layerKey);
        bme.setType(type);
        bme.setShow(show);
        addBaseMap(bme);
    }
    public void addBaseMap(final BaseMapEntity bme){
        Layer layer=null;
        switch (bme.getType()){
            case BUNDLE:
                TileCache tileCache = new TileCache(bme.getUrl());
                layer = new ArcGISTiledLayer(tileCache);
                break;
            case TILE:
                layer = new ArcGISTiledLayer(bme.getUrl());
                break;
            case IMG:
                layer=new ArcGISMapImageLayer(bme.getUrl());
                break;
            case WMTS:
                WmtsService wmtsService = new WmtsService(bme.getUrl());
                WmtsServiceInfo wmtsServiceInfo = wmtsService.getServiceInfo();
                layer = new WmtsLayer(wmtsServiceInfo.getLayerInfos().get(0));
        }
        layer.setId(bme.getLayerKey());
        bme.setLayer(layer);
        baseMaps.add(bme);
        layer.loadAsync();
        final Layer finalLayer = layer;
        layer.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (finalLayer.getLoadStatus() == LoadStatus.LOADED) {
                    bme.setBasemap(new Basemap(finalLayer));
                    if(bme.isShow()) {
                        map.setBasemap(bme.getBasemap());
                    }
                    if(listener!=null){
                        listener.loadingSuccess();
                    }
                }else{
                    if(listener!=null){
                        listener.loadingFailed(finalLayer.getLoadStatus());
                    }
                }
            }
        });
    }

    public void setEnvelope(Envelope envelope){
        this.envelope=envelope;
        Viewpoint vp = new Viewpoint(envelope);
        map.setInitialViewpoint(vp);
    }

    public void switcBaseMap(){
        for(int i=0;i<baseMaps.size();i++){
            BaseMapEntity bme=baseMaps.get(i);
            if(bme.isShow()){
                bme.setShow(false);
                if((i+1)>=baseMaps.size()){
                    switcBaseMap(0);
                }else{
                    switcBaseMap(i+1);
                }
                break;
            }
        }
    }

    public void switcBaseMap(int index){
        for(BaseMapEntity bme:baseMaps){
            bme.setShow(false);
        }
        BaseMapEntity bme=baseMaps.get(index);
        bme.setShow(true);
        map.setBasemap(bme.getBasemap());
    }

}
