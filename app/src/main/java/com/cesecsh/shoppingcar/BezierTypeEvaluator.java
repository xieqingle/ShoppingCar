package com.cesecsh.shoppingcar;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * ShoppingCar
 * Created by RockQ on 2017/2/22.
 */

public class BezierTypeEvaluator implements TypeEvaluator<PointF> {
    /**
     * 每个估值器对应一个属性动画，每个属性动画对应唯一一个控制点
     */
    private PointF control;
    /**
     * 估值器返回值
     */
    private PointF mPointF = new PointF();

    public BezierTypeEvaluator(PointF control) {
        this.control = control;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return drawBezierEvaluate(fraction, startValue, endValue, control);
    }

    /**
     * 二次贝塞尔曲线公司
     *
     * @param fraction   float 0-1
     * @param startValue 开始的数据点
     * @param endValue   结束的数据点
     * @param control    控制点
     * @return 不同fraction对应的PointF
     */
    public PointF drawBezierEvaluate(float fraction, PointF startValue, PointF endValue, PointF control) {
        mPointF.x = (1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * control.x + fraction * fraction * endValue.x;
        mPointF.y = (1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * control.y + fraction * fraction * endValue.y;
        return mPointF;
    }
}
