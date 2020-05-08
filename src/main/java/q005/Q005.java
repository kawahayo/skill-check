package q005;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Q005 データクラスと様々な集計
 *
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 *
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 *
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 *
[出力イメージ]
部長: xx時間xx分
課長: xx時間xx分
一般: xx時間xx分
Z-7-31100: xx時間xx分
I-7-31100: xx時間xx分
T-7-30002: xx時間xx分
（省略）
194033: xx時間xx分
195052: xx時間xx分
195066: xx時間xx分
（省略）
 */
public class Q005 {
    /**
     * データファイルを開く
     * resources/q005/data.txt
     */
    private static InputStream openDataFile() {
        return Q005.class.getResourceAsStream("data.txt");
    }

    public static void main(String[] args) throws Exception {
        // WorkData管理用にArrayListを作成
        ArrayList<WorkData> workDataList = new ArrayList();

        try {
            InputStream opf = openDataFile();
            InputStreamReader isr =  new InputStreamReader(opf, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            // dataファイルを1行ずつ読み込み
            String line;
            while ((line = br.readLine()) != null) {
                // data.txtの1行目はスキップ
                Pattern ptr1st = Pattern.compile("社員番号");
                Matcher m1st = ptr1st.matcher(line);
                if(m1st.find()) {
                    continue;
                }

                // カンマで分割
                Pattern ptrcomma = Pattern.compile("[,]+");
                String[] strdata = ptrcomma.split(line);

                // データの過不足がないかチェック
                if(strdata.length != 5) {
                    System.out.println("データに過不足があります");
                    System.exit(1);
                }                

                // WorkData一覧に追加
                workDataList.add(new WorkData(strdata[0], strdata[1], strdata[2], strdata[3], Integer.parseInt(strdata[4])));
            }

            // BufferedReaderをcloseしてリソース解放
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // 作業時間で数字以外が入っていた時はエラー通知して終了
            System.out.println("作業時間は数字を入れてください");
            System.exit(1);
        }

        // 役職別の合計作業時間
        // いずれもTreeMapでやりたかったが役職だけ一般、課長、部長の順番に表示されたため、
        // 役職別だけ、キー追加した順番になるLinkedHashMapを利用
        LinkedHashMap<String, Integer> posTotalTimeList = new LinkedHashMap<String, Integer>();
        // Pコード別の合計作業時間
        TreeMap<String, Integer> pcodeTotalTimeList = new TreeMap<String, Integer>();
        // 社員番号別の合計作業時間
        TreeMap<String, Integer> numTotalTimeList = new TreeMap<String, Integer>();

        // 役職別、Pコード別、社員番号別に作業時間の合計を算出
        // 同じロジックなので、冗長になっているが、チェックする変数が違うため、別functionで共通化できず。
        for (int i=0; i < workDataList.size(); i++) {
            int poswt = workDataList.get(i).getWorkTime();
            int pcodewt = workDataList.get(i).getWorkTime();
            int numwt = workDataList.get(i).getWorkTime();

            // 登録済みの役職の場合は作業時間に追加
            if (posTotalTimeList.containsKey(workDataList.get(i).getPosition())) {
                poswt += posTotalTimeList.get(workDataList.get(i).getPosition());
            }
            // PCODE
            if (pcodeTotalTimeList.containsKey(workDataList.get(i).getpCode())) {
                pcodewt += pcodeTotalTimeList.get(workDataList.get(i).getpCode());
            }
            // 社員番号
            if (numTotalTimeList.containsKey(workDataList.get(i).getNumber())) {
                numwt += numTotalTimeList.get(workDataList.get(i).getNumber());
            }

            // 役職別、PCOD別、社員番号別で作業時間を登録
            posTotalTimeList.put(workDataList.get(i).getPosition(), poswt);
            pcodeTotalTimeList.put(workDataList.get(i).getpCode(), pcodewt);
            numTotalTimeList.put(workDataList.get(i).getNumber(), numwt);
        }

        // 役職別時間の作業時間表示
        for (String key : posTotalTimeList.keySet()) {
            System.out.println(key + ": " + getStringTime(posTotalTimeList.get(key)));
        }
        // PCODE別の作業時間表示
        for (String key : pcodeTotalTimeList.keySet()) {
            System.out.println(key + ": " + getStringTime(pcodeTotalTimeList.get(key)));
        }
        // 社員番号別の作業時間表示
        for (String key : numTotalTimeList.keySet()) {
            System.out.println(key + ": " + getStringTime(numTotalTimeList.get(key)));
        }
    }

    /**
     * 勤務時間を時間、分形式に出力する
     * data.txtの作業時間については単位の言及が無かったが、出力イメージから"分"と判断して変換する
     * @param minutes
     * @return ret
     */
    private static String getStringTime(int minutes) {
        int hour = Math.abs(minutes / 60);
        int min = Math.abs(minutes % 60);
        String ret = hour + "時間" + min + "分";
        return ret;
    }
}
// 完成までの時間: 3時間 10分