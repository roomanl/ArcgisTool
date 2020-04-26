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
import cn.sddman.arcgistool.listener.DrawGraphListener;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.util.ArcGisDrawGraph;
import cn.sddman.arcgistool.util.ArcGisMeasure;
import cn.sddman.arcgistool.util.Util;

public class DrawGraphView extends LinearLayout {
    private Context context;
    private ArcGisMeasure arcgisMeasure;
    private MapView mMapView;
    private LinearLayout drawingBgView,drawingLineLayout,drawingPolygonLayout,drawingOrthogonLayout,drawingCircleLayout,drawingEllipseLayout,drawingRhombusLayout,clearDrawLayout;
    private ImageView drawingLineImageView,drawingPolygonImageView,drawingOrthogonImageView,drawingCircleImageView,drawingEllipseImageView,drawingRhombusImageView;
    private TextView drawingLineText,drawingPolygonText,drawingOrthogonText,drawingCircleText,drawingEllipseText,drawingRhombusText,clearDrawText;
    private int bgColor,fontColor,drawingLineImage,drawingPolygonImage,drawingOrthogonImage,drawingCircleImage,drawingEllipseImage,drawingRhombusImage;
    private int buttonWidth,buttonHeight,fontSize;
    private boolean isHorizontal,showText=false;
    private Variable.GraphType graphType=null;
    private String drawingLineStr,drawingPolygonStr,drawingOrthogonStr,drawingCircleStr,drawingEllipseStr,drawingRhombusStr,clearDrawStr;
    private MeasureClickListener measureClickListener;
    private ArcGisDrawGraph arcGisDrawGraph=null;
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
        clearDrawText=(TextView)findViewById(R.id.clear_draw_text);

        drawingBgView=(LinearLayout)findViewById(R.id.drawing_bg);
        drawingLineLayout=(LinearLayout)findViewById(R.id.drawing_line_layout);
        drawingPolygonLayout=(LinearLayout)findViewById(R.id.drawing_polygon_layout);
        drawingOrthogonLayout=(LinearLayout)findViewById(R.id.drawing_orthogon_layout);
        drawingCircleLayout=(LinearLayout)findViewById(R.id.drawing_circle_layout);
        drawingEllipseLayout=(LinearLayout)findViewById(R.id.drawing_ellipse_layout);
        drawingRhombusLayout=(LinearLayout)findViewById(R.id.drawing_rhombus_layout);
        clearDrawLayout=(LinearLayout)findViewById(R.id.drawing_clear_layout);

        //drawingLineLayout.setVisibility(GONE);
        //drawingPolygonLayout.setVisibility(GONE);

