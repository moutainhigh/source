package com.dchip.cloudparking.api.utils;


import java.util.HashMap;


@SuppressWarnings({"serial", "rawtypes","unchecked"})
public class MapKit extends HashMap {
	
	public static MapKit create() {
		return new MapKit();
	}

	public MapKit set(Object key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public MapKit delete(Object key) {
		super.remove(key);
		return this;
	}

}
