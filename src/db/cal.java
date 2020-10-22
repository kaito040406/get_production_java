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

	public static void main() throws ClassNotFoundException, SQLException {
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

		java.util.Map<String, Integer> map = outputs.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.summingInt(s->1))
                );
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

        for(String data : countOutputs) {
        	for(String list : ctLists) {
        		if(data.contains(list)) {
        			ctOutputs.add(data);
        		}
        	}
        }

		java.util.Map<String, Integer> map2 = ctOutputs.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
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
						outputs2.add(data.getCategory());
					}
				}
			}
			java.util.Map<String, Integer> map3 = outputs2.stream().collect(
	                Collectors.groupingBy(
	                        Function.identity(),
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
