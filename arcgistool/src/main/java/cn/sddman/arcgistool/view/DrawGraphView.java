package cn.sddman.arcgistool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.R;
import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.DrawEntity;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.util.ArcGisMeasure;
import cn.sddman.arcgistool.util.Util;

public class DrawGraphView extends LinearLayout {
    private Context context;
    private ArcGisMeasure arcgisMeasure;
    private MapView mMapView;
    private LinearLayout drawingBgView,drawingLineLayout,drawingPolygonLayout,drawingOrthogonLayout,drawingCircleLayout,drawingEllipseLayout,drawingRhombusLayout;
    private ImageView drawingLineImageView,drawingPolygonImageView,drawingOrthogonImageView,drawingCircleImageView,drawingEllipseImageView,drawingRhombusImageView;
    private TextView drawingLineText,drawingPolygonText,drawingOrthogonText,drawingCircleText,drawingEllipseText,drawingRhombusText;
    private int bgColor,fontColor,measurePrevImage,measureNextImage,measureLengthImage,measureAreaImage,measureClearImage,measureEndImage;
    private int buttonWidth,buttonHeight,fontSize;
    private boolean isHorizontal,showText=false;
    private String measurePrevStr,measureNextStr,measureLengthStr,measureAreaStr,measureClearStr,measureEndStr;
    private Variable.DrawType drawType=null;
    private Variable.Measure measureLengthType=Variable.Measure.M;
    private Variable.Measure measureAreaType=Variable.Measure.M2;
    private MeasureClickListener measureClickListener;
    public DrawGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewAttr);
        isHorizontal=ta.getBoolean(R.styleable.ViewAttr_is_horizontal,true);
        //if(!isHorizontal) {
           // LayoutInflater.from(context).inflate(R.layout.measure_tool_vertical_view, this);
       // }else{
            LayoutInflater.from(context).inflate(R.layout.drawing_tool_horizontal_view, this);
       // }

        initView();
        initAttr(ta);
    }
    public void init(MapView mMapView){
        this.mMapView=mMapView;
        arcgisMeasure=new ArcGisMeasure(context,mMapView);
    }

    private void initView(){
        drawingLineImageView=(ImageView)findViewById(R.id.drawing_line);
        drawingPolygonImageView=(ImageView)findViewById(R.id.drawing_polygon);
        drawingOrthogonImageView=(ImageView)findViewById(R.id.drawing_orthogon);
        drawingCircleImageView=(ImageView)findViewById(R.id.drawing_circle);
        drawingEllipseImageView=(ImageView)findViewById(R.id.drawing_ellipse);
        drawingRhombusImageView=(ImageView)findViewById(R.id.drawing_rhombus);
        drawingLineText=(TextView)findViewById(R.id.drawing_line_text);
        drawingPolygonText=(TextView)findViewById(R.id.drawing_polygon_text);
        drawingOrthogonText=(TextView)findViewById(R.id.drawing_orthogon_text);
        drawingCircleText=(TextView)findViewById(R.id.drawing_circle_text);
        drawingEllipseText=(TextView)findViewById(R.id.drawing_ellipse_text);
        drawingRhombusText=(TextView)findViewById(R.id.drawing_rhombus_text);

        drawingBgView=(LinearLayout)findViewById(R.id.measure_bg);
        drawingLineLayout=(LinearLayout)findViewById(R.id.measure_prev_layout);
        drawingPolygonLayout=(LinearLayout)findViewById(R.id.measure_next_layout);
        drawingOrthogonLayout=(LinearLayout)findViewById(R.id.measure_length_layout);
        drawingCircleLayout=(LinearLayout)findViewById(R.id.measure_area_layout);
        drawingEllipseLayout=(LinearLayout)findViewById(R.id.measure_clear_layout);
        drawingRhombusLayout=(LinearLayout)findViewById(R.id.measure_end_layout);

        //drawingLineLayout.setVisibility(GONE);
        //drawingPolygonLayout.setVisibility(GONE);

        drawingLineLayout.setOnClickListener(listener);
        drawingPolygonLayout.setOnClickListener(listener);
        drawingOrthogonLayout.setOnClickListener(listener);
        drawingCircleLayout.setOnClickListener(listener);
        drawingEllipseLayout.setOnClickListener(listener);
        drawingRhombusLayout.setOnClickListener(listener);
    }

    private void initAttr(TypedArray ta){
        bgColor=ta.getResourceId(R.styleable.ViewAttr_view_background,R.drawable.sddman_shadow_bg);
        buttonWidth=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_width, Util.valueToSp(getContext(),35));
        buttonHeight=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_height, Util.valueToSp(getContext(),35));
        showText=ta.getBoolean(R.styleable.ViewAttr_show_text,false);
        measurePrevStr=ta.getString(R.styleable.ViewAttr_measure_prev_text);
        measureNextStr=ta.getString(R.styleable.ViewAttr_measure_next_text);
        measureLengthStr=ta.getString(R.styleable.ViewAttr_measure_length_text);
        measureAreaStr=ta.getString(R.styleable.ViewAttr_measure_area_text);
        measureClearStr=ta.getString(R.styleable.ViewAttr_measure_clear_text);
        measureEndStr=ta.getString(R.styleable.ViewAttr_measure_end_text);
        fontColor=ta.getResourceId(R.styleable.ViewAttr_font_color,R.color.gray);
        fontSize=ta.getInt(R.styleable.ViewAttr_font_size,12);
        measurePrevImage=ta.getResourceId(R.styleable.ViewAttr_measure_prev_image,R.drawable.sddman_measure_prev);
        measureNextImage=ta.getResourceId(R.styleable.ViewAttr_measure_next_image,R.drawable.sddman_measure_next);
        measureLengthImage=ta.getResourceId(R.styleable.ViewAttr_measure_length_image,R.drawable.sddman_measure_length);
        measureAreaImage=ta.getResourceId(R.styleable.ViewAttr_measure_area_image,R.drawable.sddman_measure_area);
        measureClearImage=ta.getResourceId(R.styleable.ViewAttr_measure_clear_image,R.drawable.sddman_measure_clear);
        measureEndImage=ta.getResourceId(R.styleable.ViewAttr_measure_end_image,R.drawable.sddman_measure_end);

        setMeasureBackground(bgColor);
        setDpButtonWidth(buttonWidth);
        setDpButtonHeight(buttonHeight);
        setSohwText(showText);
        setMeasurePrevStr(measurePrevStr);
        setMeasureNextStr(measureNextStr);
        setMeasureLengthStr(measureLengthStr);
        setMeasureAreaStr(measureAreaStr);
        setMeasureClearStr(measureClearStr);
        setMeasureEndStr(measureEndStr);
        setFontColor(fontColor);
        setFontSize(fontSize);
        setMeasurePrevImage(measurePrevImage);
        setMeasureNextImage(measureNextImage);
        setMeasureLengthImage(measureLengthImage);
        setMeasureAreaImage(measureAreaImage);
        setMeasureClearImage(measureClearImage);
        setMeasureEndImage(measureEndImage);
    }

    private OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.measure_prev_layout){
                boolean hasPrev=arcgisMeasure.prevDraw();
                if(measureClickListener!=null){
                    measureClickListener.prevClick(hasPrev);
                }
            }else if (i == R.id.measure_next_layout){
                boolean hasNext=arcgisMeasure.nextDraw();
                if(measureClickListener!=null){
                    measureClickListener.nextClick(hasNext);
                }
            }else if (i == R.id.measure_length_layout){
                drawType=Variable.DrawType.LINE;
                arcgisMeasure.endMeasure();
                drawingOrthogonLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
                drawingCircleLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                if(measureClickListener!=null){
                    measureClickListener.lengthClick();
                }
            }else if (i == R.id.measure_area_layout){
                drawType=Variable.DrawType.POLYGON;
                arcgisMeasure.endMeasure();
                drawingOrthogonLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                drawingCircleLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
                if(measureClickListener!=null){
                    measureClickListener.areaClick();
                }
            }else if (i == R.id.measure_clear_layout){
                drawType=null;
                DrawEntity draw=arcgisMeasure.clearMeasure();
                drawingOrthogonLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                drawingCircleLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                if(measureClickListener!=null){
                    measureClickListener.clearClick(draw);
                }
            }else if (i == R.id.measure_end_layout){
                drawType=null;
                DrawEntity draw=arcgisMeasure.endMeasure();
                drawingOrthogonLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                drawingCircleLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                if(measureClickListener!=null){
                    measureClickListener.endClick(draw);
                }
            }
        }
    };
    public void onMapSingleTapUp(MotionEvent e){
        if(drawType==Variable.DrawType.LINE) {
            arcgisMeasure.startMeasuredLength(e.getX(), e.getY());
        }else if(drawType==Variable.DrawType.POLYGON){
            arcgisMeasure.startMeasuredArea(e.getX(), e.getY());
        }
    }

    public void setMeasureClickListener(MeasureClickListener measureClickListener) {
        this.measureClickListener = measureClickListener;
    }

    private void setDpButtonWidth(int buttonWidth) {
        this.buttonWidth = buttonWidth;
        drawingLineImageView.getLayoutParams().width=buttonWidth;
        drawingPolygonImageView.getLayoutParams().width=buttonWidth;
        drawingOrthogonImageView.getLayoutParams().width=buttonWidth;
        drawingCircleImageView.getLayoutParams().width=buttonWidth;
        drawingEllipseImageView.getLayoutParams().width=buttonWidth;
        drawingRhombusImageView.getLayoutParams().width=buttonWidth;
    }

    private void setDpButtonHeight(int buttonHeight) {
        this.buttonHeight = buttonHeight;
        drawingLineImageView.getLayoutParams().height=buttonHeight;
        drawingPolygonImageView.getLayoutParams().height=buttonHeight;
        drawingOrthogonImageView.getLayoutParams().height=buttonHeight;
        drawingCircleImageView.getLayoutParams().height=buttonHeight;
        drawingEllipseImageView.getLayoutParams().height=buttonHeight;
        drawingRhombusImageView.getLayoutParams().height=buttonHeight;
    }
    @Deprecated
    public void setSpatialReference(SpatialReference spatialReference) {
        arcgisMeasure.setSpatialReference(spatialReference);
    }
    @Deprecated
    public void setLengthType(Variable.Measure type){
        this.measureLengthType=type;
        arcgisMeasure.setLengthType(type);
    }
    @Deprecated
    public void setAreaType(Variable.Measure type){
        this.measureAreaType=type;
        arcgisMeasure.setAreaType(type);
    }
    @Deprecated
    public void setMeasureBackground(int bg) {
        this.bgColor=bgColor;
        drawingBgView.setBackground(getResources().getDrawable(bg));
    }
    @Deprecated
    public void setButtonWidth(int buttonWidth) {
        setDpButtonWidth(Util.valueToSp(getContext(),buttonWidth));
    }
    @Deprecated
    public void setButtonHeight(int buttonHeight) {
        setDpButtonHeight(Util.valueToSp(getContext(),buttonHeight));
    }
    @Deprecated
    public void setSohwText(boolean showText){
        this.showText=showText;
        int view=showText?View.VISIBLE:View.GONE;
        drawingLineText.setVisibility(view);
        drawingPolygonText.setVisibility(view);
        drawingOrthogonText.setVisibility(view);
        drawingCircleText.setVisibility(view);
        drawingEllipseText.setVisibility(view);
        drawingRhombusText.setVisibility(view);
    }
    @Deprecated
    public void setMeasurePrevStr(String measurePrevStr) {
        if(measurePrevStr==null) return;
        this.measurePrevStr = measurePrevStr;
        drawingLineText.setText(measurePrevStr);
    }
    @Deprecated
    public void setMeasureNextStr(String measureNextStr) {
        if(measureNextStr==null) return;
        this.measureNextStr = measureNextStr;
        drawingPolygonText.setText(measureNextStr);
    }
    @Deprecated
    public void setMeasureLengthStr(String measureLengthStr) {
        if(measureLengthStr==null) return;
        this.measureLengthStr = measureLengthStr;
        drawingOrthogonText.setText(measureLengthStr);
    }
    @Deprecated
    public void setMeasureAreaStr(String measureAreaStr) {
        if(measureAreaStr==null) return;
        this.measureAreaStr = measureAreaStr;
        drawingCircleText.setText(measureAreaStr);
    }
    @Deprecated
    public void setMeasureClearStr(String measureClearStr) {
        if(measureClearStr==null) return;
        this.measureClearStr = measureClearStr;
        drawingEllipseText.setText(measureClearStr);
    }
    @Deprecated
    public void setMeasureEndStr(String measureEndStr) {
        if(measureEndStr==null) return;
        this.measureEndStr = measureEndStr;
        drawingRhombusText.setText(measureEndStr);
    }
    @Deprecated
    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
        int color = getResources().getColor(fontColor);
        drawingLineText.setTextColor(color);
        drawingPolygonText.setTextColor(color);
        drawingOrthogonText.setTextColor(color);
        drawingCircleText.setTextColor(color);
        drawingEllipseText.setTextColor(color);
        drawingRhombusText.setTextColor(color);
    }
    @Deprecated
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        drawingLineText.setTextSize(fontSize);
        drawingPolygonText.setTextSize(fontSize);
        drawingOrthogonText.setTextSize(fontSize);
        drawingCircleText.setTextSize(fontSize);
        drawingEllipseText.setTextSize(fontSize);
        drawingRhombusText.setTextSize(fontSize);
    }
    @Deprecated
    public void setMeasurePrevImage(int measurePrevImage) {
        this.measurePrevImage = measurePrevImage;
        drawingLineImageView.setImageDrawable(getResources().getDrawable(measurePrevImage));
    }
    @Deprecated
    public void setMeasureNextImage(int measureNextImage) {
        this.measureNextImage = measureNextImage;
        drawingPolygonImageView.setImageDrawable(getResources().getDrawable(measureNextImage));
    }
    @Deprecated
    public void setMeasureLengthImage(int measureLengthImage) {
        this.measureLengthImage = measureLengthImage;
        drawingOrthogonImageView.setImageDrawable(getResources().getDrawable(measureLengthImage));
    }
    @Deprecated
    public void setMeasureAreaImage(int measureAreaImage) {
        this.measureAreaImage = measureAreaImage;
        drawingCircleImageView.setImageDrawable(getResources().getDrawable(measureAreaImage));
    }
    @Deprecated
    public void setMeasureClearImage(int measureClearImage) {
        this.measureClearImage = measureClearImage;
        drawingEllipseImageView.setImageDrawable(getResources().getDrawable(measureClearImage));
    }
    @Deprecated
    public void setMeasureEndImage(int measureEndImage) {
        this.measureEndImage = measureEndImage;
        drawingRhombusImageView.setImageDrawable(getResources().getDrawable(measureEndImage));
    }

}
