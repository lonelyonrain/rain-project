package com.time.scenery.rain.space.jt808protocol.simulation.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.xmgps.yun.basetype.*;
import com.xmgps.yun.communication.message.IoMessage;

/**
 * <JT/T 808> 协议中消息的基类，包含基本消息头的解析功能。
 * 所有从JT808Message派生的类需要重载以下三个接口：
 * 		array();
 * 		parse();
 * 		initialize();
 * 
 * @author YYH
 *
 */
public class JT808Message implements IoMessage {
	/**
	 * 消息ID
	 */
    private WordBinary ID = new WordBinary();
    /**
     *  终端手机号
     */
    private BcdStringBinary sim = new BcdStringBinary(6);
    /**
     *  消息流水号
     */
    private WordBinary serial = new WordBinary();
    
    /**
     *  消息总包数，为消息包封装项；如果消息有分包，则有该项内容，否则没有。
     */
    private WordBinary msgPackageTotal = new WordBinary();
    /**
     *  包序号，为消息包封装项；如果消息有分包，则有该项内容，否则没有。
     */
    private WordBinary msgPackageCur = new WordBinary();
    
    /**
     *  消息体
     */
    private ByteArrayBinary msgBody;
    
    /**
     *  消息是否分包，默认不分包
     */
    private BooleanBinary isMultiPackage = new BooleanBinary();
    /**
     *  消息加密方式，用bit2~bit4三位表示，默认不加密
     */
    private ByteBinary encryption = new ByteBinary();

	public int getMsgID() {
		return ID.getValue();
	}

	public byte[] array() {
		int length = (this.isMultiPackage.getValue() ? 16 : 12) 
				   + (this.msgBody != null ? this.msgBody.getBinaryLength() : 0);
		
		IoBuffer buf = IoBuffer.allocate(length);
        
        // 消息ID
        buf.put(ID.array());
        
        // 消息体属性
        buf.put(getMsgAttribute());
        
        // 终端手机号
        buf.put(sim.array());
        
        // 消息流水号
        buf.put(serial.array());
        
        // 消息包封装项
        if (isMultiPackage.getValue()) {
        	// 消息总包数
        	buf.put(this.msgPackageTotal.array());
            // 包序号
            buf.put(this.msgPackageCur.array());
        }
        
        // 消息体
        if (msgBody != null) {
        	buf.put(msgBody.array());
        }
		
		return buf.array();
	}

	public IoMessage parse(byte[] data) {
        JT808Message msg = new JT808Message();
        if (msg.initialize(data)) {
        	return msg;
        }
		return null;
	}
	
	/**
	 * 初始化消息对像
	 * @param data 用于初始化的二进制数据
	 * @return 初始化成功，返回true；否则返回false
	 */
	public boolean initialize(byte[] data) {
		if (data == null || data.length < 12) {
			return false;
		}
        Cursor offSet = new Cursor();
        WordBinary msgBodyLength = new WordBinary();

        // 消息ID
        ID.parse(data, offSet);
        
        // 消息体属性
        WordBinary msgAttr = new WordBinary();
        msgAttr.parse(data, offSet);
        byte[] attr = msgAttr.array();
        //当终端标识第一字节为0x10时
        if (data[4]==(byte)0x10) {
        	msgBodyLength.parse(attr, new Cursor());
			isMultiPackage.setValue(false);
		}else {
			isMultiPackage.setValue(((attr[0] & 0x20) > 0) ? true : false);
	        encryption.setValue((byte) (attr[0] & 0x1C));
	        // 消息体长度
	        attr[0] = (byte) (attr[0] & 0x03);
	        msgBodyLength.parse(attr, new Cursor());
		}
        //10位手机号前加上1
		data[offSet.getPosition()]=(byte)0x01;
        // 终端手机号
        sim.parse(data, offSet);        
        // 消息流水号
        serial.parse(data, offSet);
        if (isMultiPackage.getValue()) {
        	if (data.length < 16) {
        		return false;
        	}
        	// 消息总包数
            msgPackageTotal.parse(data, offSet);
            
            // 包序号
            msgPackageCur.parse(data, offSet);
        }
        
        // 消息体
        if (data.length - offSet.getPosition() >= msgBodyLength.getValue()) {
        	msgBody = new ByteArrayBinary(msgBodyLength.getValue());
        	msgBody.parse(data, offSet);
        }
        return true;
	}
	
