package xyz.quinntian.test;

import xyz.quinntian.Util.WeixinUtil;
import xyz.quinntian.po.AccessToken;

public class WeixinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("Ʊ��"+token.getToken());
			System.out.println("��Чʱ��"+token.getExpiresIn());
			//�ļ����ϴ�
			String path = "e:/fileupload.png";
			String mediaId = WeixinUtil.upload(path, token.getToken(), "thumb");
			System.out.println(mediaId);
			
			//String result = WeixinUtil.translate("my name is laobi");
			//String result = WeixinUtil.translateFull("");
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
