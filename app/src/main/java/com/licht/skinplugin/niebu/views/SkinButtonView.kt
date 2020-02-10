package com.licht.skinplugin.niebu.views

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.licht.skinplugin.R
import com.licht.skinplugin.niebu.SkinManager
import com.licht.skinplugin.niebu.core.ISkinView
import com.licht.skinplugin.niebu.mode.SkinAttrsBean

class SkinButtonView(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr), ISkinView {
    var skinAttrsBean: SkinAttrsBean? = null

    init {
        context?.let {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SkinButtonView, defStyleAttr, 0);
            skinAttrsBean = SkinAttrsBean();
            skinAttrsBean!!.saveSkinViewResource(typedArray, R.styleable.SkinButtonView)
            typedArray.recycle()
        }
    }

    override fun changeView() {
        val bg = R.styleable.SkinButtonView[R.styleable.SkinButtonView_android_background]
        skinAttrsBean?.let {
            val skinResourceId = it.getSkinResource(bg)
            if (skinResourceId > 0) {
                val bg = SkinManager.get().getBackgroundOrSrc(skinResourceId)
                if (bg is Int) {
                    setBackgroundColor(bg)
                } else if (bg is Drawable) {
                    background = bg
                }
            }
        }

        val tid = R.styleable.SkinButtonView[R.styleable.SkinButtonView_android_textColor]
        skinAttrsBean?.let {
            val skinResource = it.getSkinResource(tid)
            if (skinResource <= 0) {
                return
            }
            val color = SkinManager.get().getColorStateList(skinResource)
            setTextColor(color)
        }


        val typeFace = SkinManager.get()
            .getTypeFace()
        if (typeFace != null){
            setTypeface(typeFace)
        }else{
            setTypeface(Typeface.DEFAULT)
        }
    }


}