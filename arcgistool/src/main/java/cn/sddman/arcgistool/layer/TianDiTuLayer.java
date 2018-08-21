package cn.sddman.arcgistool.layer;

import com.esri.arcgisruntime.arcgisservices.LevelOfDetail;
import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.WebTiledLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by polyl on 2017/7/1.
 */

public class TianDiTuLayer {
    private static final List<String> SubDomain = Arrays.asList(new String[]{"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"});
    private static final String URL_VECTOR_2000 = "http://{subDomain}.tianditu.com/DataServer?T=vec_c&x={col}&y={row}&l={level}";
    private static final String URL_VECTOR_ANNOTATION_CHINESE_2000 = "http://{subDomain}.tianditu.com/DataServer?T=cva_c&x={col}&y={row}&l={level}";
    private static final String URL_VECTOR_ANNOTATION_ENGLISH_2000 = "http://{subDomain}.tianditu.com/DataServer?T=eva_c&x={col}&y={row}&l={level}";
    private static final String URL_IMAGE_2000 = "http://{subDomain}.tianditu.com/DataServer?T=img_c&x={col}&y={row}&l={level}";
    private static final String URL_IMAGE_ANNOTATION_CHINESE_2000 = "http://{subDomain}.tianditu.com/DataServer?T=cia_c&x={col}&y={row}&l={level}";
    private static final String URL_IMAGE_ANNOTATION_ENGLISH_2000 = "http://{subDomain}.tianditu.com/DataServer?T=eia_c&x={col}&y={row}&l={level}";
    private static final String URL_TERRAIN_2000 = "http://{subDomain}.tianditu.com/DataServer?T=ter_c&x={col}&y={row}&l={level}";
    private static final String URL_TERRAIN_ANNOTATION_CHINESE_2000 = "http://{subDomain}.tianditu.com/DataServer?T=cta_c&x={col}&y={row}&l={level}";

    private static final String URL_VECTOR_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=vec_w&x={col}&y={row}&l={level}";
    private static final String URL_VECTOR_ANNOTATION_CHINESE_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=cva_w&x={col}&y={row}&l={level}";
    private static final String URL_VECTOR_ANNOTATION_ENGLISH_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=eva_w&x={col}&y={row}&l={level}";
    private static final String URL_IMAGE_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=img_w&x={col}&y={row}&l={level}";
    private static final String URL_IMAGE_ANNOTATION_CHINESE_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=cia_w&x={col}&y={row}&l={level}";
    private static final String URL_IMAGE_ANNOTATION_ENGLISH_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=eia_w&x={col}&y={row}&l={level}";
    private static final String URL_TERRAIN_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=ter_w&x={col}&y={row}&l={level}";
    private static final String URL_TERRAIN_ANNOTATION_CHINESE_MERCATOR = "http://{subDomain}.tianditu.com/DataServer?T=cta_w&x={col}&y={row}&l={level}";

    private static final int DPI = 96;
    private static final int minZoomLevel = 1;
    private static final int maxZoomLevel = 18;
    private static final int tileWidth = 256;
    private static final int tileHeight = 256;
    private static final String LAYER_NAME_VECTOR = "vec";
    private static final String LAYER_NAME_VECTOR_ANNOTATION_CHINESE = "cva";
    private static final String LAYER_NAME_VECTOR_ANNOTATION_ENGLISH = "eva";
    private static final String LAYER_NAME_IMAGE = "img";
    private static final String LAYER_NAME_IMAGE_ANNOTATION_CHINESE = "cia";
    private static final String LAYER_NAME_IMAGE_ANNOTATION_ENGLISH = "eia";
    private static final String LAYER_NAME_TERRAIN = "ter";
    private static final String LAYER_NAME_TERRAIN_ANNOTATION_CHINESE = "cta";

    private static final SpatialReference SRID_2000 = SpatialReference.create(4490);
    private static final SpatialReference SRID_MERCATOR = SpatialReference.create(102100);
    private static final double X_MIN_2000 = -180;
    private static final double Y_MIN_2000 = -90;
    private static final double X_MAX_2000 = 180;
    private static final double Y_MAX_2000 = 90;

    private static final double X_MIN_MERCATOR = -20037508.3427892;
    private static final double Y_MIN_MERCATOR = -20037508.3427892;
    private static final double X_MAX_MERCATOR = 20037508.3427892;
    private static final double Y_MAX_MERCATOR = 20037508.3427892;
    private static final Point ORIGIN_2000 = new Point(-180, 90, SRID_2000);
    private static final Point ORIGIN_MERCATOR = new Point(-20037508.3427892, 20037508.3427892, SRID_MERCATOR);
    private static final Envelope ENVELOPE_2000 = new Envelope(X_MIN_2000, Y_MIN_2000, X_MAX_2000, Y_MAX_2000, SRID_2000);
    private static final Envelope ENVELOPE_MERCATOR = new Envelope(X_MIN_MERCATOR, Y_MIN_MERCATOR, X_MAX_MERCATOR, Y_MAX_MERCATOR, SRID_MERCATOR);