	/**
	 * 获取消息体属性
	 */
    private byte[] getMsgAttribute() {
    	WordBinary sb = new WordBinary();
        // 消息体长度
    	short msgLength = 0;
    	if (msgBody != null) {
    		msgLength = (short)msgBody.getBinaryLength();
    	}
    	sb.setValue(msgLength);

        byte[] b = sb.array();
        
        // 是否分包
        if (this.isMultiPackage.getValue()) {
            b[0] |= 0x20;
        }
        // 加密方式 0x1C (00011100)
        b[0] |= (encryption.getValue() & 0x1C);
        
        return b;
    }


	/**
	 * 获取消息ID
	 */
	public int getID() {
		return ID.getValue();
	}

	/**
	 * 设置消息ID
	 */
	public void setID(int id) {
		ID.setValue(id);
	}

	/**
	 * 获取终端手机号
	 */
	public String getSim() {
		return sim.getValue();
	}

	/**
	 * 设置终端手机号
	 */
	public void setSim(String sim) {
		this.sim.setValue(sim);
	}

	/**
	 * 获取消息流水号
	 */
	public int getSerial() {
		return serial.getValue();
	}

	/**
	 * 设置消息流水号
	 */
	public void setSerial(int serial) {
		this.serial.setValue(serial);
	}

	/**
	 * 获取消息总包数
	 */
	public int getMsgPackageTotal() {
		return msgPackageTotal.getValue();
	}

	/**
	 * 设置消息总包数
	 */
	public void setMsgPackageTotal(int msgPackageTotal) {
		this.msgPackageTotal.setValue(msgPackageTotal);
	}

	/**
	 * 获取包序号，从1开始
	 */
	public int getMsgPackageCur() {
		return msgPackageCur.getValue();
	}

	/**
	 * 设置包序号，从1开始
	 */
	public void setMsgPackageCur(int msgPackageCur) {
		this.msgPackageCur.setValue(msgPackageCur);
	}

	/**
	 * 获取消息体
	 */
	public byte[] getMsgBody() {
		if (msgBody != null) {
			return msgBody.getValue();
		}
		return null;
	}

	/**
	 * 设置消息体
	 */
	public void setMsgBody(byte[] msgBody) {
		if (this.msgBody != null) {
			this.msgBody.setValue(msgBody);
		} else {
			this.msgBody = new ByteArrayBinary(msgBody);
		}
	}

	/**
	 * 获取消息是否分包
	 */
	public boolean isMultiPackage() {
		return isMultiPackage.getValue();
	}

	/**
	 * 设置消息是否分包
	 */
	public void setMultiPackage(boolean isMultiPackage) {
		this.isMultiPackage.setValue(isMultiPackage);
	}

	/**
	 * 获取消息加密方式
	 * 用bit2~bit4三位表示：
	 * ---当此三位都为0，表示消息体不加密；
	 * ---当bit2为1，表示消息体经过RSA算法加密
	 * ---其他保留
	 * 默认不加密
	 */
	public byte getEncryption() {
		return encryption.getValue();
	}

	/**
	 * 设置消息加密方式
	 * 用bit2~bit4三位表示：
	 * ---当此三位都为0，表示消息体不加密；
	 * ---当bit2为1，表示消息体经过RSA算法加密
	 * ---其他保留
	 * 默认不加密
	 * 
	 */
	public void setEncryption(byte encryption) {
		this.encryption.setValue(encryption);
	}

	
}
