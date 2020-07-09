package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.ValidateToken;
import com.common.utils.Pagination;
import com.common.utils.ValidateUtil;
import com.enums.ResultCode;
import com.sys.service.SysDatainterfaceOrganizationManager;
import com.trade.model.TradePurchaseorderdetail;
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

@RestController(CompInterfaceController.ACTION_PATH)
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
            resultJsonObj.put("returnCode", ResultCode.SUCCESS.getCode());
            resultJsonObj.put("returnMsg", ResultCode.SUCCESS.getMessage());
            resultJsonObj.put("dataList", dataList);
            resultJsonObj.put("currentPageNumber", page.getPage());// 按照数据返回正确的页码
            resultJsonObj.put("totalPageCount", page.getTotal());
            resultJsonObj.put("totalRecordCount", page.getRecords());
            return resultJsonObj;
        } catch (Exception e) {
            log.error("Failed to getOrder", e);
            resultJsonObj.put("returnCode", ResultCode.FAIL.getCode());
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
    }
}
