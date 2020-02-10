package com.licht.skinplugin.niebu.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.licht.skinplugin.R
import com.licht.skinplugin.niebu.SkinManager
import com.licht.skinplugin.niebu.core.ISkinView
import com.licht.skinplugin.niebu.mode.SkinAttrsBean

class SkinLinearLayout(context: Context?,attrs: AttributeSet? = null ,defStyleAttr: Int=0,defStyleRes:Int =0)
    :LinearLayout(context,attrs,defStyleAttr),ISkinView{
   var skinAttrsBean:SkinAttrsBean;
    init {
        skinAttrsBean = SkinAttrsBean();
        context?.let {
            val typedArray =
                it.obtainStyledAttributes(attrs, R.styleable.SkinLinearLayout)
            skinAttrsBean.saveSkinViewResource(typedArray,R.styleable.SkinLinearLayout)
            typedArray.recycle()
        }
    }

    override fun changeView() {
        val skinResourceId =
            skinAttrsBean.getSkinResource(R.styleable.SkinLinearLayout[R.styleable.SkinLinearLayout_android_background])
        if (skinResourceId>0){
            val backgroundOrSrc = SkinManager.get().getBackgroundOrSrc(skinResourceId)
            if (backgroundOrSrc is Int){
                setBackgroundColor(backgroundOrSrc)
            }else if (backgroundOrSrc is Drawable){
                background = backgroundOrSrc
            }
        }

    }

}