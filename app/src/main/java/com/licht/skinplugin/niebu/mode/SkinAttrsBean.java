package com.licht.skinplugin.niebu.mode;

import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseIntArray;

public class SkinAttrsBean {
    private SparseIntArray skinResourcesMap;

    public SkinAttrsBean( ) {
        this.skinResourcesMap = new SparseIntArray();
    }

    public void saveSkinViewResource(TypedArray typedArray,int[] styleable){
        for (int i = 0; i < typedArray.length(); i++) {
            int key = styleable[i];
            int resourceId = typedArray.getResourceId(i, -1);
            skinResourcesMap.put(key,resourceId);

        }
    }
    public int getSkinResource(int styleable){
        return skinResourcesMap.get(styleable);
    }
}
