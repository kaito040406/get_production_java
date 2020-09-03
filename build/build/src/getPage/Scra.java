package getPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scra {
	public static List<String> accessUrl = new ArrayList<>();
	public static Map<String,String> accessUrl2 = new HashMap<>();
	String furl = null;
	public static int maxCount = 0;
	public static int counter = 0;
	public static int acquisition = 5;
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
				String prime = data.select(".a-icon.a-icon-prime.a-icon-medium").attr("aria-label");
				String wBuy = data.select(".a-size-base.s-addon-highlight-color.s-highlighted-text-padding.aok-inline-block").text();
				Elements colorEl = data.select(".a-row.a-size-base.a-color-secondary");
				Elements colors = colorEl.select(".a-color-price");


				//Asin取得
				String asinElement = data.select(".rush-component").attr("data-component-props");
				String[] asinList = asinElement.split(",");
				String asin = "";
				for(String asinData : asinList) {
					System.out.println(asinData);
					if(asinData.contains("asin")) {
						asin = asinData.replace("\"asin\":\"","").replace("\"}","");
						System.out.println(asin);
					}
				}


				int intColor = 100;
				for(Element color : colors) {
					if(color.text().contains("残り")) {
						intColor = Integer.valueOf(color.text().replaceAll("[^0-9]", ""));
					}
				}
				if(prime.equals("Amazon プライム")) {
					if(!wBuy.contains("あわせ買い対象商品")) {
						if(intColor > acquisition) {
							System.out.println("在庫は" + intColor);
							accessUrl.add("https://www.amazon.co.jp" + title.attr("href"));

							accessUrl2.put("https://www.amazon.co.jp" + title.attr("href"), asin);

							System.out.println("https://www.amazon.co.jp" + title.attr("href"));
						}
					}
					else {
						System.out.println("あわせ買い" + wBuy +"除去");
					}
				}
//				try {
////					Thread.sleep(300);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
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
		Thread.sleep(1000);
		System.out.println("次のページ");
		FirstPage(url);
	}
	public Scra(String url, Integer count, Integer getNumber) throws IOException, InterruptedException{
		this.furl = url;
		Scra.maxCount = count;
		Scra.acquisition = getNumber;
		FirstPage(this.furl);
	}

}
