package com.downfall.caterplanner.rest;

import androidx.annotation.NonNull;

import com.downfall.caterplanner.SingletonContainer;
import com.downfall.caterplanner.rest.service.DetailPlansService;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

public class DetailPlansControllerModule extends ReactContextBaseJavaModule {

    private DetailPlansService detailPlansService;

    public DetailPlansControllerModule(ReactApplicationContext reactContext){
        super(reactContext);
        detailPlansService = SingletonContainer.get(DetailPlansService.class);
    }

    @NonNull
    @Override
    public String getName() {
        return "DetailPlansController";
    }

    @ReactMethod
    public void create(ReadableArray readableDetailPlans, String authorName, Integer authorId, Integer baseId, Promise promise){
        try{
            promise.resolve(detailPlansService.createByReact(readableDetailPlans, authorName, authorId, baseId));
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void update(Integer detailPlanHeaderId, ReadableArray readableDetailPlans, Promise promise){
        try{
            detailPlansService.updateByReact(detailPlanHeaderId, readableDetailPlans);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void readByReact(Integer detailPlanHeaderId, Promise promise){
        try{
            promise.resolve(detailPlansService.readByReact(detailPlanHeaderId));
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void delete(Integer detailPlanHeaderId, Promise promise){
        try{
            detailPlansService.deleteByReact(detailPlanHeaderId);
        }catch (Exception e){
            promise.reject(e);
        }
    }

}
