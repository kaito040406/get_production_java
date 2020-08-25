package application;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import dbj.SqliteDBJ;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.priceModel;

public class PriceController {
	@FXML Button load;
    @FXML private TextField price1000;
    @FXML private TextField price2000;
    @FXML private TextField price3000;
    @FXML private TextField price4000;
    @FXML private TextField price5000;
    @FXML private TextField price6000;
    @FXML private TextField price7000;
    @FXML private TextField price8000;
    @FXML private TextField price9000;
    @FXML private TextField price10000;
    @FXML private TextField price15000;
    @FXML private TextField price20000;
    @FXML private TextField over;

    @FXML Button priceSave;

    @FXML
    public void onLoadP(ActionEvent e) throws ClassNotFoundException, SQLException {
        ObservableList<priceModel> priceLists = SqliteDBJ.searchAllDataPrice();

        for(priceModel priceList : priceLists) {
        	if(priceList.getPrice() == 1000) {
        		price1000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 2000) {
        		price2000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 3000) {
        		price3000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 4000) {
        		price4000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 5000) {
        		price5000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 6000) {
        		price6000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 7000) {
        		price7000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 8000) {
        		price8000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 9000) {
        		price9000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 10000) {
        		price10000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 15000) {
        		price15000.setText(Float.toString(priceList.getCallPrice()));
        	}
        	else if(priceList.getPrice() == 20000) {
        		price20000.setText(Float.toString(priceList.getCallPrice()));
        	}else {
        		over.setText(Float.toString(priceList.getCallPrice()));
        	}
        }
    }

    public void onSave(ActionEvent e) throws ClassNotFoundException, SQLException {
    	boolean valCheck = false;
    	int valCheck2 = 0;
    	Map<Integer, String> inputLists = new HashMap<Integer ,String>();
    	inputLists.put(1000,price1000.getText());
    	inputLists.put(2000,price2000.getText());
    	inputLists.put(3000,price3000.getText());
    	inputLists.put(4000,price4000.getText());
    	inputLists.put(5000,price5000.getText());
    	inputLists.put(6000,price6000.getText());
    	inputLists.put(7000,price7000.getText());
    	inputLists.put(8000,price8000.getText());
    	inputLists.put(9000,price9000.getText());
    	inputLists.put(10000,price10000.getText());
    	inputLists.put(15000,price15000.getText());
    	inputLists.put(20000,price20000.getText());
    	inputLists.put(10000000,price20000.getText());
    	for(Map.Entry<Integer, String> inputList : inputLists.entrySet()) {
    		valCheck = inputCheck(inputList.getValue());
//    		System.out.println(valCheck);
    		if (!valCheck) {
    			valCheck2++;
    		}
    	}
		if(valCheck2 == 0) {
			SqliteDBJ.updataPrice(inputLists);
		}else {
			System.out.println("入力値エラー");
		}
    }

    public boolean inputCheck(String inputText) {
    	String check = "[0-9.]*";
    	if(!inputText.equals("") && inputText.matches(check) ) {
	    		return true;
    	}else {
    		return false;
    	}
    }
}
