package com.jtchen.beans;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 16:24
 */
public class Pair<K, V> {
	private K key;
	private V value;


	public boolean isEmpry() {
		return key == null && value == null;
	}


	public void init() {
		key = null;
		value = null;
	}

	public Pair() {
		init();
	}


	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Pair{");
		sb.append("key='").append(key).append('\'');
		sb.append(", value=").append(value);
		sb.append('}');
		return sb.toString();
	}
}
