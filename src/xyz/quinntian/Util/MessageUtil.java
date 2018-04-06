package xyz.quinntian.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import xyz.quinntian.po.Image;
import xyz.quinntian.po.ImageMessage;
import xyz.quinntian.po.Music;
import xyz.quinntian.po.MusicMessage;
import xyz.quinntian.po.NewMessage;
import xyz.quinntian.po.News;
import xyz.quinntian.po.TextMessage;

/**
 * @author QuinnTian
 */
public class MessageUtil {
	
	
	//消息类型的常量
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	private static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_MUSIC = "music";
	/**
	 * 接收xml消息转到Map集合
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();

		SAXReader reader = new SAXReader();

		// 从request中获取输入流
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();// 获取根元素
		List<Element> list = root.elements();// 获取子节点集合
		// 遍历节点元素，存入Map
		for (Element element : list) {
			// 把每个元素存入map
			map.put(element.getName(), element.getText());
		}
		ins.close();// 释放输入流
		return map;
	}

	/**
	 * Text消息转xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	public static String initText(String toUserName,String fromUserName,
			String content ){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		System.out.println(text);
		return textMessageToXml(text);
		
	}
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer str = new StringBuffer();
		str.append("欢迎您的关注!\n");
		str.append("WeChat-Java后台对接测试\n请按照菜单提示操作\n---------------------\n");
		str.append("回复1.测试文本消息回复\n");
		str.append("回复2.测试图文消息回复\n");
		str.append("回复3.测试图片消息回复\n（有时间限制）\n");
		str.append("回复4.测试音乐消息回复\n（有时间限制）\n");
		str.append("回复？.调出主菜单\n");
		return str.toString();
		
	}
	public static String firstMenu(){
		StringBuffer str = new StringBuffer();
		str.append("first\n");
		
		
		return str.toString();
		
	}
	/**
	 * 图文消息转为xml
	 * @param newsMeassage
	 * @return
	 */
	public static String newsMessageToXml(NewMessage newsMeassage) {
		XStream xStream = new XStream();
		xStream.alias("xml", newsMeassage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMeassage);
	}
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName) {
		String message = null;
		List<News> newslist = new ArrayList<News>();
		NewMessage newsMessage = new NewMessage();
		
		News news =new News();
		news.setTitle("test img");
		news.setDescription("test img description");
		news.setPicUrl("http://jjup8v.natappfree.cc/Weixin/image/test.png");
		news.setUrl("http://123.206.73.184");
		newslist.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newslist);
		newsMessage.setArticleCount(newslist.size());
		message = newsMessageToXml(newsMessage);
		return message;
	}
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("ENYQuY15C6Yp2e5HETVXctnZu9K1s4sJxNrnZ15fNvGsxfuhYSPYr4qxOMDoLsYT");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("DM_esBaLHmBNgsAah3JBtLaA2ZNUAAixE6HhAwSPmXciQI6AEmHEHKQH0wUWTPkp");
		music.setTitle("骄傲的少年");
		music.setDescription("骄傲的少年");
		music.setMusicUrl("http://6fdsev.natappfree.cc/WeChat-Java/resource/Horner.mp3");
		music.setHQMusicUrl("http://6fdsev.natappfree.cc/WeChat-Java/resource/Horner.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
	
}
