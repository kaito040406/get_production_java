package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.categoryModel;
import model.searchIdModel;


public class cal {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Tokenizer tokenizer = Tokenizer.builder().build();
        List<Token> tokens = tokenizer.tokenize("アイリスオーヤマ 2口 IHクッキングヒーター 工事不要 1400W 100V 脚付き ブラック IHK-W12SP-B 商品サイズ(cm):幅約56×奥行約40×高さ約18\n" +
        		"材質:【本体】ポリアミド樹脂、【トッププレート】結晶化ガラス\n" +
        		"電源:AC100V(50/60Hz共用)\n" +
        		"消費電力:1400W\n" +
        		"製品仕様:【火力調整】〈左ヒーター>加熱調理:6段階(約100W相当から約1400W)、揚げ物調理:6段階(150~200℃)〈右ヒーター〉加熱調理:4段階(約100W相当から約700W) 【コードの長さ】約1.6m 【タイマー】1分から9時間50分\n" +
        		"使えるなべ:【材質】鉄・鉄鋳物、耐熱ほうろう、ステンレス、多層鋼なべ【大きさ】〈左ヒーター〉直径12から26cmのもの※湯わかしの場合は直径15から23cm、〈右ヒーター〉直径12から20cmのもの【形状】底の平らなもの、反りが3mm以下のもの\n" +
        		"使えないなべ:【材質】耐熱ガラス、陶磁器・土なべ、アルミ・銅 【大きさ】直径12cm未満のもの、トッププレートからはみ出すサイズのもの 【形状】中華なべなど底が丸いもの、底に段がありトッププレートに密着しないもの、脚があるもの※IH調理器では、材質や大きさにより使える鍋と使えない鍋があります。使用する鍋は、財団法人「製品安全協会」のSGマークのあるものをお勧めします。");
        List<String> outputs = new ArrayList<String>();
        List<String> countOutputs = new ArrayList<String>();
        List<String> lists = new ArrayList<String>();
        List<String> ctOutputs = new ArrayList<String>();
        List<String> ctOutputs2 = new ArrayList<String>();
        List<String> ctLists0 = new ArrayList<String>();

        lists.add("ホーム＆キッチン");
        lists.add("キッチン家電");
        lists.add("コンロ");
        lists.add("クッキングヒーター");
        lists.add("据置型IHクッキングヒーター");

        for (String list : lists) {
        	List<Token> ctTokens = tokenizer.tokenize(list);
        	 for (Token token : ctTokens) {
        		 if(token.getSurfaceForm().length() > 1) {
        			 ctLists0.add(token.getSurfaceForm());
        		 }
        	 }
        }
        List<String> ctLists = ctLists0.stream().distinct().collect(Collectors.toList());
        System.out.println(ctLists);



        List<String> outputs2 = new ArrayList<String>();
//        lists.add("競技備品");
//        lists.add("健康サポート機器");
//        lists.add("活動量計");

//        for (Token token : tokens) {
//            System.out.println("====================");
            // allFeatures tokenの全ての要素を出力
//            System.out.println("allFeatures: " + token.getAllFeatures());
            // partOfSpeech 品詞など形態素上で意味のある言葉を出力
//            System.out.println("partOfSpeech: " + token.getPartOfSpeech());
            // position 単語の位置を出力
//            System.out.println("partOfSpeech: " + token.getPosition());
            // reading カナ読みを出力
//            System.out.println("reading: " + token.getReading());
            // surfaceForm 元の単語を出力
//            System.out.println("surfaceFrom: " + token.getSurfaceForm());
            // allFeaturesArray allFeaturesをArray型で返す
//            System.out.println("allFeaturesArray" + token.getAllFeaturesArray());
            // isKnown 辞書にある言葉かどうか
//            System.out.println("isKnown: " + token.isKnown());
            // isUnknown 辞書にない言葉かどうか、isKnownと反対の結果が出力される
//            System.out.println("isUnKnown: " + token.isUnknown());
            // isUser ユーザーで定義した言葉かどうか
//            System.out.println("User: " + token.isUser());
//        }
        for (Token token : tokens) {
//        	System.out.println("surfaceFrom: " + token.getSurfaceForm());
//        	System.out.println("partOfSpeech: " + token.getPartOfSpeech());
        	if(token.getSurfaceForm().length() > 2) {
        		String SQL1 = "select * from categoryTbl where category like '%" + token.getSurfaceForm() + "%';";
        		ObservableList<categoryModel> datas = searchCategory(SQL1);
        		for(categoryModel data : datas) {
					outputs.add(data.getCategory());
//					System.out.println(data.getCategory());
        		}
        	}
        }

		java.util.Map<String, Integer> map = outputs.stream().collect(
                Collectors.groupingBy(
                        //MapのキーにはListの要素をそのままセットする
                        Function.identity(),
                        //Mapの値にはListの要素を1に置き換えて、それをカウントするようにする
                        Collectors.summingInt(s->1))
                );
//        System.out.println(map.get("2084221201=1"));
		int countCheck = 0;
		int i = 0;
		for(Entry<String, Integer> entry : map.entrySet()) {
			if(i == 0) {
				countCheck = entry.getValue();
			}
			else {
				if(countCheck < entry.getValue()) {
					countCheck = entry.getValue();
				}
			}
			i++;
		}
		for(Entry<String, Integer> entry : map.entrySet()) {
			if(entry.getValue() == countCheck) {
				countOutputs.add(entry.getKey());
//				System.out.println(entry.getKey());
			}
		}



