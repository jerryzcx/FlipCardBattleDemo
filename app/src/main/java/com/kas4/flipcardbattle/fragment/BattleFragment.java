package com.kas4.flipcardbattle.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.kas4.flipcardbattle.R;
import com.kas4.flipcardbattle.model.BattleMatchModel;
import com.kas4.flipcardbattle.model.Video;
import com.kas4.flipcardbattle.widget.battle.BattleCardView;
import com.kas4.flipcardbattle.widget.battle.BottomBattleCardView;
import com.kas4.flipcardbattle.widget.battle.ImageBattleCardView;
import com.kas4.flipcardbattle.widget.battle.StartBattleCardView;
import com.kas4.flipcardbattle.widget.battle.TopBattleCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by chengxin on 16/12/15.
 */

public class BattleFragment extends Fragment {

    View mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_battle, null);
        initView();
        return mContentView;
    }

    public static BattleFragment newInstance() {
        Bundle args = new Bundle();
        BattleFragment fragment = new BattleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected <T extends View> T getViewById(@IdRes int id) {
        return (T) mContentView.findViewById(id);
    }

    RelativeLayout layout_top;
    RelativeLayout layout_bottom;

    private void initView() {
        layout_top = getViewById(R.id.layout_top);
        layout_bottom = getViewById(R.id.layout_bottom);

    }

    private long[] mHits = new long[2];
    private View.OnClickListener mDoubleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            System.out.println("" + mHits.length);
            Object tag = v.getTag();

            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis(); // 系统开机时间
            if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                match_count++;// 计数

                String id = mData.get(0).getMatch_id();
                postVote(tag, id);

                if (mData.size() <= 5) {
                    loadMore();
                }
                chooseView(v);
                rotateView();

            }
        }
    };

    private void postVote(Object tag, String id) {
    }

    void initCardView() {
        final ImageBattleCardView topView = new ImageBattleCardView(getActivity());
        layout_top.addView(topView);

        final StartBattleCardView bottomView = new StartBattleCardView(getActivity());
        layout_bottom.addView(bottomView);

        bottomView.setBtnStartListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCardView();
            }
        });
    }


    void addCardView() {
        BattleMatchModel model;

        if (mData.size() <= 1) {// 最后一个
            final View oldTopView = layout_top.getChildAt(0);
            final View oldBottomView = layout_bottom.getChildAt(0);


            ViewAnimator.
                    animate(oldTopView).dp().translationX(0, 360).descelerate()
                    .addAnimationBuilder(oldBottomView).dp().translationX(0, -360).descelerate()
                    .duration(DURATION_ANIM_SLIDE).

                    onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {
                            layout_top.removeView(oldTopView);
                            layout_bottom.removeView(oldBottomView);

                            if (mData.size() == 1)
                                mData.remove(0);
                        }
                    }).start();

            return;
        } else {
            model = mData.get(0);
        }

        final Video va = model.getMatch_video_a();
        final Video vb = model.getMatch_video_b();

