package com.globallogic.gl_smart.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.globallogic.gl_smart.App;
import com.globallogic.gl_smart.BuildConfig;
import com.globallogic.gl_smart.model.mqtt.StatusMessage;
import com.globallogic.gl_smart.model.mqtt.Topic;
import com.globallogic.gl_smart.model.type.MessageType;
import com.globallogic.gl_smart.ui.GatewayCallback;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author eugenii.samarskyi.
 */
public class GatewayFragment extends NodeFragment {

	private static final String TAG = GatewayFragment.class.getSimpleName();

	public static Fragment newInstance() {
		return new GatewayFragment();
	}

	private GatewayCallback mGatewayCallback;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (BuildConfig.BUILD_TYPE.equals("offlane")) {
			App.getHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					try {
						messageArrived(gwStatus, new MqttMessage(status.getBytes()));
					} catch (Exception e) {
						Log.e(TAG, "run: " + e.getLocalizedMessage());
					}
				}
			}, 2000);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		mGatewayCallback = (GatewayCallback) context;

	}

	@Override
	protected String[] getTopics() {
		String[] topics = new String[1];
		topics[0] = mTopic == null ? new Topic.Builder()
				.gatewayId("+")
				.type(MessageType.Status)
				.build().topic : mTopic.topic;

		return topics;
	}

	@Override
	protected void onStatus(Topic topic, StatusMessage message) {
		super.onStatus(topic, message);

		mGatewayCallback.onGateway(topic.gateway());

		mToolbar.setTitle(topic.gateway());
		mToolbar.setSubtitle(message.status);
	}

	public static final String property = "000000000000";
	public static final String gwStatus = "A000000000000001/status";
	public static final String pluginStatus = "A000000000000001/nestPlugin/status";
	public static final String sensorStatus = "A000000000000001/nestPlugin/nestThermostat/status";

	public static final String status = "{\n" +
			"\t\"status\": \"online\",\n" +
			"\t\"data\": [{\n" +
			"\t\t\"name\": \"SSID\",\n" +
			"\t\t\"type\": \"string\",\n" +
			"\t\t\"descr\": \"WiFi Name\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": \"VCH-Simulator\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"SSIDPassword\",\n" +
			"\t\t\"type\": \"string\",\n" +
			"\t\t\"descr\": \"WiFi Password\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": \"12345678\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"GWID\",\n" +
			"\t\t\"type\": \"string\",\n" +
			"\t\t\"descr\": \"Gateway ID\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": \"A000000000000777\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"MQTTAddr\",\n" +
			"\t\t\"type\": \"string\",\n" +
			"\t\t\"descr\": \"MQTT Broker Address\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": \"172.24.254.20\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"restart\",\n" +
			"\t\t\"type\": \"int\",\n" +
			"\t\t\"descr\": \"Restart\",\n" +
			"\t\t\"lim_type\": \"range\",\n" +
			"\t\t\"lim_json\": [\n" +
			"\t\t\t0,\n" +
			"\t\t\t1\n" +
			"\t\t],\n" +
			"\t\t\"default\": \"0\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"power\",\n" +
			"\t\t\"type\": \"string\",\n" +
			"\t\t\"descr\": \"LED power\",\n" +
			"\t\t\"lim_type\": \"enum\",\n" +
			"\t\t\"lim_json\": [\n" +
			"\t\t\t\"on\",\n" +
			"\t\t\t\"off\"\n" +
			"\t\t],\n" +
			"\t\t\"default\": \"off\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"temperature\",\n" +
			"\t\t\"descr\": \"Temperature, C\",\n" +
			"\t\t\"type\": \"float\",\n" +
			"\t\t\"lim_type\": \"range\",\n" +
			"\t\t\"lim_json\": [\n" +
			"\t\t\t-20.12,\n" +
			"\t\t\t99.8\n" +
			"\t\t],\n" +
			"\t\t\"default\": \"10.0\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"ip\",\n" +
			"\t\t\"descr\": \"Ip Address\",\n" +
			"\t\t\"type\": \"string\",\n" +
			"\t\t\"lim_type\": \"regexp\",\n" +
			"\t\t\"lim_json\": [\n" +
			"\t\t\t\"([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3})[.]([0-9]{1,3})\"\n" +
			"\t\t],\n" +
			"\t\t\"default\": \"10.35.0.2\",\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"IsStreaming\",\n" +
			"\t\t\"descr\": \"Show picture\",\n" +
			"\t\t\"type\": \"boolean\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": true,\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"BuildNumber\",\n" +
			"\t\t\"descr\": \"Current build number\",\n" +
			"\t\t\"type\": \"int\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": 166,\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"Version number\",\n" +
			"\t\t\"descr\": \"Current version number\",\n" +
			"\t\t\"type\": \"float\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": 0.12,\n" +
			"\t\t\"rw\": \"rw\"\n" +
			"\t}, {\n" +
			"\t\t\"name\": \"Host address\",\n" +
			"\t\t\"type\": \"url\",\n" +
			"\t\t\"descr\": \"Address\",\n" +
			"\t\t\"lim_type\": null,\n" +
			"\t\t\"lim_json\": null,\n" +
			"\t\t\"default\": \"http://www.google.com\",\n" +
			"\t\t\"rw\": \"r\"\n" +
			"\t}],\n" +
			"\t\"time\": 1234123412,\n" +
			"\t\"err\": \"\"\n" +
			"}";
}
