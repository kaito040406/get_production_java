package test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import getPage.Scra;

public class asin3 {

	public static void main(String[] args) throws ClassNotFoundException, IndexOutOfBoundsException, SQLException, IOException, InterruptedException {
//		String url = "https://www.amazon.co.jp/%E3%82%A2%E3%82%A4%E3%83%AA%E3%82%B9%E3%82%AA%E3%83%BC%E3%83%A4%E3%83%9E-AV%E3%83%9C%E3%83%BC%E3%83%89-%E5%B9%8573-2x%E5%A5%A5%E8%A1%8C29x%E9%AB%98%E3%81%9536-6cm-%E3%82%AA%E3%83%95%E3%83%9B%E3%83%AF%E3%82%A4%E3%83%88-MDB-3S/dp/B076LD8X46/ref=sr_1_1?dchild=1&fst=as%3Aoff&qid=1598267132&refinements=p_76%3A2227292051&rnid=2227291051&rps=1&s=kitchen&sr=1-1";
//		List<String> accessUrl = new ArrayList<>();
//		accessUrl.add(url);
//		Detail productionDetail = new Detail(accessUrl);
		String url ="https://www.amazon.co.jp/s?i=kitchen&bbn=392219011&rh=n%3A3828871%2Cn%3A3839151%2Cn%3A16428011%2Cn%3A392219011%2Cp_76%3A2227292051&dc&fst=as%3Aoff&qid=1598267132&rnid=2227291051&ref=sr_pg_1";
		List<String> accessUrl = new ArrayList<>();
		accessUrl.add(url);
		Scra purodDetail = new Scra(url,1,1);
	}

}
