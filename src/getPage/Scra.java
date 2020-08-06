package getPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scra {
	public static List<String> accessUrl = new ArrayList<>();
	String furl = null;
	public static int maxCount = 0;
	public static int counter = 0;
	public static void FirstPage(String url) throws IOException, InterruptedException {
		if (counter++ < maxCount) {
			Document doc = Jsoup.connect(url).get();
			Elements datas;
//			amazonのテンプレートが複数に分かれているので、各パターンで対応する
			Elements dataPetern1 = doc.select(".sg-col-4-of-12.sg-col-8-of-16.sg-col-16-of-24.sg-col-12-of-20.sg-col-24-of-32.sg-col.sg-col-28-of-36.sg-col-20-of-28");
			Elements dataPetern2 = doc.select(".sg-col-4-of-24.sg-col-4-of-12.sg-col-4-of-36.s-result-item.s-asin.sg-col-4-of-28.sg-col-4-of-16.sg-col.sg-col-4-of-20.sg-col-4-of-32");
			if(dataPetern1.size() > dataPetern2.size()) {
				datas = dataPetern1;
			}
			else {
				datas = dataPetern2;
			}
			for (Element data : datas) {
				Elements title = data.select(".a-link-normal.a-text-normal");
				accessUrl.add("https://www.amazon.co.jp" + title.attr("href"));
				System.out.println("https://www.amazon.co.jp" + title.attr("href"));
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			String nextPageElement = doc.select(".a-last").select("a[href]").attr("href");
			String nextPage = "https://www.amazon.co.jp" + nextPageElement;
//			System.out.println(nextPage);
			Next(nextPage);
		}
		else {
			System.out.println("処理が終了いたしました");
		}
	}
	public static void Next(String url) throws IOException, InterruptedException {
		Thread.sleep(800);
		System.out.println("次のページ");
		FirstPage(url);
	}
	public Scra(String url, Integer count) throws IOException, InterruptedException{
		this.furl = url;
		Scra.maxCount = count;
		FirstPage(this.furl);
	}

}
