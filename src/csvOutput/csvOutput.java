package csvOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import dbj.SqliteDBJ;
import javafx.collections.ObservableList;
import model.ngwordModel;

public class csvOutput {
	public static void output() throws ClassNotFoundException, SQLException, IOException {
		ObservableList<ngwordModel> Datas = SqliteDBJ.searchAllDataNg();
		FileWriter fileWriter = new FileWriter("CSV/test.csv");
		for(ngwordModel Data :Datas) {
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
