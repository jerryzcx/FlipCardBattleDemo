package com.kas4.flipcardbattle.widget.battle;

import android.content.Context;
import android.util.AttributeSet;

import com.kas4.flipcardbattle.R;


/**
 * Created by chengxin on 16/12/15.
 */

public class BottomBattleCardView extends BattleCardView {
    public BottomBattleCardView(Context context) {
        super(context);
    }

    public BottomBattleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBattleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_battle_card_right;
    }
}
