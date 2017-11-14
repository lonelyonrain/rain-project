package com.time.scenery.rain.space.jt808protocol.simulation.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.time.scenery.rain.space.jt808protocol.simulation.message.JT808Message;
import com.time.scenery.rain.util.StringUtils;

public class ClinetIoHandler extends IoHandlerAdapter {
	private final static Logger logger=LoggerFactory.getLogger(ClinetIoHandler.class);
	public ClinetIoHandler() {
	}
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		JT808Message jt=(JT808Message)message;
		if (jt.getSim().equals("015345984531")) {
//			if (jt.getMsgID()!=0x8001) {
				byte[] a=jt.array();	
				logger.error("宝马接收到的信息为-->："+StringUtils.byte2hex(a));							
//			}
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("宝马发生异常了...", cause);
	}
	@Override
	 public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("宝马已发送定位数据");
	 }
	
     
} 