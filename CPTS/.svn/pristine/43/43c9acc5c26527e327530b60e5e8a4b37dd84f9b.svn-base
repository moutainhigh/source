package com.dchip.cloudparking;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dchip.cloudparking.model.vo.HeartBeatVO;
import com.dchip.cloudparking.socket.constant.Constant;
import com.dchip.cloudparking.socket.utils.RetKit;

@ServerEndpoint(value = "/webSocket/{controllerMac}")
@Component
public class WebSocket {

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//	private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();
	private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<String, WebSocket>();
	static Log log = LogFactory.getLog(WebSocket.class);
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private String controllerMac;
	
	@Autowired
	private StringRedisTemplate stringTemplate;

	public WebSocket() {
		this.stringTemplate = ApplicationContextRegister.getApplicationContext().getBean(StringRedisTemplate.class);
	}

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("controllerMac") String controllerMac) {
		this.session = session;
		this.controllerMac = controllerMac;
		webSocketSet.put(controllerMac,this); 
		log.info("连接成功");
		try {
			sendMessage(RetKit.ok());
		} catch (IOException e) {
			log.info("IO异常");
		}
		
		if(stringTemplate.opsForHash().get(Constant.DeviceOnlineSituation.DeviceOffline.getValue().toString(), controllerMac) != null) {  // 如果设备在线则删除这个设备的缓存
			stringTemplate.opsForHash().delete(Constant.DeviceOnlineSituation.DeviceOffline.getValue().toString(),controllerMac);
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		log.info("onClose");
		webSocketSet.remove(this); // 从set中删除

		stringTemplate.opsForHash().put(Constant.DeviceOnlineSituation.DeviceOffline.getValue().toString(), controllerMac, new Date().toString());// 断线时间
	
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String msg, Session session) {
		log.info(controllerMac + "-----------------------" + msg);
		HeartBeatVO heartBeat;
		try {
			heartBeat = JSON.parseObject(msg, HeartBeatVO.class);
			// 心跳
			if (heartBeat.getType().equals("99")) {
				if (stringTemplate.opsForHash().hasKey(Constant.DeviceOnlineSituation.DeviceOffline.getValue().toString(), controllerMac)) { // 如果设备不在线
					HeartBeatVO DeviceMessage = JSON.parseObject(msg, HeartBeatVO.class);
					if (DeviceMessage != null) {
						DeviceMessage.setOffline(true);
						this.session.getAsyncRemote().sendText(JSONObject.toJSONString(DeviceMessage));
					} else {
						this.session.getAsyncRemote().sendText(msg);
					}
				}else {
					this.session.getBasicRemote().sendText(msg);
				}
				
				//如果发送了99心跳回来说明已经不是离线状态
				if (stringTemplate.opsForHash().get(
						Constant.DeviceOnlineSituation.DeviceOffline.getValue().toString(),
						controllerMac) != null) {
					stringTemplate.opsForHash().delete(
							Constant.DeviceOnlineSituation.DeviceOffline.getValue().toString(), controllerMac);
				}
			}else {
				this.session.getBasicRemote().sendText(msg);
			}
			stringTemplate.opsForHash().put(Constant.ValueAndExplian.LatestHeart.getValue().toString(), controllerMac, new Date().toString());// 每次心跳上来都保存到缓存中，并更新时间
			stringTemplate.expire(controllerMac, 60, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.debug(e);
		}
	}

	/**
	 * 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 增加 synchronized 防止  The remote endpoint was in state [TEXT_FULL_WRITING] 
	 */
	public synchronized void sendMessage(Map<String,Object> param) throws IOException {
		this.session.getBasicRemote().sendText(JSONObject.toJSONString(param));
	}
	
	public static RetKit sendMsgToUser(String mac,Map<String,Object> param) {
		log.info("try to target:" + mac + " send message:" + param.size() + "");
		
		if (webSocketSet.get(mac) == null) {
			return RetKit.fail("设备不在线");
		}
		try {
			webSocketSet.get(mac).sendMessage(param);
		} catch (IOException e) {
			log.error(e);
			return RetKit.fail("发送失败");
		}
		return RetKit.ok("发送成功");
	}
	
	public static RetKit sendGroundLockMsgToUser(String mac, Map<String,Object> param) {
		log.info("try to target:" + mac + "  send message:" + param.size() + "");
		
		if (webSocketSet.get(mac) == null) {
			return RetKit.fail("设备不在线");
		}
		try {
			webSocketSet.get(mac).sendMessage(param);
		} catch (IOException e) {
			log.error(e);
			return RetKit.fail("发送失败");
		}
		return RetKit.ok("发送成功");
	}

}
