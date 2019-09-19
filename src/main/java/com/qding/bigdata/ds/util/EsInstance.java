package com.qding.bigdata.ds.util;

import java.net.InetSocketAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsInstance {

	private static TransportClient client;

	private final static String CONFIG_FILE="config.properties";
	public static TransportClient getInstance() {
		if (client != null) {
			return client;
		}

		Settings settings = Settings.builder()
				.put("cluster.name", PropertiesUtil.getPropertiesByKey(CONFIG_FILE, "es.cluster.name"))
				.put("client.transport.ping_timeout", "15s").put("client.transport.nodes_sampler_interval", "15s")
				.build();

		try {
			client = new PreBuiltTransportClient(settings);
			String[] hosts = PropertiesUtil.getPropertiesByKey(CONFIG_FILE, "es.cluster.hosts").split(",");
			for (String host : hosts) {
				client.addTransportAddress(new TransportAddress(new InetSocketAddress(host, Integer.valueOf(PropertiesUtil.getPropertiesByKey(CONFIG_FILE, "es.cluster.port")))));
			}

		} catch (Exception e) {
			e.printStackTrace();
			client = null;
		}
		return client;
	}

}
