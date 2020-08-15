package getPage;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public class Image {
	public static String getImage(String accessurl) {
		//ランダム文字列生成
        UUID filename = UUID.randomUUID();
        System.out.println(accessurl);
	    try {

	    	Thread.sleep(1000);
//	    	System.out.println(accessurl);

	        URL url = new URL(accessurl);

	        HttpURLConnection conn =
	        	(HttpURLConnection) url.openConnection();
	        conn.setAllowUserInteraction(false);
	        conn.setInstanceFollowRedirects(true);
	        conn.setRequestMethod("GET");
	        conn.connect();

	        int httpStatusCode = conn.getResponseCode();

	        if(httpStatusCode != HttpURLConnection.HTTP_OK){
	          throw new Exception();
	        }

	        // Input Stream
	        DataInputStream dataInStream
	          = new DataInputStream(
	              conn.getInputStream());

	        // Output Stream



	         DataOutputStream dataOutStream
	           = new DataOutputStream(
	            new BufferedOutputStream(
	              new FileOutputStream("images/"+ filename +".jpg")));
	        // Read Data
	        byte[] b = new byte[4096];
	        int readByte = 0;

	        while(-1 != (readByte = dataInStream.read(b))){
	          dataOutStream.write(b, 0, readByte);
	        }

	        // Close Stream
	        dataInStream.close();
	        dataOutStream.close();

	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      } catch (ProtocolException e) {
	        e.printStackTrace();
	      } catch (MalformedURLException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    return filename.toString();


	}

}
