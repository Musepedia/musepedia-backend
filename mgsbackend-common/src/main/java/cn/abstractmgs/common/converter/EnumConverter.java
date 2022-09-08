package cn.abstractmgs.common.converter;

import cn.abstractmgs.common.model.BaseEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class EnumConverter<T extends BaseEnum> extends JsonDeserializer<T> implements Converter<String, T> {

    protected Class<T> enumClass;

    protected Map<String, T> enumMap;

    @SuppressWarnings("unchecked")
    protected EnumConverter(){
        init((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public EnumConverter(Class<T> enumClass) {
        init(enumClass);
    }

    private void init(Class<T> enumClass) {
        this.enumClass = enumClass;
        this.enumMap = new HashMap<>();
        for (T enumConstant : this.enumClass.getEnumConstants()) {
            enumMap.put(enumConstant.getTextValue(), enumConstant);
        }
        // JsonDeserializer和Converter会对同一个enumClass创建多个实例
        // 使用JsonDeserializer创建的实例（这个）
        EnumConverterFactory.converterMap.put(this.enumClass, this);
    }

    @Override
    public T deserialize(JsonParser jsonParser,
                         DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return getEnum(jsonParser.getText());
    }

    @Override
    public T convert(String str) {
        return getEnum(str);
    }

    protected T getEnum(String str){
        T e = enumMap.get(str);
        Assert.notNull(e, "枚举值不存在：" + str);
        return e;
    }
}
