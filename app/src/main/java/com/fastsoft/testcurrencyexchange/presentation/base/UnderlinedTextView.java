package com.fastsoft.testcurrencyexchange.presentation.base;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class UnderlinedTextView extends android.support.v7.widget.AppCompatTextView {

    public UnderlinedTextView(Context context) {
        super(context);
        setPaintFlags(getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaintFlags(getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaintFlags(getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }
}
