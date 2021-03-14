package com.jtchen.json.impl;

import com.jtchen.json.AbstractJSON;

import java.util.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/10 19:43
 */
public class LinkedJSON extends AbstractJSON {
	/*
	采用多级双端链表思路完成JSON对象的数据结构实现
	采用尾巴插入法来实现
	JSON的每一个层级就是双端链表的每个层级
	如下JSON
	{
  		"name": "Jack (\"Bee\") Nimble",
  		"format": {
    		"width": 1920.0,
    		"frame rate": 24.0,
    		"interlace": false,
    		"height": 1080.0
  		}
	}
	数据结构表示为
	"node"(head)----->"Jack (\"Bee\") Nimble"
		|
	"format"(tail)--->"width"(head)--------->1920.0
							|
					  "frame rate"---------->24.0
							|
					  "interlace"----------->false
							|
					  "height"(tail)-------->1080.0
	 */
	// JNode为自定义数据结构
	private JNode head;
	private JNode tail;
	private int size;

	public LinkedJSON() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public boolean containsKey(Object key) {
		return find(key) != null;
	}

	// 待完善...
	@Override
	public boolean containsValue(Object value) {
		JNode cur = head;

		while (cur != null) {
			if (Objects.equals(value, cur.child.value.toString()) || Objects.equals(value, cur.child.value))
				return true;
			cur = cur.next;
		}
		return false;
	}

	@Override
	public Object get(Object key) {
		JNode node = find(key);

		// 如果不存在key返回null
		if (node == null) return null;

			// 如果存在key, 则返回对应的值
		else return node.child.value;
	}

	@Override
	public Object put(String key, Object value) {
		if (isEmpty()) {
			head = new JNode(key);
			head.child = new JNode(value);
			tail = head;

			// 更新长度
			this.size++;
			return null;
		} else {
			JNode node = find(key);
			if (node != null) {
				JNode tmp = node.child;

				// 赋为新值
				node.child = new JNode(value);

				return tmp.value;
			} else {
				// 更新新的key为尾节点之下一个节点
				tail.next = new JNode(key);

				// 更新尾节点
				tail = tail.next;

				// 更新尾节点对应的值
				tail.child = new JNode(value);

				// 更新长度
				this.size++;
				return null;
			}
		}
	}


	/**
	 * 通过key找到相应的JNode
	 */
	private JNode find(Object key) {
		JNode cur = head;
		while (cur != null) {
			String tmp = (String) cur.value;
			if (tmp.equals(key) || tmp.equals(key.toString())) return cur;
			cur = cur.next;
		}

		// 找不到返回null
		return null;
	}

	@Override
	public Object remove(Object key) {
		// 删除只需把相应JNode抹去
		// 垃圾回收器自然会进行回收
		JNode cur = find(key);
		if (cur == null) return null;

		if (cur == head) {
			head = head.next;
			head.prev = null;
		} else if (cur == tail) {
			cur = cur.prev;
			cur.next = null;
		} else {
			JNode prev = cur.prev;
			JNode next = cur.next;

			prev.next = next;
			next.prev = prev;
		}

		// 更新长度
		size--;

		// 返回被删除元素的值
		return cur.value;
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	@Override
	public Set<String> keySet() {
		HashSet<String> set = new HashSet<>();

		JNode cur = head;
		while (cur != null) {
			set.add((String) cur.value);

			cur = cur.next;
		}

		return set;
	}

	@Override
	public Collection<Object> values() {
		ArrayList<Object> list = new ArrayList<>();

		JNode cur = head;
		while (cur != null) {
			if (cur.child != null)
				list.add(cur.child.value);

			cur = cur.next;
		}

		return list;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {

		HashSet<Entry<String, Object>> set = new HashSet<>();

		JNode cur = head;
		while (cur != null) {
			set.add(new AbstractMap.SimpleEntry<>((String) cur.value, cur.child.value));
			cur = cur.next;
		}

		return set;
	}

	/**
	 * 链表元素, 包含前后驱, 还有孩子节点
	 * 孩子节点可以是value, 也可以代表一个新的JSON对象
	 * 因此value只能用Object来描述
	 *
	 * @author jtchen
	 * @version 1.0
	 * @date 2021/3/10 19:40
	 */
	private static class JNode {

		public JNode next;
		public JNode prev;
		public JNode child;

		/*
		value 可能性:
		1. Integer
		2. Boolean
		3. String
		4. JSON
		5. JSONArray
		 */
		public Object value;

		public JNode(Object value) {
			this.value = value;

		}
	}
}
