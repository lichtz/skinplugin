package com.licht.skinplugin.niebu.base

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatViewInflater
import androidx.core.view.LayoutInflaterCompat
import com.licht.skinplugin.niebu.SkinManager
import com.licht.skinplugin.niebu.core.ISkinView
import com.licht.skinplugin.niebu.core.SkinAppCompatViewInflater
import com.licht.skinplugin.niebu.utils.ActionBarUtils
import com.licht.skinplugin.niebu.utils.NavigationUtils
import com.licht.skinplugin.niebu.utils.StatusBarUtils

open class SkinControlBaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutInflater = LayoutInflater.from(this)
        LayoutInflaterCompat.setFactory2(layoutInflater,this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val skinAppCompatViewInflater = SkinAppCompatViewInflater( name, context, attrs)
        return skinAppCompatViewInflater.createSkinView()
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun loadSkin(skinId:String,skinPath:String? ,themeColor:Int){
        SkinManager.get().loaderSkinResources(skinId,skinPath);
        if (themeColor !=0){
            val color = SkinManager.get().getColor(themeColor)
            StatusBarUtils.forStatusBar(this,color)
            NavigationUtils.forNavigation(this,color)
            ActionBarUtils.forActionBar(this,color)
        }
        applySkinView(window.decorView)
    }

    fun  applySkinView(view: View){
        if (view is ISkinView){
            view.changeView()
        }
        if (view is ViewGroup){
            val childCount = view.childCount
            for (i in 0..(childCount-1)){
                applySkinView(view.getChildAt(i))
            }
        }
    }
}
