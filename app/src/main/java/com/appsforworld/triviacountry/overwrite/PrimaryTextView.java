package com.appsforworld.triviacountry.overwrite;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by marcelo on 21/09/16.
 */
public class PrimaryTextView extends TextView {
    private Context context;

    public PrimaryTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public PrimaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PrimaryTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "JandaManateeSolid.ttf");
        setTypeface(tf);
    }
}