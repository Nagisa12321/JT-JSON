package com.jtchen.utils;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/12 15:12
 */
public interface Parser<T> {
	/*
	本项目最重要接口。
	描述:
	 	这是一个Parser解析器泛型接口, 未定义类型T意味着会返回什么类型的对象
	以下是解析器将要附有的功能
	 */

	/**
	 * 当本解析器在调用parse(char) 方法的时候返回了false
	 * 意味着相应元件解析完成/解析失败
	 * <p>
	 * 这个函数中应该判断是否为终态, 如果是终态, 则证明正常解析成功
	 * 应该return 对象中保有的已解析内容返回给父解析器
	 * <p>
	 * 如果解析失败了, 不是终态, 调用commit方法应该抛出一个JSONException
	 * 表示解析失败
	 * <p>
	 * 别忘了返回之前要调用init() 方法, 重置以下对象中缓存的解析内容
	 *
	 * @return 解析成功后的内容, 返回给父亲解析器
	 */
	T commit();


	/**
	 * 解析一个字符, 改变对象中的state状态, 如果到达ERROR状态则返回false
	 * @param ch 待解析的下一个字符
	 * @return 解析是否成功
	 */
	boolean parse(char ch);


	// 是不是该类型的开头字符
	boolean isStart(char ch);


	// 初始化状态转换机的内容, 以及对象中的已解析的缓存
	void init();

	// 判断是否为终态
	boolean isEnd();
}
