package com.hust.models;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.hust.cons.ConstFieldValue;

public class Connect {

	@SuppressWarnings("resource")
	public static Client getClient() {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", ConstFieldValue.CLUSTER).build();
		return new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						ConstFieldValue.HOST, ConstFieldValue.POST));
	}

}
