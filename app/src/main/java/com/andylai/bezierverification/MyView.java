package com.andylai.bezierverification;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyView extends View {

	private final Path path = new Path();

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		path.reset();
		copyPointDataToPath(getCurve1(), path);
		canvas.drawPath(path, getRedPaint());

		path.reset();
		copyPointDataToPath(getCurve2(), path);
		canvas.drawPath(path, getBluePaint());
	}

	Paint getRedPaint() {
		return getPaint(Color.RED);
	}

	Paint getBluePaint() {
		return getPaint(Color.BLUE);
	}

	Paint getPaint(int color) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
		return paint;
	}

	List<PointF> getRawData() {
		List<PointF> list = new ArrayList<>();
		list.add(new PointF(100, 100));
		list.add(new PointF(200, 200));
		list.add(new PointF(300, 100));
		list.add(new PointF(400, 200));
		list.add(new PointF(400, 800));
		list.add(new PointF(500, 400));
		list.add(new PointF(600, 200));
		list.add(new PointF(700, 500));
		list.add(new PointF(800, 700));
		list.add(new PointF(900, 300));
		return list;
	}

	List<PointF> getCurve1() {
		return getNormalCurve(getRawData());
	}

	List<PointF> getCurve2() {
		return getBezierCurve(getRawData());
	}

	List<PointF> getNormalCurve(List<PointF> rawData) {
		return rawData;
	}

	List<PointF> getBezierCurve(List<PointF> rawData) {
		return CubicBezier.calculate(rawData);
	}

	void dump(List<PointF> points) {
		if (!points.isEmpty()) {
			for (PointF pointF : points) {
				Log.d("Andy", "pointF = " + pointF);
			}
		}
	}

	void copyPointDataToPath(List<PointF> pointData, Path path) {
		path.moveTo(pointData.get(0).x, pointData.get(0).y);
		for (PointF pointF : pointData) {
			path.lineTo(pointF.x, pointF.y);
		}
	}
}
