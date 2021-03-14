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
	���ö༶˫������˼·���JSON��������ݽṹʵ��
	����β�Ͳ��뷨��ʵ��
	JSON��ÿһ���㼶����˫�������ÿ���㼶
	����JSON
	{
  		"name": "Jack (\"Bee\") Nimble",
  		"format": {
    		"width": 1920.0,
    		"frame rate": 24.0,
    		"interlace": false,
    		"height": 1080.0
  		}
	}
	���ݽṹ��ʾΪ
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
	// JNodeΪ�Զ������ݽṹ
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

	// ������...
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

		// ���������key����null
		if (node == null) return null;

			// �������key, �򷵻ض�Ӧ��ֵ
		else return node.child.value;
	}

	@Override
	public Object put(String key, Object value) {
		if (isEmpty()) {
			head = new JNode(key);
			head.child = new JNode(value);
			tail = head;

			// ���³���
			this.size++;
			return null;
		} else {
			JNode node = find(key);
			if (node != null) {
				JNode tmp = node.child;

				// ��Ϊ��ֵ
				node.child = new JNode(value);

				return tmp.value;
			} else {
				// �����µ�keyΪβ�ڵ�֮��һ���ڵ�
				tail.next = new JNode(key);

				// ����β�ڵ�
				tail = tail.next;

				// ����β�ڵ��Ӧ��ֵ
				tail.child = new JNode(value);

				// ���³���
				this.size++;
				return null;
			}
		}
	}


	/**
	 * ͨ��key�ҵ���Ӧ��JNode
	 */
	private JNode find(Object key) {
		JNode cur = head;
		while (cur != null) {
			String tmp = (String) cur.value;
			if (tmp.equals(key) || tmp.equals(key.toString())) return cur;
			cur = cur.next;
		}

		// �Ҳ�������null
		return null;
	}

	@Override
	public Object remove(Object key) {
		// ɾ��ֻ�����ӦJNodeĨȥ
		// ������������Ȼ����л���
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

		// ���³���
		size--;

		// ���ر�ɾ��Ԫ�ص�ֵ
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
	 * ����Ԫ��, ����ǰ����, ���к��ӽڵ�
	 * ���ӽڵ������value, Ҳ���Դ���һ���µ�JSON����
	 * ���valueֻ����Object������
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
		value ������:
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
