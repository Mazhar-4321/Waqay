package com.example.waqay;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

public class CustomFontHelper {
    public static void setCustomFont(Paint paint, String font, Context context) {
        if (font == null) {
            return;
        }
        Typeface typeface = FontCache.get(font, context);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
    }
}
