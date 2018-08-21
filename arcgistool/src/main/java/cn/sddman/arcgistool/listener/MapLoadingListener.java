package cn.sddman.arcgistool.listener;

import com.esri.arcgisruntime.loadable.LoadStatus;

public interface MapLoadingListener {
    void loadingSuccess();
    void loadingFailed(LoadStatus status);
}
