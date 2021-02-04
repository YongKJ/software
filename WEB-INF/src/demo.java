
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.eeepay.pub.utils.DateUtils;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Guid;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.WireFeedOutput;

public class AddRss {

	 public void getAddRss(AreaContext ctx)  {
			HttpServletResponse response = ctx.getResponse();
			response.setContentType("text/html");
			response.setHeader("content-type", "text/html;charset=UTF-8");
			Channel channel = new Channel("rss_2.0");
			channel.setTitle("测试rss订阅");// 网站标题
			channel.setDescription("study environment");// 网站描述
			channel.setLink("http://www.baidu.com/");// 网站主页链接
			channel.setEncoding("utf-8");// RSS文件编码
			channel.setLanguage("zh-cn");// RSS使用的语言
			channel.setTtl(60);// time to live的简写，在刷新前当前RSS在缓存中可以保存多长时间（分钟）
			channel.setCopyright("版权声明");// 版权声明
			channel.setPubDate(new Date());// RSS发布时间
			//channel.set
			//DataMap customPriceParams = new DataMap();
			List<Item> items = new ArrayList<Item>();// 这个list对应rss中的item列表
			if(true) {
				for(int i = 0; i < 10; i ++) {
					Item item = new Item();// 新建Item对象，对应rss中的<item></item>

					item.setTitle("百度");// 对应<item>中的<title></title>
					item.setAuthor("kkkk");
					item.setLink("http://www.baidu.com");  //对应 <item>中的具体标题
					Guid guid = new Guid();// 为当前新闻指定一个全球唯一标示，这个不是必须的
					guid.setValue("http://www.baidu.com");
					item.setGuid(guid);
					// 新建一个Description，它是Item的描述部分
					Description description = new Description();
					description.setType("text/html");
					String str = "<![CDATA["
					+
					"<div></div>"
					+
					"<p><a href='http://www.baidu.com'><img width='270px' height='129px' src='http://www.baidu.com/img/bd_logo1.png/></a></p>"
					+
					"<div></div>"
					+
					"<p><a href='http://www.baidu.com'>»查看详情</a></p>";
					description.setValue(str);// <description>中的内容
					item.setDescription(description);// 添加到item节点中
					item.setPubDate(DateUtils.format(new Date().toLocaleString(),"yyyy-MM-dd HH:mm:ss"));// 这个<item>对应的发布时间
					items.add(item);// 代表一个段落<item></item>，

				}
			}

			channel.setItems(items);
			// 用WireFeedOutput对象输出rss文本
			WireFeedOutput out = new WireFeedOutput();
			try {
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(out.outputString(channel).getBytes("utf-8"));
				//OutputStreamWriter writer = new OutputStreamWriter(outputstream,"utf-8");
				outputStream.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FeedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
}
