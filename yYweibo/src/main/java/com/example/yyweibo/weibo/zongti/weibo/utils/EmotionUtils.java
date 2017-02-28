package com.example.yyweibo.weibo.zongti.weibo.utils;

import com.example.yyweibo.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan fuchang on 2016/10/21.
 */

public class EmotionUtils implements Serializable {
    public static Map<String,Integer> emoMap;
    static {
        emoMap = new HashMap<>();
        emoMap.put("[呵呵]", R.drawable.d_hehe);
        emoMap.put("[嘻嘻]", R.drawable.d_xixi);
        emoMap.put("[哈哈]", R.drawable.d_haha);
        emoMap.put("[爱你]", R.drawable.d_aini);
        emoMap.put("[挖鼻屎]", R.drawable.d_wabishi);
        emoMap.put("[吃惊]", R.drawable.d_chijing);
        emoMap.put("[晕]", R.drawable.d_yun);
        emoMap.put("[泪]", R.drawable.d_lei);
        emoMap.put("[馋嘴]", R.drawable.d_chanzui);
        emoMap.put("[抓狂]", R.drawable.d_zhuakuang);
        emoMap.put("[哼]", R.drawable.d_heng);
        emoMap.put("[可爱]", R.drawable.d_keai);
        emoMap.put("[怒]", R.drawable.d_nu);
        emoMap.put("[汗]", R.drawable.d_han);
        emoMap.put("[害羞]", R.drawable.d_haixiu);
        emoMap.put("[睡觉]", R.drawable.d_shuijiao);
        emoMap.put("[钱]", R.drawable.d_qian);
        emoMap.put("[偷笑]", R.drawable.d_touxiao);
        emoMap.put("[笑cry]", R.drawable.d_xiaoku);
        emoMap.put("[doge]", R.drawable.d_doge);
        emoMap.put("[喵喵]", R.drawable.d_miao);
        emoMap.put("[酷]", R.drawable.d_ku);
        emoMap.put("[衰]", R.drawable.d_shuai);
        emoMap.put("[闭嘴]", R.drawable.d_bizui);
        emoMap.put("[鄙视]", R.drawable.d_bishi);
        emoMap.put("[花心]", R.drawable.d_huaxin);
        emoMap.put("[鼓掌]", R.drawable.d_guzhang);
        emoMap.put("[悲伤]", R.drawable.d_beishang);
        emoMap.put("[思考]", R.drawable.d_sikao);
        emoMap.put("[生病]", R.drawable.d_shengbing);
        emoMap.put("[亲亲]", R.drawable.d_qinqin);
        emoMap.put("[怒骂]", R.drawable.d_numa);
        emoMap.put("[太开心]", R.drawable.d_taikaixin);
        emoMap.put("[懒得理你]", R.drawable.d_landelini);
        emoMap.put("[右哼哼]", R.drawable.d_youhengheng);
        emoMap.put("[左哼哼]", R.drawable.d_zuohengheng);
        emoMap.put("[嘘]", R.drawable.d_xu);
        emoMap.put("[委屈]", R.drawable.d_weiqu);
        emoMap.put("[吐]", R.drawable.d_tu);
        emoMap.put("[可怜]", R.drawable.d_kelian);
        emoMap.put("[打哈气]", R.drawable.d_dahaqi);
        emoMap.put("[挤眼]", R.drawable.d_jiyan);
        emoMap.put("[失望]", R.drawable.d_shiwang);
        emoMap.put("[顶]", R.drawable.d_ding);
        emoMap.put("[疑问]", R.drawable.d_yiwen);
        emoMap.put("[困]", R.drawable.d_kun);
        emoMap.put("[感冒]", R.drawable.d_ganmao);
        emoMap.put("[拜拜]", R.drawable.d_baibai);
        emoMap.put("[黑线]", R.drawable.d_heixian);
        emoMap.put("[阴险]", R.drawable.d_yinxian);
        emoMap.put("[打脸]", R.drawable.d_dalian);
        emoMap.put("[傻眼]", R.drawable.d_shayan);
        emoMap.put("[猪头]", R.drawable.d_zhutou);
        emoMap.put("[熊猫]", R.drawable.d_xiongmao);
        emoMap.put("[兔子]", R.drawable.d_tuzi);
    }

    public static int getImgByName(String name) {
        Integer integer = emoMap.get(name);
        return integer == null ? -1 : integer;
    }
}
