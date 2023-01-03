package com.test;

import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AssignmentJSON extends TestBaseWithReqAndRespSpecs {
    String path = (String) readProperties("assignmentPath");

    @Test
    public void assignmentColors() {
       Map<String, Object> body = new HashMap<>() {{
          put("colors", Arrays.asList(new HashMap<>() {{
              put("color", "black");
              put("category", "hue");
              put("type", "primary");
              put("code", new HashMap<>() {{
                  put("rgba", Arrays.asList(255, 255, 255, 1));
                  put("hex", "#000");
              }});
          }},
              new HashMap<>() {{
                  put("color", "white");
                  put("category", "value");
                  put("code", new HashMap<>() {{
                      put("rgba", Arrays.asList(0, 0, 0, 1));
                      put("hex", "#FFF");
                  }});
              }}
          ));
       }};

        createWorkSpacePost(path,body);
    }
}
/*
{
  "colors": [
    {
      "color": "black",
      "category": "hue",
      "type": "primary",
      "code": {
        "rgba": [
          255,
          255,
          255,
          1
        ],
        "hex": "#000"
      }
    },
    {
      "color": "white",
      "category": "value",
      "code": {
        "rgba": [
          0,
          0,
          0,
          1
        ],
        "hex": "#FFF"
      }
    }
  ]
}
*/