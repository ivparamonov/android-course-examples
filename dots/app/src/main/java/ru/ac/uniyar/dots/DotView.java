package ru.ac.uniyar.dots;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import android.util.AttributeSet;
import android.view.View;

/**
 * View that show dots
 */
public class DotView extends View {

    private Dots dots;

    public DotView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        setFocusable(true);
    }

    public void setDots(Dots dots) {
        this.dots = dots;
        dots.setDotsChangeListener(new Dots.DotsChangeListener() {
            @Override
            public void onDotsChange(Dots dots) {
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(dots == null) return;
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);
        for (Dot dot : dots.getDots()) {
            paint.setColor(dot.getColor());
            canvas.drawCircle(dot.getX(), dot.getY(),
                    dot.getDiameter(), paint);
        }
    }
}
