package com.licht.skinplugin.niebu

import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import com.licht.skinplugin.R
import com.licht.skinplugin.niebu.mode.SkinCache
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap


class SkinManager private constructor() {
    private val ADD_ASSET_PATH: String = "addAssetPath"; // 方法名
    private val defaultSkinId = "100000";
    private var currentSkinId = "100000"
    private var currentSkinResources: Resources? = null;
    private var currentSkinPackageName: String? = null;
    private var skinCacheMap: HashMap<String, SkinCache> = HashMap();

    companion object {
        private var application: Application?=null
        fun initSkin(application: Application){
            this.application = application;
        }
        private var instance: SkinManager? = null

        @Synchronized
        fun get(): SkinManager {
            if (instance == null) {
                instance = SkinManager()
            }
            return instance!!
        }
    }

    fun loaderSkinResources(skinId: String, path: String?) {
        if (TextUtils.isEmpty(path)) {
            currentSkinId = defaultSkinId;
            return
        }
        if (skinCacheMap.containsKey(path)) {
            val skinCache = skinCacheMap[path]
            skinCache?.let {
                currentSkinId = skinCache.skinId
                currentSkinResources = skinCache.skinResources;
                currentSkinPackageName = skinCache.skinPackageName;
                return
            }
        }
        try {
            /**
             * Kotlin 反射
             */
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath =
                assetManager.javaClass.getDeclaredMethod(ADD_ASSET_PATH, String::class.java)
            addAssetPath.isAccessible = true;
            addAssetPath.invoke(assetManager, path);
            currentSkinResources = Resources(
                assetManager, application!!.resources.displayMetrics,
                application!!.resources.configuration
            )
            currentSkinPackageName = application!!.packageManager.getPackageArchiveInfo(
                path,
                PackageManager.GET_ACTIVITIES
            ).packageName;
            if (!currentSkinPackageName.isNullOrEmpty()) {
                skinCacheMap.put(
                    path!!,
                    SkinCache(skinId, currentSkinResources!!, currentSkinPackageName!!)
                );
                currentSkinId = skinId;
            }
        } catch (e: Exception) {

        }


    }

    private fun getSkinResourceIds(resourceId: Int): Int {
        if (defaultSkinId.equals(currentSkinId) || currentSkinResources == null) {
            return resourceId;
        }
        val resourceEntryName = application!!.resources.getResourceEntryName(resourceId)
        val resourceTypeName = application!!.resources.getResourceTypeName(resourceId)
        val resourceName = application!!.resources.getResourceName(resourceId)
        val resourcePackageName = application!!.resources.getResourcePackageName(resourceId)
        Log.i("getSkinResourceIds","$resourceEntryName  $resourceTypeName   $resourceName   $resourcePackageName")
        val skinResourceId = currentSkinResources!!.getIdentifier(
            resourceEntryName,
            resourceTypeName,
            currentSkinPackageName
        )
        if (skinResourceId == 0) {
            return resourceId
        } else {
            return skinResourceId
        }
    }

    fun isDefaultSkin(): Boolean {
        return (defaultSkinId == currentSkinId) || (currentSkinResources == null)
    }

    fun getColor(resourceId: Int): Int {
        val skinResourceIds = getSkinResourceIds(resourceId)
        return if (isDefaultSkin()) {
            application!!.resources.getColor(skinResourceIds)
        } else {
            currentSkinResources!!.getColor(skinResourceIds)
        }

    }

    fun getColorStateList(resourceId: Int): ColorStateList {
        val skinResourceIds = getSkinResourceIds(resourceId)
        return if (isDefaultSkin()) {
            application!!.resources.getColorStateList(skinResourceIds)
        } else {
            currentSkinResources!!.getColorStateList(skinResourceIds)
        }
    }

    // （待测）mipmap和drawable统一用法
    fun  getDrawableOrMipMap(resourceId:Int): Drawable {
      val skinResourceIds = getSkinResourceIds(resourceId)
      return if (isDefaultSkin()) {
          application!!.resources.getDrawable(skinResourceIds)
      } else {
          currentSkinResources!!.getDrawable(skinResourceIds)
      }
    }
    fun getString(resourceId: Int): String {
        val skinResourceIds = getSkinResourceIds(resourceId)
        return if (isDefaultSkin()) {
            application!!.resources.getString(skinResourceIds)
        } else {
            currentSkinResources!!.getString(skinResourceIds)
        }
    }

    fun getBackgroundOrSrc(resourceId: Int): Any? {
        val resourceTypeName = application!!.resources.getResourceTypeName(resourceId)
        when (resourceTypeName){
            "color"-> return getColor(resourceId)
             "mipmap" ,  "drawable"-> return getDrawableOrMipMap(resourceId)
        }
        return null
    }

    fun  getTypeFace():Typeface?{
        val skinResourceIds = getSkinResourceIds(R.string.custom_typeface_string)
        val skinTypefacePath = getString(skinResourceIds)
        if (TextUtils.isEmpty(skinTypefacePath)){
            return Typeface.DEFAULT;
        }
        return if (isDefaultSkin()){
            Typeface.DEFAULT
        }else{
            Typeface.createFromAsset(currentSkinResources!!.assets,skinTypefacePath)
        }
    }
}