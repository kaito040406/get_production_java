package model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class priceModel {
	public final SimpleIntegerProperty price;
	public final SimpleFloatProperty callPrice;

	public priceModel(int price, float callPrice) {
		this.price = new SimpleIntegerProperty(price);
		this.callPrice = new SimpleFloatProperty(callPrice);
	}

	public int getPrice() {
		return this.price.get();
	}
	public void setPrice(int price) {
		this.price.set(price);
	}
	public float getCallPrice() {
		return this.callPrice.get();
	}
	public void setCallPrice(float callPrice) {
		this.callPrice.set(callPrice);
	}

}
