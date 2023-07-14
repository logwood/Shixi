package org.example.shixi.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.Objects;

public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 将null转为一个空值
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
        MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.registerModule(new JavaTimeModule());
    }
    //构造方法用于对象的初始化。静态初始化块，用于类的初始化操作。
    //在静态初始化块中不能直接访问非static成员。
    private JsonUtil(){

    }

    /**
     * 对象转 Json
     * @param obj 对象
     * @return 转换后 json 字符串
     * @throws JsonProcessingException 抛出异常
     */
    public static String toJsonStr(Object obj) throws JsonProcessingException{
        return MAPPER.writeValueAsString(obj);
    }

    /**
     * json转为list
     * @param data json字符串
     * @param beanType bean的类型
     * @return 转换后的list
     * @param <T> 泛型
     * @throws JsonProcessingException 返回异常
     */
    public static <T> List<T> toList(String data,Class<T>beanType) throws JsonProcessingException{
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class,beanType);
        return MAPPER.readValue(data,javaType);
    }
    //也可以用TypeReference转换类型，效果相似

    /**
     * json 转为对象
     * @param data json字符串
     * @param beanType 转换类型
     * @return 转换后的对象
     * @param <T> 模板类型
     * @throws JsonProcessingException 返回异常
     */
    public static <T> T toobj(String data,Class<T>beanType)throws JsonProcessingException{
        return MAPPER.readValue(data,beanType);
    }

    /**
     * 获取 json 子节点
     * @param json json 字符串
     * @param nodePath 节点路径
     * @return 子节点
     * @throws JsonProcessingException 返回异常
     */
    public static String getNode(String json,String... nodePath) throws JsonProcessingException{
        JsonNode treeNode = MAPPER.readTree(json);
        if(Objects.nonNull(nodePath)){
            for (String nodeName : nodePath){
                treeNode = treeNode.get(nodeName);
            }
        }
        return Objects.isNull(treeNode) ? null : treeNode.toString();
    }
}
