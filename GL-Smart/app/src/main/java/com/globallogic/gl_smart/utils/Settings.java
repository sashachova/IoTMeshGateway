package com.globallogic.gl_smart.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.globallogic.gl_smart.App;
import com.globallogic.gl_smart.R;


/**
 * @author eugenii.samarskyi.
 */
public class Settings {

	public static final Settings self = new Settings();

	private SharedPreferences settingsPref;

	private Settings() {
		settingsPref = App.getPreferences();
	}

	public String getGateway() {
		return settingsPref.getString("gateway", App.self().getString(R.string.server_address));
	}

	public void setGateway(String gateway) {
		settingsPref.edit().putString("gateway", gateway).apply();
	}

	public boolean isConfigured() {
		return !TextUtils.isEmpty(getGateway());
	}

	public void setOffline(boolean online) {
		settingsPref.edit().putBoolean("offline", online).apply();
	}

	public boolean isOffline() {
		return settingsPref.getBoolean("offline", false);
	}
}
