package com.enums;

import com.common.enums.ICommonEnum;

import java.util.Objects;

/**
 * @author SunHong
 * @date 2017-11-13
 * 订单状态枚举
 */
public enum OrderDetailStatus implements ICommonEnum {
    //'订单状态（状态 0未处理、1已确认、4已配送、5拒绝配送、6已入库、7拒收、8过期撤废、9确认采购单）
    Unsubmitted(0, "未提交"),
    ToBeIssued(1, "待下发"),
    BusinessNotDealtWith(2, "企业未处理"),
    BeatenBack(3, "被打回"),
    Delivery(4, "已配送"),
    RefusedToDeliver(5, "拒绝配送"),
    Received(6, "已入库"),
    RefusedToAccept(7, "拒绝收货"),
    Expired(8, "过期撤废"),
    Confirmed(9, "已确认"),
    SubmitToInterfaceSuccess(11, "提交前置机成功")
    ;





    private int key;
    private String value;

    private OrderDetailStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static String getValueByKey(Integer key) {
        for (ICommonEnum<Integer> item : values()) {
            if (Objects.equals(item.getKey(), key)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static Integer getKeyByValue(String value) {
        for (ICommonEnum<Integer> item : values()) {
            if (item.getValue().equals(value)) {
                return (Integer)item.getKey();
            }
        }
        return null;
    }

    public static Integer getKeyByItemName(String itemName) {
        Integer res = null;
        try{
            res = valueOf(itemName).getKey();
        }catch(IllegalArgumentException|NullPointerException e){
        }
        return res;
    }
}
