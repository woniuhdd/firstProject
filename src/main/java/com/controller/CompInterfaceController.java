package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.ValidateDistributeInfo;
import com.common.controller.ValidateToken;
import com.common.utils.Pagination;
import com.common.utils.ValidateUtil;
import com.enums.ResultCode;
import com.model.DisBatch;
import com.model.DistributeInfo;
import com.model.ValidateResult;
import com.github.pagehelper.Page;
import com.sys.service.SysDatainterfaceOrganizationManager;
import com.trade.model.BaseHospital;
import com.trade.model.TradeCominfo;
import com.trade.model.TradeDruginfo;
import com.trade.model.TradePurchaseorderdetail;
import com.trade.service.BaseHospitalManager;
import com.trade.service.TradeCominfoManager;
import com.trade.service.TradeDruginfoManager;
import com.trade.service.TradePurchaseorderdetailManager;
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
    @Value("${page.size}")
    private String pageSize;
    @Autowired
    private BaseHospitalManager baseHospitalManager;
    @Autowired
    private TradeCominfoManager tradeCominfoManager;
    @Autowired
    private TradeDruginfoManager tradeDruginfoManager;

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
            page.setCount(Integer.parseInt(pageSize) );// 每页查询数据量
            tradePurchaseorderdetailManager.queryAllOrderDetailRecentForInterface(page);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<TradePurchaseorderdetail> tradePurchaseorderdetailList = (List<TradePurchaseorderdetail>) page.getRows();
            if (tradePurchaseorderdetailList != null && tradePurchaseorderdetailList.size() > 0) {
                for (TradePurchaseorderdetail item : tradePurchaseorderdetailList) {
                    Map dataMap = new HashMap();
                    dataMap.put("orderId", item.getPurchaseorderid());
                    dataMap.put("orderName", item.getPurchaseordercode());
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
            resultJsonObj.put("ResultCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage());
            resultJsonObj.put("dataList", dataList);
            resultJsonObj.put("currentPageNumber", page.getPage());// 按照数据返回正确的页码
            resultJsonObj.put("totalPageCount", page.getTotal());
            resultJsonObj.put("totalRecordCount", page.getRecords());
            return resultJsonObj;
        } catch (Exception e) {
            log.error("Failed to getOrder", e);
            resultJsonObj.put("ResultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage());
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
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //验证参数是否为空
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(distributeInfo) ){
            resultMap.put("errorCode", ResultCode.PARAM_IS_BLANK.getCode());
            resultMap.put("errorMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            errorList.add(resultMap);
        }

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            resultMap.put("errorCode", map.get("resultCode"));
            resultMap.put("errorMsg", map.get("resultMsg"));
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
            checkDetailMap.put("detailList", validateResult.getCompanyDistributeList());//设置订单明细集合
            checkList = purchaseorderdetailManager.checkDistributeDataByInterface(checkDetailMap);
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
                    errorMap.put("errorCode", ErrorCodeCompany.DATA_NOT_FOUND.getKey());
                    errorMap.put("errorMsg", ErrorCodeCompany.DATA_NOT_FOUND.getValue());
                    errorReasonList.add(errorMap);
                } else if (isCanDistribute.equals("0")) {   //数据状态不符合
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ErrorCodeCompany.DATA_STATUS_ERROR.getKey());
                    errorMap.put("errorMsg", ErrorCodeCompany.DATA_STATUS_ERROR.getValue() + "【当前数据状态为[" + com.hsnn.medstgmini.base.enums.OrderDetaillStatus.getValueByKey(Integer.parseInt(orderDetailState)) + "]已不可配送】");
                    errorReasonList.add(errorMap);
                } else if (isMorePurchaseCount.equals("1")) {   //配送大于采购量
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ErrorCodeCompany.DISTRIBUTE_NUMBER_ERROR.getKey());
                    errorMap.put("errorMsg", ErrorCodeCompany.DISTRIBUTE_NUMBER_ERROR.getValue() + "【当前数据企业配送总量大于医疗机构采购量】");
                    errorReasonList.add(errorMap);
                } else if (isCanDis.equals("1")) {
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ErrorCodeCompany.DATA_STATUS_ERROR.getKey());
                    errorMap.put("errorMsg", ErrorCodeCompany.DATA_STATUS_ERROR.getValue() + "【当前数据状态[订单已过期]已不可配送】");
                    errorReasonList.add(errorMap);
                }
                if (!companyIdPs.toLowerCase().equals(mapAskToken.get("orgId").toString().toLowerCase())) {   //非本企业数据,转为小写比较，防止医疗机构提交数据与平台大小写不一致
                    JSONObject errorMap = new JSONObject();
                    errorMap.put("errorCode", ErrorCodeCompany.DATA_NOT_FOUND.getKey());
                    errorMap.put("errorMsg", ErrorCodeCompany.DATA_NOT_FOUND.getValue() + "【非本企业数据，请勿操作；如发现多次操作，系统将取消接口权限】");
                    errorReasonList.add(errorMap);
                }
                //如果发生错误，则添加错误原因
                if (errorReasonList.size() != 0) {
                    returnMap.put("errorReasonList", errorReasonList);
                    errorList.add(returnMap);
                }
            }

            if (errorList.size() != 0) {
                resultJsonObj.put("returnCode", ResultCode.FAIL.getCode());
                resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage());
                resultJsonObj.put("errorList", errorList);
                return resultJsonObj;
            }
            //5、数据库操作
            //设置有序主键
            //批号信息
            List<DisBatch> batchList = new ArrayList<>();
            //发票信息
            List<TradeInvoiceDis> invoiceList = new ArrayList<>();
            for (DistributeInfo recent : validateResult.getCompanyDistributeList()) {
                //获取本次配送编号
                String disTriId = PrimaryKeyUtil.getPrimaryId(RequestType.Interface);
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

                if (!org.apache.commons.lang.StringUtils.isEmpty(recent.getFirstInviceID())) {
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
                if (!org.apache.commons.lang.StringUtils.isEmpty(recent.getMiddleInviceID())) {
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
                if (!org.apache.commons.lang.StringUtils.isEmpty(recent.getSecondInviceID())) {
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
            map.put("distributeList", nowValidateResult.getCompanyDistributeList());
            map.put("distributeBatchList", batchList);
            map.put("invoiceList", invoiceList);
            map.put("userId", mapAskToken.get("userId").toString());
            map.put("userName", mapAskToken.get("userName").toString());
            //与数据库交互
            //省平台直接交换，
            int num = tradeDisrecManager.addDistributeDataByDistributeProvince(map);
            if (num > 0) {
                resultJsonObj.put("returnCode", ResultCode.SUCCESS.getCode());
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage());
                resultJsonObj.put("successList", successList);
            } else {
                resultJsonObj.put("returnCode", ResultCode.FAIL.getCode());// 配送失败
                resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage() + "【配送数据不存在】");
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
        return  null;
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
        return null;
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
        return null;
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
        return null;
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
            resultJsonObj.put("returnCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage());
            resultJsonObj.put("currentPageNumber", cur);// 按照数据返回正确的页码
            resultJsonObj.put("totalPageCount", totalPages);
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to confirmOrder", e);
            resultJsonObj.put("returnCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
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
        return null;
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
    public JSONObject getProcurecatalog(String token, String month, String procurecatalogIds, String currentPageNumber){
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
            page.setCount(Integer.parseInt(pageSize));
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
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getCode());
            } else {
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage() + ResultCode.PARAM_IS_BLANK.getMessage());
            }
            resultJsonObj.put("ResultCode", ResultCode.SUCCESS.getCode());
            //总页数
            resultJsonObj.put("totalPageCount", totalPages);
            //当前页码
            resultJsonObj.put("currentPageNumber", cur);
            //总行数
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to getCompany", e);
            resultJsonObj.put("ResultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
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
    public JSONObject getCompany(String token, String companyIds, String month, String currentPageNumber){
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
            page.setCount(Integer.parseInt(pageSize));
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
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getCode());
            } else {
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage() + ResultCode.PARAM_IS_BLANK.getMessage());
            }
            resultJsonObj.put("ResultCode", ResultCode.SUCCESS.getCode());
            //总页数
            resultJsonObj.put("totalPageCount", totalPages);
            //当前页码
            resultJsonObj.put("currentPageNumber", cur);
            //总行数
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("dataList", dataList);
        } catch (Exception e) {
            log.error("Failed to getCompany", e);
            resultJsonObj.put("ResultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
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
    public JSONObject getHospital(String token, String hospitalIds, String month, String currentPageNumber){
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
            page.setCount(Integer.parseInt(pageSize));
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
                    retMap.put("addTime", baseHospital.getAddtime() == null ? "" : DateFormatUtils.format(baseHospital.getAddtime(), "yyyy-MM-dd"));
                    retMap.put("lastUpdateTime", baseHospital.getLastUpdateTime()== null ? "" : DateFormatUtils.format(baseHospital.getLastUpdateTime(), "yyyy-MM-dd"));
                    dataList.add(retMap);
                }
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage());
            } else {
                resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage() + ResultCode.PARAM_IS_BLANK.getMessage());
            }
            resultJsonObj.put("ResultCode", ResultCode.SUCCESS.getCode());
            //总页数
            resultJsonObj.put("totalPageCount", totalPages);
            //当前页码
            resultJsonObj.put("currentPageNumber", cur);
            //总行数
            resultJsonObj.put("totalRecordCount", total);
            resultJsonObj.put("hospInfList", dataList);
        } catch (Exception e) {
            log.error("Failed to getHospital", e);
            resultJsonObj.put("ResultCode", ResultCode.FAIL.getCode());
            resultJsonObj.put("returnMsg", ResultCode.FAIL.getMessage() + "【获取数据错误，请联系管理员】" + "【异常信息：" + e.getMessage() + "】");
            resultJsonObj.put("totalPageCount", "");
            resultJsonObj.put("hospInfList", dataList);
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
