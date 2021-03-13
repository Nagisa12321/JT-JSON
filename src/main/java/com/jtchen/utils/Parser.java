package com.jtchen.utils;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/12 15:12
 */
public interface Parser<T> {

	// ���������еĶ�������
	T commit();


	// ����һ���ַ�, ���ҷ����Ƿ�����ɹ�
	boolean parse(char ch);


	// �ǲ��Ǹ����͵Ŀ�ͷ�ַ�
	boolean isStart(char ch);


	// ��ʼ��״̬ת����������
	void init();

	// �ж��Ƿ�Ϊ��̬
	boolean isEnd();
}
