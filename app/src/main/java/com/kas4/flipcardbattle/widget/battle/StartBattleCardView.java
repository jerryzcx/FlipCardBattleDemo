package com.kas4.flipcardbattle.widget.battle;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas4.flipcardbattle.R;


/**
 * Created by chengxin on 16/12/15.
 */

public class StartBattleCardView extends BattleCardView {
    public StartBattleCardView(Context context) {
        super(context);
    }

    public StartBattleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StartBattleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_battle_card_start;
    }

    @Override
    protected void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(getLayout(), this);
        setOrientation(LinearLayout.VERTICAL);

        btn_start = (Button) findViewById(R.id.btn_start);
        tv_start_tip= (TextView) findViewById(R.id.tv_start_tip);


        SpannableString spanString = new SpannableString("为了帮助你喜欢的视频获胜，双击它吧！");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#ef402f"));
        spanString.setSpan(span, 13, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_start_tip.setText(spanString);

    }

    Button btn_start;
    TextView tv_start_tip;


    public void setBtnStartListener(OnClickListener l) {
        btn_start.setOnClickListener(l);
    }

}
