package com.coolweather.app.util;

public interface HttpCallbackListener {
	/**
	 * 调用成功
	 * @param string
	 */
	void onFinish(String string);
	/**
	 * 调用失败
	 * @param e
	 */
	void onError(Exception e);

}