        drawingLineLayout.setOnClickListener(listener);
        drawingPolygonLayout.setOnClickListener(listener);
        drawingOrthogonLayout.setOnClickListener(listener);
        drawingCircleLayout.setOnClickListener(listener);
        drawingEllipseLayout.setOnClickListener(listener);
        drawingRhombusLayout.setOnClickListener(listener);
        clearDrawLayout.setOnClickListener(listener);
    }

    private void initAttr(TypedArray ta){
        bgColor=ta.getResourceId(R.styleable.ViewAttr_view_background,R.drawable.sddman_shadow_bg);
        buttonWidth=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_width, Util.valueToSp(getContext(),35));
        buttonHeight=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_height, Util.valueToSp(getContext(),35));
        showText=ta.getBoolean(R.styleable.ViewAttr_show_text,false);
        drawingLineStr=ta.getString(R.styleable.ViewAttr_drawing_line_text);
        drawingPolygonStr=ta.getString(R.styleable.ViewAttr_drawing_polygon_text);
        drawingOrthogonStr=ta.getString(R.styleable.ViewAttr_drawing_orthogon_text);
        drawingCircleStr=ta.getString(R.styleable.ViewAttr_drawing_circle_text);
        drawingEllipseStr=ta.getString(R.styleable.ViewAttr_drawing_ellipse_text);
        drawingRhombusStr=ta.getString(R.styleable.ViewAttr_drawing_rhombus_text);
        fontColor=ta.getResourceId(R.styleable.ViewAttr_font_color,R.color.gray);
        fontSize=ta.getInt(R.styleable.ViewAttr_font_size,12);
        drawingLineImage=ta.getResourceId(R.styleable.ViewAttr_drawing_line_image,R.drawable.sddman_drawing_line);
        drawingPolygonImage=ta.getResourceId(R.styleable.ViewAttr_drawing_polygon_image,R.drawable.sddman_drawing_polygon);
        drawingOrthogonImage=ta.getResourceId(R.styleable.ViewAttr_drawing_orthogon_image,R.drawable.sddman_drawing_orthogon);
        drawingCircleImage=ta.getResourceId(R.styleable.ViewAttr_drawing_circle_image,R.drawable.sddman_drawing_circle);
        drawingEllipseImage=ta.getResourceId(R.styleable.ViewAttr_drawing_ellipse_image,R.drawable.sddman_drawing_ellipse);
        drawingRhombusImage=ta.getResourceId(R.styleable.ViewAttr_drawing_rhombus_image,R.drawable.sddman_drawing_rhombus);

        setBackground(bgColor);
        setDpButtonWidth(buttonWidth);
        setDpButtonHeight(buttonHeight);
        setSohwText(showText);
        setdrawingLineStr(drawingLineStr);
        setdrawingPolygonStr(drawingPolygonStr);
        setdrawingOrthogonStr(drawingOrthogonStr);
        setdrawingCircleStr(drawingCircleStr);
        setdrawingEllipseStr(drawingEllipseStr);
        setdrawingRhombusStr(drawingRhombusStr);
        setFontColor(fontColor);
        setFontSize(fontSize);
        setdrawingLineImage(drawingLineImage);
        setdrawingPolygonImage(drawingPolygonImage);
        setdrawingOrthogonImage(drawingOrthogonImage);
        setdrawingCircleImage(drawingCircleImage);
        setdrawingEllipseImage(drawingEllipseImage);
        setdrawingRhombusImage(drawingRhombusImage);
    }
    private void initDrawGraph(){
        if(arcGisDrawGraph==null){
            arcGisDrawGraph=new ArcGisDrawGraph(context,mMapView);
            arcGisDrawGraph.setDrawGraphListener(new DrawGraphListener() {
                @Override
                public void drawEnd(Variable.GraphType graphType) {
                    clearBgColor();
                }
            });
        }
    }
    private OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.drawing_line_layout){
                clearBgColor();
                drawingLineLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
            }else if (i == R.id.drawing_polygon_layout){
                clearBgColor();
                drawingPolygonLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
            }else if (i == R.id.drawing_orthogon_layout){
                clearBgColor();
                drawingOrthogonLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
                graphType= Variable.GraphType.BOX;
            }else if (i == R.id.drawing_circle_layout){
                clearBgColor();
                drawingCircleLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
                graphType= Variable.GraphType.CIRCLE;
            }else if (i == R.id.drawing_ellipse_layout){
                clearBgColor();
                drawingEllipseLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
            }else if (i == R.id.drawing_rhombus_layout){
                clearBgColor();
                drawingRhombusLayout.setBackgroundColor(getResources().getColor(R.color.black_1a));
            }else if (i == R.id.drawing_clear_layout){
                clearBgColor();
                initDrawGraph();
                arcGisDrawGraph.clear();
            }
        }
    };
    private void clearBgColor(){
        graphType=null;
        drawingLineLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        drawingPolygonLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        drawingOrthogonLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        drawingCircleLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        drawingEllipseLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        drawingRhombusLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
    }
    public void onMapSingleTapUp(MotionEvent e){
        initDrawGraph();
        if(graphType== Variable.GraphType.CIRCLE){
            arcGisDrawGraph.drawCircle(e.getX(), e.getY());
        }else if(graphType== Variable.GraphType.BOX){
            arcGisDrawGraph.drawBox(e.getX(), e.getY());
        }
    }
    public void onScroll(MotionEvent e1,MotionEvent e2,float distanceX, float distanceY) {


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
    public void setBackground(int bg) {
        this.bgColor=bg;
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
        clearDrawText.setVisibility(view);
    }
    @Deprecated
    public void setdrawingLineStr(String drawingLineStr) {
        if(drawingLineStr==null) return;
        this.drawingLineStr = drawingLineStr;
        drawingLineText.setText(drawingLineStr);
    }
    @Deprecated
    public void setdrawingPolygonStr(String drawingPolygonStr) {
        if(drawingPolygonStr==null) return;
        this.drawingPolygonStr = drawingPolygonStr;
        drawingPolygonText.setText(drawingPolygonStr);
    }
    @Deprecated
    public void setdrawingOrthogonStr(String drawingOrthogonStr) {
        if(drawingOrthogonStr==null) return;
        this.drawingOrthogonStr = drawingOrthogonStr;
        drawingOrthogonText.setText(drawingOrthogonStr);
    }
    @Deprecated
    public void setdrawingCircleStr(String drawingCircleStr) {
        if(drawingCircleStr==null) return;
        this.drawingCircleStr = drawingCircleStr;
        drawingCircleText.setText(drawingCircleStr);
    }
    @Deprecated
    public void setdrawingEllipseStr(String drawingEllipseStr) {
        if(drawingEllipseStr==null) return;
        this.drawingEllipseStr = drawingEllipseStr;
        drawingEllipseText.setText(drawingEllipseStr);
    }
    @Deprecated
    public void setdrawingRhombusStr(String drawingRhombusStr) {
        if(drawingRhombusStr==null) return;
        this.drawingRhombusStr = drawingRhombusStr;
        drawingRhombusText.setText(drawingRhombusStr);
    }
    @Deprecated
    public void setClearDrawStr(String clearDrawStr) {
        if(clearDrawStr==null) return;
        this.clearDrawStr = clearDrawStr;
        clearDrawText.setText(clearDrawStr);
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
        clearDrawText.setTextColor(color);
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
        clearDrawText.setTextSize(fontSize);
    }
    @Deprecated
    public void setdrawingLineImage(int drawingLineImage) {
        this.drawingLineImage = drawingLineImage;
        drawingLineImageView.setImageDrawable(getResources().getDrawable(drawingLineImage));
    }
    @Deprecated
    public void setdrawingPolygonImage(int drawingPolygonImage) {
        this.drawingPolygonImage = drawingPolygonImage;
        drawingPolygonImageView.setImageDrawable(getResources().getDrawable(drawingPolygonImage));
    }
    @Deprecated
    public void setdrawingOrthogonImage(int drawingOrthogonImage) {
        this.drawingOrthogonImage = drawingOrthogonImage;
        drawingOrthogonImageView.setImageDrawable(getResources().getDrawable(drawingOrthogonImage));
    }
    @Deprecated
    public void setdrawingCircleImage(int drawingCircleImage) {
        this.drawingCircleImage = drawingCircleImage;
        drawingCircleImageView.setImageDrawable(getResources().getDrawable(drawingCircleImage));
    }
    @Deprecated
    public void setdrawingEllipseImage(int drawingEllipseImage) {
        this.drawingEllipseImage = drawingEllipseImage;
        drawingEllipseImageView.setImageDrawable(getResources().getDrawable(drawingEllipseImage));
    }
    @Deprecated
    public void setdrawingRhombusImage(int drawingRhombusImage) {
        this.drawingRhombusImage = drawingRhombusImage;
        drawingRhombusImageView.setImageDrawable(getResources().getDrawable(drawingRhombusImage));
    }

}
