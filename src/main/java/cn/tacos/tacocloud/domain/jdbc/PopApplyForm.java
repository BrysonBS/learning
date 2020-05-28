package cn.tacos.tacocloud.domain.jdbc;

import lombok.Data;

import java.util.List;

@Data
public class PopApplyForm {
    private PopApply popApply;
    private List<PopApplySon> popApplySons;
}
