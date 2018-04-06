package xyz.quinntian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import xyz.quinntian.Util.CheckUtil;
import xyz.quinntian.Util.MessageUtil;
import xyz.quinntian.po.TextMessage;

public class WeixinServlet extends HttpServlet {
/**
	 * 
	 */
	

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	//super.doGet(req, resp);
	String signature = req.getParameter("signature");
	System.out.println("signature:"+signature);
	String timestamp = req.getParameter("timestamp");
	System.out.println("timestamp:"+timestamp);
	String nonce = req.getParameter("nonce");
	System.out.println("nonce:"+nonce);
	String echostr = req.getParameter("echostr");
	System.out.println("echostr:"+echostr);
	PrintWriter out =resp.getWriter();
	if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
		System.err.println("��֤�ɹ�");
		out.println(echostr);
	}else {
		System.out.println("��֤ʧ��");
	}
}

@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
	    PrintWriter out = resp.getWriter();
		//xml����ת�ɼ�������
	    try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			System.out.println(req);
			String fromUserName = map.get("FromUserName");//���ͷ��ʺţ�һ��OpenID��
			System.out.println(fromUserName);
			String toUserName = map.get("ToUserName");//������΢�ź�
			String createTime = map.get("CreateTime");//��Ϣ����ʱ�� �����ͣ�
			String msgType = map.get("MsgType");//text
			System.err.println(msgType);
			String content = map.get("Content");//�ı���Ϣ����
			System.out.println(content);
			String msgId = map.get("MsgId");//��Ϣid��64λ����
			
			
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());	
				}else if ("2".equals(content)) {
					message =MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if ("3".equals(content)) {
					message =MessageUtil.initImageMessage(toUserName, fromUserName);
				}else if ("4".equals(content)) {
					message =MessageUtil.initMusicMessage(toUserName, fromUserName);
				}else if ("?".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());	
				}else {
					message="�޷�ʶ��";
				}
//				TextMessage text = new TextMessage();
//				text.setFromUserName(toUserName);
//				text.setToUserName(fromUserName);
//				text.setMsgType("text");
//				text.setCreateTime(new Date().getTime());
//				text.setContent("�����͵���Ϣ�ǣ�"+content);
//				System.out.println(text);
//				message = MessageUtil.textMessageToXml(text);
			//�¼����ʹ���
			}else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				String eventType = map.get("Event");
				//��ע������
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
				message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());	
				}
			}
			//System.out.println(message);
			out.print(message);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	    //��������תxml����
	}
}
