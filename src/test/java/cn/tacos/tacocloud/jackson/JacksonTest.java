package cn.tacos.tacocloud.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JacksonTest {
    //测试
    @Test
    public void JsonAnyGetter() throws JsonProcessingException {
        ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void JsonGetter() throws JsonProcessingException {
        MyBean bean = new MyBean(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }
    @Test
    public void JsonPropertyOrder() throws JsonProcessingException {
        OrderBean bean = new OrderBean(1, "Order bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void JsonRawValue() throws JsonProcessingException {
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void JsonValue() throws JsonProcessingException{
        ValueBean bean = new ValueBean(1,"Value Bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void jsonRootName() throws JsonProcessingException{
        RootBean bean = new RootBean(1,"Root Bean");
        String result = new ObjectMapper()
                .enable(SerializationFeature.WRAP_ROOT_VALUE) //需要设置此属性
                .writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void jsonSerialize() throws JsonProcessingException{
        SerializerBean bean = new SerializerBean("Serializer Bean", LocalDate.now());
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void jsonCreator() throws JsonProcessingException{
        String json = "{\"id\":1,\"theName\":\"Creator bean\"}";
        CreatorBean bean = new ObjectMapper()
                .readerFor(CreatorBean.class) //反序列化类型
                .readValue(json); //json字符串
        System.out.println(bean.toString());
    }

    @Test
    public void jacksonInject() throws IOException{
        String json = "{\"name\":\"Inject bean\"}";
        InjectableValues inject = new InjectableValues.Std()
                .addValue(int.class, 1); //添加值
        InjectBean bean = new ObjectMapper().reader(inject)
                .forType(InjectBean.class)
                .readValue(json);
        System.out.println(bean);
    }

    @Test
    public void jsonAnySetter() throws IOException {
        String json = "{\"name\":\"AnySetter bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";
        AnySetterBean bean = new ObjectMapper()
                .readerFor(AnySetterBean.class)
                .readValue(json);
        System.out.println(bean);
    }

    @Test
    public void jsonSetter() throws IOException {
        String json = "{\"id\":1,\"theName\":\"Setter bean\"}";
        SetterBean bean = new ObjectMapper()
                .readerFor(SetterBean.class)
                .readValue(json);
        System.out.println(bean);
    }
    @Test
    public void jsonDeserialize() throws IOException {
        String json = "{\"name\":\"party\",\"localDate\":\"2020-04-27\"}";
        DeserializerBean bean = new ObjectMapper()
                .readerFor(DeserializerBean.class)
                .readValue(json);
        System.out.println(bean);
    }

    @Test
    public void jsonAlias() throws IOException {
        String json = "{\"f_name\": \"John\", \"lastName\": \"Green\"}";
        AliasBean bean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
        System.out.println(bean);
    }

    @Test
    public void jsonAutoDetect() throws JsonProcessingException {
        PrivateBean bean = new PrivateBean(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }
    @Test
    public void whenSerializingPolymorphic_thenCorrect() throws JsonProcessingException {
        Zoo.Dog dog = new Zoo.Dog("lacy");
        Zoo zoo = new Zoo(dog);
        String result = new ObjectMapper().writeValueAsString(zoo);
        System.out.println(result);
    }
    @Test
    public void deserializingPolymorphic() throws IOException {
        String json = "{\"animal\":{\"name\":\"lacy\",\"type\":\"cat\"}}";
        Zoo zoo = new ObjectMapper().readerFor(Zoo.class).readValue(json);
        System.out.println(zoo);
    }

    @Test
    public void jsonProperty() throws IOException {
        PropertyBean bean = new PropertyBean(1, "Property bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
        PropertyBean resultBean = new ObjectMapper().readerFor(PropertyBean.class).readValue(result);
        System.out.println(resultBean);
    }

    @Test
    public void jsonFormat() throws Exception {
        FormatBean event = new FormatBean("party", new Date());
        String result = new ObjectMapper().writeValueAsString(event);
        System.out.println(result);
    }

    @Test
    public void JsonUnwrapped() throws Exception {
        UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        UnwrappedUser user = new UnwrappedUser(1, name);
        String result = new ObjectMapper().writeValueAsString(user);
        System.out.println(result);
    }

    @Test
    public void jsonView() throws JsonProcessingException {
        ViewBean item = new ViewBean(2, "book", "John");
        String result = new ObjectMapper()
                //设置视图: 此处序列化只序列化配置@JsonView(Views.Public.class)的字段及其父类
                .writerWithView(Views.Public.class)
                .writeValueAsString(item);
        System.out.println(result);

        String result2 = new ObjectMapper()
                //设置视图: 只序列化配置@JsonView(Views.Internal.class)
                //因为Internal extends Public 因此也会序列化父类Public的配置
                .writerWithView(Views.Internal.class)
                .writeValueAsString(item);
        System.out.println(result2);
    }

    @Test
    public void jacksonReferenceAnnotation() throws JsonProcessingException {
        UserWithRef user = UserWithRef.of(1, "John");
        ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writeValueAsString(item);
        System.out.println(result);
    }

    @Test
    public void jsonIdentityInfo() throws JsonProcessingException {
        UserWithIdentity user = UserWithIdentity.of(1, "John");
        ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
        user.addItem(item);
        String result = new ObjectMapper().writeValueAsString(item);
        System.out.println(result);
    }

    @Test
    public void jsonFilter() throws JsonProcessingException {
        FilterBean bean = new FilterBean(1, "Filter bean");
        //创建过滤器
        FilterProvider filters
                = new SimpleFilterProvider().addFilter(
                "myFilter", //添加过滤器名称
                SimpleBeanPropertyFilter.filterOutAllExcept("name") //指定序列化的属性
                //SimpleBeanPropertyFilter.serializeAllExcept("id")) //指定不序列化的属性
        );
        String result = new ObjectMapper()
                .writer(filters)  //添加过滤器
                .writeValueAsString(bean);

        System.out.println(result);
    }

    @Test
    public void customAnnotation() throws JsonProcessingException {
        CustomBean bean = new CustomBean(1, "Custom bean", null);
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    @Test
    public void mixInAnnotation() throws JsonProcessingException {
        Item item = new Item(1, "book", null);
        String result = new ObjectMapper().writeValueAsString(item);
        System.out.println(result);

        ObjectMapper mapper = new ObjectMapper();
        //通过MyMixInForIgnoreType.class类型忽略,动态的设置忽略Item.User.class类型
        mapper.addMixIn(Item.User.class, MyMixInForIgnoreType.class);
        result = mapper.writeValueAsString(item);

        System.out.println(result);
    }

    @Test
    public void whenDisablingAllAnnotations_thenAllDisabled() throws IOException {
        DisableBean bean = new DisableBean(1, null);
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
        result = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS) //禁用Jackson注解
                .writeValueAsString(bean);
        System.out.println(result);
    }
}
