package getPage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbj.SqliteDBJ;
import model.ngwordModel;

public class Detail {
	public static List<String> pruductionUrl = new ArrayList<>();
	public static List<ngwordModel> titleVal = new ArrayList<>();
	public static Map<String,String> pruductionUrl2 = new HashMap<>();
	//2020/9/18 追加 最大タイトル文字数
	private static final int titleLength = 65;
	public static void GetDetail(Map<String, String> urlList) throws  IndexOutOfBoundsException{
		int i = 1;
		//タイトルの最大長さ

		for(Map.Entry<String, String> url : urlList.entrySet()) {
			try {
				System.out.println(url.getKey());
				//全体のhtmlを取得
				Document doc = Jsoup.connect(url.getKey()).get();

				//タイトル情報を取得
				//2020/9/19 修正 タイトルを６５文字以下になるように修正
				String title_raw = doc.select(".a-size-large.product-title-word-break").text();
				String title = "";
				for(ngwordModel val : titleVal) {
					if(val.getLevel().equals("0")) {
						if(title_raw.contains(val.getWord())) {
							title_raw = "";
						}
					}
				}
				try {
					if(title_raw.length() > titleLength) {
						title = title_raw.substring(0,titleLength);
					}
					else {
						title = title_raw;
					}
				}catch(Exception e) {
					title = "";
				}


				//サイズや種類を取得
				Elements selection = doc.select(".selection");


				//値段を取得
				String priceText = doc.select(".a-size-medium.a-color-price.priceBlockBuyingPriceString").text().replace("￥", "").replace(",", "").replace(" ", "");
				String price = "";
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
						if(val.getLevel().equals("2")) {
							if(text.text().contains(val.getWord())) {
								appendText = "";
							}
						}
					}
					productText = productText + appendText;
				};
				for(ngwordModel val : titleVal) {
					if(val.getLevel().equals("1")) {
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
				asin = url.getValue();
				if(asin.equals("")) {
					Elements asinElements = asinBlandMakerElements.select(".attrG").select("tr");
					for (Element asinElement : asinElements) {
						if(asinElement.select(".label").text().equals("ASIN")) {
							asin = asinElement.select(".value").text();
						}
					}
					if(asin.equals("")) {
						Elements asinElements2 = doc.select("#detail_bullets_id");
						Elements asinContentLists = asinElements2.select(".content").select("li");
						System.out.println(asinContentLists);
						for(Element asinContentList : asinContentLists) {
							System.out.println(asinContentList.select("b"));
							if(asinContentList.text().contains("ASIN:")) {
								asin = asinContentList.text().replace("ASIN ", "");
							}
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



				//カテゴリー算出

				Category ct = new Category();
				String categoryId = "";
				String yCategory = "";
				try {
					if(productText=="" && categorySearchData.size()==0) {

					}else {
						ct.calCategory(productText, categorySearchData);
						categoryId = ct.categoryId;
						yCategory = ct.category;
					}
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}

				//画像のURL取得
				Elements imageWapperElement = doc.select(".image.item.itemNo0.maintain-height.selected");
//				System.out.println(imageWapperElement);
				Elements imageElements = imageWapperElement.select(".imgTagWrapper");
//				System.out.println(imageElements);
				String imageUrl = imageElements.select("img").attr("data-old-hires");
				String imageName = "";
				System.out.println(i);
				imageName = Image.getImage(imageUrl,i);
//				if(!Image.getImage(imageUrl,i).equals("")) {
//					imageName = Image.getImage(imageUrl);
//				}


				System.out.println(url);
				System.out.println(title);
				System.out.println(price);
				System.out.println(selection.text());
				System.out.println(productText);
				System.out.println(maker);
				System.out.println(bland);
				System.out.println(asin);
//				if(asin == null) {
//					System.exit(0);
//				}
				System.out.println(category);
				String text = selection.text() + "<br>" + productText;
				String strDate = toStr(LocalDateTime.now(), "yyyy/MM/dd");
				//ファイルを正しく保存できているかの確認
				String filePath = "images/" + Integer.toString(i) + ".jpg";
				try {
					//2020/9/18 追加 カテゴリーIDが存在しない時は保存しない
					if(!categoryId.equals("")) {
						if(asin == null || title.equals("")) {
						}else {
							if(price.equals("")|| text.equals("")) {
							}else {
								if(!imageName.equals("")) {
									//画像データ確認
									File file = new File(filePath);
									Boolean fileExists = file.exists();
									if (fileExists) {
										System.out.println("保存します");
										SqliteDBJ.insertData(asin.replace("ASIN: ",""), title, url.getKey(), text, price, category, maker, bland, strDate, categoryId, yCategory,imageName);
										i++;
										System.out.println("保存成功");
									}
								}
							}
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
        };
	}

	public static String toStr(LocalDateTime localDateTime, String format) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);

    }

	public Detail(Map<String, String> pruductionUrl) throws ClassNotFoundException, SQLException, IndexOutOfBoundsException{
		Detail.titleVal = SqliteDBJ.searchAllDataNg();
		Detail.pruductionUrl2 = pruductionUrl;
		GetDetail(Detail.pruductionUrl2);
	}

}
