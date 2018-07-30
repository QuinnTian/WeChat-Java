
# Wechat-Java![](https://img.shields.io/badge/build-passing-green.svg)![](https://img.shields.io/badge/JDK-8-red.svg)![enter description here](https://img.shields.io/badge/IDE-IDEA-brightgreen.svg)


## Java微信公众号开发
### 说明
* 使用最基本的JavaWeb进行微信公众平台的后台开发
* 后期将在基本的JavaWeb的基础上，添加与数据库对接
* [CSDN博客][1]、[简书][2]、博客园同步更新
### 目的
* 通过这个小型项目来巩固JavaWeb基础
* 构建自己的微信平台
## Java微信公众号开发（一）准备工作
### 需要的开发环境和开发工具

* **Eclipse 4.5**
* **Tomcat 7.0**
* **内网映射工具**
### 内网映射工具的使用
* **这里使用的是 [natapp](https://natapp.cn/)**
*  注册账号后购买免费隧道
*  注意指定映射本地端口号是在网页端设置
*  下载windows客户端，打开后是命令行模式
*  输入**natapp -authtoken=xxxxx （此为网页端的免费隧道的token）**
## Java微信公众号开发（二）与微信后台对接
### 需要的jar包
*  dom4j-1.6.1.jar
*  servlet-api.jar
*  xstream-1.3.1.jar
### 创建servlet类
##### doGet方法用于验证消息的确来自微信服务器
* **获取这个五个参数**
signature、timestamp、timestamp、nonce、echostr
* **调用CheckUtil类里的checkSignature()方法进行验证**
此方法写好后直接调用，涉及一些加密算法无需多看
![1][3]
### 填写服务器配置
* URL为内网映射工具得到的地址
* token与代码中的一致
### 容易出现的问题
*  **doGet方法不要继承父类，否则会出现[405错误]**
![2][4]


代码部分
``` 
package xyz.quinntian.servlet;
public class WeixinServlet extends HttpServlet {
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
	if (CheckUitl.checkSignature(signature, timestamp, nonce)) {
		System.err.println("验证成功");
		out.println(echostr);
	}else {
		System.out.println("验证失败");
	}
}

```
工具类
```          
package xyz.quinntian.Uitl;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUitl {
	private static final String token = "quinn";

	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 排序
		Arrays.sort(arr);
		// 生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		// sha加密
        String temp = getSha1(content.toString());
        System.out.println(signature);
        System.out.println(temp);
		return temp.equals(signature);

	}

	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];

			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}

```


  [1]: https://blog.csdn.net/LeoFitz
  [2]: https://www.jianshu.com/u/20ba9f30c422
  [3]: https://github.com/QuinnTian/WeChat-Java/raw/master/image-md/%E4%BA%94%E4%B8%AA%E5%8F%82%E6%95%B0.png
  [4]: https://github.com/QuinnTian/WeChat-Java/raw/master/image-md/%E6%9C%8D%E5%8A%A1%E5%99%A8%E9%85%8D%E7%BD%AE.png