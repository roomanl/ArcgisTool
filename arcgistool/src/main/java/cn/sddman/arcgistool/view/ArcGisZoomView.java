package cn.sddman.arcgistool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.arcgisruntime.mapping.view.MapView;

import cn.sddman.arcgistool.R;
import cn.sddman.arcgistool.listener.ZoomClickListener;
import cn.sddman.arcgistool.util.Util;

public class ArcGisZoomView extends LinearLayout{
    private LinearLayout linearLayoutView,zoomBgView,zoomInLayout,zoomoutLayout;
    private ImageView zoomInView,zoomOutView;
    private TextView spiltLineView,spiltLineView2;
    private TextView zoomInTextView,zoomOutTextView;
    private MapView mMapView;
    private ZoomClickListener zoomClickListener=null;
    private int bgColor,fontColor,zoomInImage,zoomOutImage;
    private int zoomWidth,zoomHeight,fontSize;
    private double zoomInNum,zoomOutNum;
    private String zoomInText,zoomOutText;
    private boolean isHorizontal=false,showText=false;
    public ArcGisZoomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcGisZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.zoom_view, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewAttr);
        initView();
        initAttr(ta);


    }
    public void init(MapView mMapView){
        this.mMapView=mMapView;
    }
    public void init(MapView mMapView,ZoomClickListener zoomClickListener){
        this.mMapView=mMapView;
        this.zoomClickListener=zoomClickListener;
    }

    private void initAttr(TypedArray ta){
        bgColor=ta.getResourceId(R.styleable.ViewAttr_view_background,R.drawable.sddman_shadow_bg);
        zoomWidth=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_width, Util.valueToSp(getContext(),35));
        zoomHeight=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_height,Util.valueToSp(getContext(),35));
        isHorizontal=ta.getBoolean(R.styleable.ViewAttr_is_horizontal,false);
        showText=ta.getBoolean(R.styleable.ViewAttr_show_text,false);
        zoomInNum=ta.getDimension(R.styleable.ViewAttr_zoom_in_num,2);
        zoomOutNum=ta.getDimension(R.styleable.ViewAttr_zoom_out_num,2);
        fontSize=ta.getInt(R.styleable.ViewAttr_font_size,12);
        zoomInImage=ta.getResourceId(R.styleable.ViewAttr_zoom_in_image,R.drawable.sddman_zoomin);
        zoomOutImage=ta.getResourceId(R.styleable.ViewAttr_zoom_out_image,R.drawable.sddman_zoomout);
        fontColor=ta.getResourceId(R.styleable.ViewAttr_font_color,R.color.gray);
        zoomInText=ta.getString(R.styleable.ViewAttr_zoom_in_text);
        zoomOutText=ta.getString(R.styleable.ViewAttr_zoom_out_text);


        setZoomBackground(bgColor);
        setZoomDpWidth(zoomWidth);
        setZoomDpHeight(zoomHeight);
        isHorizontal(isHorizontal);
        setZoomInImage(zoomInImage);
        setZoomOutImage(zoomOutImage);
        setShowText(showText);
        setFontSize(fontSize);
        setFontColor(fontColor);
        setZoomInText(zoomInText);
        setZoomOutText(zoomOutText);
        setZoomInNum(zoomInNum);
        setZoomOutNum(zoomOutNum);


    }
    private void initView(){
        linearLayoutView = (LinearLayout)findViewById(R.id.linearLayout);
        zoomInLayout= (LinearLayout)findViewById(R.id.zoom_in_layout);
        zoomoutLayout= (LinearLayout)findViewById(R.id.zoom_out_layout);
        zoomBgView = (LinearLayout)findViewById(R.id.zoom_bg);
        zoomInView = (ImageView)findViewById(R.id.zoom_in);
        zoomOutView = (ImageView)findViewById(R.id.zoom_out);
        spiltLineView=(TextView)findViewById(R.id.spilt_line);
        spiltLineView2=(TextView)findViewById(R.id.spilt_line2);
        zoomInTextView=(TextView)findViewById(R.id.zoom_in_text);
        zoomOutTextView=(TextView)findViewById(R.id.zoom_out_text);

        zoomInLayout.setOnClickListener(listener);
        zoomoutLayout.setOnClickListener(listener);

    }
    private OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.zoom_in_layout) {
                double scale = mMapView.getMapScale();
                mMapView.setViewpointScaleAsync(scale * (1.0/zoomInNum));
                if(zoomClickListener!=null) {
                    zoomClickListener.zoomInClick(view);
                }
            }else if (i == R.id.zoom_out_layout) {
                double scale = mMapView.getMapScale();
                mMapView.setViewpointScaleAsync(scale * zoomOutNum);
                if(zoomClickListener!=null){
                    zoomClickListener.zoomOutClick(view);
                }
            }
        }
    };
    private void setZoomDpWidth(int w){
        zoomWidth=w;
        zoomInView.getLayoutParams().width=w;
        zoomOutView.getLayoutParams().width=w;
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) spiltLineView.getLayoutParams();
        linearParams.width = w-20;
        spiltLineView.setLayoutParams(linearParams);
    }
    private void setZoomDpHeight(int h){
        zoomHeight=h;
        zoomInView.getLayoutParams().height=h;
        zoomOutView.getLayoutParams().height=h;
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) spiltLineView2.getLayoutParams();
        linearParams.height = zoomHeight-20;
        spiltLineView2.setLayoutParams(linearParams);
    }
    public void setZoomWidth(int w){
        setZoomDpWidth(Util.valueToSp(getContext(),w));
    }

    public void setZoomHeight(int h){
        setZoomDpHeight(Util.valueToSp(getContext(),h));
    }

    public void setZoomBackground(int bg){
        zoomBgView.setBackground(getResources().getDrawable(bg));
    }

    public void isHorizontal(boolean horizontal) {
        isHorizontal=horizontal;
        if(horizontal){
            linearLayoutView.setOrientation(HORIZONTAL);
            zoomBgView.setOrientation(HORIZONTAL);
            spiltLineView.setVisibility(View.GONE);
            spiltLineView2.setVisibility(View.VISIBLE);
        }else{
            linearLayoutView.setOrientation(VERTICAL);
            zoomBgView.setOrientation(VERTICAL);
            spiltLineView.setVisibility(View.VISIBLE);
            spiltLineView2.setVisibility(View.GONE);
        }
    }


    public void setZoomInNum(int num){
        zoomInNum=num;
    }

    public void setZoomOutNum(int num){
        zoomOutNum=num;
    }

    public void setZoomInImage(int zoomInImage) {
        this.zoomInImage = zoomInImage;
        zoomInView.setImageDrawable(getResources().getDrawable(zoomInImage));
    }

    public void setZoomOutImage(int zoomOutImage) {
        this.zoomOutImage = zoomOutImage;
        zoomOutView.setImageDrawable(getResources().getDrawable(zoomOutImage));
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        int padding=Util.valueToSp(getContext(),10);
        if(showText){
            zoomInTextView.setVisibility(View.VISIBLE);
            zoomOutTextView.setVisibility(View.VISIBLE);
            zoomInView.setPadding(padding,padding,padding,0);
            zoomOutView.setPadding(padding,padding,padding,0);
        }else{
            zoomInTextView.setVisibility(View.GONE);
            zoomOutTextView.setVisibility(View.GONE);
            zoomInView.setPadding(padding,padding,padding,padding);
            zoomOutView.setPadding(padding,padding,padding,padding);
        }
    }

    public void setZoomInText(String zoomInText) {
        if(zoomInText==null) return;
        this.zoomInText = zoomInText;
        zoomInTextView.setText(zoomInText);
    }

    public void setZoomOutText(String zoomOutText) {
        if(zoomOutText==null) return;
        this.zoomOutText = zoomOutText;
        zoomOutTextView.setText(zoomOutText);
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        zoomInTextView.setTextSize(fontSize);
        zoomOutTextView.setTextSize(fontSize);
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
        int color = getResources().getColor(fontColor);
        zoomInTextView.setTextColor(color);
        zoomOutTextView.setTextColor(color);
    }

    public void setZoomInNum(double zoomInNum) {
        this.zoomInNum = zoomInNum;
    }

    public void setZoomOutNum(double zoomOutNum) {
        this.zoomOutNum = zoomOutNum;
    }
}
