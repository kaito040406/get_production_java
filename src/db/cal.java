package db;

import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;


public class cal {

	public static void main(String[] args) {
		Tokenizer tokenizer = Tokenizer.builder().build();
        List<Token> tokens = tokenizer.tokenize("もう眠い");

        for (Token token : tokens) {
            System.out.println("====================");
            // allFeatures tokenの全ての要素を出力
            System.out.println("allFeatures: " + token.getAllFeatures());
            // partOfSpeech 品詞など形態素上で意味のある言葉を出力
            System.out.println("partOfSpeech: " + token.getPartOfSpeech());
            // position 単語の位置を出力
            System.out.println("partOfSpeech: " + token.getPosition());
            // reading カナ読みを出力
            System.out.println("reading: " + token.getReading());
            // surfaceForm 元の単語を出力
            System.out.println("surfaceFrom: " + token.getSurfaceForm());
            // allFeaturesArray allFeaturesをArray型で返す
            System.out.println("allFeaturesArray" + token.getAllFeaturesArray());
            // isKnown 辞書にある言葉かどうか
            System.out.println("isKnown: " + token.isKnown());
            // isUnknown 辞書にない言葉かどうか、isKnownと反対の結果が出力される
            System.out.println("isUnKnown: " + token.isUnknown());
            // isUser ユーザーで定義した言葉かどうか
            System.out.println("User: " + token.isUser());
        }
	}


}
