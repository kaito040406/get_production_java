package db;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import dbj.SqliteDBJ;
import javafx.collections.ObservableList;
import model.ngwordModel;

public class csvOutput {

	@SuppressWarnings("null")
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		ObservableList<ngwordModel> outputDatas = null;
		ObservableList<ngwordModel> Datas = SqliteDBJ.searchAllDataNg();
		FileWriter fileWriter = new FileWriter("CSV/test.csv");
		for(ngwordModel Data :Datas) {
//			outputDatas.addAll(new ngwordModel(Data.getWord(),Data.getLevel()));
			fileWriter.append(Data.getWord());
			fileWriter.append(",");
			fileWriter.append(Data.getLevel());
			fileWriter.append("\r\n");
		}
		try {
	        fileWriter.flush();
	        fileWriter.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }

	}

}
