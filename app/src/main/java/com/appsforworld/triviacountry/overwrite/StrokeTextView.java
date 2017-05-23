package com.appsforworld.triviacountry.overwrite;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.appsforworld.triviacountry.R;

/**
 * Created by marcelo on 24/09/16.
 */
public class StrokeTextView extends TextView {
    private Context context;
    private Paint mTextPaint;
    private Paint mTextPaintOutline;

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public StrokeTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }



    private void init() {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "JandaManateeSolid.ttf");
        setTypeface(tf);
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        int textColor = getTextColors().getDefaultColor();
        setTextColor(ContextCompat.getColor(context, R.color.white)); // your stroke's color
        getPaint().setStrokeWidth(8);
        getPaint().setStyle(Paint.Style.STROKE);
        super.onDraw(pCanvas);
        setTextColor(textColor);
        getPaint().setStrokeWidth(0);
        getPaint().setStyle(Paint.Style.FILL);
        super.onDraw(pCanvas);
    }
}