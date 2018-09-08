package com.kas4.flipcardbattle.widget.battle;

import android.content.Context;
import android.util.AttributeSet;

import com.kas4.flipcardbattle.R;


/**
 * Created by chengxin on 16/12/15.
 */

public class TopBattleCardView extends BattleCardView {
    public TopBattleCardView(Context context) {
        super(context);
    }

    public TopBattleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopBattleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_battle_card_left;
    }
}
