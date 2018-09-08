package com.kas4.flipcardbattle.model;

import java.io.Serializable;

/**
 * Created by chengxin on 16/12/16.
 */

public class BattleMatchModel implements Serializable {
    String match_id;
    Video match_video_a;
    Video match_video_b;

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public Video getMatch_video_a() {
        return match_video_a;
    }

    public void setMatch_video_a(Video match_video_a) {
        this.match_video_a = match_video_a;
    }

    public Video getMatch_video_b() {
        return match_video_b;
    }

    public void setMatch_video_b(Video match_video_b) {
        this.match_video_b = match_video_b;
    }
}
