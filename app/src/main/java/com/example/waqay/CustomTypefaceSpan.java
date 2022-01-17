package com.example.waqay;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;

public class CustomTypefaceSpan extends TypefaceSpan {

    private String font;
    private Context context;

    public CustomTypefaceSpan(@NonNull String font, @NonNull Context context) {
        super("");
        this.font = font;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        CustomFontHelper.setCustomFont(ds, font, context);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        CustomFontHelper.setCustomFont(paint, font, context);
    }
}