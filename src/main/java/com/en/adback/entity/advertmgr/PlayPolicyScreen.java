package com.en.adback.entity.advertmgr;

import com.en.adback.entity.calpolicy.screencut.Screen;
import lombok.Data;

import java.util.List;

/**播放策略（屏幕）
 * Created by Administrator on 2018/12/3.
 */
@Data
public class PlayPolicyScreen {

    private String  screenpolicyId;   	// 策略id
    private String  policyName;  	// 策略名称
    private List<Screen> screens ; // 屏幕
    private double duration  ; // 播放时长
    private String ltmark ; // 'eq' 等于，‘lt’ 小于
    private String memo ;
    private boolean choosed ; // 是否選擇了本屏幕策略

    public PlayPolicyScreen() {
    }

    public PlayPolicyScreen(String screenpolicyId, String policyName, List<Screen> screens, double duration, String ltmark, String memo, boolean choosed) {
        this.screenpolicyId = screenpolicyId;
        this.policyName = policyName;
        this.screens = screens;
        this.duration = duration;
        this.ltmark = ltmark;
        this.memo = memo;
        this.choosed = choosed;
    }
}
