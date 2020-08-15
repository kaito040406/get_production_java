package csvOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import dbj.SqliteDBJ;
import javafx.collections.ObservableList;
import model.productionModel;

public class csvOutput {
	public static void output() throws ClassNotFoundException, SQLException, IOException {
		ObservableList<productionModel> Datas = SqliteDBJ.searchAllData();
		FileWriter fileWriter = new FileWriter("CSV/test.csv");
		for(productionModel Data :Datas) {
			fileWriter.append(Data.getAsin());
			fileWriter.append(",");
			fileWriter.append(Data.getName());
			fileWriter.append(",");
			fileWriter.append(Data.getUrl());
			fileWriter.append(",");
			fileWriter.append(Data.getMemo());
			fileWriter.append(",");
			fileWriter.append(Data.getPrice());
			fileWriter.append(",");
			fileWriter.append(Data.getCategory());
			fileWriter.append(",");
			fileWriter.append(Data.getMaker());
			fileWriter.append(",");
			fileWriter.append(Data.getBland());
			fileWriter.append(",");
			fileWriter.append(Data.getCategoryId());
			fileWriter.append(",");
			fileWriter.append(Data.getYCatedory());
			fileWriter.append(",");
			fileWriter.append(Data.getImage());
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
