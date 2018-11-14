package com.example.asus.handbook.userdefined;

import android.support.v7.widget.AppCompatImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircleImageView extends AppCompatImageView {
    //圆形图片的半径
    private int mRadius;

    public CircleImageView(Context context) {
        super(context);


    }

    public CircleImageView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于是圆形，宽高应保持一致
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = size / 2;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        Bitmap bitmap = drawableToBitmap(getDrawable());

        if (null != drawable) {

            //初始化BitmapShader，传入bitmap对象
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            //计算缩放比例
            float mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());

            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            bitmapShader.setLocalMatrix(matrix);

            Paint mPaint = new Paint();
            mPaint.setShader(bitmapShader);
            //画圆形，指定好坐标，半径，画笔
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else {
            super.onDraw(canvas);
        }
    }
    //写一个drawble转BitMap的方法
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}


