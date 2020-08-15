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
		String aaa = null;
		Tokenizer tokenizer = Tokenizer.builder().build();
        List<Token> tokens = tokenizer.tokenize(aaa);
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