    private static final double[] SCALES = {
            2.958293554545656E8, 1.479146777272828E8,
            7.39573388636414E7, 3.69786694318207E7,
            1.848933471591035E7, 9244667.357955175,
            4622333.678977588, 2311166.839488794,
            1155583.419744397, 577791.7098721985,
            288895.85493609926, 144447.92746804963,
            72223.96373402482, 36111.98186701241,
            18055.990933506204, 9027.995466753102,
            4513.997733376551, 2256.998866688275,
            1128.4994333441375
    };
    private static final double[] RESOLUTIONS_MERCATOR = {
            78271.51696402048, 39135.75848201024,
            19567.87924100512, 9783.93962050256,
            4891.96981025128, 2445.98490512564,
            1222.99245256282, 611.49622628141,
            305.748113140705, 152.8740565703525,
            76.43702828517625, 38.21851414258813,
            19.109257071294063, 9.554628535647032,
            4.777314267823516, 2.388657133911758,
            1.194328566955879, 0.5971642834779395,
            0.298582141738970};

    private static final double[] RESOLUTIONS_2000 = {
            0.7031249999891485, 0.35156249999999994,
            0.17578124999999997, 0.08789062500000014,
            0.04394531250000007, 0.021972656250000007,
            0.01098632812500002, 0.00549316406250001,
            0.0027465820312500017, 0.0013732910156250009,
            0.000686645507812499, 0.0003433227539062495,
            0.00017166137695312503, 0.00008583068847656251,
            0.000042915344238281406, 0.000021457672119140645,
            0.000010728836059570307, 0.000005364418029785169};

    public static WebTiledLayer CreateTianDiTuTiledLayer(String layerType) {
        return CreateTianDiTuTiledLayer(getTianDiTuLayerType(layerType));
    }

