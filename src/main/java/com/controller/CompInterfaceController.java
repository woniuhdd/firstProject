package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.controller.ValidateDistributeInfo;
import com.common.controller.ValidateToken;
import com.common.utils.Pagination;
import com.common.utils.ToolUtils;
import com.common.utils.ValidateInterface;
import com.common.utils.ValidateUtil;
import com.enums.OrderDetailStatus;
import com.enums.ResultCode;
import com.github.pagehelper.Page;
import com.model.*;
import com.sys.service.SysDatainterfaceOrganizationManager;
import com.trade.model.*;
import com.trade.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(CompInterfaceController.ACTION_PATH)
public class CompInterfaceController {
    protected static final String ACTION_PATH = "/compInterface";
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private ValidateToken validateToken;
    @Autowired
    private TradePurchaseorderdetailManager tradePurchaseorderdetailManager;
    @Autowired
    private SysDatainterfaceOrganizationManager sysDatainterfaceOrganizationManager;
    @Autowired
    private TradeDisrecManager tradeDisrecManager;
    @Autowired
    private TradeInvoicenewManager tradeInvoicenewManager;
    @Autowired
    private BaseImgannexManager baseImgannexManager;
    @Autowired
    private TradeGoodslistManager tradeGoodslistManager;
    @Autowired
    private BaseHospitalManager baseHospitalManager;
    @Autowired
    private TradeCominfoManager tradeCominfoManager;
    @Autowired
    private TradeDruginfoManager tradeDruginfoManager;
    @Value("${page.size}")
    private String pageSize;

