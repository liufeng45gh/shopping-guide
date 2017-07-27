package com.lucifer.utils;

public class IdUtil {
	
	private static Integer idIndex = 1;
	/**
	 * 生成唯一性id
	 * <p>
	 * 当前时间秒值加3位同步数字，使用秒值避免long型越界
	 * </p>
	 * 
	 * @return
	 */
	public static synchronized long nextId() {
		if (idIndex > 999)
			idIndex = 1;
		return Long.parseLong(System.currentTimeMillis() + IdUtil.toString(idIndex++,3));
	}

	public static String toString(Integer number,Integer digit){
		String _value = String.valueOf(number);
		int supplement = digit - _value.length();
		for (int i = 0;i< supplement; i++) {
			_value = "0" + _value;
		}
		return _value;
	}

}