    public static WebTiledLayer CreateTianDiTuTiledLayer(LayerType layerType) {
        WebTiledLayer webTiledLayer = null;
        String mainUrl = "";
        String mainName = "";
        TileInfo mainTileInfo = null;
        Envelope mainEnvelope = null;
        boolean mainIs2000 = false;
        try {
            switch (layerType) {
                case TIANDITU_VECTOR_2000:
                    mainUrl = URL_VECTOR_2000;
                    mainName = LAYER_NAME_VECTOR;
                    mainIs2000 = true;
                    break;
                case TIANDITU_VECTOR_MERCATOR:
                    mainUrl = URL_VECTOR_MERCATOR;
                    mainName = LAYER_NAME_VECTOR;
                    break;
                case TIANDITU_IMAGE_2000:
                    mainUrl = URL_IMAGE_2000;
                    mainName = LAYER_NAME_IMAGE;
                    mainIs2000 = true;
                    break;
                case TIANDITU_IMAGE_ANNOTATION_CHINESE_2000:
                    mainUrl = URL_IMAGE_ANNOTATION_CHINESE_2000;
                    mainName = LAYER_NAME_IMAGE_ANNOTATION_CHINESE;
                    mainIs2000 = true;
                    break;
                case TIANDITU_IMAGE_ANNOTATION_ENGLISH_2000:
                    mainUrl = URL_IMAGE_ANNOTATION_ENGLISH_2000;
                    mainName = LAYER_NAME_IMAGE_ANNOTATION_ENGLISH;
                    mainIs2000 = true;
                    break;
                case TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR:
                    mainUrl = URL_IMAGE_ANNOTATION_CHINESE_MERCATOR;
                    mainName = LAYER_NAME_IMAGE_ANNOTATION_CHINESE;
                    break;
                case TIANDITU_IMAGE_ANNOTATION_ENGLISH_MERCATOR:
                    mainUrl = URL_IMAGE_ANNOTATION_ENGLISH_MERCATOR;
                    mainName = LAYER_NAME_IMAGE_ANNOTATION_ENGLISH;
                    break;
                case TIANDITU_IMAGE_MERCATOR:
                    mainUrl = URL_IMAGE_MERCATOR;
                    mainName = LAYER_NAME_IMAGE;
                    break;
                case TIANDITU_VECTOR_ANNOTATION_CHINESE_2000:
                    mainUrl = URL_VECTOR_ANNOTATION_CHINESE_2000;
                    mainName = LAYER_NAME_VECTOR_ANNOTATION_CHINESE;
                    mainIs2000 = true;
                    break;
                case TIANDITU_VECTOR_ANNOTATION_ENGLISH_2000:
                    mainUrl = URL_VECTOR_ANNOTATION_ENGLISH_2000;
                    mainName = LAYER_NAME_VECTOR_ANNOTATION_ENGLISH;
                    mainIs2000 = true;
                    break;
                case TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR:
                    mainUrl = URL_VECTOR_ANNOTATION_CHINESE_MERCATOR;
                    mainName = LAYER_NAME_VECTOR_ANNOTATION_CHINESE;
                    break;
                case TIANDITU_VECTOR_ANNOTATION_ENGLISH_MERCATOR:
                    mainUrl = URL_VECTOR_ANNOTATION_ENGLISH_MERCATOR;
                    mainName = LAYER_NAME_VECTOR_ANNOTATION_ENGLISH;
                    break;
                case TIANDITU_TERRAIN_2000:
                    mainUrl = URL_TERRAIN_2000;
                    mainName = LAYER_NAME_TERRAIN;
                    mainIs2000 = true;
                    break;
                case TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000:
                    mainUrl = URL_TERRAIN_ANNOTATION_CHINESE_2000;
                    mainName = LAYER_NAME_TERRAIN_ANNOTATION_CHINESE;
                    mainIs2000 = true;
                    break;
                case TIANDITU_TERRAIN_MERCATOR:
                    mainUrl = URL_TERRAIN_MERCATOR;
                    mainName = LAYER_NAME_TERRAIN;
                    break;
                case TIANDITU_TERRAIN_ANNOTATION_CHINESE_MERCATOR:
                    mainUrl = URL_TERRAIN_ANNOTATION_CHINESE_MERCATOR;
                    mainName = LAYER_NAME_TERRAIN_ANNOTATION_CHINESE;
                    break;
            }
            List<LevelOfDetail> mainLevelOfDetail = new ArrayList<LevelOfDetail>();
            Point mainOrigin = null;
            if (mainIs2000 == true) {
                for (int i = minZoomLevel; i <= maxZoomLevel; i++) {
                    LevelOfDetail item = new LevelOfDetail(i, RESOLUTIONS_2000[i - 1], SCALES[i - 1]);
                    mainLevelOfDetail.add(item);
                }
                mainEnvelope = ENVELOPE_2000;
                mainOrigin = ORIGIN_2000;
            } else {
                for (int i = minZoomLevel; i <= maxZoomLevel; i++) {
                    LevelOfDetail item = new LevelOfDetail(i, RESOLUTIONS_MERCATOR[i - 1], SCALES[i - 1]);
                    mainLevelOfDetail.add(item);
                }
                mainEnvelope = ENVELOPE_MERCATOR;
                mainOrigin = ORIGIN_MERCATOR;
            }
            mainTileInfo = new TileInfo(
                    DPI,
                    TileInfo.ImageFormat.PNG24,
                    mainLevelOfDetail,
                    mainOrigin,
                    mainOrigin.getSpatialReference(),
                    tileHeight,
                    tileWidth
            );
            webTiledLayer = new WebTiledLayer(
                    mainUrl,
                    SubDomain,
                    mainTileInfo,
                    mainEnvelope);
            webTiledLayer.setName(mainName);
            webTiledLayer.loadAsync();
        } catch (Exception e) {
            e.getCause();
        }
        return webTiledLayer;
    }

