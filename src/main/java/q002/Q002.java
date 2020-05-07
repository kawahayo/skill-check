package q002;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Q002 並べ替える
 *
 * dataListに "ID,名字" の形式で20個のデータがあります。
 * これをID順に並べて表示するプログラムを記述してください。
 *
 * dataListの定義を変更してはいけません。
 *
 *
[出力結果イメージ]
1,伊藤
2,井上
（省略）
9,清水
10,鈴木
11,高橋
（省略）
20,渡辺
 */
public class Q002 {
    /**
     * データ一覧
     */
    private static final String[] dataList = {
            "8,佐藤",
            "10,鈴木",
            "11,高橋",
            "12,田中",
            "20,渡辺",
            "1,伊藤",
            "18,山本",
            "13,中村",
            "5,小林",
            "3,加藤",
            "19,吉田",
            "17,山田",
            "7,佐々木",
            "16,山口",
            "6,斉藤",
            "15,松本",
            "2,井上",
            "4,木村",
            "14,林",
            "9,清水",
            "31,xxx,aaa"
    };

    public static void main(String[] args) {
        ArrayList<Member> memberList = new ArrayList<>();

        // dataListからidとnameを抜き出してMemberに格納
        for(String data : dataList){
            String[] mdata = data.split(",",0);

            // dataがID,名前の形式ではなく過不足がある場合はエラー終了
            if(mdata.length != 2){
                System.out.println("ID,名前の形式になっていません:" + data);
                System.exit(1);
            }

            // idが数字以外の場合はエラー終了
            int id;
            try {
                id = Integer.parseInt(mdata[0]);
                memberList.add(new Member(id, mdata[1]));
            } catch (NumberFormatException nfex) {
                System.out.println("IDが数字形式になっていません:" + data);
                System.exit(1);
            }
        };

        // Member一覧をid順にソートする
        memberList.sort(Comparator.comparing(Member::getId));

        // ソートした結果を表示
        memberList.forEach(m -> System.out.println(m.getId() + "," +m.getName()));
    }
}

// 完成までの時間: 2時間 20分