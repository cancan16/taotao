import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @ClassName Jackson
 * @Author volc
 * @Description 解析json字符串中的节点对象
 * @Date 2017年6月2日 上午1:51:26
 */
public class Jackson2 {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException, IOException {
	String json = "{\"data\":[{\"title\":\"123\",\"result\":{\"name\":\"volc\"}},{\"title\":\"123\",\"result\":{\"name\":\"volc\"}}]}";
	// 解析json生成前端所需要的json数据
	JsonNode jsonNode = MAPPER.readTree(json);
	ArrayNode arrayNode = (ArrayNode) jsonNode.get("data");
	// 定义含有map集合的list用于组织封装想要的格式
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	for (JsonNode data : arrayNode) {
	    // 有顺序的hashmap
	    Map<String, Object> map = new LinkedHashMap<String, Object>();
	    map.put("result", data.get("result").get("name").asText()); // 获取节点对象并获取值
	    result.add(map);
	}
	System.out.println(MAPPER.writeValueAsString(result));
    }

}
