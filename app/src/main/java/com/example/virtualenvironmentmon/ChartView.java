package com.example.virtualenvironmentmon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.Object;
import android.graphics.RectF;

public class ChartView extends View {

    private long maxMem;
    private long currentMem;

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMemory(long currentMem, long maxMem) {
        this.currentMem = currentMem;
        this.maxMem = maxMem;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint_green = new Paint();
        Paint paint_gray = new Paint();
        Paint paint_ltgray = new Paint();
        Paint paint_black = new Paint();
        paint_gray.setColor(Color.GRAY);
        paint_green.setColor(Color.GREEN);
        paint_ltgray.setColor(Color.LTGRAY);
        paint_black.setColor(Color.BLACK);
        //paint_gray.setStrokeWidth(2);
        //canvas.drawLine(50, 50, 300, 300, paint);

        RectF oval_bk_rc = new RectF(175, 100, 930, 200);
        RectF arc_bk_rc = new RectF(150, 675, 930, 1400);
        RectF arc_fg_rc = new RectF(190, 710, 890, 1315);
        // Draw the shadow
        canvas.drawOval(oval_bk_rc, paint_ltgray);
        paint_black.setTextSize(70);
        canvas.drawText("Used Memory", 300, 170, paint_black);


        canvas.drawArc(arc_bk_rc,
                    180,
                    180,
                    true, paint_gray);

        canvas.drawArc(arc_fg_rc,
                    180,
                (float)currentMem/maxMem * 180,
                    true, paint_green);

        canvas.drawText(String.valueOf(currentMem) +"/ " +
                String.valueOf(maxMem) + " MB", 300, 1250, paint_black);

    }
}







