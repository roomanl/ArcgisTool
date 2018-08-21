package cn.sddman.arcgistool.entity;

import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.Basemap;

import cn.sddman.arcgistool.common.Variable;

public class BaseMapEntity {
    private String layerKey;
    private String url;
    private Variable.LayerType type;
    private Layer layer;
    private boolean show=false;
    private Basemap basemap;

    public String getLayerKey() {
        return layerKey;
    }

    public void setLayerKey(String layerKey) {
        this.layerKey = layerKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Variable.LayerType getType() {
        return type;
    }

    public void setType(Variable.LayerType type) {
        this.type = type;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Basemap getBasemap() {
        return basemap;
    }

    public void setBasemap(Basemap basemap) {
        this.basemap = basemap;
    }
}
