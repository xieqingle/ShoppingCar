package com.cesecsh.shoppingcar.helpview;

import com.cesecsh.shoppingcar.R;

/**
 * ShoppingCar
 * Created by RockQ on 2017/2/22.
 */
public class AlertAnimateUtil {
    public static int getAnimationResource(boolean isInAnimator) {
        return isInAnimator ? R.anim.fade_in_center : R.anim.fade_out_center;
    }
}
