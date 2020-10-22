package fileDelete;

import java.io.File;

public class imageDelete {
	public static void delete() {
	    File dir = new File("images");
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	      if (files[i].exists() && files[i].isFile()) {
	        files[i].delete();
	      }
	    }
	}
}
