
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatasResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private List<?> data;

    public DatasResult() {
    }

    public DatasResult(List<?> data) {
	this.data = data;
    }

    public List<?> getData() {
	return data;
    }

    public void setData(List<?> data) {
	this.data = data;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static DatasResult formatToList(String jsonData, Class<?> clazz) {
	try {
	    JsonNode jsonNode = MAPPER.readTree(jsonData);
	    JsonNode data = jsonNode.get("data");
	    List<?> list = null;
	    if (data.isArray() && data.size() > 0) {
		list = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
	    }
	    return new DatasResult(list);
	} catch (Exception e) {
	    return null;
	}
    }
}