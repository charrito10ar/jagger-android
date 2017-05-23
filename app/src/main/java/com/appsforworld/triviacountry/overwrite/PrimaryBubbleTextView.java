package com.appsforworld.triviacountry.overwrite;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by marcelo on 25/09/16.
 */
public class PrimaryBubbleTextView extends TextView {
    private Context context;

    public PrimaryBubbleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public PrimaryBubbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PrimaryBubbleTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "JandaManateeBubble.ttf");
        setTypeface(tf);
    }
}
