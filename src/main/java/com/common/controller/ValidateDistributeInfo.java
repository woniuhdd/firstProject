package com.common.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.ValidateUtil;
import com.enums.ResultCode;
import com.model.DisBatch;
import com.model.DistributeInfo;
import com.model.ValidateResult;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateDistributeInfo {

    public static ValidateResult validateDistributeInfo(String distributeInfo) {
        ValidateResult validateResult = new ValidateResult();
        validateResult.setSuccess(true);

        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<>();

        try {
            Map<String, Object> distributeInfomap = (Map<String, Object>) JSON.parseObject(distributeInfo, Map.class);

            JSONArray batchList = (JSONArray) distributeInfomap.get("list");
            if (batchList == null || batchList.size() == 0) {
                resultJsonObj.put("resultCode", ResultCode.PARAM_NOT_COMPLETE.getCode());
                resultJsonObj.put("resultMsg", ResultCode.PARAM_NOT_COMPLETE.getMessage()+ "【批次信息不能为空】");
                validateResult.setJsonObject(resultJsonObj);
                validateResult.setSuccess(false);
                return validateResult;
            }

            List<DistributeInfo> distributeInfoList = JSONObject.parseArray(JSONObject.parseObject(distributeInfo).getString("list"), DistributeInfo.class);
            if (distributeInfoList == null || distributeInfoList.size() == 0) {
                resultJsonObj.put("resultCode", ResultCode.PARAM_NOT_COMPLETE.getCode());
                resultJsonObj.put("resultMsg", ResultCode.PARAM_NOT_COMPLETE.getMessage()+ "【配送信息不能为空】");
                validateResult.setJsonObject(resultJsonObj);
                validateResult.setSuccess(false);
                return validateResult;
            }

            List<String> companyDistributeIdList = new ArrayList<>();
            //相关参数验证
            for (DistributeInfo distributeInfoTemp : distributeInfoList){
                //配送数量
                int totalDisCount = 0;
                JSONObject JsonObjTemp = new JSONObject();
                List<Map<String, Object>> errorListTemp = new ArrayList<Map<String, Object>>();
                //判断企业的配送明细编号是否为空
                if (StringUtils.isEmpty(distributeInfoTemp.getCompanyDistributeId())) {
                    map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
                    map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage()+ "【企业配送明细编号不能为空】");
                    errorListTemp.add(map);
                }

                //订单明细编号orderDetailId
                if (StringUtils.isEmpty(distributeInfoTemp.getOrderDetailId())) {
                    map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
                    map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage()+ "【订单明细编号不能为空】");
                    errorListTemp.add(map);
                }

                //配送日期disTime
                if (StringUtils.isEmpty(distributeInfoTemp.getDisTime())) {
                    map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
                    map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage()+ "【配送日期不能为空】");
                    errorListTemp.add(map);
                } else {
                    if (!ValidateUtil.checkDate(distributeInfoTemp.getDisTime()) ) {
                        map.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
                        map.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage()+ "【配送日期格式不正确】");
                        errorListTemp.add(map);
                    }
                }

                //判断批号
                if (distributeInfoTemp.getBatchList() == null || distributeInfoTemp.getBatchList().size() <= 0) {
                    map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
                    map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage()+ "【企业配送批号不能为空】");
                    errorListTemp.add(map);
                }else {

                    List<String> withOutBatchInfo = new ArrayList<>();
                    List<DisBatch> disBatchList = JSONObject.parseArray(distributeInfoTemp.getBatchList().get("list").toString(), DisBatch.class);
                    //判断批号信息
                    for (DisBatch disBatch : disBatchList){
                        //判断批号是否重复
                        if (StringUtils.isEmpty(disBatch.getBatchcode())) {
                            map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
                            map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage()+ "【批号不能空】");
                            errorListTemp.add(map);
                        }else if (withOutBatchInfo.contains(disBatch.getBatchcode())) {
                            map.put("resultCode", ResultCode.PARAM_IS_INVALID.getCode());
                            map.put("resultMsg", ResultCode.PARAM_IS_INVALID.getMessage()+ "【批号重复】");
                            errorListTemp.add(map);
                        } else {
                            withOutBatchInfo.add(disBatch.getBatchcode());
                        }

                        //判断配送数量
                        if (StringUtils.isNotEmpty(disBatch.getBatchcount().toString())) {// 配送数量格式校验
                            if (!ValidateUtil.checkCount(disBatch.getBatchcount().toString())) {
                                map.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
                                map.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage()+ "【配送数量不能转为整数或者配送数量为0】");
                                errorListTemp.add(map);
                            }
                            totalDisCount += disBatch.getBatchcount();
                        } else {
                            map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
                            map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage()+ "【配送数量不能为空】");
                            errorListTemp.add(map);
                        }
                    }
                }

                if (errorListTemp != null && errorListTemp.size() > 0) {
                    JsonObjTemp.put("companyDistributeId", distributeInfoTemp.getCompanyDistributeId());
                    JsonObjTemp.put("errorReasonList", errorListTemp);
                    errorList.add(JsonObjTemp);
                } else{
                    distributeInfoTemp.setDiscount(totalDisCount);
                }
                companyDistributeIdList.add(distributeInfoTemp.getCompanyDistributeId());
            }
            //如果错误集合数量大于0 ，则提示验证失败
            if (errorList.size() > 0) {
                resultJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
                resultJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
                resultJsonObj.put("errorList", errorList);
                validateResult.setSuccess(false);
                validateResult.setJsonObject(resultJsonObj);
                return validateResult;
            } else {
                //验证本批次请求的企业主键是否重复
                validateResult = ValidateDistributeInfo.validatePrimaryKey(companyDistributeIdList);
                if (validateResult.isSuccess()) {
                    validateResult.setDistributeInfoList(distributeInfoList);
                }
            }

        }catch (Exception e){
            resultJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
            validateResult.setJsonObject(resultJsonObj);
            validateResult.setSuccess(false);
            return validateResult;
        }
        return validateResult;
    }


    public static ValidateResult validatePrimaryKey(List<String> primaryList) {
        ValidateResult validateResult = new ValidateResult();
        validateResult.setSuccess(true);

        //返回集合
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<>();

        //去重复后的配送明细编号
        List<String> listWithoutDup = new ArrayList<>();
        //重复的编号
        List<String> listDup = new ArrayList<>();
        for (String primaryId : primaryList) {
            if (listWithoutDup.contains(primaryId)) {
                //出现多次的企业配送编号
                listDup.add(primaryId);
            } else {
                //首次出现时候，插入该数组，供后续判断是否再次出现
                listWithoutDup.add(primaryId);
            }
        }
        //如果数量与原始记录一致，则无重复提交
        if (listWithoutDup.size() == primaryList.size()) {
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
            validateResult.setJsonObject(resultJsonObj);
            validateResult.setSuccess(true);
            return validateResult;
        }else {
            //如果数量与原始记录不一致，则是有重复提交
            resultJsonObj.put("resultCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultJsonObj.put("resultMsg", ResultCode.PARAM_IS_INVALID.getMessage());
            validateResult.setJsonObject(resultJsonObj);
            validateResult.setSuccess(false);
            return validateResult;
        }
    }
}
