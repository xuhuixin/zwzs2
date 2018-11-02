package qyw.xhx.zwzs.wh;
//{"FULL_ADDR":"曹县安才楼镇榆林集0号楼0单元11层1102号","SFZY":"否"}
public class House {
    private String FULL_ADDR; //注意大小写
    private String SFZY;

    public House(String FULL_ADDR, String SFZY){
        this.FULL_ADDR=FULL_ADDR;
        this.SFZY=SFZY;
     }

    public String getFULL_ADDR() {
        return FULL_ADDR;
    }
    public void setFULL_ADDR(String FULL_ADDR){
        this.FULL_ADDR=FULL_ADDR;
    }
    public String getSFZY() {
        return SFZY;
    }
    public void setSFZY(String SFZY){
        this.SFZY=SFZY;
    }


}
