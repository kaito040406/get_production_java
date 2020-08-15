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
import model.ngwordModel;

public class Detail {
	public static List<String> pruductionUrl = new ArrayList<>();
	public static List<ngwordModel> titleVal = new ArrayList<>();

	public static void GetDetail(List<String> urlList) {
		urlList.forEach(url -> {
			try {
				//全体のhtmlを取得
				Document doc = Jsoup.connect(url).get();

				//タイトル情報を取得
				String title = doc.select(".a-size-large.product-title-word-break").text();
				for(ngwordModel val : titleVal) {
					if(val.getLevel() == "0") {
						if(title.contains(val.getWord())) {
							title = "";
						}
					}
				}


				//サイズや種類を取得
				Elements selection = doc.select(".selection");


				//値段を取得
				String priceText = doc.select(".a-size-medium.a-color-price.priceBlockBuyingPriceString").text().replace("￥", "").replace(",", "").replace(" ", "");
				String price = null;
				if(priceText.matches("^[0-9]*$")) {
					price = priceText;
				}


				//商品テキスト1を取得
				Elements texts = doc.select(".a-section.a-spacing-medium.a-spacing-top-small");
				Elements lineTexts = texts.select(".a-list-item");
				String productText = "";
				String appendText = "";
				//商品テキスト1をラインごとに取得し、<br>と共に足し合わせる
				for (Element text : lineTexts) {
					appendText = text.text() + "<br>";
					for(ngwordModel val : titleVal) {
						if(val.getLevel() == "2") {
							if(text.text().contains(val.getWord())) {
								appendText = "";
							}
						}
					}
					productText = productText + appendText;
				};
				for(ngwordModel val : titleVal) {
					if(val.getLevel() == "1") {
						if(productText.contains(val.getWord())) {
							productText = "";
						}
					}
				}

				//下部の商品テキストを取得
//				Elements texts2 = doc.select(".aplus-v2.desktop.celwidget").select(".a-column.a-span4.a-spacing-base.a-span-last");
				//取得するか検討中
//				String innerText = texts2.text().replace('"','<br>');


				//下記メーカー名とブランド名を取得
				Elements asinBlandMakerElements = doc.select(".wrapper.JPlocale");
				Elements blandMakerElements = asinBlandMakerElements.select(".attrG").select("tr");
				String maker = null;
				String bland = null;
				String asin = null;
				for (Element blandMakerElement : blandMakerElements) {
					if(blandMakerElement.select(".label").text().equals("メーカー")) {
						maker = blandMakerElement.select(".value").text();
					}
					else if(blandMakerElement.select(".label").text().equals("ブランド名")) {
						bland = blandMakerElement.select(".value").text();
					}
				};

				//asin取得
				Elements asinElements = asinBlandMakerElements.select(".attrG").select("tr");
				for (Element asinElement : asinElements) {
					if(asinElement.select(".label").text().equals("ASIN")) {
						asin = asinElement.select(".value").text();
					}
				}
				if(asin == null) {
					Elements asinElements2 = doc.select("#detail_bullets_id");
					Elements asinContentLists = asinElements2.select(".content").select("li");
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

				//画像のURL取得
				Elements imageWapperElement = doc.select(".image.item.itemNo0.maintain-height.selected");
//				System.out.println(imageWapperElement);
				Elements imageElements = imageWapperElement.select(".imgTagWrapper");
//				System.out.println(imageElements);
				String imageUrl = imageElements.select("img").attr("data-old-hires");
				String imageName = Image.getImage(imageUrl);

				//カテゴリー算出

				Category ct = new Category();
				String categoryId = "";
				String yCategory = "";
				try {
					ct.calCategory(productText, categorySearchData);
					categoryId = ct.categoryId;
					yCategory = ct.category;
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}



				System.out.println(title);
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
					if(asin == "" || title == "") {
					}else {
						if(price == "" || text == "") {
						}else {
							SqliteDBJ.insertData(asin.replace("ASIN: ",""), title, url, text, price, category, maker, bland, strDate, categoryId, yCategory,imageName);
						}
					}

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

	public Detail(List<String> pruductionUrl) throws ClassNotFoundException, SQLException{
		Detail.titleVal = SqliteDBJ.searchAllDataNg();
		Detail.pruductionUrl = pruductionUrl;
		GetDetail(Detail.pruductionUrl);
	}
}
