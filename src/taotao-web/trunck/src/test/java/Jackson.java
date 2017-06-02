
/**
 * @ClassName Jackson
 * @Author volc
 * @Description 解析json字符串节点为对象
 * @Date 2017年6月2日 上午1:51:26
 */
public class Jackson {

    public static void main(String[] args) {
	String json = "{\"data\":[{\"title\":\"123\",\"result\":{\"name\":\"volc\"}},{\"title\":\"123\",\"result\":{\"name\":\"volc\"}}]}";
	DatasResult dataResult = DatasResult.formatToList(json, Data.class);
	System.out.println(dataResult);
    }

}
