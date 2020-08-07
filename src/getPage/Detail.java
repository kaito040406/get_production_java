package getPage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbj.SqliteDBJ;
import search.Search;

public class Detail {
	public static List<String> pruductionUrl = new ArrayList<>();

	public static void GetDetail(List<String> urlList) {
		urlList.forEach(url -> {
			try {
				//全体のhtmlを取得
				Document doc = Jsoup.connect(url).get();
				//タイトル情報を取得
				Elements title = doc.select(".a-size-large.product-title-word-break");
				//サイズや種類を取得
				Elements selection = doc.select(".selection");
				//値段を取得
				//二つ取得し同一値であることを確認する。
				String price = doc.select(".a-size-medium.a-color-price.priceBlockBuyingPriceString").text();
				//商品テキスト1を取得
				Elements texts = doc.select(".a-section.a-spacing-medium.a-spacing-top-small");
				Elements lineTexts = texts.select(".a-list-item");
				String productText = null;
				//商品テキスト1をラインごとに取得し、<br>と共に足し合わせる
				for (Element text : lineTexts) {
					productText = productText + text.text() + "<br>";
				};
				//下部の商品テキストを取得
//				Elements texts2 = doc.select(".aplus-v2.desktop.celwidget").select(".a-column.a-span4.a-spacing-base.a-span-last");
				//取得するか検討中
//				String innerText = texts2.text().replace('"','<br>');
				//下記メーカー名とブランド名を取得
				Elements asinBlandMakerElements = doc.select(".wrapper.JPlocale");
				Elements blandMakerElements = asinBlandMakerElements.select(".attrG").select("tr");
				String maker = "";
				String bland = "";
				String asin = "";
				for (Element blandMakerElement : blandMakerElements) {
					if(blandMakerElement.select(".label").text().equals("メーカー")) {
						maker = blandMakerElement.select(".value").text();
					}
					else if(blandMakerElement.select(".label").text().equals("ブランド名")) {
						bland = blandMakerElement.select(".value").text();
					}
				};

				Elements asinElements = asinBlandMakerElements.select(".attrG").select("tr");
				for (Element asinElement : asinElements) {
					if(asinElement.select(".label").text().equals("ASIN")) {
						asin = asinElement.select(".value").text();
					}
				}
				if(asin == "") {
					Elements asinElements2 = doc.select("#detail_bullets_id");
					Elements asinContentLists = asinElements2 = doc.select(".content").select("li");
					for(Element asinContentList : asinContentLists) {
						if(asinContentList.text().contains("ASIN:")) {
							asin = asinContentList.text().replace("ASIN ", "");
						}
					}
				}

				//カテゴリー取得

				String category = "";
				Elements categoryElement = doc.select(".a-unordered-list.a-horizontal.a-size-small");
				category = categoryElement.text();

				Elements categories = categoryElement.select(".a-link-normal.a-color-tertiary");

				List<String> categorySearchData = new ArrayList<>();

				for(Element categoryData : categories) {
					String searchData = categoryData.text();
					//「・」がついているものは検索に引っかからないので、分割して格納する。
					if(searchData.contains("・")) {
						String[] innerWords = searchData.split("・", 0);
						for(String innerWord : innerWords) {
							categorySearchData.add(innerWord);
						}
					}
					else {
						categorySearchData.add(searchData);
					}
				}
				String categoryId = "";
				String yCategory = "";
				try {
					Search cateData = new Search(categorySearchData);
//					System.out.println(cateData.category);
//					System.out.println(category);
					categoryId = cateData.categoryId;
					yCategory = cateData.category;
				} catch (ClassNotFoundException | SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}


				System.out.println(title.text());
				System.out.println(price);
				System.out.println(selection.text());
				System.out.println(productText);
				System.out.println(maker);
				System.out.println(bland);
				System.out.println(asin);
				System.out.println(category);
				String text = selection.text() + "<br>" + productText;
				String strDate = toStr(LocalDateTime.now(), "yyyy/MM/dd");
				try {
					SqliteDBJ.insertData(asin, title.text(), url, text, price, category, maker, bland, strDate, categoryId, yCategory);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        });
	}

	public static String toStr(LocalDateTime localDateTime, String format) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);

    }

	public Detail(List<String> pruductionUrl){
		Detail.pruductionUrl = pruductionUrl;
		GetDetail(Detail.pruductionUrl);
	}
}