    protected static LayerType getTianDiTuLayerType(String layerType) {
        LayerType result = LayerType.TIANDITU_VECTOR_2000;
        switch (layerType) {
            /**
             * 天地图矢量墨卡托投影地图服务
             */
            case "TIANDITU_VECTOR_MERCATOR":
                result = LayerType.TIANDITU_VECTOR_MERCATOR;
                break;
            /**
             * 天地图矢量墨卡托中文标注
             */
            case "TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR":
                result = LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR;
                break;
            /**
             * 天地图矢量墨卡托英文标注
             */
            case "TIANDITU_VECTOR_ANNOTATION_ENGLISH_MERCATOR":
                result = LayerType.TIANDITU_VECTOR_ANNOTATION_ENGLISH_MERCATOR;
                break;
            /**
             * 天地图影像墨卡托投影地图服务
             */
            case "TIANDITU_IMAGE_MERCATOR":
                result = LayerType.TIANDITU_IMAGE_MERCATOR;
                break;
            /**
             * 天地图影像墨卡托投影中文标注
             */
            case "TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR":
                result = LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR;
                break;
            /**
             * 天地图影像墨卡托投影英文标注
             */
            case "TIANDITU_IMAGE_ANNOTATION_ENGLISH_MERCATOR":
                result = LayerType.TIANDITU_IMAGE_ANNOTATION_ENGLISH_MERCATOR;
                break;
            /**
             * 天地图地形墨卡托投影地图服务
             */
            case "TIANDITU_TERRAIN_MERCATOR":
                result = LayerType.TIANDITU_TERRAIN_MERCATOR;
                break;
            /**
             * 天地图地形墨卡托投影中文标注
             */
            case "TIANDITU_TERRAIN_ANNOTATION_CHINESE_MERCATOR":
                result = LayerType.TIANDITU_TERRAIN_ANNOTATION_CHINESE_MERCATOR;
                break;
            /**
             * 天地图矢量国家2000坐标系地图服务
             */
            case "TIANDITU_VECTOR_2000":
                result = LayerType.TIANDITU_VECTOR_2000;
                break;
            /**
             * 天地图矢量国家2000坐标系中文标注
             */
            case "TIANDITU_VECTOR_ANNOTATION_CHINESE_2000":
                result = LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000;
                break;
            /**
             * 天地图矢量国家2000坐标系英文标注
             */
            case "TIANDITU_VECTOR_ANNOTATION_ENGLISH_2000":
                result = LayerType.TIANDITU_VECTOR_ANNOTATION_ENGLISH_2000;
                break;
            /**
             * 天地图影像国家2000坐标系地图服务
             */
            case "TIANDITU_IMAGE_2000":
                result = LayerType.TIANDITU_IMAGE_2000;
                break;
            /**
             * 天地图影像国家2000坐标系中文标注
             */
            case "TIANDITU_IMAGE_ANNOTATION_CHINESE_2000":
                result = LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000;
                break;
            /**
             * 天地图影像国家2000坐标系英文标注
             */
            case "TIANDITU_IMAGE_ANNOTATION_ENGLISH_2000":
                result = LayerType.TIANDITU_IMAGE_ANNOTATION_ENGLISH_2000;
                break;
            /**
             * 天地图地形国家2000坐标系地图服务
             */
            case "TIANDITU_TERRAIN_2000":
                result = LayerType.TIANDITU_TERRAIN_2000;
                break;
            /**
             * 天地图地形国家2000坐标系中文标注
             */
            case "TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000":
                result = LayerType.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000;
                break;
        }
        return result;
    }

    public enum LayerType {
        /**
         * 天地图矢量墨卡托投影地图服务
         */
        TIANDITU_VECTOR_MERCATOR,
        /**
         * 天地图矢量墨卡托中文标注
         */
        TIANDITU_VECTOR_ANNOTATION_CHINESE_MERCATOR,
        /**
         * 天地图矢量墨卡托英文标注
         */
        TIANDITU_VECTOR_ANNOTATION_ENGLISH_MERCATOR,
        /**
         * 天地图影像墨卡托投影地图服务
         */
        TIANDITU_IMAGE_MERCATOR,
        /**
         * 天地图影像墨卡托投影中文标注
         */
        TIANDITU_IMAGE_ANNOTATION_CHINESE_MERCATOR,
        /**
         * 天地图影像墨卡托投影英文标注
         */
        TIANDITU_IMAGE_ANNOTATION_ENGLISH_MERCATOR,
        /**
         * 天地图地形墨卡托投影地图服务
         */
        TIANDITU_TERRAIN_MERCATOR,
        /**
         * 天地图地形墨卡托投影中文标注
         */
        TIANDITU_TERRAIN_ANNOTATION_CHINESE_MERCATOR,
        /**
         * 天地图矢量国家2000坐标系地图服务
         */
        TIANDITU_VECTOR_2000,
        /**
         * 天地图矢量国家2000坐标系中文标注
         */
        TIANDITU_VECTOR_ANNOTATION_CHINESE_2000,
        /**
         * 天地图矢量国家2000坐标系英文标注
         */
        TIANDITU_VECTOR_ANNOTATION_ENGLISH_2000,
        /**
         * 天地图影像国家2000坐标系地图服务
         */
        TIANDITU_IMAGE_2000,
        /**
         * 天地图影像国家2000坐标系中文标注
         */
        TIANDITU_IMAGE_ANNOTATION_CHINESE_2000,
        /**
         * 天地图影像国家2000坐标系英文标注
         */
        TIANDITU_IMAGE_ANNOTATION_ENGLISH_2000,
        /**
         * 天地图地形国家2000坐标系地图服务
         */
        TIANDITU_TERRAIN_2000,
        /**
         * 天地图地形国家2000坐标系中文标注
         */
        TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000

    }
}
