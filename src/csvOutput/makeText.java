package csvOutput;


public class makeText {
	public static String outputText(String rowTitle, String rowText, String text1, String text2, String text3) {
		String htmlText = "";
		htmlText = text1 + rowTitle + text2 + rowText + text3;
		return htmlText;
	}

}
