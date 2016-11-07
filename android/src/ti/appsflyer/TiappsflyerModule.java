/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.appsflyer;

import java.util.HashMap;
import java.util.Map;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.google.android.gms.common.GooglePlayServicesUtil;

@Kroll.module(name = "Tiappsflyer", id = "ti.appsflyer")
public class TiappsflyerModule extends KrollModule {

	private static final String LCAT = "AppsFlyer >>>>>";
	String appId;
	String appUserId;
	String devKey;
	AppsFlyerLib instance;
	TiApplication ctx = TiApplication.getInstance();
	String deviceId;
	static Boolean optOut = false;
	
	// Response from isGooglePlayServicesAvailable()
	@Kroll.constant public static final int SUCCESS = 0;
	@Kroll.constant public static final int SERVICE_MISSING = 1;
	@Kroll.constant public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
	@Kroll.constant public static final int SERVICE_DISABLED = 3;
	@Kroll.constant public static final int SERVICE_INVALID = 9;

	public TiappsflyerModule() {
		super();
		this.instance = AppsFlyerLib.getInstance();
		Log.i(LCAT, "AppsFlyer module initiated");
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.i(LCAT, "onAppCreate started");
	}


	@Kroll.method
	public void startTracker(String appId, String devKey) {

		Log.i(LCAT, "AppsFlyer start tracker");

		this.appId = appId;
		this.devKey = devKey;
		instance.setAppId(appId);
		instance.startTracking(ctx, devKey);
		
		Log.i(LCAT, "AppsFlyer App Id: " + this.appId);
		Log.i(LCAT, "AppsFlyer Dev Key: " + this.devKey);
		
		
		// provide a way for the user to opt out sending Android Id and IEMI
		if(!optOut)
		{		
			@SuppressWarnings("static-access")
			TelephonyManager tm = (TelephonyManager) ctx.getSystemService(ctx.TELEPHONY_SERVICE);
			String deviceId = tm.getDeviceId();
			String androidId = Secure.getString(ctx.getContentResolver(),Secure.ANDROID_ID);
			
			AppsFlyerLib.getInstance().setImeiData(androidId);
			AppsFlyerLib.getInstance().setAndroidIdData(deviceId);
			
			Log.i(LCAT, "ANDROID ID: " + androidId);
			Log.i(LCAT, "DEVICE ID: " + deviceId);
		}
	}	

	@Kroll.method
	public void setCustomerUserId(String customerId) {
		instance.setCustomerUserId(customerId);
		Log.i(LCAT, "AppsFlyer Customer Id set: " + customerId);
	}

	@Kroll.method
	public void trackAppLaunch() {
		instance.trackAppLaunch(ctx, devKey);
		Log.i(LCAT, "AppsFlyer App Launch Tracked");
	}

	@Kroll.method
	public void trackPurchase(KrollDict dict) {
			
		if (dict != null) {
			
			Map<String, Object> eventValue = new HashMap<String, Object>();
			eventValue.put(AFInAppEventParameterName.REVENUE, dict.get("amount"));
			eventValue.put(AFInAppEventParameterName.CONTENT_TYPE,dict.get("service"));
			eventValue.put(AFInAppEventParameterName.CURRENCY,"USD");
			AppsFlyerLib.getInstance().trackEvent(ctx,AFInAppEventType.PURCHASE,eventValue);
			
		}
	}
	
	@Kroll.getProperty @Kroll.method
	public static Boolean getOptOut() {
	    return optOut;
	}
	 
	@Kroll.setProperty @Kroll.method
	public void setOptOut(Boolean oo) {
	    Log.d(LCAT, "Tried setting clientId to: " + oo);
		optOut = oo;
	}
	
	@Kroll.method
	public int isGooglePlayServicesAvailable() {
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(TiApplication.getAppRootOrCurrentActivity());
	}
}