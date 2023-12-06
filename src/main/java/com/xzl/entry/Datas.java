package com.xzl.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xzl.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName : Data
 * Package : com.xzl.entry
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 13:11
 * @Version: jdk 1.8
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hlccx01")
public class Datas extends BaseEntity implements Serializable {

    @JsonProperty("actual_quantity")
    private String actualQuantity;

    @JsonProperty("deduction_impurity")
    private String deductionImpurity;

    @JsonProperty("deduction_pack")
    private String deductionPack;

    @JsonProperty("deduction_water")
    private String deductionWater;

    @JsonProperty("driver_name")
    private String driverName;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("in_weight")
    private String inWeight;

    @JsonProperty("in_weight_time")
    private String inWeightTime;

    @JsonProperty("load_remark")
    private String loadRemark;

    @JsonProperty("net")
    private String net;

    @JsonProperty("order_remark")
    private String orderRemark;

    @JsonProperty("out_weight")
    private String outWeight;

    @JsonProperty("out_weight_time")
    private String outWeightTime;

    @JsonProperty("provider_name")
    private String providerName;

    @JsonProperty("receiver_name")
    private String receiverName;

    @JsonProperty("settle_net")
    private String settleNet;

    @JsonProperty("ship_from_name")
    private String shipFromName;

    @JsonProperty("ship_to_name")
    private String shipToName;

    @JsonProperty("sub_price")
    private String subPrice;

    @JsonProperty("tax_sum")
    private String taxSum;

    @JsonProperty("total_deduction")
    private String totalDeduction;

    @JsonProperty("transporter_name")
    private String transporterName;

    @JsonProperty("truck_number")
    private String truckNumber;

    @JsonProperty("weight_number")
    private String weightNumber;
}
