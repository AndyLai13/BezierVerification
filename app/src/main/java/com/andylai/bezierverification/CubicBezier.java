package com.andylai.bezierverification;

import static java.lang.Math.pow;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * When we want calculate a segment of curve between P(i) and P(i+1), we need
 */
public final class CubicBezier {
	public static List<PointF> calculate(List<PointF> rawData) {
		List<PointF> accurate = new ArrayList<>();
		for (int i = 0; i < rawData.size() - 1; i++) {
			PointF startPrev;
			PointF start;
			PointF end;
			PointF endNext;
			if (i - 1 <= 0) {
				startPrev = rawData.get(i);
				start = rawData.get(i);
				end = rawData.get(i + 1);
				endNext = rawData.get(i + 2);
			} else if (i + 2 >= rawData.size()) {
				startPrev = rawData.get(i - 1);
				start = rawData.get(i);
				end = rawData.get(i + 1);
				endNext = rawData.get(i + 1);
			} else {
				startPrev = rawData.get(i - 1);
				start = rawData.get(i);
				end = rawData.get(i + 1);
				endNext = rawData.get(i + 2);
			}

			int slope = 10;
			PointF p0 = start;
			PointF p1 = new PointF(
					start.x + (end.x - startPrev.x) / slope,
					start.y + ((end.y - startPrev.y) / slope)); // control point A
			PointF p2 = new PointF(
					end.x - (endNext.x - start.x) / slope,
					end.y - ((endNext.y - start.y) / slope));  // control point B
			PointF p3 = end;
			accurate.addAll(bezierCubicCurve(p0, p1, p2, p3, 10));
		}
		return accurate;
	}

	private static List<PointF> bezierCubicCurve(PointF p0, PointF p1, PointF p2, PointF p3, int segment) {
		List<PointF> ret = new ArrayList<>();
		for (int i = 0; i <= segment; i++) {
			float t = ((float) (i)) / segment;
			double c0 = pow(1 - t, 3);
			double c1 = 3 * t * pow(1 - t, 2);
			double c2 = 3 * pow(t, 2) * (1 - t);
			double c3 = pow(t, 3);
			double x = c0 * p0.x + c1 * p1.x + c2 * p2.x + c3 * p3.x;
			double y = c0 * p0.y + c1 * p1.y + c2 * p2.y + c3 * p3.y;
			ret.add(new PointF((float) x, (float) y));
		}
		return ret;
	}

}
