package com.time.scenery.rain.respond;

public enum RespondCode {

	/** (成功)服务器已成功处理了请求 */
	SUCCESS("200", "(成功)服务器已成功处理了请求"),
	/** 请求已经完成并一个新的返回资源被创建 */
	REQUESCOMPLETE("201", "请求已经完成并一个新的返回资源被创建"),
	/** (错误请求) 服务器不理解请求的语法 */
	GRAMMARMISTAKES("400", "(错误请求) 服务器不理解请求的语法"),
	/** (未授权)请求要求身份验证 TODO 未授权时自动重新获取token */
	UNAUTHORIZED("401", "(未授权)请求要求身份验证"),
	/** (禁止)服务器拒绝请求 */
	REQUESTREFUSE("403", "(禁止)服务器拒绝请求"),
	/** (未找到)服务器找不到请求的网页 */
	REQUESTNOTFIND("404", "(未找到)服务器找不到请求的网页"),
	/** 服务器遭遇异常阻止了当前请求的执行 */
	SERVEREXCEPTION("500", "服务器遭遇异常阻止了当前请求的执行"),
	/** (错误网关)服务器作为网关或代理，从上游服务器收到无效响应 */
	GATEWAYERROR("502", "(错误网关)服务器作为网关或代理，从上游服务器收到无效响应"),
	/** 请求文件不存在 */
	FILENOTFIND("702", "请求文件不存在"),
	/** 请求文件名格式不正确 */
	FILENAMEFORMATERROR("948", "请求文件名格式不正确"),
	/** 文件解压失败 */
	FILEDECOMPRESSIONFAILED("949", "文件解压失败"),
	/** 格式校验失败 */
	FORMATCHECKFAILED("952", "格式校验失败");

	private String respondCode;
	private String respondDsc;

	private RespondCode(String respondCode, String respondDsc) {
		this.respondCode = respondCode;
		this.respondDsc = respondDsc;
	}

	public String getRespondCode() {
		return respondCode;
	}

	public String getRespondDsc() {
		return respondDsc;
	}

}
