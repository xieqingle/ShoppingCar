package com.cesecsh.shoppingcar.helpview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesecsh.shoppingcar.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ShoppingCar
 * Created by RockQ on 2017/2/22.
 */

public class HelpView {
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    private List<HelpContent> helpContents;
    private WeakReference<Context> contextWeak;
    private ViewGroup contentContainer;
    private View rootView;
    private ViewGroup decorView;
    private Animation outAnim;
    private OnDismissListener onDismissListener;
    private boolean isShowing;
    private Animation inAnim;
    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public HelpView(List<HelpContent> helpContents, Context context) {
        this.helpContents = helpContents;
        contextWeak = new WeakReference<Context>(context);
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
        initView();
    }

    private void initView() {
        Context context = contextWeak.get();
        if (context == null)
            return;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = layoutInflater.inflate(R.layout.layout_help_view_root_view, decorView, false);
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
        params.gravity = Gravity.CENTER;
        int margin_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_alert_left_right);
        params.setMargins(margin_left_right, 0, margin_left_right, 0);
        contentContainer.setLayoutParams(params);
        initHelpView(layoutInflater);

    }

    private void initHelpView(LayoutInflater layoutInflater) {
        Context context = contextWeak.get();
        if (context == null)
            return;
        View view = layoutInflater.inflate(R.layout.layout_help_view, contentContainer, false);
        contentContainer.addView(view);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_help_content);
        for (HelpContent helpContent : helpContents) {
            TextView textView = new TextView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setPadding(20, 20, 20, 20);
            textView.setLayoutParams(layoutParams);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(helpContent.getHelpContent());
            int end = helpContent.getHelpContent().indexOf("：");
            if (end <= 0)
                end = helpContent.getHelpContent().indexOf(":");
            if (end > 0)
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 0, end + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(spannableStringBuilder);
            linearLayout.addView(textView);
        }
        view.findViewById(R.id.img_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(rootView);
    }

    private void onAttached(View rootView) {
        isShowing = true;
        decorView.addView(rootView);
        contentContainer
                .startAnimation(inAnim);
    }

    public boolean isShowing() {
        return rootView.getParent() != null && isShowing;
    }



    public void dismiss() {
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        contentContainer.startAnimation(outAnim);
    }

    public void dismissImmediately() {
        decorView.removeView(rootView);
        isShowing = false;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }

    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }
    public Animation getInAnimation() {
        Context context = contextWeak.get();
        if (context == null) return null;

        int res = AlertAnimateUtil.getAnimationResource(true);
        return AnimationUtils.loadAnimation(context, res);
    }
    public Animation getOutAnimation() {
        Context context = contextWeak.get();
        if (context == null) return null;

        int res = AlertAnimateUtil.getAnimationResource(false);
        return AnimationUtils.loadAnimation(context, res);
    }
}
