package com.madXdesign.book.image.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public class TextDrawable extends Drawable {

    private final String text;
    private final Paint paint;
    private final float textSize;
    private final float marginLeft;
    
    public TextDrawable(String text) {
        this(text, 14f);
    }
    
    public TextDrawable(String text, float textSize) {

        this.text = text;
        this.textSize = textSize;
        this.paint = new Paint();
        this.marginLeft = 5f;
        //float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
        //int height = (int) (baseline + paint.descent() + 0.5f);
        paint.setColor(Color.BLACK);
        paint.setTextSize(this.textSize);
        paint.setAntiAlias(true);
        //paint.setFakeBoldText(true);
        paint.setTypeface(Typeface.MONOSPACE);
        //paint.setShadowLayer(6f, 0, baseline, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
    }
    
    public Paint getPaint() {
        return this.paint;
    }

    @Override
    public void draw(Canvas canvas) {
        float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
        canvas.drawText(text, marginLeft, baseline, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