    /**
     * 内容摘要：获取采购订单数据
     *
     * @param token 授权令牌, startTime 订单开始时间, endTime 订单结束时间, currentPageNumber 分页数
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/purchaseOrder/getOrder", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getOrder(String token, String startTime, String endTime, String currentPageNumber){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(startTime)
                || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            dataList.add(resultMap);
        }

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", map.get("resultCode"));
            resultMap.put("errorMsg", map.get("resultMsg"));
            dataList.add(resultMap);
        }

        //验证数据有效性
        //1.页数是否为正整数
        if(!ValidateUtil.checkCount(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_INVALID.getCode());
            dataList.add(resultMap);
        }
        //2.时间格式是否正确
        if (!ValidateUtil.checkDate(startTime) || !ValidateUtil.checkDate(endTime) ){
            resultMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            dataList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (dataList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }


        //执行逻辑
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        try {
            Date startDate=format.parse(startTime);
            Date endDate=format.parse(endTime);
            Pagination page = new Pagination();
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("startTime", startDate);
            paramsMap.put("endTime", endDate);
            paramsMap.put("orderStatuses", 2);

            List<String> orgId = sysDatainterfaceOrganizationManager.getData((String) map.get("orgId"));

            paramsMap.put("delCompCode", orgId);
            page.setConditions(paramsMap);
            page.setPage(Integer.parseInt(currentPageNumber));
            page.setCount(Integer.parseInt(pageSize));// 每页查询数据量
            tradePurchaseorderdetailManager.queryAllOrderDetailRecentForInterface(page);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Page<TradePurchaseorderdetail> tradePurchaseorderdetailList = (Page<TradePurchaseorderdetail>) page.getRows();
            if (tradePurchaseorderdetailList != null && tradePurchaseorderdetailList.size() > 0) {
                for (TradePurchaseorderdetail item : tradePurchaseorderdetailList) {
                    Map dataMap = new HashMap();
                    dataMap.put("orderId", item.getPurchaseorderid());
                    dataMap.put("orderCode", item.getPurchaseordercode());
                    dataMap.put("orderRemarks", item.getRemark());
                    dataMap.put("orderDetailId", item.getPurchaseorderdetailid());
                    dataMap.put("hospitalId", item.getHosid());
                    dataMap.put("hospitalName", item.getHosName());
                    dataMap.put("companyNamePs", item.getComnamePs());
                    dataMap.put("companyIdPs", item.getComidPs());
                    dataMap.put("procurecatalogId", item.getProcurecatalogid());
                    dataMap.put("purchaseCount", item.getPurchasecount());
                    dataMap.put("purchasePrice", item.getPurchaseprice());
                    dataMap.put("purchaseAmount", item.getPurchaseamount());
                    dataMap.put("orderDetailState", item.getDetailstatus());
                    dataMap.put("submitTime", item.getSubmittime() == null ? "" : DateFormatUtils.format(item.getSubmittime(), "yyyy-MM-dd HH:mm:ss"));
                    dataMap.put("ordertype", item.getOrdertype());
                    dataMap.put("totalDetailCount",item.getDetailCount());
                    dataList.add(dataMap);
                }
            }
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
            resultJsonObj.put("dataList", dataList);
            resultJsonObj.put("currentPageNumber", tradePurchaseorderdetailList.getPageNum());// 按照数据返回正确的页码
            resultJsonObj.put("totalPageCount", tradePurchaseorderdetailList.getPages());
            resultJsonObj.put("totalRecordCount", tradePurchaseorderdetailList.getTotal());
            return resultJsonObj;
        } catch (Exception e) {
            log.error("Failed to getOrder", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            return resultJsonObj;
        }
    }

    /**
     * 配送订单
     *
     * @param token    令牌
     * @param distributeInfo 密文
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/distribution/distribute", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject distribute(String token, String distributeInfo){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(distributeInfo) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> tokenMap = validateToken.validateToken(token);
        Integer resultCode = (Integer) tokenMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", tokenMap.get("resultCode"));
            resultMap.put("errorMsg", tokenMap.get("resultMsg"));
            errorList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (errorList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }


        //验证distributeInfo
        ValidateResult validateResult = ValidateDistributeInfo.validateDistributeInfo(distributeInfo);
        if (!validateResult.isSuccess()) {
            return validateResult.getJsonObject();
        }


        try {
            //4、数据库交互验证订单明细是否存在，配送数量 是否符合
            List<Map<String, Object>> checkList;
            Map<String, Object> checkDetailMap = new HashMap<>();//存储list，用于校验接口数据
            checkDetailMap.put("detailList", validateResult.getDistributeInfoList());//设置订单明细集合
            checkList = tradePurchaseorderdetailManager.checkDistributeData(checkDetailMap);
            for (int i = 0; i < checkList.size(); i++) {
                Map<String, Object> map = checkList.get(i);
                String orderDetailId = map.get("ORDERDETAILID").toString();//订单明细编号
                String companyDistributeId = map.get("COMPANYDISTRIBUTEID").toString();//企业配送明细编号
                String isExists = map.get("ISEXISTS").toString();//用于判断订单明细是否存在
                String isCanDistribute = map.get("ISCANDISTRIBUTE").toString();//用于判断状态是否可配送
                String orderDetailState = map.get("DETAILSTATUS").toString();//用于提示当前处于何种状态
                String companyIdPs = map.get("COMPANYIDPS").toString();//配送企业编号，用于验证是否本企业的数据
                String isMorePurchaseCount = map.get("ISMOREPURCHASECOUNT").toString();//用于判断状态是否大于采购量
                String isCanDis = map.get("ISCANDIS").toString();//用于判断状态订单是否过期

                JSONObject returnMap = new JSONObject();//单条明细错误信息实体
                returnMap.put("companyDistributeId", companyDistributeId);
                List<JSONObject> errorReasonList = new ArrayList<JSONObject>();//单条明细验证不通过原因集合
                //数据不存在
                if (isExists.equals("0")) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                    errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage());
                    errorReasonList.add(errorMap);
                } else if (isCanDistribute.equals("0")) {   //数据状态不符合
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                    errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage() + "【当前数据状态为[" + com.enums.OrderDetailStatus.getValueByKey(Integer.parseInt(orderDetailState)) + "]已不可配送】");
                    errorReasonList.add(errorMap);
                } else if (isMorePurchaseCount.equals("1")) {   //配送大于采购量
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.DISTRIBUTE_NUMBER_ERROR.getCode());
                    errorMap.put("errorMsg", ResultCode.DISTRIBUTE_NUMBER_ERROR.getMessage() + "【当前数据企业配送总量大于医疗机构采购量】");
                    errorReasonList.add(errorMap);
                } else if (isCanDis.equals("1")) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                    errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage()  + "【当前数据状态[订单已过期]已不可配送】");
                    errorReasonList.add(errorMap);
                }
                if (!companyIdPs.toLowerCase().equals(tokenMap.get("orgId").toString().toLowerCase())) {   //非本企业数据,转为小写比较，防止医疗机构提交数据与平台大小写不一致
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                    errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage() + "【非本企业数据，请勿操作；如发现多次操作，系统将取消接口权限】");
                    errorReasonList.add(errorMap);
                }
                //如果发生错误，则添加错误原因
                if (errorReasonList.size() != 0) {
                    returnMap.put("errorReasonList", errorReasonList);
                    errorList.add(returnMap);
                }
            }

            if (errorList.size() != 0) {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
                resultJsonObj.put("errorList", errorList);
                return resultJsonObj;
            }
            //5、数据库操作
            //设置有序主键
            //批号信息
            List<DisBatch> batchList = new ArrayList<>();
            //发票信息
            List<TradeInvoiceDis> invoiceList = new ArrayList<>();
            for (DistributeInfo recent : validateResult.getDistributeInfoList()) {
                //获取本次配送编号
                String disTriId = ToolUtils.getPrimaryId("2");
                //设置对应关系，待成功返回时，反馈给企业
                JSONObject detailMap = new JSONObject();
                detailMap.put("companyDistributeId", recent.getCompanyDistributeId());
                detailMap.put("distributeId", disTriId);
                successList.add(detailMap);
                //设置实体值，用于插入数据库
                recent.setDistributeId(disTriId);
                Map<String,Object> map = recent.getBatchList();
                List<DisBatch> comInterfaceDisBatchList = JSONObject.parseArray(map.get("list").toString(), DisBatch.class);
                for (DisBatch batch : comInterfaceDisBatchList) {
                    batch.setCompanyDistributeId(recent.getCompanyDistributeId());
                    batch.setDistributeId(disTriId);
                    batchList.add(batch);
                }

                if (!StringUtils.isEmpty(recent.getFirstInviceID())) {
                    String[] firstInvoiceStr = recent.getFirstInviceID().split(";");
                    if (firstInvoiceStr.length > 0) {
                        for (String iems : firstInvoiceStr) {
                            TradeInvoiceDis invoiceFirst = new TradeInvoiceDis();
                            invoiceFirst.setDisid(disTriId);
                            invoiceFirst.setInvoiceid(iems);
                            invoiceList.add(invoiceFirst);
                        }
                    }

                }
                if (!StringUtils.isEmpty(recent.getMiddleInviceID())) {
                    String[] middleInvoiceStr = recent.getMiddleInviceID().split(";");
                    if (middleInvoiceStr.length > 0) {
                        for (String iems : middleInvoiceStr) {
                            TradeInvoiceDis invoiceMiddle = new TradeInvoiceDis();
                            invoiceMiddle.setDisid(disTriId);
                            invoiceMiddle.setInvoiceid(iems);
                            invoiceList.add(invoiceMiddle);
                        }
                    }

                }
                if (!StringUtils.isEmpty(recent.getSecondInviceID())) {
                    String[] secondInvoiceStr = recent.getSecondInviceID().split(";");
                    if (secondInvoiceStr.length > 0) {
                        for (String iems : secondInvoiceStr) {
                            TradeInvoiceDis invoiceSecond = new TradeInvoiceDis();
                            invoiceSecond.setDisid(disTriId);
                            invoiceSecond.setInvoiceid(iems);
                            invoiceList.add(invoiceSecond);
                        }
                    }

                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("distributeList", validateResult.getDistributeInfoList());
            map.put("distributeBatchList", batchList);
            map.put("invoiceList", invoiceList);
            map.put("userId", tokenMap.get("userId").toString());
            map.put("userName", tokenMap.get("userName").toString());
            //与数据库交互
            //省平台直接交换，
            int num = tradeDisrecManager.addDistributeDataByDistributeProvince(map);
            if (num > 0) {
                resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("successList", successList);
            } else {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());// 配送失败
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【配送数据不存在】");
            }
        } catch (Exception e) {
            //插入执行日志
            log.error("Failed to distribute", e);
            resultJsonObj.put("errorCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("errorMsg", ResultCode.FAIL.getMessage() +"【配送订单错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
            errorList.add(resultMap);
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
        }
        return resultJsonObj;
    }

    /**
     * 票据上传
     * @param token 令牌
     * @param invoiceInfo 发票信息
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/invoice/addInvoice", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addInvoice(String token, String invoiceInfo){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(invoiceInfo) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> tokenMap = validateToken.validateToken(token);
        Integer resultCode = (Integer) tokenMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", tokenMap.get("resultCode"));
            resultMap.put("errorMsg", tokenMap.get("resultMsg"));
            errorList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (errorList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }

        List<ComInterfaceInvoice> invoiceInfoList = JSONArray.parseArray(JSONObject.
                parseObject(invoiceInfo).getString("list"), ComInterfaceInvoice.class);

        //3.验证相关数据是否符合要求
        ValidateResult nowValidateResult = ValidateInterface.validateInvoiceList(invoiceInfoList);
        if (!nowValidateResult.isSuccess()) {
            return nowValidateResult.getJsonObject();
        }


        List<Map<String, Object>> checkList;
        //1.校验发票代码+发票号码+所属企业是否已经存在
        try {
            checkList = tradeInvoicenewManager.checkInvoiceForInterFace(invoiceInfoList);
        } catch (Exception e) {
            log.error("校验发票信息是否存在", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】");
            return resultJsonObj;//直接返回
        }
        for (int i = 0; i < checkList.size(); i++) {
            Map<String, Object> map = checkList.get(i);
            map.put("invoicePrimaryId", ToolUtils.getPrimaryId("2"));//生成发票主键
            //用于关联后续上传的图片
            map.put("invoiceImgId", ToolUtils.getPrimaryId("2"));//
            map.put("saleImgId", ToolUtils.getPrimaryId("2"));
            String companyPrimaryKey = map.get("COMPANYPRIMARYKEY").toString();//企业主键
            String isExists = map.get("ISEXISTS").toString();//用于判断发票是否存在

            JSONObject jsonObject = new JSONObject();//单条明细错误信息实体
            JSONObject reason = new JSONObject();
            jsonObject.put("companyPrimaryKey", companyPrimaryKey);
            List<JSONObject> errorReasonList = new ArrayList<JSONObject>();//单条明细验证不通过原因集合
            //数据已经存在
            if (isExists.equals("1")) {
                reason.put("errorCode", ResultCode.DATA_ALREADY_EXISTED.getCode());
                reason.put("errorMsg", ResultCode.DATA_ALREADY_EXISTED.getMessage() + "【发票代码+发票号码已存在】");
                errorReasonList.add(reason);
            }
            //如果发生错误，则添加错误原因
            if (errorReasonList.size() != 0) {
                jsonObject.put("errorReasonList", errorReasonList);
                errorList.add(jsonObject);
            }
        }
        if (errorList.size() != 0) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }
        //region 与数据库交互
        try {
            //设置对应关系，待成功返回时，反馈给企业
            for (Map<String, Object> map : checkList) {
                JSONObject detailMap = new JSONObject();
                detailMap.put("companyPrimaryKey", map.get("COMPANYPRIMARYKEY"));
                detailMap.put("ID", map.get("invoicePrimaryId"));
                detailMap.put("invoiceImgId", map.get("invoiceImgId"));
                detailMap.put("saleImgId", map.get("saleImgId"));
                successList.add(detailMap);
            }
            //设置操作人操作信息
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("list", checkList);
            //与数据库交互
            int num = tradeInvoicenewManager.addInvoiceInfo(params);
            if (num > 0) {
                resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("successList", successList);
                return resultJsonObj;
            } else {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
                return resultJsonObj;
            }
        } catch (Exception e) {
            log.error("Failed to addInvoice", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【上传票据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
            return resultJsonObj;
        }
    }

    /**
     * 上传票据图片
     *
     * @param token 令牌
     * @param imgList      图片集合
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/invoiceImage/addImage", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addImage(String token, String imgList){

        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(imgList) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> tokenMap = validateToken.validateToken(token);
        Integer resultCode = (Integer) tokenMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", tokenMap.get("resultCode"));
            resultMap.put("errorMsg", tokenMap.get("resultMsg"));
            errorList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (errorList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }

        List <ComInterfaceImage> invoiceImgLst=new ArrayList<>();
        try{
            invoiceImgLst=JSONArray.parseArray(JSONObject.parseObject(imgList).getString("list"), ComInterfaceImage.class);
        }catch (Exception e){
            resultJsonObj.put("resultCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultJsonObj.put("resultMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
            resultJsonObj.put("errorList", errorList);
            resultJsonObj.put("successList", successList);
            return resultJsonObj;//直接返回
        }
        // 2、请求参数非空验证
        ValidateResult nowValidateResult = ValidateInterface.validateImgList(invoiceImgLst);
        if (!nowValidateResult.isSuccess()) {
            return nowValidateResult.getJsonObject();
        }
        //3、验证图片路径是否可以访问

        for (ComInterfaceImage item : invoiceImgLst) {
            JSONObject returnMap = new JSONObject();//单条明细错误信息实体
            //单条数据错误集合
            List<Map<String, Object>> errorReasonList = new ArrayList<>();
            TradeInvoicenew tradeInvoicenew = tradeInvoicenewManager.getById(item.getInvoicePrimaryID());
            if(tradeInvoicenew==null){
                Map<String, Object> errorReasonMap = new HashMap<>(16);
                errorReasonMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
                errorReasonMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage() + "【发票信息不存在】");
                errorReasonList.add(errorReasonMap);
            }else{
                item.setImgPrimaryID(item.getImageType().equals("0")?tradeInvoicenew.getInvoiceimgid():tradeInvoicenew.getInvoiceimgid2());
            }
            if (errorReasonList.size() > 0) {
                returnMap.put("companyPrimaryKey", item.getCompanyPrimaryKey());
                returnMap.put("errorReasonList", errorReasonList);
                errorList.add(returnMap);
            }
        }
        if (errorList.size() != 0) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }
        try {
            List<Map<String, Object>> checkList = new ArrayList<>();
            for (ComInterfaceImage item : invoiceImgLst) {
                Map<String, Object> imgs = new HashMap<>(16);
                imgs.put("companyPrimaryKey", item.getCompanyPrimaryKey());
                imgs.put("ID", ToolUtils.getPrimaryId("2"));
                imgs.put("imgPrimaryID", item.getImgPrimaryID());
                imgs.put("invoicePrimaryID", item.getInvoicePrimaryID());
                imgs.put("imageType", item.getImageType().equals("0") ? "9" : "10");
                imgs.put("fileName", "发票图片(接口)");
                imgs.put("imgOriginalUrl", item.getImgUrl());
                imgs.put("imgThumbUrl", item.getImgUrl());
                //随货单图片(接口)   77B4A1B9DB4301D0E053C0A8757101D0
                //发票图片(接口)      77B4A1B9DB4201D0E053C0A8757101D0
                imgs.put("folderId", item.getImageType().equals("0") ? "77B4A1B9DB4201D0E053C0A8757101D0" : "77B4A1B9DB4301D0E053C0A8757101D0");
                imgs.put("orgId", tokenMap.get("orgId").toString());
                checkList.add(imgs);
                //设置返回对应关系
                JSONObject detailMap = new JSONObject();
                detailMap.put("companyPrimaryKey", item.getCompanyPrimaryKey());
                detailMap.put("ID", imgs.get("ID"));
                successList.add(detailMap);
            }
            //4、数据操作
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("list", checkList);
            //与数据库(省平台)交互
            int num = baseImgannexManager.addInvoiceImageProvince(params);
            if (num > 0) {
                resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("successList", successList);
            } else {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            }

        } catch (Exception e) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【操作错误，请联系管理员】");
            return resultJsonObj;//直接返回
        }
        return resultJsonObj;

    }

    /**
     * 发票清单上传
     *
     * @param token 令牌
     * @param saleInfo    发票信息
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/saleList/addSale", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addSale(String token, String saleInfo){

        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(saleInfo) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> tokenMap = validateToken.validateToken(token);
        Integer resultCode = (Integer) tokenMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", tokenMap.get("resultCode"));
            resultMap.put("errorMsg", tokenMap.get("resultMsg"));
            errorList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (errorList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }

        //2.数据非空验证
        List<ComInterfaceSaleList> saleList = JSONArray.parseArray(JSONObject.
                parseObject(saleInfo).getString("list"), ComInterfaceSaleList.class);
        //3.验证相关数据是否符合要求
        ValidateResult nowValidateResult = ValidateInterface.validateSaleList(saleList);
        if (!nowValidateResult.isSuccess()) {
            return nowValidateResult.getJsonObject();
        }
        //数据库校验
        List<Map<String, Object>> checkList;
        //1.发票主键+产品ID+批号是否已经存在
        try {
            checkList = tradeGoodslistManager.checkSaleIsExistsByInterface(saleList);
        } catch (Exception e) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】");
            return resultJsonObj;//直接返回
        }

        for (int i = 0; i < checkList.size(); i++) {
            Map<String, Object> map = checkList.get(i);
            String companyPrimaryKey = map.get("COMPANYPRIMARYKEY").toString();//企业主键
            String isExists = map.get("ISEXISTS").toString();//用于判断发票是否存在
            JSONObject returnMap = new JSONObject();//单条明细错误信息实体
            returnMap.put("companyPrimaryKey", companyPrimaryKey);
            List<JSONObject> errorReasonList = new ArrayList<JSONObject>();//单条明细验证不通过原因集合
            //数据已经存在
            if (isExists.equals("1")) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.DATA_ALREADY_EXISTED.getCode());
                errorMap.put("errorMsg", ResultCode.DATA_ALREADY_EXISTED.getMessage() + "【发票主键+产品ID+批号已存在】");
                errorReasonList.add(errorMap);
            }
            //如果发生错误，则添加错误原因
            if (errorReasonList.size() != 0) {
                returnMap.put("errorReasonList", errorReasonList);
                errorList.add(returnMap);
            }
        }
        if (errorList.size() != 0) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }

        //2.校验发票与产品是否在系统中存在
        try {
            checkList = tradeGoodslistManager.checkSaleDataByInterface(saleList);
        } catch (Exception e) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】");
            return resultJsonObj;//直接返回
        }

        for (int i = 0; i < checkList.size(); i++) {
            Map<String, Object> map = checkList.get(i);
            map.put("ID", ToolUtils.getPrimaryId("2"));
            String companyPrimaryKey = map.get("COMPANYPRIMARYKEY").toString();//企业主键
            String invoiceIsExists = map.get("INVOICEISEXISTS").toString();
            String goodsIsExists = map.get("GOODSISEXISTS").toString();

            JSONObject returnMap = new JSONObject();//单条明细错误信息实体
            returnMap.put("companyPrimaryKey", companyPrimaryKey);
            List<JSONObject> errorReasonList = new ArrayList<JSONObject>();//单条明细验证不通过原因集合

            if (invoiceIsExists.equals("0")) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage() + "【发票不存在】");
                errorReasonList.add(errorMap);
            }

            if (goodsIsExists.equals("0")) {
                JSONObject errorMap = new JSONObject();
                errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage() + "【产品ID不存在】");
                errorReasonList.add(errorMap);
            }

            //如果发生错误，则添加错误原因
            if (errorReasonList.size() != 0) {
                returnMap.put("errorReasonList", errorReasonList);
                errorList.add(returnMap);
            }
        }
        if (errorList.size() != 0) {
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }
        //region 与数据库交互
        try {
            //设置对应关系，待成功返回时，反馈给企业
            for (Map<String, Object> map : checkList) {
                JSONObject detailMap = new JSONObject();
                detailMap.put("companyPrimaryKey", map.get("COMPANYPRIMARYKEY"));
                detailMap.put("ID", map.get("ID"));
                successList.add(detailMap);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("list", checkList);
            params.put("orgId", tokenMap.get("userId").toString());
            //与数据库交互(省平台)
            int num = tradeGoodslistManager.addSaleInfo(params);
            if (num > 0) {
                resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("successList", successList);
            } else {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            }
        } catch (Exception e) {
            log.error("Failed to addInvoice", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage()+ "【上传票据清单错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
        }
        return resultJsonObj;
    }

    /**
     * 获取发票核验结果
     * @param token 令牌
     * @param startTime
     * @param endTime
     * @param currentPageNumber
     * @return
     */
    @RequestMapping(value = "/distribution/invoiceInfo", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getInvoiceCheckInfo(String token, String startTime, String endTime, String currentPageNumber){

        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(startTime)
                || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(currentPageNumber) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> tokenMap = validateToken.validateToken(token);
        Integer resultCode = (Integer) tokenMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", tokenMap.get("resultCode"));
            resultMap.put("errorMsg", tokenMap.get("resultMsg"));
            errorList.add(resultMap);
        }
        //验证数据有效性
        //1.页数是否为正整数
        if(!ValidateUtil.checkCount(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_INVALID.getCode());
            errorList.add(resultMap);
        }
        //2.时间格式是否正确
        if (!ValidateUtil.checkDate(startTime) || !ValidateUtil.checkDate(endTime) ){
            resultMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            errorList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (errorList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }


        //4、数据整理
        try {
            Pagination page = new Pagination();
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("startTime", startTime);
            paramsMap.put("endTime", endTime);
            paramsMap.put("companyIdPs", tokenMap.get("orgId").toString());
            page.setConditions(paramsMap);
            page.setPage(Integer.parseInt(currentPageNumber));
            page.setCount(Integer.parseInt(pageSize));// 每页查询数据量
            page.setOrderby();
            //数据库交换
            tradeDisrecManager.getVerificationInvoiceResultList(page);
            Page<Map<String, Object>> detailsPage = (Page<Map<String, Object>>) page.getRows();
            int totalPages = detailsPage.getPages();
            int cur = detailsPage.getPageNum();
            long total = detailsPage.getTotal();// 总记录数
            List<Map<String, Object>> details = detailsPage;
            if (details != null && details.size() > 0) {
                for (Map<String, Object> detail : details) {
                    JSONObject returnMap = new JSONObject(16);
                    returnMap.put("distributeId", detail.get("DISRECID"));
                    returnMap.put("invoicePrimaryKeyId", detail.get("INVOICEPRIMARYKEYID"));
                    returnMap.put("invoiceId", detail.get("INVOICEID"));
                    returnMap.put("invoiceCode", detail.get("INVOICECODE"));
                    returnMap.put("comid_ps", detail.get("COMID_PS"));
                    returnMap.put("checkResult", detail.get("FLAGLOG"));
                    returnMap.put("submittime", detail.get("SUBMITTIME"));
                    successList.add(returnMap);
                }
            }
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
            resultJsonObj.put("currentPageNumber", cur);// 按照数据返回正确的页码
            resultJsonObj.put("totalPageCount", totalPages);
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", successList);
        } catch (Exception e) {
            log.error("Failed to confirmOrder", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
        }
        return resultJsonObj;
    }

    /**
     * 获取医院撤单信息
     * @param token
     * @param startTime
     * @param endTime
     * @param currentPageNumber
     * @return
     */
    @RequestMapping(value = "/purchaseOrder/getCancelOrder", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getCancelPurchaseOrder(String token, String startTime, String endTime, String currentPageNumber){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(startTime)
                || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(currentPageNumber) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            dataList.add(resultMap);
        }

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", map.get("resultCode"));
            resultMap.put("errorMsg", map.get("resultMsg"));
            dataList.add(resultMap);
        }

        //验证数据有效性
        //1.页数是否为正整数
        if(!ValidateUtil.checkCount(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_INVALID.getCode());
            dataList.add(resultMap);
        }
        //2.时间格式是否正确
        if (!ValidateUtil.checkDate(startTime) || !ValidateUtil.checkDate(endTime) ){
            resultMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            dataList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (dataList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }
        try {
            Pagination page = new Pagination();
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("startTime", startTime);
            paramsMap.put("endTime", endTime);
            paramsMap.put("companyIdPs", map.get("orgId").toString());
            page.setConditions(paramsMap);
            page.setPage(Integer.parseInt(currentPageNumber));
            page.setCount(Integer.parseInt(pageSize));// 每页查询数据量
            page.setSord("ASC");// 排序方向
            page.setOrderby();
            //数据库交换
            tradePurchaseorderdetailManager.getCancelPurchaseOrderForInterface(page);
            Page<TradePurchaseorderdetail> detailsPage = (Page<TradePurchaseorderdetail>) page.getRows();
            int totalPages = detailsPage.getPages();
            int cur = detailsPage.getPageNum();
            long total = detailsPage.getTotal();// 总记录数
            List<TradePurchaseorderdetail> details = detailsPage;
            if (details != null && details.size() > 0) {
                for (TradePurchaseorderdetail detail : details) {
                    JSONObject retMap = new JSONObject(16);
                    retMap.put("orderId", detail.getPurchaseorderid());
                    retMap.put("orderCode", detail.getPurchaseordercode());
                    retMap.put("hospitalId", detail.getHosid());
                    retMap.put("cancelTime", detail.getEduptime()==null?"":DateFormatUtils.format(detail.getEduptime(),"yyyy-MM-dd"));
                    dataList.add(retMap);
                }
            }
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
            resultJsonObj.put("currentPageNumber", cur);// 按照数据返回正确的页码
            resultJsonObj.put("totalPageCount", totalPages);
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to confirmOrder", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
        }

        return resultJsonObj;
    }

    /**
     * 配送发票补录
     * @param token
     * @param distributeInfo
     * @return
     */
    @RequestMapping(value = "/distribution/addDistributeInvoice", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addDistributeInvoice(String token, String distributeInfo){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(distributeInfo)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> tokenMap = validateToken.validateToken(token);
        Integer resultCode = (Integer) tokenMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", tokenMap.get("resultCode"));
            resultMap.put("errorMsg", tokenMap.get("resultMsg"));
            errorList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (errorList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("errorList", errorList);
            return resultJsonObj;
        }

        //请求数据转换为list
        List<DistributeInfo> list = JSONObject.parseArray(JSONObject.
                parseObject(distributeInfo).getString("list"), DistributeInfo.class);

        //3、验证数据是否符合规范
        ValidateResult nowValidateResult = ValidateInterface.validataInvioceDistributeData(list);
        if (!nowValidateResult.isSuccess()) {
            return nowValidateResult.getJsonObject();
        }
        try {

            //4、数据库校验数据
            List<Map<String, Object>> checkList;
            Map<String, Object> checkDetailMap = new HashMap<>();//存储list，用于校验接口数据
            checkDetailMap.put("detailList", nowValidateResult.getDistributeInfoList());//设置订单明细集合
            checkList = tradeDisrecManager.checkDistributeInvoiceDataByInterface(checkDetailMap);
            for (int i = 0; i < checkList.size(); i++) {
                Map<String, Object> map = checkList.get(i);
                String companyDistributeId = map.get("COMPANYDISTRIBUTEID").toString();//企业配送明细编号
                String isExists = map.get("ISEXISTS").toString();//用于判断订单明细是否存在
                String isCanDistribute = map.get("ISCANDISTRIBUTE").toString();//用于判断状态是否可配送
                String orderDetailState = (String)map.get("DETAILSTATUS");//用于提示当前处于何种状态

                JSONObject returnMap = new JSONObject();//单条明细错误信息实体
                returnMap.put("companyDistributeId", companyDistributeId);
                List<JSONObject> errorReasonList = new ArrayList<JSONObject>();//单条明细验证不通过原因集合
                //数据不存在
                if (isExists.equals("0")) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.RESULT_DATA_NONE.getCode());
                    errorMap.put("errorMsg", ResultCode.RESULT_DATA_NONE.getMessage());
                    errorReasonList.add(errorMap);
                } else if (isCanDistribute.equals("0")) {   //数据状态不符合
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ResultCode.DATA_STATUS_ERROR.getCode());
                    errorMap.put("errorMsg", ResultCode.DATA_STATUS_ERROR.getMessage() + "【当前数据状态为[" + OrderDetailStatus.getValueByKey(Integer.parseInt(orderDetailState)) + "]已不可补录】");
                    errorReasonList.add(errorMap);
                }
                //如果发生错误，则添加错误原因
                if (errorReasonList.size() != 0) {
                    returnMap.put("errorReasonList", errorReasonList);
                    errorList.add(returnMap);
                }
            }

            //返回校验结果
            if (errorList.size() != 0) {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
                resultJsonObj.put("errorList", errorList);
                return resultJsonObj;
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("invoiceList", nowValidateResult.getDistributeInfoList());
            map.put("userId", tokenMap.get("userId").toString());
            map.put("userName", tokenMap.get("userName").toString());
            for (Map<String, Object> check : checkList) {
                JSONObject detailMap = new JSONObject();
                detailMap.put("companyDistributeId", check.get("COMPANYDISTRIBUTEID"));
                successList.add(detailMap);
            }
            //与数据库交互
            //省平台直接交换，
            int num = tradeDisrecManager.addInvoiceDistributeData(map);
            if (num > 0) {
                resultJsonObj.put("resultCode", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("successList", successList);
            } else {
                resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());// 配送失败
                resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【配送数据不存在】");
            }
        } catch (Exception e) {
            log.error("Failed to distribute", e);
            //插入执行日志
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【发票补录错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
        }
        return resultJsonObj;
    }

    /**
     * 获取商品信息
     *
     * @param token       令牌
     * @param month             月份
     * @param procurecatalogIds 商品ID
     * @param currentPageNumber 当前页码
     * @return
     */
    @RequestMapping(value = "/procurecatalog/getProcurecatalog", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getProcurecatalog(String token, String month, String procurecatalogIds, String currentPageNumber,String perpage){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(currentPageNumber) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            dataList.add(resultMap);
        }

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", map.get("resultCode"));
            resultMap.put("errorMsg", map.get("resultMsg"));
            dataList.add(resultMap);
        }

        //验证数据有效性
        //1.页数是否为正整数
        if(!ValidateUtil.checkCount(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_INVALID.getMessage());
            dataList.add(resultMap);
        }
        //2.时间格式是否正确
        if (month!=null&&!ValidateUtil.match("^(19|20\\d{2})-(0[1-9]|1[0-2])$",month) ){
            resultMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
            dataList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (dataList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }
        try {
            Map<String, Object> params = new HashMap<>(16);
            Pagination page = new Pagination();
            if (procurecatalogIds!=null&&!"".equals(procurecatalogIds)) {
                List<String> procurecatalogId = JSONObject.parseObject(procurecatalogIds).getObject("list",List.class);
                params.put("procurecatalogId", procurecatalogId);
            }
            if (month!=null&&!"".equals(month)) {
                params.putAll(getTimes(month));
            }
            page.setConditions(params);
            //4 校验输入参数是否满足配置要求
            page.setPage(Integer.parseInt(currentPageNumber));
            // 每页查询数据量
            if(perpage!=null&&!"".equals(perpage)){
                page.setCount(Integer.parseInt(perpage));
            }else{
                page.setCount(Integer.parseInt(pageSize));
            }
            page.setOrderby();
            //5.数据库交互
            tradeDruginfoManager.getBaseGoodsList(page);
            Page<TradeDruginfo> detailsPage = (Page<TradeDruginfo>) page.getRows();
            // 总页数
            int totalPages = detailsPage.getPages();
            //  当前页
            int cur = detailsPage.getPageNum();
            //  总记录数
            long total = detailsPage.getTotal();
            List<TradeDruginfo> baseGoodsList = detailsPage;
            if (baseGoodsList.size() > 0) {
                for (TradeDruginfo tradeDruginfo : baseGoodsList) {
                    Map<String, Object> retMap = new HashMap<>(16);
                    retMap.put("procurecatalogId", tradeDruginfo.getDrugid());
                    retMap.put("goodsId", tradeDruginfo.getProductid());
                    retMap.put("productName", tradeDruginfo.getDrugname());
                    retMap.put("goodsName", tradeDruginfo.getDruggoodsname());
                    retMap.put("medicinemodel", tradeDruginfo.getDrugform());
                    retMap.put("outlook", tradeDruginfo.getDrugspec());
                    retMap.put("factor", tradeDruginfo.getDrugfactor());
                    retMap.put("materialName", tradeDruginfo.getDrugmaterial());
                    retMap.put("unit", tradeDruginfo.getPack());
                    retMap.put("companyIdSc", tradeDruginfo.getCompanyidSc());
                    retMap.put("companyNameSc", tradeDruginfo.getCompanynameSc());
                    retMap.put("companyIdTb", tradeDruginfo.getCompanyidTb());
                    retMap.put("companyNameTb", tradeDruginfo.getCompanynameTb());
                    retMap.put("purchaseType", tradeDruginfo.getCatalogtype());
                    retMap.put("sourceName", tradeDruginfo.getDrugsource());
                    retMap.put("middlePack", tradeDruginfo.getMiddlepack());
                    retMap.put("maxPack", tradeDruginfo.getBigpack());
                    retMap.put("bidPrice", tradeDruginfo.getPrice());
                    retMap.put("comPrice", tradeDruginfo.getComprice());
                    retMap.put("isUrbmi", tradeDruginfo.getIsurbmi());
                    retMap.put("medicalinsuranceType", tradeDruginfo.getMedicalinsurancetype());
                    retMap.put("isBaseDrug", tradeDruginfo.getIsBasicDrug());
                    retMap.put("isUsing", tradeDruginfo.getIsusing());
                    retMap.put("ispilot", tradeDruginfo.getIspilot());
                    retMap.put("id", tradeDruginfo.getId());
                    retMap.put("addTime", tradeDruginfo.getAddtime()== null ? "" : DateFormatUtils.format(tradeDruginfo.getAddtime(), "yyyy-MM-dd"));
                    retMap.put("lastUpdateTime", tradeDruginfo.getLastUpdateTime()== null ? "" : DateFormatUtils.format(tradeDruginfo.getLastUpdateTime(), "yyyy-MM-dd"));
                    dataList.add(retMap);
                }
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getCode());
            } else {
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage() + ResultCode.PARAM_IS_BLANK.getMessage());
            }
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            //总页数
            resultJsonObj.put("totalPageCount", totalPages);
            //当前页码
            resultJsonObj.put("currentPageNumber", cur);
            //总行数
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to getCompany", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }
        return resultJsonObj;
    }

