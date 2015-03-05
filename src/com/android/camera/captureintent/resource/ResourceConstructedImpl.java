/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera.captureintent.resource;

import com.android.camera.FatalErrorHandler;
import com.android.camera.app.AppController;
import com.android.camera.app.LocationManager;
import com.android.camera.app.OrientationManager;
import com.android.camera.async.MainThread;
import com.android.camera.async.RefCountBase;
import com.android.camera.captureintent.CaptureIntentModuleUI;
import com.android.camera.one.OneCameraManager;
import com.android.camera.settings.CameraFacingSetting;
import com.android.camera.settings.ResolutionSetting;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;

public final class ResourceConstructedImpl implements ResourceConstructed {
    private final Intent mIntent;
    private final CaptureIntentModuleUI mModuleUI;
    private final MainThread mMainThread;
    private final Context mContext;
    private final OneCameraManager mCameraManager;
    private final LocationManager mLocationManager;
    private final OrientationManager mOrientationManager;
    private final CameraFacingSetting mCameraFacingSetting;
    private final ResolutionSetting mResolutionSetting;
    private final HandlerThread mCameraThread;
    private final Handler mCameraHandler;
    private final FatalErrorHandler mFatalErrorHandler;

    // TODO: Hope one day we could get rid of AppController.
    private final AppController mAppController;

    /**
     * Creates a reference counted {@link ResourceConstructedImpl} object.
     */
    public static RefCountBase<ResourceConstructed> create(
            Intent intent,
            CaptureIntentModuleUI moduleUI,
            MainThread mainThread,
            Context context,
            OneCameraManager cameraManager,
            LocationManager locationManager,
            OrientationManager orientationManager,
            CameraFacingSetting cameraFacingSetting,
            ResolutionSetting resolutionSetting,
            AppController appController,
            FatalErrorHandler fatalErrorHandler) {
        return new RefCountBase<ResourceConstructed>(new ResourceConstructedImpl(
                intent, moduleUI, mainThread, context, cameraManager, locationManager,
                orientationManager, cameraFacingSetting, resolutionSetting,
                appController, fatalErrorHandler));
    }

    private ResourceConstructedImpl(
            Intent intent,
            CaptureIntentModuleUI moduleUI,
            MainThread mainThread,
            Context context,
            OneCameraManager cameraManager,
            LocationManager locationManager,
            OrientationManager orientationManager,
            CameraFacingSetting cameraFacingSetting,
            ResolutionSetting resolutionSetting,
            AppController appController,
            FatalErrorHandler fatalErrorHandler) {
        mIntent = intent;
        mModuleUI = moduleUI;
        mMainThread = mainThread;
        mContext = context;
        mCameraManager = cameraManager;
        mLocationManager = locationManager;
        mOrientationManager = orientationManager;
        mCameraFacingSetting = cameraFacingSetting;
        mResolutionSetting = resolutionSetting;
        mFatalErrorHandler = fatalErrorHandler;
        mAppController = appController;

        mCameraThread = new HandlerThread("ImageCaptureIntentModule.CameraHandler");
        mCameraThread.start();
        mCameraHandler = new Handler(mCameraThread.getLooper());
    }

    @Override
    public void close() {
        mCameraThread.quit();
    }

    @Override
    public Intent getIntent() {
        return mIntent;
    }

    @Override
    public CaptureIntentModuleUI getModuleUI() {
        return mModuleUI;
    }

    @Override
    public MainThread getMainThread() {
        return mMainThread;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public OneCameraManager getCameraManager() {
        return mCameraManager;
    }

    @Override
    public LocationManager getLocationManager() {
        return mLocationManager;
    }

    @Override
    public OrientationManager getOrientationManager() {
        return mOrientationManager;
    }

    @Override
    public CameraFacingSetting getCameraFacingSetting() {
        return mCameraFacingSetting;
    }

    @Override
    public ResolutionSetting getResolutionSetting() {
        return mResolutionSetting;
    }

    @Override
    public Handler getCameraHandler() {
        return mCameraHandler;
    }

    @Override
    public AppController getAppController() {
        return mAppController;
    }

    @Override
    public FatalErrorHandler getFatalErrorHandler() {
        return mFatalErrorHandler;
    }
}
