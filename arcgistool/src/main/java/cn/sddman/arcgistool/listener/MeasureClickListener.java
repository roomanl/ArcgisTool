package cn.sddman.arcgistool.listener;

import cn.sddman.arcgistool.entity.DrawEntity;

public interface MeasureClickListener {
    void prevClick(boolean hasPrev);
    void nextClick(boolean hasNext);
    void lengthClick();
    void areaClick();
    void clearClick(DrawEntity draw);
    void endClick(DrawEntity draw);
}
