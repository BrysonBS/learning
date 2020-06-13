package cn.tacos.tacocloud.rest.client;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GettingResource {
    //private RestTemplate restTemplate = new RestTemplate();

    //keytool -export -alias tomcat -keystore mykeys.jks -file server.crt
    //keytool -import -v -trustcacerts -alias tomcat -file server.crt -storepass changeit -keystore cacerts

    //keytool -delete -alias tomcat -keystore cacerts -storepass changeit
    @Test
    public void test(){
        PopInStock pop = getPopInStockById(2);
        System.out.println(pop);

/*        PopInStock popInStock = new PopInStock();
        popInStock.setId(1);
        popInStock.setTask(1111);
        popInStock.setName("hello");
        updatePopInStock(popInStock);*/

/*        PopInStock popInStock = new PopInStock();
        popInStock.setName("hello");
        popInStock.setTask(123455);
        createPopInStock(popInStock);
        createPopInStockEntity(popInStock);
        System.out.println(createPopInStockLocation(popInStock).getPath());*/

    }
    public PopInStock getPopInStockById(int id){
        RestTemplate restTemplate = new RestTemplate();
        PopInStock pop = null;
        String url = "https://localhost:8443/api/popInStocks/{id}";
        //方式一: 直接传入id
        //pop = restTemplate.getForObject(url, PopInStock.class, id);

        //方式二: 传入map
        HashMap<String,Integer> map = new HashMap<>(1);
        map.put("id",id);
        //pop = restTemplate.getForObject(url,PopInStock.class,map);

        //方式三: 通过URI参数
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(id);
        //pop = restTemplate.getForObject(uri,PopInStock.class);

        //方式四: 通过getForEntity方法,返回ResponseEntity对象(记录了详细的响应信息)
        ResponseEntity<PopInStock> responseEntity = restTemplate.getForEntity(url,PopInStock.class,id);
        pop = responseEntity.getBody();

        return pop;
    }

    //更新
    public void updatePopInStock(PopInStock popInStock){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://localhost:8443/api/popInStocks/{id}";
        restTemplate.put(url,popInStock,popInStock.getId());
    }
    //删除
    public void deletePopInStock(int id){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://localhost:8443/api/popInStocks/{id}";
        restTemplate.delete(url,id);
    }
    //新增
    public PopInStock createPopInStock(PopInStock popInStock){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://localhost:8443/api/popInStocks";
        return restTemplate.postForObject(url,popInStock,PopInStock.class);
    }
    public URI createPopInStockLocation(PopInStock popInStock){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://localhost:8443/api/popInStocks";
        return restTemplate.postForLocation(url,popInStock);
    }
    public PopInStock createPopInStockEntity(PopInStock popInStock){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://localhost:8443/api/popInStocks";
        ResponseEntity<PopInStock> responseEntity = restTemplate.postForEntity(url,popInStock,PopInStock.class);
        return responseEntity.getBody();
    }

    @Test
    public void traverson(){
        //创建Traverson
        Traverson traverson = new Traverson(URI.create("https://localhost:8443/api"), MediaTypes.HAL_JSON);
        //获取PopInStocks
        ParameterizedTypeReference<CollectionModel<PopInStock>> popInStocksType = new ParameterizedTypeReference<>(){};
        /** 1 **/
        CollectionModel<PopInStock> popInStocks = traverson.follow("pops")//根据json字段名
                .toObject(popInStocksType);//因为泛型擦除的特性,无法传入正常的泛型类型,因此需要通过ParameterizedTypeReference
        assert popInStocks != null;
        //获取结果集
        Collection<PopInStock> collection = popInStocks.getContent();
        /** 2 **/
        Collection<PopInStock> recents = traverson.follow("pops","recents") //等价于follow("pops").follow("recents")
                .toObject(popInStocksType).getContent();
        /** 3 **/
        //获取对应的url
        String url = traverson.follow("pops").asLink().getHref();
        //再使用RestTemplate插入数据
        PopInStock popInStock = new PopInStock();
        popInStock.setName("Traverson");
        new RestTemplate().postForEntity(url,popInStock,PopInStock.class);
    }

}
