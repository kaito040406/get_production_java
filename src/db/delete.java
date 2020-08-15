package db;

import java.io.File;

public class delete {

	public static void main(String[] args) {
	    File dir = new File("images");
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	      if (files[i].exists() && files[i].isFile()) {
	        files[i].delete();
	        System.out.println("ファイル削除");
	      }
	    }
	}

}
