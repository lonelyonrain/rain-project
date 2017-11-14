package com.time.scenery.rain.respond;


/**
 * @ClassName: RespondMsg
 * @Description: 应答消息结构体
 * @author: zhzp
 */
public class RespondMsg<T> {

	/** 状态返回码 */
	public String code;

	/** 描述信息 */
	public String message;

	/** 返回体信息 */
	public T result;
}
