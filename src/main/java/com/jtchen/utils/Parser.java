package com.jtchen.utils;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/12 15:12
 */
public interface Parser<T> {

	// 将解析器中的东西返回
	T commit();


	// 解析一个字符, 并且返回是否解析成功
	boolean parse(char ch);


	// 是不是该类型的开头字符
	boolean isStart(char ch);


	// 初始化状态转换机的内容
	void init();

	// 判断是否为终态
	boolean isEnd();
}
