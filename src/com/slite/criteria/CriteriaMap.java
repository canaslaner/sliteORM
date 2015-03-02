package com.slite.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class CriteriaMap {

	private static CriteriaMap criteriaMap;
	private static HashMap<String, Object> map;

	private CriteriaMap() {

	}

	public static CriteriaMap getInstance() {
		if (criteriaMap == null) {
			criteriaMap = new CriteriaMap();
		}
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return criteriaMap;
	}

	public CriteriaMap addCriteria(String key, Object value) {
		map.put(key, value);
		return criteriaMap;
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Collection<Object> values() {
		return map.values();
	}
}
