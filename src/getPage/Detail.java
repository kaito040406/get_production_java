package getPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Detail {
	public static List<String> pruductionUrl = new ArrayList<>();

	public static void GetDetail(List<String> urlList) {
		urlList.forEach(url -> {
			try {
				Document doc = Jsoup.connect(url).get();
				Elements title = doc.select(".a-size-large.product-title-word-break");
				Elements selection = doc.select(".selection");
				Elements price = doc.select(".a-size-medium.a-color-price.priceBlockBuyingPriceString");
				Elements text = doc.select(".a-section.a-spacing-medium.a-spacing-top-small");
				System.out.println(title.text());
				System.out.println(price.text());
				System.out.println(selection.text());
				System.out.println(text.text());
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

	public Detail(List<String> pruductionUrl){
		Detail.pruductionUrl = pruductionUrl;
		GetDetail(Detail.pruductionUrl);
	}
}
