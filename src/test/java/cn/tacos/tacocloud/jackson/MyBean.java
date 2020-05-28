package cn.tacos.tacocloud.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor //lombok注解
public class MyBean {
    public int id;
    private String name;

    //@JsonGetter("name")
    public String getTheName() {
        return name;
    }
}

@AllArgsConstructor //lombok注解
//@JsonPropertyOrder({ "name", "id" })
class OrderBean {
    public int id;
    public String name;
}

@AllArgsConstructor //lombok注解
class RawBean {
    public String name;
    @JsonRawValue
    public String json;
}

@AllArgsConstructor //lombok注解
@Data   //lombok注解
class ValueBean{
    private int id;
    @JsonValue
    private String name;
}

@AllArgsConstructor
@Data   //lombok注解
//@JsonRootName(value = "root")
@JsonRootName(value = "root", namespace = "rootName") //2.4+新增namespace属性用于xml
class RootBean{
    private int id;
    private String name;
}

@AllArgsConstructor
@Data   //lombok注解
class SerializerBean{
    private String name;
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate localDate;
}
class CustomDateSerializer extends StdSerializer<LocalDate>{
    public CustomDateSerializer() {
        this(null); //调用带参的构造函数
    }
    public CustomDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}


@Data   //lombok注解
class CreatorBean{
    private int id;
    private String name;

    @JsonCreator
    public CreatorBean(
            @JsonProperty("id") int id,
            @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }
}

@Data   //lombok注解
class InjectBean{
    @JacksonInject
    private int id;
    private String name;
}

@Data
class AnySetterBean {
    public String name;
    private Map<String, String> properties = new HashMap<>();
    @JsonAnySetter
    public void add(String key, String value){
        properties.put(key,value);
    }
}

@Data   //lombok注解
class SetterBean{
    private int id;
    private String name;
    @JsonSetter(value = "theName") //指定使用json字符串中theName赋值
    public void setName(String name) {
        this.name = name;
    }
}

@Data   //lombok注解
class DeserializerBean{
    private String name;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate localDate;
}
class CustomDateDeserializer extends StdDeserializer<LocalDate>{
    public CustomDateDeserializer() {
        this(null);
    }
    protected CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return LocalDate.parse(jsonParser.getText(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

@Data
class AliasBean {
    @JsonAlias({ "fName", "f_name" })
    private String firstName;
    private String lastName;
}

@AllArgsConstructor //lombok注解, 只有构造函数
//@Getter //不提供Getter方法
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //等价于将private 设置为public
class PrivateBean {
    private int id;
    private String name;
    //私有属性不提供Getter方法无法被序列化,
    //@JsonAutoDetect 使用注解设置可见性后可以访问private属性进行设置
}

@ToString
class Zoo {
    public Animal animal;
    public Zoo() {}
    public Zoo(Animal animal) {
        this.animal = animal;
    }

    @JsonTypeInfo(
            //use 在序列化时标志出不同的类型用什么区分，用在反序列化时转换成响应的类型
            //此处用定义的name区分 又如默认: JsonTypeInfo.Id.CLASS 完整类名区分
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY, //包含类型元数据的一种机制
            property = "type" //property自定义的区分类型的id，默认是@Class
            //,visible = true //是否序列化/反序列化定义的type属性字段(此处是"type")
            // 如果为true则反序列化时json字符串中必须有type字段对应
    )
    @JsonSubTypes({ //添加所涉及的子类/实现类
            @JsonSubTypes.Type(value = Dog.class, name = "dog"),
            @JsonSubTypes.Type(value = Cat.class, name = "cat")
    })
    @ToString
    public static class Animal {
        public String name;
        public Animal() {
        }
        public Animal(String name) {
            this.name = name;
        }
    }

    @JsonTypeName("dog")
    public static class Dog extends Animal {
        public Dog(String name) {
            super(name);
        }
        public double barkVolume;
    }
    @JsonTypeName("cat")
    public static class Cat extends Animal {
        boolean likesCream;
        public int lives;
    }
}

@AllArgsConstructor
@NoArgsConstructor
@ToString  //lombok注解
class PropertyBean {
    public int id;
    private String name;
    @JsonProperty("name")
    public void setTheName(String name) {
        this.name = name;
    }
    @JsonProperty("name")
    public String getTheName() {
        return name;
    }
}

@AllArgsConstructor
class FormatBean {
    public String name;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    public Date eventDate;
}

@AllArgsConstructor
class UnwrappedUser {
    public int id;
    //@JsonUnwrapped
    public Name name;
    @AllArgsConstructor
    public static class Name {
        public String firstName;
        public String lastName;
    }
}

class Views {
    public static class Public {}
    public static class Internal extends Public {}
}
@AllArgsConstructor
class ViewBean {
    @JsonView(Views.Public.class)
    public int id;
    @JsonView(Views.Public.class)
    public String itemName;

    @JsonView(Views.Internal.class)
    public String ownerName;
}

@AllArgsConstructor
class ItemWithRef {
    public int id;
    public String itemName;

    @JsonManagedReference //此处需要序列化
    public UserWithRef owner;
}
//lombok注解提供一个静态的of(int id,String name)方法, 参数为使用@NonNull的属性
@RequiredArgsConstructor(staticName = "of")
class UserWithRef {
    @NonNull
    public int id;
    @NonNull
    public String name;

    @JsonBackReference //序列化时不再序列化ItemWithRef对象中的UserWithRef属性
    public List<ItemWithRef> userItems = new ArrayList<>();
    public void addItem(ItemWithRef item) {
        userItems.add(item);
    }
}

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id") //使用对象的id值作为对象
@AllArgsConstructor
class ItemWithIdentity {
    public int id;
    public String itemName;
    public UserWithIdentity owner;
}
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id") //使用对象的id值作为对象*/
@RequiredArgsConstructor(staticName = "of")
class UserWithIdentity {
    @NonNull public int id;
    @NonNull public String name;
    public List<ItemWithIdentity> userItems = new ArrayList<>();
    public void addItem(ItemWithIdentity item) {
        userItems.add(item);
    }
}

@JsonFilter("myFilter")
@AllArgsConstructor
class FilterBean {
    public int id;
    public String name;
}

//注解的注解
@Retention(RetentionPolicy.RUNTIME) //jvm加载class文件后仍然保留注解
@JacksonAnnotationsInside //自定义Jackson注解
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "id", "dateCreated" })
@interface CustomAnnotation {}

@AllArgsConstructor
@CustomAnnotation //使用自定义注解
class CustomBean {
    public int id;
    public String name;
    public Date dateCreated;
}

@AllArgsConstructor
class Item {
    public int id;
    public String itemName;
    public User owner;
    class User{}
}
@JsonIgnoreType //类型忽略
class MyMixInForIgnoreType {}

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "id" })
class DisableBean {
    public int id;
    public String name;
}