package com.kas4.flipcardbattle.widget.battle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kas4.flipcardbattle.R;
import com.kas4.flipcardbattle.model.Video;
import com.pili.pldroid.player.widget.PLVideoTextureView;


/**
 * Created by zjerry on 16/6/17.
 */
public abstract class BattleCardView extends LinearLayout {
    public BattleCardView(Context context) {
        super(context);
        init(context);
    }

    public BattleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BattleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    Context mContext;


    public int px2dip(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    abstract protected int getLayout();


    ImageView iv_back;
    ImageView iv_icon;
    TextView tv_nick;
    TextView tv_loc;
    RelativeLayout layout_pos;

    protected void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(getLayout(), this);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        layout_pos = (RelativeLayout) findViewById(R.id.layout_pos);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        tv_loc = (TextView) findViewById(R.id.tv_loc);

        video_view = (PLVideoTextureView) findViewById(R.id.video_view);

        initReverseView();
        setSuccess(false);// 初始值
    }

    ImageView iv_back_rev;
    ImageView iv_icon_rev;
    TextView tv_nick_rev;
    TextView tv_loc_rev;
    RelativeLayout layout_rev;
    ImageView iv_center_face;
    TextView tv_center_tip;

    private void initReverseView() {

        layout_rev = (RelativeLayout) findViewById(R.id.layout_rev);
        layout_rev.setRotationX(-90);

        iv_back_rev = (ImageView) findViewById(R.id.iv_back_rev);
        iv_icon_rev = (ImageView) findViewById(R.id.iv_icon_rev);
        tv_nick_rev = (TextView) findViewById(R.id.tv_nick_rev);
        tv_loc_rev = (TextView) findViewById(R.id.tv_loc_rev);

        iv_center_face = (ImageView) findViewById(R.id.iv_center_face);
        tv_center_tip = (TextView) findViewById(R.id.tv_center_tip);

    }

    public interface AnimCallback {
        public void onStart();

        public void onStop();
    }

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();

    public void rotateDown(long dura, final AnimCallback callback) {
        final View visibleList;
        final View invisibleList;
        if (layout_pos.getVisibility() == View.GONE) {
            visibleList = layout_rev;
            invisibleList = layout_pos;
        } else {
            invisibleList = layout_rev;
            visibleList = layout_pos;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationX", 0f, -90f);
        visToInvis.setDuration(dura);
//        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationX",
                90f, 0f);
        invisToVis.setDuration(dura);
//        invisToVis.setInterpolator(accelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);

                if (callback != null)
                    callback.onStop();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (callback != null)
                    callback.onStart();
            }
        });
        visToInvis.start();
    }

    public void rotateUp(long dura, final AnimCallback callback) {
        final View visibleList;
        final View invisibleList;
        if (layout_pos.getVisibility() == View.GONE) {
            visibleList = layout_rev;
            invisibleList = layout_pos;
        } else {
            invisibleList = layout_rev;
            visibleList = layout_pos;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationX", 0f, 90f);
        visToInvis.setDuration(dura);
//        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationX",
                -90f, 0f);
        invisToVis.setDuration(dura);
//        invisToVis.setInterpolator(accelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);

                if (callback != null)
                    callback.onStop();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (callback != null)
                    callback.onStart();
            }
        });
        visToInvis.start();
    }

    public void setSuccess(boolean isSuccess) {
        if (isSuccess) {
            iv_center_face.setImageResource(R.drawable.sorry_happy);
            tv_center_tip.setText("胜利");
            iv_back_rev.setBackgroundColor(Color.parseColor("#ffd900"));
        } else {
            iv_center_face.setImageResource(R.drawable.sorry_unhappy);
            tv_center_tip.setText("很遗憾");

            iv_back_rev.setBackgroundColor(Color.parseColor("#ff6252"));
        }
    }


    public void update(Object model) {
        if (model == null)
            return;

    }

    private PLVideoTextureView video_view;

    public void play(Video video) {
        video_view.setLooping(true);
        video_view.setVideoPath(video.getUrl());
        video_view.setVolume(0.0f, 0.0f);

    }

    public void start() {
        if (video_view == null) return;
        video_view.start();
    }

    public void pause() {
        if (video_view == null) return;
        video_view.pause();
    }

    public void stop() {
        if (video_view == null) return;
        video_view.stopPlayback();
    }

    public void togglePlay() {
        if (video_view == null) return;
        if (video_view.isPlaying())
            video_view.pause();
        else
            video_view.start();
    }


}
