package kr.deu.sw.lnp.whmanageapplication;

/**
 * Created by how32 on 2016-11-30.
 */

public class WHData {
    //창고 데이터베이스에서 사용하는 데이터
    private String p_num;
    private String p_name;
    private int amount;

    WHData(String p_num, String p_name, int amount) {
        this.p_num = p_num;
        this.p_name = p_name;
        this.amount = amount;
    }

    public String getP_num() {return p_num;}

    public String getP_name() {return p_name;}

    public int getAmount() {return amount;}
}
