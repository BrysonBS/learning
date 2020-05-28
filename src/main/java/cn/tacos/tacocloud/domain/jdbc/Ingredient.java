package cn.tacos.tacocloud.domain.jdbc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {
    private final String id;
    private final String name;
    private final Color color;
    public static enum Color{
        RED,ORANGE,YELLOW,GREEN,CYAN,BLUE,PURPLE
    }
}
