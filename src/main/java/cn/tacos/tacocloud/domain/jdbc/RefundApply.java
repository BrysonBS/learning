package cn.tacos.tacocloud.domain.jdbc;

import lombok.Data;

import java.util.List;

@Data
public class RefundApply {
    private Refund refund;
    private List<RefundDetail> details;
}
