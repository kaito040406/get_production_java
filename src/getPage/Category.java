package getPage;

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

import db.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.categoryModel;
import model.searchIdModel;

public class Category {
	public static String category = "";
	public static String categoryId = "";

	public static void calCategory(String text, List<String> lists) throws ClassNotFoundException, SQLException {
		Tokenizer tokenizer = Tokenizer.builder().build();

        List<String> outputs = new ArrayList<String>();
        List<String> countOutputs = new ArrayList<String>();
        List<String> ctOutputs = new ArrayList<String>();
        List<String> ctOutputs2 = new ArrayList<String>();
        List<String> ctLists0 = new ArrayList<String>();
        String SQL2 = "select * from searchLinkTbl where amazonCategory like '%" + lists.get(0) + "%';";
        ObservableList<searchIdModel> links = searchLinks(SQL2);
//        String category = "";
        //商品説明欄の形素体解析
        List<Token> tokens = tokenizer.tokenize(text);

        //カテゴリーデータの形素体解析
        for (String list : lists) {
        	List<Token> ctTokens = tokenizer.tokenize(list);
        	 for (Token token : ctTokens) {
        		 if(token.getSurfaceForm().length() > 1) {
        			 ctLists0.add(token.getSurfaceForm());
        		 }
        	 }
        }

        //商品説明欄の解析結果を元に、カテゴリーデータから検索
        List<String> ctLists = ctLists0.stream().distinct().collect(Collectors.toList());
        List<String> outputs2 = new ArrayList<String>();
        for (Token token : tokens) {
        	if(token.getSurfaceForm().length() > 2) {
        		String SQL1 = "select * from categoryTbl where category like '%" + token.getSurfaceForm() + "%';";
        		ObservableList<categoryModel> datas = searchCategory(SQL1);
        		for(categoryModel data : datas) {
					outputs.add(data.getCategory());
        		}
        	}
        }

        //検索結果よりヒット数をランキング
		java.util.Map<String, Integer> map = outputs.stream().collect(
                Collectors.groupingBy(
                        //MapのキーにはListの要素をそのままセットする
                        Function.identity(),
                        //Mapの値にはListの要素を1に置き換えて、それをカウントするようにする
                        Collectors.summingInt(s->1))
                );

		//ランキング結果を別配列に格納
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
			}
		}

		//文章解析で残った結果の中で、カテゴリーにヒットするものを検索
		//検索結果を別配列に格納
        for(String data : countOutputs) {
        	for(String list : ctLists) {
        		if(data.contains(list)) {
        			ctOutputs.add(data);
        		}
        	}
        }
        //カテゴリーを用いた検索でヒット数のランキングを行う
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
		}
		for(Entry<String, Integer> entry : map2.entrySet()) {
			if(entry.getValue() == countCheck2) {
				ctOutputs2.add(entry.getKey());
			}
		}

		//上記の検索アルゴリズムでヒットしなかった場合の検索方法
		if(ctOutputs2.size() == 0) {
//			String SQL2 = "select * from searchLinkTbl where amazonCategory like '%" + lists.get(0) + "%';";
//			ObservableList<searchIdModel> links = searchLinks(SQL2);
			String SQL3 = "select * from categoryTbl where searchId='" + links.get(0).getCategoryId() + "';";
			ObservableList<categoryModel> datas = searchCategory(SQL3);
			List<String> kuroutouts = new ArrayList<String>();
			Map<String, Integer> checkList = new HashMap<String, Integer>();
			for(categoryModel data : datas) {
				for(String list : lists) {
					if(data.getCategory().contains(list)) {
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
					ctOutputs2.add(kuroutput);
				}
			}
		}

		//算出したカテゴリーを格納
		//ヒットしない場合はその他を返す
		if(ctOutputs2.size() == 0) {
			if(links.get(0).getCategoryId() == "1") {
				category = " オークション > 家電、AV、カメラ > その他";
			}else if(links.get(0).getCategoryId() == "2") {
				category = " オークション > コンピュータ > その他";
			}else if(links.get(0).getCategoryId() == "3") {
				category = " オークション > 事務、店舗用品 > その他";
			}else if(links.get(0).getCategoryId() == "4") {
				category = " オークション > 住まい、インテリア > その他";
			}else if(links.get(0).getCategoryId() == "5") {
				category = " オークション > ビューティー、ヘルスケア > その他";
			}else if(links.get(0).getCategoryId() == "6") {
				category = " オークション > ベビー用品 > その他";
			}else if(links.get(0).getCategoryId() == "7") {
				category = " オークション > おもちゃ、ゲーム > その他";
			}else if(links.get(0).getCategoryId() == "8") {
				category = " オークション > ファッション > ファッション小物 > その他";
			}else if(links.get(0).getCategoryId() == "9") {
				category = " オークション > アクセサリー、時計 > 時計用工具 > その他";
			}else if(links.get(0).getCategoryId() == "10") {
				category = " オークション > スポーツ、レジャー > その他";
			}else if(links.get(0).getCategoryId() == "11") {
				category = " オークション > 自動車、オートバイ > アクセサリー > その他";
			}
		}
		else {
			category = ctOutputs2.get(0);
		}

		String SQL4 = "select * from categoryTbl where category='" + category + "';";
		ObservableList<categoryModel> outPutId = searchCategory(SQL4);
		categoryId = outPutId.get(0).getCategoryId();
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
