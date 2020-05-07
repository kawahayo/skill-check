package q002;

// メンバー情報を管理
public class Member{
    private int id;  //id
    private String name;   //名前
   
    public Member(int id, String name) {
      this.id = id;
      this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
