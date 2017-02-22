package com.cesecsh.shoppingcar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener {


    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(new RecyclerAdapter());
        ivShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item_shop_car, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_list_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.show();
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //动画结束后 父布局移除 img
        Object target = ((ObjectAnimator) animation).getTarget();
        activityMain.removeView((View) target);
        //shopImg 开始一个放大动画
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.shape_scale);
        ivShopCar.startAnimation(scaleAnim);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }


    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyAdapter> {

        @Override
        public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyAdapter(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_product, parent, false));
        }

        @Override
        public void onBindViewHolder(MyAdapter holder, int position) {
            holder.imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "加入购物车成功", Toast.LENGTH_SHORT).show();
                    int[] childCoordinate = new int[2];
                    int[] parentCoordinate = new int[2];
                    int[] shopCoordinate = new int[2];
                    //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
                    //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
                    v.getLocationInWindow(childCoordinate);
                    ViewGroup decorView = createAnimLayout();
                    decorView.getLocationInWindow(parentCoordinate);
                    ivShopCar.getLocationInWindow(shopCoordinate);
                    //2.自定义ImageView 继承ImageView
                    MoveImageView img = new MoveImageView(MainActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    img.setLayoutParams(lp);
                    img.setImageResource(R.mipmap.ic_launcher);
                    //3.设置img在父布局中的坐标位置
                    img.setX(childCoordinate[0] - parentCoordinate[0]);
                    img.setY(childCoordinate[1] - parentCoordinate[1]);
                    //4.父布局添加该Img
                    decorView.addView(img);

                    //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
                    PointF startP = new PointF();
                    PointF endP = new PointF();
                    PointF controlP = new PointF();
                    //开始的数据点坐标就是 addV的坐标
                    startP.x = childCoordinate[0] - parentCoordinate[0];
                    startP.y = childCoordinate[1] - parentCoordinate[1];
                    //结束的数据点坐标就是 shopImg的坐标
                    endP.x = shopCoordinate[0] - parentCoordinate[0];
                    endP.y = shopCoordinate[1] - parentCoordinate[1];
                    //控制点坐标 x等于 购物车x；y等于 addV的y
                    controlP.x = endP.x;
                    controlP.y = startP.y;

                    //启动属性动画
                    ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                            new BezierTypeEvaluator(controlP), startP, endP);
                    animator.setDuration(1000);
                    animator.addListener(MainActivity.this);
                    animator.start();
                }
            });
        }

        /**
         * @param
         * @return void
         * @throws
         * @Description: 创建动画层
         */
        private ViewGroup createAnimLayout() {
            ViewGroup rootView = (ViewGroup) MainActivity.this.getWindow().getDecorView();
            LinearLayout animLayout = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            animLayout.setLayoutParams(lp);
            animLayout.setId(Integer.MAX_VALUE - 1);
            animLayout.setBackgroundResource(android.R.color.transparent);
            rootView.addView(animLayout);
            return animLayout;
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class MyAdapter extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_product_name)
            TextView tvName;
            @BindView(R.id.tv_product_price)
            TextView tvPrice;
            @BindView(R.id.imageView)
            ImageView imgAdd;

            public MyAdapter(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
