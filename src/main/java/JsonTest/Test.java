package JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Test {
    public static void main (String args[]) {
        Test test = new Test();
        test.test();

    }

    public void test () {
        String JsonData = "{\"version\":1.0,\"object\":[{\"a1\":\"aa\"},{\"a1\":\"bb\"}],\"deploy\":{\"applicationName\":\"app\",\"namespace\":\"com.abc.xyz\"}}";

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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
