package dbj;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ngwordModel;
import model.productionModel;
import model.searchIdModel;

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

	//NGワードの全検索
	  private static ObservableList<ngwordModel> searchAllNgwordList(ResultSet rs) throws SQLException, ClassNotFoundException {
	    ObservableList<ngwordModel> data = FXCollections.observableArrayList();
	    while (rs.next()){
	      data.add(new ngwordModel(
	      rs.getString("word"),
	      rs.getString("level")));
	    }
	      return data;
	  }
	//サーチリンクの全検索
	  private static ObservableList<searchIdModel> searchAllLinkList(ResultSet rs) throws SQLException, ClassNotFoundException {
		  ObservableList<searchIdModel> data = FXCollections.observableArrayList();
		    while (rs.next()){
		      data.add(new searchIdModel(
		      rs.getString("amazonCategory"),
		      rs.getString("yahooCategory"),
		      rs.getString("categoryId")));
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

	  public static ObservableList<productionModel> searchAllData() throws SQLException, ClassNotFoundException {
		    String sql = "SELECT * FROM productionTbl";
		    try {
		      ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<productionModel> data = searchAllDataList(rs);
		      return data;
		    } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
		  }

	  //NGワードの全検索
	  public static ObservableList<ngwordModel> searchAllDataNg() throws SQLException, ClassNotFoundException {
		    String sql = "SELECT * FROM ngWordTbl";
		    try {
		      ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<ngwordModel> data = searchAllNgwordList(rs);
		      return data;
		    } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
		  }
	  //サーチリンクの全検索
	  public static ObservableList<searchIdModel> searchAllDataLink() throws SQLException, ClassNotFoundException {
		  String sql = "SELECT * FROM searchLinkTbl";
		  try {
			  ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<searchIdModel> data = searchAllLinkList(rs);
		      return data;
		  } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
	  }

}
