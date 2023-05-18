package JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;

public class Test {
    public static void main (String args[]) {
        Test test = new Test();
        test.test();


    }

    public void test () {
        String JsonData = "{\"version\":1.0,\"data\": {\"object\":[{\"a1\":\"aa\"},{\"a1\":\"bb\"}], \"date\": \"2023\"},\"deploy\":{\"applicationName\":\"app\",\"namespace\":\"com.abc.xyz\"}}";

        JsonNode rootNode = null;
        try {
            rootNode = new ObjectMapper().readTree(JsonData);
            String ns = rootNode.at("/deploy/namespace").asText();
            System.out.println(ns);
            JsonNode array = rootNode.at("/object");
            if (array.isArray()) {
                for (final JsonNode objNode : array) {
                    System.out.println(objNode.at("/a1").asText());
                }
            }

            List<MyNode> keys = getKeysInJsonUsingMaps (JsonData, new ObjectMapper());
            System.out.println(keys);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public List<MyNode> getKeysInJsonUsingMaps(String json, ObjectMapper mapper) throws JsonMappingException, JsonProcessingException {
        List<MyNode> keys = new ArrayList<>();
        Map<String, Object> jsonElements = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        getAllKeys(jsonElements, keys, "", -1);
        return keys;
    }

    private void getAllKeys(Map<String, Object> jsonElements, List<MyNode> keys, String parent, int index) {

        jsonElements.entrySet()
                .forEach(entry -> {
                    keys.add(new MyNode(parent+"/"+entry.getKey(),index) );
                    if (entry.getValue() instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) entry.getValue();
                        getAllKeys(map, keys, "/"+entry.getKey(),index);
                    } else if (entry.getValue() instanceof List) {
                        List<?> list = (List<?>) entry.getValue();
                        int count = 0;
                        for (final Object listEntry : list ) {
 //                       list.forEach(listEntry -> {
                            if (listEntry instanceof Map) {
                                Map<String, Object> map = (Map<String, Object>) listEntry;
                                getAllKeys(map, keys, parent+"/"+entry.getKey(), count++);
                            }
                        }
                    }
                });
    }
}
