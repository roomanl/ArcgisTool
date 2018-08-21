package cn.sddman.arcgistool.layer;

import android.content.Context;

import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.layers.WmtsLayer;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.ogc.wmts.WmtsService;
import com.esri.arcgisruntime.ogc.wmts.WmtsServiceInfo;

import java.util.ArrayList;
import java.util.List;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.OpenLayerEntity;

public class ArcGisLayer {
    private Context context;
    private  MapView mapView;
    private List<OpenLayerEntity> openLayers;

    public ArcGisLayer(Context context, MapView mapView) {
        this.context=context;
        this.mapView=mapView;
        openLayers=new ArrayList<>();
    }

    public void openLayer(String url, String layerKey, Variable.LayerType type){
        OpenLayerEntity ole=new OpenLayerEntity();
        ole.setUrl(url);
        ole.setLayerKey(layerKey);
        ole.setType(type);
        openLayer(ole);
    }
    public void openLayer(OpenLayerEntity ole){
        boolean hasLayer=false;
        for(OpenLayerEntity entity:openLayers){
            if(entity.getLayerKey().equals(ole.getLayerKey())){
                hasLayer=true;
                break;
            }
        }
        if(hasLayer) return;
        Layer layer=null;
        switch (ole.getType()){
            case TILE:
                layer=new ArcGISTiledLayer(ole.getUrl());
                break;
            case WMTS:
                WmtsService wmtsService = new WmtsService(ole.getUrl());
                WmtsServiceInfo wmtsServiceInfo = wmtsService.getServiceInfo();
                layer = new WmtsLayer(wmtsServiceInfo.getLayerInfos().get(0));
                break;
            case IMG:
                layer=new ArcGISMapImageLayer(ole.getUrl());
                break;
            case BUNDLE:
                TileCache vTileCache = new TileCache(ole.getUrl());
                layer = new ArcGISTiledLayer(vTileCache);
                break;
        }
        layer.setId(ole.getLayerKey());
        mapView.getMap().getOperationalLayers().add(layer);
        ole.setLayer(layer);
        openLayers.add(ole);
    }
    public void removeLayer(String layerKey){
        OpenLayerEntity ole=new OpenLayerEntity();
        ole.setLayerKey(layerKey);
        removeLayer(ole);
    }
    public void removeLayer(OpenLayerEntity entity){
        for (OpenLayerEntity ol:openLayers){
            if(ol.getLayerKey().equals(entity.getLayerKey())){
                mapView.getMap().getOperationalLayers().remove(ol.getLayer());
                openLayers.remove(ol);
                break;
            }
        }
    }

    public List<OpenLayerEntity> getOpenLayers() {
        return openLayers;
    }
}
