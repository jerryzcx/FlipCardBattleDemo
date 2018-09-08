package com.kas4.flipcardbattle.widget.battle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.kas4.flipcardbattle.R;


/**
 * Created by chengxin on 16/12/15.
 */

public class ImageBattleCardView extends BattleCardView {
    public ImageBattleCardView(Context context) {
        super(context);
    }

    public ImageBattleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageBattleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_battle_card_image;
    }

    @Override
    protected void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(getLayout(), this);
        setOrientation(LinearLayout.VERTICAL);
    }
}
