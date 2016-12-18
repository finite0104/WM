package kr.deu.sw.lnp.whmanageapplication;

/**
 * Created by how32 on 2016-11-30.
 */

public class WHData {
    //창고 데이터베이스에서 사용하는 데이터
    private String p_id;
    private String p_name;
    private int amount;

    WHData(String p_id, String p_name, int amount) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.amount = amount;
    }

    public String getP_num() {return p_id;}

    public String getP_name() {return p_name;}

    public int getAmount() {return amount;}
}
