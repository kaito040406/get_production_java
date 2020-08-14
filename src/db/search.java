package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.categoryModel;
import model.searchIdModel;
public class search {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		List<String> lists = new ArrayList<String>();
		List<String> outputs = new ArrayList<String>();
        lists.add("ホーム＆キッチン");
        lists.add("家具");
        lists.add("収納家具");
        lists.add("メタルラック");
        lists.add("メタルラック本体");
        System.out.println(lists.get(0));
        String SQL1 = "select * from searchLinkTbl where amazonCategory like '%" + lists.get(0) + "%';";
		ObservableList<searchIdModel> links = searchLinks(SQL1);
		for(searchIdModel link : links) {
			System.out.println(link.getCategoryId());
		}
		String SQL2 = "select * from categoryTbl where searchId='" + links.get(0).getCategoryId() + "';";
		System.out.println(SQL2);
		ObservableList<categoryModel> datas = searchCategory(SQL2);
		System.out.println(datas.size());
		for(categoryModel data : datas) {
			for(String list : lists) {
				if(data.getCategory().contains(list)) {
//					System.out.println(data.getCategory());
					outputs.add(data.getCategory());
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
		for(Entry<String, Integer> entry : map.entrySet()) {
		    System.out.println(entry.getKey());
		    System.out.println(entry.getValue());
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
