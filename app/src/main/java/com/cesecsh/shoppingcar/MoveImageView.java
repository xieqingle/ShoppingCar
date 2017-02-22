package com.cesecsh.shoppingcar;

import android.content.Context;
import android.graphics.PointF;
import android.widget.ImageView;

/**
 * ShoppingCar
 * Created by RockQ on 2017/2/22.
 */

public class MoveImageView extends ImageView {
    public MoveImageView(Context context) {
        super(context);
    }

    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }
}
