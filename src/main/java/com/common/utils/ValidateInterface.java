package com.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.enums.ResultCode;
import com.model.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class ValidateInterface {


    /**
     * 验证票据上传参数
     *
     * @return
     * @parameter
     */
    public static ValidateResult validateInvoiceList(List<ComInterfaceInvoice> comRequest) {
        //返回集合
        JSONObject returnJsonObj = new JSONObject();
        //总的错误集合
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        //设置验证对象
        ValidateResult validateResult = new ValidateResult();
        //默认成功
        validateResult.setSuccess(true);
        //验证集合是否为空
        if (comRequest == null || comRequest.size() == 0) {
            returnJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            returnJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage()+ "【JSON格式错误,未接收到相关有效信息】");
            validateResult.setJsonObject(returnJsonObj);
            validateResult.setSuccess(false);
            return validateResult;
        }
        //验证参数接口入参数量(取消)

        //1.验证发票代码发票号码是否重复传入2
        // .验证企业主键是否重复传入
        List<String> withDupIdCodeList = new ArrayList<>();//去重复后的发票代码+发票号码 md5
        List<String> dupIdCodeList = new ArrayList<>();//去重复的发票代码+发票号码 md5
        List<String> withDupKeyList = new ArrayList<>();//去重复后的企业主键
        List<String> dupKeyList = new ArrayList<>();//去重复的企业主键
        //第一次循环，记录发票代码+发票号码不唯一的记录
        // 循环校验必填以及格式
        for (ComInterfaceInvoice obj : comRequest) {
            String invoiceCode = obj.getInvoiceCode();//发票代码
            String invoiceID = obj.getInvoiceId();//发票号码
            String companyPrimaryKey = obj.getCompanyPrimaryKey();//企业主键
            String md5Info;
            if(1==obj.getInvoiceType()){
                md5Info= DigestUtils.md5Hex(invoiceCode) + invoiceID+obj.getOrgId();
            }else{
                md5Info= DigestUtils.md5Hex(invoiceCode) + invoiceID;
            }
            //md5信息
            if (withDupIdCodeList.contains(md5Info)) {
                dupIdCodeList.add(md5Info);//加入重复出现的数组中
            } else {
                withDupIdCodeList.add(md5Info);//加入唯一的的数组中
            }
            if (withDupKeyList.contains(companyPrimaryKey)) {
                dupKeyList.add(companyPrimaryKey);//加入重复出现的数组中
            } else {
                withDupKeyList.add(companyPrimaryKey);//加入唯一的的数组中
            }
        }


        //验证判断参数格式、是否非空
        for (ComInterfaceInvoice obj : comRequest) {
            JSONObject detailMap = new JSONObject();
            List<JSONObject> errorReasonList = new ArrayList<>();//错误集合
            //
            if (StringUtils.isEmpty(obj.getCompanyPrimaryKey())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【企业主键不能为空】");
                errorReasonList.add(errorMap);
            } else {
                if (dupKeyList.contains(obj.getCompanyPrimaryKey())) {//如果出现在重复的记录中，就增加失败
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.DATA_IS_WRONG.getCode());
                    errorMap.put("errorMsg", ResultCode.DATA_IS_WRONG.getMessage() + "【企业主键重复】");
                    errorReasonList.add(errorMap);
                }
            }
            //发票号+发票代码不能重复
            String invoiceCode = obj.getInvoiceCode();//发票代码
            String invoiceID = obj.getInvoiceId();//发票号码
            String md5Info;
            if(1==obj.getInvoiceType()){
                md5Info= DigestUtils.md5Hex(invoiceCode) + invoiceID+obj.getOrgId();
            }else{
                md5Info= DigestUtils.md5Hex(invoiceCode) + invoiceID;
            }
            if(dupIdCodeList.contains(md5Info)){
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.DATA_IS_WRONG.getCode());
                errorMap.put("errorMsg", ResultCode.DATA_IS_WRONG.getMessage()+"[发票号重复]");
                errorReasonList.add(errorMap);
            }
            //发票ID
            if (StringUtils.isEmpty(obj.getInvoiceId())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【发票号不能空】");
                errorReasonList.add(errorMap);
            } else {
                if(obj.getInvoiceId().trim().length()>10){
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                    errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【发票号码长度不能超过10位】");
                    errorReasonList.add(errorMap);
                }
                if (!ValidateUtil.checkInvoiceId(obj.getInvoiceId())) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
                    errorMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage()+ "【发票号码格式不正确】");
                    errorReasonList.add(errorMap);
                }
            }
            //发票代码
            if (StringUtils.isEmpty(obj.getInvoiceCode())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【发票代码不能空】");
                errorReasonList.add(errorMap);
            }else{
                if(obj.getInvoiceCode().trim().length()>12){
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                    errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【发票代码长度不能超过12位】");
                    errorReasonList.add(errorMap);
                }
            }
            //价税金额
            if (obj.getTotaltaxprice() == null) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【价税金额不能空】");
                errorReasonList.add(errorMap);
            }

            if (obj.getInvoiceDate()==null) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【开票日期不能为空或参数格式不正确】");
                errorReasonList.add(errorMap);
            }else{
                Date invoiceDate = ToolUtils.fromShortFormat(obj.getInvoiceDate());
                if (invoiceDate == null) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                    errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【开票日期不能为空或参数格式不正确】");
                    errorReasonList.add(errorMap);
                }
            }

            if (StringUtils.isEmpty(obj.getInvoiceType().toString())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【发票类型不能为空】");
                errorReasonList.add(errorMap);
            } else {
                if (!obj.getInvoiceType().equals(1) && !obj.getInvoiceType().equals(3) && !obj.getInvoiceType().equals(2)) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
                    errorMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage()+ "【发票类型必须为1、2或3】");
                    errorReasonList.add(errorMap);
                }
            }
            if (StringUtils.isEmpty(obj.getSaleName())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【销方名称不能为空】");
                errorReasonList.add(errorMap);
            }

            if (StringUtils.isEmpty(obj.getBuyName())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【购方名称不能为空】");
                errorReasonList.add(errorMap);
            }

            if (StringUtils.isNotEmpty(obj.getSaleName()) && StringUtils.isNotEmpty(obj.getBuyName())) {
                if (obj.getBuyName().equals(obj.getSaleName())) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.DATA_STATUS_ERROR.getCode());
                    errorMap.put("errorMsg", ResultCode.DATA_STATUS_ERROR.getMessage() + "【销方名称与购方名称不能相同】");
                    errorReasonList.add(errorMap);
                }
            }
            //纳税识别号
            if (StringUtils.isEmpty(obj.getSaleTaxPayerNumber())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【销售方纳税识别号不能为空】");
                errorReasonList.add(errorMap);
            }
            if (StringUtils.isEmpty(obj.getBuyTaxPayerNumber())) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【购买方纳税识别号不能为空】");
                errorReasonList.add(errorMap);
            }


            if (errorReasonList != null && errorReasonList.size() > 0) {
                detailMap.put("companyPrimaryKey", obj.getCompanyPrimaryKey());
                detailMap.put("errorReasonList", errorReasonList);
                errorList.add(detailMap);
            }
        }
        if (errorList.size() > 0) {
            returnJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            returnJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            returnJsonObj.put("errorList", errorList);
            validateResult.setJsonObject(returnJsonObj);
            validateResult.setSuccess(false);
        }
        return validateResult;
    }

    /**
     * 发票图片
     * @param
     * @return
     */
    public static ValidateResult validateImgList(List<ComInterfaceImage> imgList) {
        JSONObject resultJsonObj = new JSONObject();//返回集合
        List<JSONObject> errorList = new ArrayList<>();//错误集合
        ValidateResult validateResult = new ValidateResult();//设置验证对象
        validateResult.setSuccess(true);//默认验证成功

        //1、验证数据是否存在
        if (imgList == null || imgList.size() == 0) {
            resultJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage() + "【JSON格式错误,未接收到相关有效信息】");
            validateResult.setJsonObject(resultJsonObj);
            validateResult.setSuccess(false);
            return validateResult;
        }
        //2.判断参数数量是否超过限制（取消）

        //3、非空验证
        for (ComInterfaceImage item : imgList) {
            //单个配送明细内的错误集合
            List<Map<String, Object>> errorReasonList = new ArrayList<>();
            JSONObject detailMap = new JSONObject();

            if (StringUtils.isEmpty(item.getCompanyPrimaryKey())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【企业唯一主键不能空】");
                errorReasonList.add(errorReasonMap);
            }

            if (StringUtils.isEmpty(item.getImgUrl())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【图片URL地址不能为空】");
                errorReasonList.add(errorReasonMap);
            }
            if (StringUtils.isEmpty(item.getImageType())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【图片类型不能为空】");
                errorReasonList.add(errorReasonMap);
            }

//            if (StringUtils.isEmpty(item.getImgPrimaryID())) {
//                Map<String, Object> errorReasonMap = new HashMap<>(16);
//                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
//                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【图片与发票关联编码不能为空】");
//                errorReasonList.add(errorReasonMap);
//            }
            if (StringUtils.isEmpty(item.getInvoicePrimaryID())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【发票唯一主键不能空】");
                errorReasonList.add(errorReasonMap);
            }

            if (errorReasonList.size() > 0) {
                detailMap.put("companyPrimaryKey", item.getCompanyPrimaryKey());
                detailMap.put("errorReasonList", errorReasonList);
                errorList.add(detailMap);
            }
        }
        if (errorList.size() > 0) {
            resultJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
            resultJsonObj.put("errorList", errorList);
            validateResult.setSuccess(false);
            validateResult.setJsonObject(resultJsonObj);
            return validateResult;
        }

        return validateResult;
    }

    public static ValidateResult validateSaleList(List<ComInterfaceSaleList> saleList) {
        JSONObject resultJsonObj = new JSONObject();//返回集合
        List<JSONObject> errorList = new ArrayList<>();//错误集合
        ValidateResult validateResult = new ValidateResult();//设置验证对象
        validateResult.setSuccess(true);//默认验证成功

        //1.验证发票号码+产品ID+批号是否重复传入2.验证企业主键是否重复传入
        List<String> withDupList = new ArrayList<>();//去重复后的发票号码+产品ID+批号 md5
        List<String> dupList = new ArrayList<>();//去重复的发票号码+产品ID+批号 md5
        List<String> withDupKeyList = new ArrayList<>();//去重复后的企业主键
        List<String> dupKeyList = new ArrayList<>();//去重复的企业主键
        // 循环校验必填以及格式
        for (ComInterfaceSaleList obj : saleList) {
            String invoicePrimaryKeyId = ToolUtils.toString(obj.getInvoicePrimaryID());//发票号码
            String invoiceID = ToolUtils.toString(obj.getInvoiceID());//发票号码
            String goodsID = ToolUtils.toString(obj.getGoodsID());//产品ID
            String batchCode = ToolUtils.toString(obj.getBatchCode());//批号
            String companyPrimaryKey = ToolUtils.toString(obj.getCompanyPrimaryKey());//企业主键
            //md5信息
            String md5Info = invoiceID + DigestUtils.md5Hex(goodsID) + batchCode;
            if (withDupList.contains(md5Info)) {
                dupList.add(md5Info);//加入重复出现的数组中
            } else {
                withDupList.add(md5Info);//加入唯一的的数组中
            }

            if (withDupKeyList.contains(companyPrimaryKey)) {
                dupKeyList.add(companyPrimaryKey);//加入重复出现的数组中
            } else {
                withDupKeyList.add(companyPrimaryKey);//加入唯一的的数组中
            }
        }


        for (ComInterfaceSaleList obj : saleList) {
            List<JSONObject> errorReasonList = new ArrayList<>();//错误集合
            String invoicePrimaryKeyId = ToolUtils.toString(obj.getInvoicePrimaryID());//发票主键
            String goodsID = ToolUtils.toString(obj.getGoodsID());//产品ID
            String batchCode = ToolUtils.toString(obj.getBatchCode());//批号
            String companyPrimaryKey = ToolUtils.toString(obj.getCompanyPrimaryKey());//企业主键
            String periodDate = ToolUtils.toString(obj.getPeriodDate());//有效期
            int saleNumber = obj.getSaleNumber();//销售数量

            String md5Info = invoicePrimaryKeyId + DigestUtils.md5Hex(goodsID) + batchCode;


            JSONObject detailMap = new JSONObject();
            if (StringUtils.isEmpty(companyPrimaryKey)) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode",  ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【企业主键不能为空】");
                errorReasonList.add(errorMap);
            } else {
                if (dupKeyList.contains(companyPrimaryKey)) {//如果出现在重复的记录中，就增加失败
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.DATA_ALREADY_EXISTED.getCode());
                    errorMap.put("errorMsg", ResultCode.DATA_ALREADY_EXISTED.getMessage() + "【企业主键重复】");
                    errorReasonList.add(errorMap);
                }
            }

            if (StringUtils.isEmpty(invoicePrimaryKeyId)) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode",  ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【票据主键不能为空】");
                errorReasonList.add(errorMap);
            }

            if (StringUtils.isEmpty(goodsID)) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode",  ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【产品ID不能为空】");
                errorReasonList.add(errorMap);
            }


            if (StringUtils.isEmpty(batchCode)) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode",  ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【批号不能为空】");
                errorReasonList.add(errorMap);
            }

            if (StringUtils.isNotEmpty(invoicePrimaryKeyId) && StringUtils.isNotEmpty(goodsID) && StringUtils.isNotEmpty(batchCode)) {
                if (dupList.contains(md5Info)) {//如果出现在重复的记录中，就增加失败
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.DATA_ALREADY_EXISTED.getCode());
                    errorMap.put("errorMsg", ResultCode.DATA_ALREADY_EXISTED.getMessage() + "【发票主键+产品ID+批号重复】");
                    errorReasonList.add(errorMap);
                }
            }

            if (StringUtils.isEmpty(periodDate)) {// 日期格式校验
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode",  ResultCode.PARAM_IS_BLANK.getCode());
                errorMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【有效期不能为空】");
                errorReasonList.add(errorMap);
            } else {
                Date saleDate = ToolUtils.fromShortFormat(periodDate);
                if (saleDate == null) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode",  ResultCode.PARAM_IS_BLANK.getCode());
                    errorMap.put("errorMsg",  ResultCode.PARAM_IS_BLANK.getMessage() + "【有效期参数格式不正确】");
                    errorReasonList.add(errorMap);
                }
            }

            if (saleNumber <= 0) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());// 销售数量格式错误
                errorMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage() + "【销售数量不能转为整数或者销售数量为0】");
                errorReasonList.add(errorMap);
            }

            if (errorReasonList != null && errorReasonList.size() > 0) {
                detailMap.put("companyPrimaryKey", companyPrimaryKey);
                detailMap.put("errorReasonList", errorReasonList);
                errorList.add(detailMap);
            }
        }
        if (errorList.size() > 0) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("rresultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            validateResult.setJsonObject(resultJsonObj);
            validateResult.setSuccess(false);
        }
        return validateResult;
    }

    public static ValidateResult validataInvioceDistributeData(List<DistributeInfo> comRequest) {
        JSONObject resultJsonObj = new JSONObject();
        //总的错误集合
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        //设置验证对象
        ValidateResult validateResult = new ValidateResult();
        //默认成功
        validateResult.setSuccess(true);
        //1、验证数据是否存在
        if (comRequest == null || comRequest.size() == 0) {
            resultJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage() + "【JSON格式错误,未接收到相关有效信息】");
            validateResult.setJsonObject(resultJsonObj);
            validateResult.setSuccess(false);
            return validateResult;
        }

        //3.相关参数非空验证
        //不重复的订单明细编号+批号
        List<String> companyDistributeIdList = new ArrayList<>();//总的配送企业编号,用于判断配送企业端主键是否重复
        for (DistributeInfo item : comRequest) {
            //配送数量
            int totalDisCount = 0;
            //单个配送明细内的错误集合
            List<Map<String, Object>> errorReasonList = new ArrayList<>();
            JSONObject detailMap = new JSONObject();
            //判断企业的配送明细编号是否为空
            if (StringUtils.isEmpty(item.getCompanyDistributeId())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage() + "【企业配送明细编号不能为空】");
                errorReasonList.add(errorReasonMap);
            }

            //判断明细是否为空
            if (StringUtils.isEmpty(item.getDistributeId())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
                errorReasonList.add(errorReasonMap);
            }
            //判断明细是否为空
            if (StringUtils.isEmpty(item.getInvoicePrimaryId())) {
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
                errorReasonList.add(errorReasonMap);
            }
            companyDistributeIdList.add(item.getCompanyDistributeId());
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
            validateResult = ValidateInterface.validatePrimaryKey(companyDistributeIdList);
            if (validateResult.isSuccess()) {
                validateResult.setDistributeInfoList(comRequest);
            }
        }
        return validateResult;
    }

    /**
     * 验证批次请求主键是否重复
     *
     * @param primaryList
     * @return
     */
    public static ValidateResult validatePrimaryKey(List<String> primaryList) {
        //返回集合
        JSONObject resultJsonObj = new JSONObject();
        //错误集合
        List<JSONObject> errorList = new ArrayList<>();
        //设置验证对象
        ValidateResult validateResult = new ValidateResult();
        //默认验证成功
        validateResult.setSuccess(true);
        //去重复后的配送明细编号
        List<String> listWithoutDup = new ArrayList<>();
        //重复的编号
        List<String> listDup = new ArrayList<>();
        for (String companyDisId : primaryList) {
            if (listWithoutDup.contains(companyDisId)) {
                //出现多次的企业配送编号
                listDup.add(companyDisId);
            } else {
                //首次出现时候，插入该数组，供后续判断是否再次出现
                listWithoutDup.add(companyDisId);
            }
        }
        if (listWithoutDup.size() == primaryList.size()) {
            return validateResult;
        }
        //如果数量与原始记录不一致，则是有重复提交
        resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
        resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
        for (String dupOrderDetailId : primaryList) {
            //如果本次循环的企业配送编号未出现过，则直接跳出
            if (!listDup.contains(dupOrderDetailId)) {
                continue;
            }
            //单条明细错误信息实体
            JSONObject returnMap = new JSONObject();
            returnMap.put("companyDistributeId", dupOrderDetailId);
            //单条明细验证不通过原因集合
            List<JSONObject> errorReasonList = new ArrayList<JSONObject>();
            //数据重复提交
            JSONObject errorMap = new JSONObject();
            errorMap.put("errorCode", ResultCode.DATA_RESUBMIT.getCode());
            errorMap.put("errorMsg", ResultCode.DATA_RESUBMIT.getMessage());
            errorReasonList.add(errorMap);
            returnMap.put("errorReasonList", errorReasonList);
            errorList.add(returnMap);
        }
        resultJsonObj.put("errorList", errorList);
        validateResult.setJsonObject(resultJsonObj);
        validateResult.setSuccess(false);
        return validateResult;
    }
}
