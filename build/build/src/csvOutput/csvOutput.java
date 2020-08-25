package csvOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import dbj.SqliteDBJ;
import javafx.collections.ObservableList;
import model.priceModel;
import model.productionModel;

public class csvOutput {
	public static void output() throws ClassNotFoundException, SQLException, IOException {
		ObservableList<productionModel> Datas = SqliteDBJ.searchAllData();
		ObservableList<priceModel> prices = SqliteDBJ.searchAllDataPrice();
		FileWriter fileWriter = new FileWriter("CSV/output.csv");
		BigDecimal sellPrice = null;
		int i = 0;
		for(productionModel Data :Datas) {
			if(!Data.getPrice().equals("")){
				for(priceModel price :prices) {
					if(Integer.parseInt(Data.getPrice()) < price.getPrice()) {
						BigDecimal priceData1 = new BigDecimal(Data.getPrice());
						BigDecimal priceData2 = new BigDecimal(price.getCallPrice());
						sellPrice = priceData1.multiply(priceData2).setScale(0, BigDecimal.ROUND_UP);
						break;
					}
				}
			}
			if(!Data.getPrice().equals("") || Data.getPrice().length() != 0) {
				i = i + 1;
				fileWriter.append(Integer.toString(i));
				fileWriter.append(",");
				fileWriter.append(Data.getAsin());
				fileWriter.append(",");
				fileWriter.append(Data.getName());
				fileWriter.append(",");
				fileWriter.append(Data.getMaker());
				fileWriter.append(",");
				fileWriter.append(Data.getName());
				fileWriter.append(",");
				fileWriter.append(Data.getMemo().replace(",", ""));
				fileWriter.append(",");
				fileWriter.append(Data.getPrice());
				fileWriter.append(",");
				fileWriter.append(sellPrice.toString());
				fileWriter.append(",");
				fileWriter.append("");
				fileWriter.append(",");
				fileWriter.append(Data.getImage());
				fileWriter.append(",");
				fileWriter.append("");
				fileWriter.append(",");
				fileWriter.append("");
				fileWriter.append(",");
				fileWriter.append("");
				fileWriter.append(",");
				fileWriter.append(Data.getUrl());
				fileWriter.append(",");
				fileWriter.append(Data.getCategoryId());
				fileWriter.append(",");
				fileWriter.append(Data.getYCatedory());
				fileWriter.append(",");
				fileWriter.append(Data.getCategory());
				fileWriter.append(",");
				fileWriter.append("-");
				fileWriter.append(",");
				fileWriter.append("-");
				fileWriter.append(",");
				fileWriter.append("-");
				fileWriter.append(",");
				fileWriter.append("-");
				fileWriter.append(",");
	//			fileWriter.append(Data.getBland());
	//			fileWriter.append(",");
				fileWriter.append("\r\n");
			}
		}
		try {
	        fileWriter.flush();
	        fileWriter.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}

}
