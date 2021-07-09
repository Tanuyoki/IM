package com.yoki.im.tools.im.iminput.emoji;

import android.util.ArrayMap;

import com.yoki.im.R;

public class EmotionUtils {
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP = new ArrayMap<>();
    public static final int EMOTION_CLASSIC_TYPE = 1;
    public static ArrayMap<String, Integer> EMPTY_MAP = new ArrayMap<>();

    static {
        EMOTION_CLASSIC_MAP.put("[呵呵]", Integer.valueOf((int) R.mipmap.d_hehe));
        EMOTION_CLASSIC_MAP.put("[嘻嘻]", Integer.valueOf((int) R.mipmap.d_xixi));
        EMOTION_CLASSIC_MAP.put("[哈哈]", Integer.valueOf((int) R.mipmap.d_haha));
        EMOTION_CLASSIC_MAP.put("[爱你]", Integer.valueOf((int) R.mipmap.d_aini));
        EMOTION_CLASSIC_MAP.put("[挖鼻屎]", Integer.valueOf((int) R.mipmap.d_wabishi));
        EMOTION_CLASSIC_MAP.put("[吃惊]", Integer.valueOf((int) R.mipmap.d_chijing));
        EMOTION_CLASSIC_MAP.put("[晕]", Integer.valueOf((int) R.mipmap.d_yun));
        EMOTION_CLASSIC_MAP.put("[泪]", Integer.valueOf((int) R.mipmap.d_lei));
        EMOTION_CLASSIC_MAP.put("[馋嘴]", Integer.valueOf((int) R.mipmap.d_chanzui));
        EMOTION_CLASSIC_MAP.put("[抓狂]", Integer.valueOf((int) R.mipmap.d_zhuakuang));
        EMOTION_CLASSIC_MAP.put("[哼]", Integer.valueOf((int) R.mipmap.d_heng));
        EMOTION_CLASSIC_MAP.put("[可爱]", Integer.valueOf((int) R.mipmap.d_keai));
        EMOTION_CLASSIC_MAP.put("[怒]", Integer.valueOf((int) R.mipmap.d_nu));
        EMOTION_CLASSIC_MAP.put("[汗]", Integer.valueOf((int) R.mipmap.d_han));
        EMOTION_CLASSIC_MAP.put("[害羞]", Integer.valueOf((int) R.mipmap.d_haixiu));
        EMOTION_CLASSIC_MAP.put("[睡觉]", Integer.valueOf((int) R.mipmap.d_shuijiao));
        EMOTION_CLASSIC_MAP.put("[钱]", Integer.valueOf((int) R.mipmap.d_qian));
        EMOTION_CLASSIC_MAP.put("[偷笑]", Integer.valueOf((int) R.mipmap.d_touxiao));
        EMOTION_CLASSIC_MAP.put("[笑cry]", Integer.valueOf((int) R.mipmap.d_xiaoku));
        EMOTION_CLASSIC_MAP.put("[doge]", Integer.valueOf((int) R.mipmap.d_doge));
        EMOTION_CLASSIC_MAP.put("[喵喵]", Integer.valueOf((int) R.mipmap.d_miao));
        EMOTION_CLASSIC_MAP.put("[酷]", Integer.valueOf((int) R.mipmap.d_ku));
        EMOTION_CLASSIC_MAP.put("[衰]", Integer.valueOf((int) R.mipmap.d_shuai));
        EMOTION_CLASSIC_MAP.put("[闭嘴]", Integer.valueOf((int) R.mipmap.d_bizui));
        EMOTION_CLASSIC_MAP.put("[鄙视]", Integer.valueOf((int) R.mipmap.d_bishi));
        EMOTION_CLASSIC_MAP.put("[花心]", Integer.valueOf((int) R.mipmap.d_huaxin));
        EMOTION_CLASSIC_MAP.put("[鼓掌]", Integer.valueOf((int) R.mipmap.d_guzhang));
        EMOTION_CLASSIC_MAP.put("[悲伤]", Integer.valueOf((int) R.mipmap.d_beishang));
        EMOTION_CLASSIC_MAP.put("[思考]", Integer.valueOf((int) R.mipmap.d_sikao));
        EMOTION_CLASSIC_MAP.put("[生病]", Integer.valueOf((int) R.mipmap.d_shengbing));
        EMOTION_CLASSIC_MAP.put("[亲亲]", Integer.valueOf((int) R.mipmap.d_qinqin));
        EMOTION_CLASSIC_MAP.put("[怒骂]", Integer.valueOf((int) R.mipmap.d_numa));
        EMOTION_CLASSIC_MAP.put("[太开心]", Integer.valueOf((int) R.mipmap.d_taikaixin));
        EMOTION_CLASSIC_MAP.put("[懒得理你]", Integer.valueOf((int) R.mipmap.d_landelini));
        EMOTION_CLASSIC_MAP.put("[右哼哼]", Integer.valueOf((int) R.mipmap.d_youhengheng));
        EMOTION_CLASSIC_MAP.put("[左哼哼]", Integer.valueOf((int) R.mipmap.d_zuohengheng));
        EMOTION_CLASSIC_MAP.put("[嘘]", Integer.valueOf((int) R.mipmap.d_xu));
        EMOTION_CLASSIC_MAP.put("[委屈]", Integer.valueOf((int) R.mipmap.d_weiqu));
        EMOTION_CLASSIC_MAP.put("[吐]", Integer.valueOf((int) R.mipmap.d_tu));
        EMOTION_CLASSIC_MAP.put("[可怜]", Integer.valueOf((int) R.mipmap.d_kelian));
        EMOTION_CLASSIC_MAP.put("[打哈气]", Integer.valueOf((int) R.mipmap.d_dahaqi));
        EMOTION_CLASSIC_MAP.put("[挤眼]", Integer.valueOf((int) R.mipmap.d_jiyan));
        EMOTION_CLASSIC_MAP.put("[失望]", Integer.valueOf((int) R.mipmap.d_shiwang));
        EMOTION_CLASSIC_MAP.put("[顶]", Integer.valueOf((int) R.mipmap.d_ding));
        EMOTION_CLASSIC_MAP.put("[疑问]", Integer.valueOf((int) R.mipmap.d_yiwen));
        EMOTION_CLASSIC_MAP.put("[困]", Integer.valueOf((int) R.mipmap.d_kun));
        EMOTION_CLASSIC_MAP.put("[感冒]", Integer.valueOf((int) R.mipmap.d_ganmao));
        EMOTION_CLASSIC_MAP.put("[拜拜]", Integer.valueOf((int) R.mipmap.d_baibai));
        EMOTION_CLASSIC_MAP.put("[黑线]", Integer.valueOf((int) R.mipmap.d_heixian));
        EMOTION_CLASSIC_MAP.put("[阴险]", Integer.valueOf((int) R.mipmap.d_yinxian));
        EMOTION_CLASSIC_MAP.put("[打脸]", Integer.valueOf((int) R.mipmap.d_dalian));
        EMOTION_CLASSIC_MAP.put("[傻眼]", Integer.valueOf((int) R.mipmap.d_shayan));
        EMOTION_CLASSIC_MAP.put("[猪头]", Integer.valueOf((int) R.mipmap.d_zhutou));
        EMOTION_CLASSIC_MAP.put("[熊猫]", Integer.valueOf((int) R.mipmap.d_xiongmao));
        EMOTION_CLASSIC_MAP.put("[兔子]", Integer.valueOf((int) R.mipmap.d_tuzi));
    }

    public static int getImgByName(int EmotionType, String imgName) {
        Integer integer = null;
        switch (EmotionType) {
            case 1:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
        }
        if (integer == null) {
            return -1;
        }
        return integer.intValue();
    }

    public static ArrayMap<String, Integer> getEmojiMap(int EmotionType) {
        switch (EmotionType) {
            case 1:
                return EMOTION_CLASSIC_MAP;
            default:
                return EMPTY_MAP;
        }
    }
}
