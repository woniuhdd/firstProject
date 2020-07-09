package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.ValidateToken;
import com.enums.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController(CompInterfaceController.ACTION_PATH)
@RequestMapping(CompInterfaceController.ACTION_PATH)
public class CompInterfaceController {
    protected static final String ACTION_PATH = "/compInterface";
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private ValidateToken validateToken;

    /**
     * 内容摘要：获取采购订单数据
     *
     * @param token 授权令牌, startTime 订单开始时间, endTime 订单结束时间, currentPageNumber 分页数
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/purchaseOrder/getOrder", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getOrder(String token, String startTime, String endTime, String currentPageNumber){
        JSONObject returnJsonObj = new JSONObject();

        //验证token
        Map<String, Object> map = validateToken.validateToken(token);
        Integer resultCode = (Integer) map.get("resultCode");
        if (resultCode.equals(ResultCode.SUCCESS.getCode())) {
            //执行逻辑
            returnJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            returnJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());

        }else {
            returnJsonObj.put("resultCode", map.get("resultCode"));
            returnJsonObj.put("resultMsg", map.get("esultMsg"));
        }

        return returnJsonObj;
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