//        RelativeLayout.LayoutParams lp =
//                new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(dip2px(24), 0, dip2px(24), 0);

        final TopBattleCardView topView = new TopBattleCardView(getActivity());
        topView.update(va);
        topView.setTag("a");

        layout_top.addView(topView);


        final BottomBattleCardView bottomView = new BottomBattleCardView(getActivity());
        bottomView.update(vb);
        bottomView.setTag("b");

        layout_bottom.addView(bottomView);


        if (layout_top.getChildCount() > 1) {
            final View oldTopView = layout_top.getChildAt(0);
            final View oldBottomView = layout_bottom.getChildAt(0);


            ViewAnimator.animate(topView).dp().translationX(-360, 0).descelerate()
                    .addAnimationBuilder(bottomView).dp().translationX(360, 0).descelerate()
                    .addAnimationBuilder(oldTopView).dp().translationX(0, 360).descelerate()
                    .addAnimationBuilder(oldBottomView).dp().translationX(0, -360).descelerate()
                    .duration(DURATION_ANIM_SLIDE)
//                    .startDelay(DURATION_ANIM / 3)
                    .onStart(new AnimationListener.Start() {
                        @Override
                        public void onStart() {

                        }
                    })
                    .onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {
                            topView.setOnClickListener(mDoubleClickListener);
                            bottomView.setOnClickListener(mDoubleClickListener);

                            topView.play(va);
                            bottomView.play(vb);

                            layout_top.removeView(oldTopView);
                            layout_bottom.removeView(oldBottomView);


                            mData.remove(0);
                        }
                    }).start();
        } else {

            ViewAnimator.animate(topView).dp().translationX(-360, 0).descelerate()
                    .addAnimationBuilder(bottomView).dp().translationX(360, 0).descelerate()
                    .duration(DURATION_ANIM_SLIDE)
//                    .startDelay(DURATION_ANIM / 3)
                    .onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {
                            topView.setOnClickListener(mDoubleClickListener);
                            bottomView.setOnClickListener(mDoubleClickListener);

                            topView.play(va);
                            bottomView.play(vb);
                        }
                    }).start();

        }


    }


    static final long DURATION_ANIM_SLIDE = 300;
    static final long DURATION_ANIM_ROTATE = 250;
    static final long DURATION_ANIM_STAY = 1000;

    void chooseView(View v) {
        if (v instanceof BattleCardView) {
            ((BattleCardView) v).setSuccess(true);
        }
    }

    void rotateView() {
        final View oldTopView = layout_top.getChildAt(0);
        final View oldBottomView = layout_bottom.getChildAt(0);

        oldTopView.setOnClickListener(null);
        oldBottomView.setOnClickListener(null);

        ((BattleCardView) oldTopView).rotateUp(DURATION_ANIM_ROTATE, new BattleCardView.AnimCallback() {
            @Override
            public void onStart() {
                ((BattleCardView) oldTopView).pause();
                ((BattleCardView) oldBottomView).pause();
            }

            @Override
            public void onStop() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addCardView();
                    }
                }, DURATION_ANIM_STAY);
            }
        });
        ((BattleCardView) oldBottomView).rotateDown(DURATION_ANIM_ROTATE, null);


    }

    void pauseVideo() {
        if (layout_top == null || layout_top.getChildCount() <= 0) return;
        final View oldTopView = layout_top.getChildAt(0);
        final View oldBottomView = layout_bottom.getChildAt(0);
        ((BattleCardView) oldTopView).pause();
        ((BattleCardView) oldBottomView).pause();

    }

    void playVideo() {
        if (layout_top == null || layout_top.getChildCount() <= 0) return;
        final View oldTopView = layout_top.getChildAt(0);
        final View oldBottomView = layout_bottom.getChildAt(0);
        ((BattleCardView) oldTopView).start();
        ((BattleCardView) oldBottomView).start();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            pauseVideo();
        } else {
            playVideo();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }

    @Override
    public void onResume() {
        super.onResume();
        playVideo();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadRefresh();
    }

    Handler mHandler = new Handler();

    int match_count = 0;

    int mPage = 1;

    void loadRefresh() {
        mPage = 1;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.addAll(getTestData());
                initCardView();
            }
        }, 1000);
    }

    private void loadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.addAll(getTestData());
            }
        }, 1000);

    }


    private List getTestData() {
        List<String> urlList = new ArrayList<>();
        urlList.addAll(Arrays.asList(TEST_URLS));
        Collections.shuffle(urlList);

        List<BattleMatchModel> list = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            BattleMatchModel item = new BattleMatchModel();
            item.setMatch_id("test");

            Video a = new Video();
            a.setUrl(urlList.get(i));
            Video b = new Video();
            b.setUrl(urlList.get((i + 2) % urlList.size()));

            item.setMatch_video_a(a);
            item.setMatch_video_b(b);

            list.add(item);
        }
        return list;
    }

    String TEST_URLS[] = new String[]{
            "http://video.wesafari.cn/878a401aac/c35e1a2e5a2a5ca372e5cd80a4c0d91fad58957db24bad74.32595838",
            "http://video.wesafari.cn/61409b38f1/bad5e064444736694ad56b2bffd6543f3157faa010bc56e6.87208839",
            "http://video.wesafari.cn/7b3c7d3963/0c6b994e41e27d8ac5c2eeaf5b8c9c155858eb13f506db85.42824368",
            "http://video.wesafari.cn/0fd78fa8b1/4b8889d65f522897bee9058e782e88e1c15871ab08805549.76106990",
            "http://video.wesafari.cn/de76ff206c/e07a0812560e5f3adf831c46fa8ed7f7d258cd45ceac2aa2.32657297",

            "http://video.wesafari.cn/d56e79f9e4/577296ec4ba18_3f32d2f1328c805a3e6577296ec4b9c15.74930420",
            "http://video.wesafari.cn/5799ce687f/7a0378e0b890f964ba6e8755935d8c9e145887b4b86e24e9.96300408",
            "http://video.wesafari.cn/8437a1cccb/71f92bd3688b65a2cec83f1bfd21dfef585849f39c656c04.41200107",
            "http://video.wesafari.cn/d88b124c3c/574a1f02e7a0b_bf58e56ca5e42bd1f56574a1f02e79ba6.62401308",
            "http://video.wesafari.cn/89bcda855c/3fbcba191e805c2216fdbf6053ad29aaeb586a1d8c2b92a7.10440808",
    };

    List<BattleMatchModel> mData = new ArrayList<>();
}

