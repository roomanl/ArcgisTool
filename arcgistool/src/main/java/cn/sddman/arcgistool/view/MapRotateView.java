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
import cn.sddman.arcgistool.listener.MapRotateClickListener;
import cn.sddman.arcgistool.util.Util;

public class MapRotateView extends LinearLayout {
    private LinearLayout rotateBgView,mapRotateLayout;
    private ImageView mapRotateView;
    private TextView mapRotateTextView;
    private MapView mMapView;
    private int bgColor,fontColor,mapRotateImage;
    private int zoomWidth,zoomHeight,fontSize;
    private double mapRotateNum;
    private String mapRotateText;
    private boolean showText=false;
    private MapRotateClickListener mapRotateClickListener;
    public MapRotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MapRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.map_rotate_view, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewAttr);
        initView();
        initAttr(ta);
    }

    public void init(MapView mMapView){
        this.mMapView=mMapView;
    }
    public void init(MapView mMapView,MapRotateClickListener mapRotateClickListener){
        this.mMapView=mMapView;
        this.mapRotateClickListener=mapRotateClickListener;
    }

    private void initAttr(TypedArray ta){
        bgColor=ta.getResourceId(R.styleable.ViewAttr_view_background,R.drawable.sddman_shadow_bg);
        zoomWidth=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_width, Util.valueToSp(getContext(),35));
        zoomHeight=ta.getDimensionPixelSize(R.styleable.ViewAttr_button_height,Util.valueToSp(getContext(),35));
        showText=ta.getBoolean(R.styleable.ViewAttr_show_text,false);
        mapRotateNum=ta.getDimension(R.styleable.ViewAttr_map_rotate_num,90);
        mapRotateImage=ta.getResourceId(R.styleable.ViewAttr_map_rotate_image,R.drawable.sddman_rotate);
        mapRotateText=ta.getString(R.styleable.ViewAttr_map_rotate_text);
        fontColor=ta.getResourceId(R.styleable.ViewAttr_font_color,R.color.gray);
        fontSize=ta.getInt(R.styleable.ViewAttr_font_size,12);

        setBackground(bgColor);
        setDpWidth(zoomWidth);
        setDpHeight(zoomHeight);
        setRotateNum(mapRotateNum);
        setRotateImage(mapRotateImage);
        setRotateText(mapRotateText);
        setShowText(showText);
        setFontSize(fontSize);
        setFontColor(fontColor);
    }

    private void initView(){
        mapRotateLayout= (LinearLayout)findViewById(R.id.zoom_rotate_layout);
        rotateBgView = (LinearLayout)findViewById(R.id.rotate_bg);
        mapRotateView = (ImageView)findViewById(R.id.zoom_rotate);
        mapRotateTextView=(TextView)findViewById(R.id.zoom_rotate_text);

        mapRotateLayout.setOnClickListener(listener);
    }

    private OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = view.getId();
             if (i == R.id.zoom_rotate_layout) {
                 double rotate=mMapView.getMapRotation();
                 mMapView.setViewpointRotationAsync(rotate-mapRotateNum);
                 if(mapRotateClickListener!=null){
                     mapRotateClickListener.mapRotateClick(view);
                 }
            }
        }
    };

    private void setDpWidth(int w) {
        mapRotateView.getLayoutParams().width = w;
    }

    private void setDpHeight(int h){
        mapRotateView.getLayoutParams().height=h;
    }
    public void setWidth(int w){
        setDpWidth(Util.valueToSp(getContext(),w));

    }
    public void setHeight(int h){
        setDpHeight(Util.valueToSp(getContext(),h));
    }

    public void setBackground(int bg){
        rotateBgView.setBackground(getResources().getDrawable(bg));
    }

    public void setRotateNum(double num){
        mapRotateNum=num;
    }

    public void setRotateImage(int zoomRotateImage) {
        this.mapRotateImage = zoomRotateImage;
        mapRotateView.setImageDrawable(getResources().getDrawable(zoomRotateImage));
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        int padding=Util.valueToSp(getContext(),10);
        if(showText){
            mapRotateTextView.setVisibility(View.VISIBLE);
            mapRotateView.setPadding(padding,padding,padding,0);
        }else{
            mapRotateTextView.setVisibility(View.GONE);
            mapRotateView.setPadding(padding,padding,padding,padding);
        }
    }

    public void setRotateText(String zoomRotateText) {
        this.mapRotateText = zoomRotateText;
        mapRotateTextView.setText(zoomRotateText);
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        mapRotateTextView.setTextSize(fontSize);
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
        int color = getResources().getColor(fontColor);
        mapRotateTextView.setTextColor(color);
    }
}
