package com.vst.skinchange;

import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过定义资源名称来换肤
 * 比如需要换肤的资源统一以skin开头
 * 打开每个页面遍历一遍整个页面的控件，如果发现资源名称以skin开头，则说明该资源需要换肤
 * 插件换肤，插件中的资源名称需和原被换资源名称一致
 * 应用内换肤，可以使用后缀来定义换肤资源
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private Button btn1;
    private Button btn2;
    private String mSkinPluginPath = Environment.getExternalStorageDirectory() + File.separator + "skipplugin.apk";
    private String mSkinPluginPkg = "com.vst.skipplugin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                loadPlugin("plugin_bg1", "pluginColor1");
                break;
            case R.id.btn2:
                loadPlugin("plugin_bg2", "pluginColor2");
                break;
        }
    }

    private void loadPlugin(String drawableName, String colorName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, mSkinPluginPath);
            Resources superResources = getResources();
            Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
            ResourcesManager resourcesManager = new ResourcesManager(resources, mSkinPluginPkg);
            Drawable bgDrawable = resourcesManager.getDrawableByName(drawableName);
            ColorStateList color = resourcesManager.getColorByName(colorName);
            btn1.setTextColor(color);
            btn2.setTextColor(color);
            if (bgDrawable != null)
                iv.setImageDrawable(bgDrawable);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
