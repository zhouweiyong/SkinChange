package com.vst.skinchange;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by zwy on 2017/9/22.
 * email:16681805@qq.com
 */

public class ResourcesManager {
    private Resources mResources;
    private String mPkgName;

    public ResourcesManager(Resources mResources, String mPkgName) {
        this.mResources = mResources;
        this.mPkgName = mPkgName;
    }

    public Drawable getDrawableByName(String name) {
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPkgName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByName(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPkgName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
