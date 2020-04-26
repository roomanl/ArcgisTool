package cn.sddman.arcgistool.common;

public class Variable {
    public static enum DrawType {
        TEXT,
        POINT,
        LINE,
        POLYGON;
    }
    public static enum GraphType {
        LINE,
        POLYGON,
        BOX,
        CIRCLE,
        ELLIPSE,
        RHOMBUS;
    }
    public static enum Measure {
        M,
        KM,
        KIM,
        M2,
        KM2,
        HM2,
        A2;
    }
    public static enum LayerType {
        TILE,
        WMTS,
        IMG,
        BUNDLE
    }
}
