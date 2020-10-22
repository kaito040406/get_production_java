package search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.categoryModel;

public class Search {
	public static List<String> categoryList= new ArrayList<>();
	public static String categoryId;
	public static String category;
	public static void categorySearch() throws ClassNotFoundException, SQLException {
		String lastCategory =  categoryList.get(categoryList.size() - 1);
		String semiLastCategory = categoryList.get(categoryList.size() - 1);

		String SQL = "select * from categoryTbl where category like '%"+ lastCategory +"%' and category like '%"+ semiLastCategory +"%';";
//		searchCategory(SQL);
		ObservableList<categoryModel> datas = searchCategory(SQL);
		for(categoryModel data : datas) {
			categoryId = data.getCategoryId();
			category = data.getCategory();
		}
	}

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

	private static ObservableList<categoryModel> searchCategoryData(ResultSet rs) throws SQLException {
		ObservableList<categoryModel> data = FXCollections.observableArrayList();
		if (rs.next()){
		      data.add(new categoryModel(
		      rs.getString("categoryId"),
		      rs.getString("category"),
		      rs.getString("searchId")
		      ));
		    }
		return data;
	}


	public Search(List<String> categorySearchData) throws ClassNotFoundException, SQLException {
		// TODO 自動生成されたコンストラクター・スタブ
		this.categoryList = categorySearchData;
		categorySearch();
	}

}
