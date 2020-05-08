package q003;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.TreeMap;

/**
 * Q003 集計と並べ替え
 *
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 *
 * 出力形式:単語=数
 *
[出力イメージ]
（省略）
highest=1
I=3
if=2
ignorance=1
（省略）

 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
    /**
     * データファイルを開く
     * resources/q003/data.txt
     */
    private static InputStream openDataFile() {
        return Q003.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) throws Exception {
        /** 
         *  単語のTreeMapを作成(TreeMapにすることでkeyでのソートを自動で行ってくれる)
         *  ・key：単語名
         *  ・value：カウント数
        */
        TreeMap<String, Integer> wordList = new TreeMap<String, Integer>();

        try {
            InputStream opf = openDataFile();
            InputStreamReader isr =  new InputStreamReader(opf, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            // dataファイルを1行ずつ読み込み
            String line;
            while ((line = br.readLine()) != null) {
                // 英文を単語ごとに分割
                // カンマ、ピリオド、コロン、セミコロン、スペースで分割
                Pattern ptr = Pattern.compile("[,.:;\\s]+");
                String[] strdata = ptr.split(line);

                /**
                 *  単語ごとにwordListと照合
                 *  ・新規の場合はwordListに追加登録
                 *  ・登録済みの場合はカウントアップ
                */ 
                for (String str: strdata){
                    int count = 1;
                    String lowerstr = "";

                    // 単品のハイフンは取り除く
                    // (split時の正規表現でやりたかったが"dead-end"も分割されてしまうため)
                    if(str.equals("–")) {
                        continue;
                    }
                    // I以外は小文字変換
                    else if(str.equals("I")) {
                        lowerstr = str;
                    }
                    else {
                        lowerstr = str.toLowerCase();
                    }

                    // 登録済みの場合はカウントアップ
                    if (wordList.containsKey(lowerstr)) {
                        count += wordList.get(lowerstr);
                    }

                    wordList.put(lowerstr, count);
                }
            }

            // BufferedReaderをcloseしてリソース解放
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 単語名とカウント数の表示
        for (String key : wordList.keySet()) {
            System.out.println(key + ": " + wordList.get(key));
        }
    }
}
// 完成までの時間: 2時間 40分