    /**
     * 获取生产企业
     *
     * @param token       令牌
     * @param companyIds        企业编号
     * @param month             月份
     * @param currentPageNumber 当前页码
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/company/getCompany", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getCompany(String token, String companyIds, String month, String currentPageNumber,String perpage){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(currentPageNumber) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            dataList.add(resultMap);
        }

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", map.get("resultCode"));
            resultMap.put("errorMsg", map.get("resultMsg"));
            dataList.add(resultMap);
        }

        //验证数据有效性
        //1.页数是否为正整数
        if(!ValidateUtil.checkCount(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_INVALID.getMessage());
            dataList.add(resultMap);
        }
        //2.时间格式是否正确
        if (month!=null&&!ValidateUtil.match("^(19|20\\d{2})-(0[1-9]|1[0-2])$",month) ){
            resultMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
            dataList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (dataList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }
        try {
            Map<String, Object> params = new HashMap<>(16);
            Pagination page = new Pagination();
            if (companyIds!=null&&!"".equals(companyIds)) {
                List<String> companyid = JSONObject.parseObject(companyIds).getObject("list",List.class);
                params.put("companyid", companyid);
            }
            if (month!=null&&!"".equals(month)) {
                params.putAll(getTimes(month));
            }
            page.setConditions(params);
            //3 校验输入参数是否满足配置要求
            page.setPage(Integer.parseInt(currentPageNumber));
            // 每页查询数据量
            if(perpage!=null&&!"".equals(perpage)){
                page.setCount(Integer.parseInt(perpage));
            }else{
                page.setCount(Integer.parseInt(pageSize));
            }

            //  排序字段
            page.setSidx("lastUpdateTime,companyid");
            // 排序方向
            page.setSord("ASC");
            page.setOrderby();
            //4.数据库交互
            tradeCominfoManager.getBaseCompanyList(page);
            Page<TradeCominfo> detailsPage = (Page<TradeCominfo>) page.getRows();
            // 总页数
            int totalPages = detailsPage.getPages();
            //  当前页
            int cur = detailsPage.getPageNum();
            //  总记录数
            long total = detailsPage.getTotal();
            List<TradeCominfo> baseCompanyList = detailsPage;
            if (baseCompanyList.size() > 0) {
                for (TradeCominfo tradeCominfo : baseCompanyList) {
                    Map<String, Object> retMap = new HashMap<>(16);
                    retMap.put("companyId", tradeCominfo.getCompanyid());
                    retMap.put("id", tradeCominfo.getId());
                    retMap.put("companyType", tradeCominfo.getCompanytype());
                    retMap.put("companyName", tradeCominfo.getCompanyname());
                    retMap.put("address", tradeCominfo.getAddress());
                    retMap.put("companyContactTel", tradeCominfo.getCompanytel());
                    //企业传真号码
                    retMap.put("companyContactFax", tradeCominfo.getFax());
                    //邮编
                    retMap.put("zipCode", tradeCominfo.getZipcode());
                    //邮箱
                    retMap.put("email", tradeCominfo.getEmail());
                    retMap.put("addTime", tradeCominfo.getAddtime()== null ? "" : DateFormatUtils.format(tradeCominfo.getAddtime(), "yyyy-MM-dd"));
                    retMap.put("lastUpdateTime", tradeCominfo.getLastUpdateTime()== null ? "" : DateFormatUtils.format(tradeCominfo.getLastUpdateTime(), "yyyy-MM-dd"));
                    dataList.add(retMap);
                }
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getCode());
            } else {
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage() + ResultCode.PARAM_IS_BLANK.getMessage());
            }
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            //总页数
            resultJsonObj.put("totalPageCount", totalPages);
            //当前页码
            resultJsonObj.put("currentPageNumber", cur);
            //总行数
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to getCompany", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }
        return resultJsonObj;
    }

    /**
     * 获取医疗机构
     *
     * @param token       令牌
     * @param hospitalIds       医疗机构编号
     * @param month             月份
     * @param currentPageNumber 页码
     * @return
     */
    @RequestMapping(value = "/hospital/getHospital", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getHospital(String token, String hospitalIds, String month, String currentPageNumber ,String perpage){
        JSONObject resultJsonObj = new JSONObject();
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(currentPageNumber) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            dataList.add(resultMap);
        }

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", map.get("resultCode"));
            resultMap.put("errorMsg", map.get("resultMsg"));
            dataList.add(resultMap);
        }

