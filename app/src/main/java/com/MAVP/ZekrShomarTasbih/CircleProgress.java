package com.MAVP.ZekrShomarTasbih;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgress extends View {

    private int   max , progress =  0   , strokewidth = 6 ;
    private Paint circle ;
    private RectF rectF ;

    public CircleProgress(Context context) {
        super(context);
        init();
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle.setColor(Color.WHITE);
        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(strokewidth);

        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        int minimum = Math.min(width,height);
        setMeasuredDimension(minimum,minimum);
        rectF.set(strokewidth/2,strokewidth/2,minimum-strokewidth/2,minimum-strokewidth/2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sweepAngel = progress  * 360 / max ;
        canvas.drawArc(rectF,-90,sweepAngel ,false,circle);
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setColor(int color) {
        circle.setColor(color);
        invalidate();
    }

}
