package com.melon.mylibrary.util;

import android.content.Context;
import android.content.res.Resources;

import com.melon.mylibrary.R;

/**
 * 广告过滤
 * @author melon.wang
 * @date 2019/1/11 16:20
 * Email 530274554@qq.com
 */
public class AdFilterTool {
    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for (int i = 0; i < adDivs.length; i++) {
            js += "var adDiv" + i + "= document.getElementById('" + adDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        return js;
    }
}