        //验证数据有效性
        //1.页数是否为正整数
        if(!ValidateUtil.checkCount(currentPageNumber)){
            resultMap.put("errorCode", ResultCode.PARAM_IS_INVALID.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_INVALID.getMessage());
            dataList.add(resultMap);
        }
        //2.时间格式是否正确
        if (month!=null&&!ValidateUtil.match("^(19|20\\d{2})-(0[1-9]|1[0-2])$",month) ){
            resultMap.put("errorCode", ResultCode.PARAM_TYPE_BIND_ERROR.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
            dataList.add(resultMap);
        }

        // 判断是否所有验证都已通过
        if (dataList.size() != 0){
            resultJsonObj.put("resultCode",  ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage());
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }

        try {
            Pagination page = new Pagination();
            Map<String,Object> params=new HashMap<>();
            if (hospitalIds!=null&&!"".equals(hospitalIds)) {
                List<String> hosIds = JSONObject.parseObject(hospitalIds).getObject("list",List.class);
                params.put("hospitalId", hosIds);
            }
            if (month!=null&&!"".equals(month)) {
                params.putAll(getTimes(month));
            }
            page.setConditions(params);
            //4 校验输入参数是否满足配置要求
            page.setPage(Integer.parseInt(currentPageNumber));
            // 每页查询数据量
            if(perpage!=null&&!"".equals(perpage)){
                page.setCount(Integer.parseInt(perpage));
            }else{
                page.setCount(Integer.parseInt(pageSize));
            }
            //  排序字段
            page.setSidx("lastUpdateTime,hospitalcode");
            // 排序方向
            page.setSord("ASC");
            page.setOrderby();
            //5.数据库交互
            baseHospitalManager.getBaseHospitalListInfo(page);
            Page<BaseHospital> detailsPage = (Page<BaseHospital>) page.getRows();
            // 总页数
            int totalPages = detailsPage.getPages();
            //  当前页
            int cur = detailsPage.getPageNum();
            //  总记录数
            long total = detailsPage.getTotal();
            List<BaseHospital> baseHospitalList = detailsPage;
            if (baseHospitalList.size() > 0) {
                for (BaseHospital baseHospital : baseHospitalList) {
                    Map<String, Object> retMap = new HashMap<>(16);
                    retMap.put("hospitalId", baseHospital.getId());
                    retMap.put("hospitalCode", baseHospital.getHospitalcode());
                    retMap.put("hospitalName", baseHospital.getHospitalname());
                    retMap.put("hospitalType", baseHospital.getHospitaltype());
                    retMap.put("areaId", baseHospital.getAreaid());
                    retMap.put("areaName", baseHospital.getAreaName());
                    retMap.put("isUsing", baseHospital.getIsusing());
                    retMap.put("addTime", baseHospital.getAddtime() == null ? "" : DateFormatUtils.format(baseHospital.getAddtime(), "yyyy-MM-dd HH:mm:ss"));
                    retMap.put("lastUpdateTime", baseHospital.getLastUpdateTime()== null ? "" : DateFormatUtils.format(baseHospital.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                    dataList.add(retMap);
                }
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
            } else {
                resultJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage() + ResultCode.PARAM_IS_BLANK.getMessage());
            }
            resultJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            //总页数
            resultJsonObj.put("totalPageCount", totalPages);
            //当前页码
            resultJsonObj.put("currentPageNumber", cur);
            //总行数
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to getHospital", e);
            resultJsonObj.put("resultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("resultMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("dataList", dataList);
            return resultJsonObj;
        }
        return resultJsonObj;
    }

    public Map<String, Object> getTimes(String date) {
        Map<String, Object> params = new HashMap<>();
        String[] dates = date.split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(dates[0]));
        cal.set(Calendar.MONTH, Integer.valueOf(dates[1]) - 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        params.put("startTime", date + "-01 00:00:00");
        params.put("endTime", date + "-" + lastDay + " 23:59:59");
        return params;
    }
}
