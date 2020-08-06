package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.categoryModel;
public class search {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		String SQL = "select * from categoryTbl where category like '%バッグ%' and category like '%エコバッグ%';";
//		searchCategory(SQL);
		ObservableList<categoryModel> datas = searchCategory(SQL);
		for(categoryModel data : datas) {
			System.out.println(data.getCategory());
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
		      rs.getString("category")
		      ));
		    }
		return data;
	}

}
