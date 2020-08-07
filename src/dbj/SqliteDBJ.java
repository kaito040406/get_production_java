package dbj;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.productionModel;

public class SqliteDBJ {
	// 登録
	  public static void insertData (String asin, String name, String url, String memo, String price, String category, String maker, String bland ,String date, String categoryId, String yCategory) throws SQLException, ClassNotFoundException {
	    String insertStmt;
	    // SQL文の作成
	    insertStmt = "INSERT INTO productionTbl" +
	                  "(asin, name, url, memo, price, category, maker ,bland, date, categoryId, yCategory)" +
	                  "VALUES('" + asin + "','" + name + "','" + url + "','" + memo + "','" + price + "','" + category + "','" + maker + "','" + bland + "','" +   date + "','" +  categoryId + "','" + yCategory +"')";
	    try {
	      // SqliteDBクラスへSQLを発行
	      SqliteDB.dbUpdate(insertStmt);
	    } catch (SQLException ex) {
	        System.out.println("登録処理に失敗しました!。\n" + ex );
	        throw ex;
	    }
	  }

	  private static ObservableList<productionModel> searchAllDataList(ResultSet rs) throws SQLException, ClassNotFoundException {
		    ObservableList<productionModel> data = FXCollections.observableArrayList();
		    while (rs.next()){
		      data.add(new productionModel(
		      rs.getInt("id"),
		      rs.getString("asin"),
		      rs.getString("name"),
		      rs.getString("memo"),
		      rs.getString("url"),
		      rs.getString("price"),
		      rs.getString("category"),
		      rs.getString("maker"),
		      rs.getString("bland"),
		      rs.getString("date"),
		      rs.getString("categoryId"),
		      rs.getString("yCategory")));
		    }
		      return data;
		  }

	  public static boolean dataDelete() throws SQLException, ClassNotFoundException{
		  String delSql;
		  delSql = "DELETE FROM productionTbl";
		  try {
			  SqliteDB.dbUpdate(delSql);
		  }catch(SQLException ex) {
			  throw ex;
		  }
		  return false;
	  }

}