        for(String data : countOutputs) {
        	for(String list : ctLists) {
        		if(data.contains(list)) {
//        			System.out.println(data);
        			ctOutputs.add(data);
        		}
        	}
        }

		java.util.Map<String, Integer> map2 = ctOutputs.stream().collect(
                Collectors.groupingBy(
                        //MapのキーにはListの要素をそのままセットする
                        Function.identity(),
                        //Mapの値にはListの要素を1に置き換えて、それをカウントするようにする
                        Collectors.summingInt(s->1))
                );
		int countCheck2 = 0;
		int j = 0;
		for(Entry<String, Integer> entry : map2.entrySet()) {
			if(j == 0) {
				countCheck2 = entry.getValue();
			}
			else {
				if(countCheck2 <  entry.getValue()) {
					countCheck2 = entry.getValue();
				}
			}
			j++;
//		    System.out.println(entry.getKey());
//		    System.out.println(entry.getValue());
		}
		for(Entry<String, Integer> entry : map2.entrySet()) {
			if(entry.getValue() == countCheck2) {
				ctOutputs2.add(entry.getKey());
//				System.out.println(entry.getKey());
			}
		}









		if(ctOutputs2.size() == 0) {
			String SQL2 = "select * from searchLinkTbl where amazonCategory like '%" + lists.get(0) + "%';";
			ObservableList<searchIdModel> links = searchLinks(SQL2);
			String SQL3 = "select * from categoryTbl where searchId='" + links.get(0).getCategoryId() + "';";
			ObservableList<categoryModel> datas = searchCategory(SQL3);
			List<String> kuroutouts = new ArrayList<String>();
			Map<String, Integer> checkList = new HashMap<String, Integer>();
			for(categoryModel data : datas) {
				for(String list : lists) {
					if(data.getCategory().contains(list)) {
//						System.out.println(data.getCategory());
						outputs2.add(data.getCategory());
					}
				}
			}
			java.util.Map<String, Integer> map3 = outputs2.stream().collect(
	                Collectors.groupingBy(
	                        //MapのキーにはListの要素をそのままセットする
	                        Function.identity(),
	                        //Mapの値にはListの要素を1に置き換えて、それをカウントするようにする
	                        Collectors.summingInt(s->1))
	                );
			for(Entry<String, Integer> entry : map3.entrySet()) {
//			    System.out.println(entry.getKey());
//			    System.out.println(entry.getValue());
			    kuroutouts.add(entry.getKey());

			}
			int counter = 0;

			for(String kuroutput : kuroutouts) {
				counter = 0;

				for (Token token : tokens) {
					if(token.getSurfaceForm().length() > 2) {
						if(kuroutput.contains(token.getSurfaceForm())) {
							counter++;
						}
					}
				}
				checkList.put(kuroutput,counter);
				if(counter > 1) {
					System.out.println(counter);
					System.out.println(kuroutput);
				}
			}
		}
	}













	//リンク検索
	public static ObservableList<searchIdModel> searchLinks(String sql) throws ClassNotFoundException, SQLException {
		ObservableList<searchIdModel> data = FXCollections.observableArrayList();
		String SQL = sql;
		try {
			ResultSet rs = SqliteDB.dataQuery(SQL);
			data = (ObservableList<searchIdModel>) searchListData(rs);
			return data;
		}catch(SQLException ex) {
			System.out.println("失敗しました");
			throw ex;
		}
	}
	//リンク検索
	private static ObservableList<searchIdModel> searchListData(ResultSet rs) throws SQLException {
		ObservableList<searchIdModel> data = FXCollections.observableArrayList();
		while (rs.next()){
		      data.add(new searchIdModel(
		      rs.getString("amazonCategory"),
		      rs.getString("yahooCategory"),
		      rs.getString("categoryId")
		      ));
		    }
		return data;
	}



	//カテゴリ検索
	public static ObservableList<categoryModel> searchCategory(String sql) throws ClassNotFoundException, SQLException {
		ObservableList<categoryModel> data = FXCollections.observableArrayList();
		String SQL = sql;
		try {
			ResultSet rs = SqliteDB.dataQuery(SQL);
			data = (ObservableList<categoryModel>) searchCategoryData(rs);
			return data;
		}catch(SQLException ex) {
			System.out.println("失敗しました");
			throw ex;
		}
	}
	//カテゴリ検索
	private static ObservableList<categoryModel> searchCategoryData(ResultSet rs) throws SQLException {
		ObservableList<categoryModel> data = FXCollections.observableArrayList();
		while (rs.next()){
		      data.add(new categoryModel(
		      rs.getString("categoryId"),
		      rs.getString("category"),
		      rs.getString("categoryId")
		      ));
		    }
		return data;
	}


}
