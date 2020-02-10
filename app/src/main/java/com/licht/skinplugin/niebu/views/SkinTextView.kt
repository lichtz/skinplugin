package com.licht.skinplugin.niebu.views

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.licht.skinplugin.R
import com.licht.skinplugin.niebu.SkinManager
import com.licht.skinplugin.niebu.core.ISkinView
import com.licht.skinplugin.niebu.mode.SkinAttrsBean

class SkinTextView(context: Context?,attrs: AttributeSet? = null,defStyleAttr: Int =android.R.attr.textViewStyle) :AppCompatTextView(context,
    attrs,defStyleAttr) ,ISkinView{
    var skinAttrsBean:SkinAttrsBean?= null


     init {
         if (context != null && attrs != null){
              skinAttrsBean = SkinAttrsBean()
             val typedArray =
                 context.obtainStyledAttributes(attrs, R.styleable.SkinTextView, defStyleAttr, 0)
              skinAttrsBean!!.saveSkinViewResource(typedArray,R.styleable.SkinTextView)
             typedArray.recycle()
         }

     }

    override fun changeView() {
        if (skinAttrsBean == null){
            return
        }

        val backgroundId =SkinManager.get().getBackgroundOrSrc(skinAttrsBean!!.getSkinResource(R.styleable.SkinTextView[R.styleable.SkinTextView_android_background])) ;

        val textColorId =SkinManager.get().getColor(skinAttrsBean!!.getSkinResource(R.styleable.SkinTextView[R.styleable.SkinTextView_android_textColor]))

            if (backgroundId is Int){
                setBackgroundColor(backgroundId)
            }else if (backgroundId is Drawable){
                background = backgroundId
            }

            setTextColor(textColorId)


        val typeFace = SkinManager.get()
            .getTypeFace()
            if (typeFace != null){
                setTypeface(typeFace)
            }else{
                setTypeface(Typeface.DEFAULT)
            }
    }
}