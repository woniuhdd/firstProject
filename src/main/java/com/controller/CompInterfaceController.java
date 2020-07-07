package com.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(CompInterfaceController.ACTION_PATH)
public class CompInterfaceController {
    protected static final String ACTION_PATH = "/compInterface";
    private static final Logger log = Logger.getLogger(HelloController.class);

    /**
     * 内容摘要：获取采购订单数据
     *
     * @param accessToken 授权令牌, startTime 订单开始时间, endTime 订单结束时间, currentPageNumber 分页数
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/purchaseOrder/getOrder", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getOrder(String accessToken, String startTime, String endTime, String currentPageNumber){
        return null;
    }

    /**
     * 配送订单
     *
     * @param accessToken    令牌
     * @param distributeInfo 密文
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/distribution/distribute", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject distribute(String accessToken, String distributeInfo){
        return null;
    }

    /**
     * 票据上传
     * @param accessToken 令牌
     * @param invoiceInfo 发票信息
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/invoice/addInvoice", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addInvoice(String accessToken, String invoiceInfo){
        return  null;
    }

    /**
     * 上传票据图片
     *
     * @param accessToken 令牌
     * @param imgList      图片集合
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/invoiceImage/addImage", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addImage(String accessToken, String imgList){
        return null;
    }

    /**
     * 发票清单上传
     *
     * @param accessToken 令牌
     * @param saleInfo    发票信息
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/saleList/addSale", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addSale(String accessToken, String saleInfo){
        return null;
    }

    /**
     * 获取发票核验结果
     * @param accessToken 令牌
     * @param startTime
     * @param endTime
     * @param currentPageNumber
     * @return
     */
    @RequestMapping(value = "/distribution/invoiceInfo", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getInvoiceCheckInfo(String accessToken, String startTime, String endTime, String currentPageNumber){
        return null;
    }

    /**
     * 获取医院撤单信息
     * @param accessToken
     * @param startTime
     * @param endTime
     * @param currentPageNumber
     * @return
     */
    @RequestMapping(value = "/purchaseOrder/getCancelOrder", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getCancelPurchaseOrder(String accessToken, String startTime, String endTime, String currentPageNumber){
        return null;
    }

    /**
     * 配送发票补录
     * @param accessToken
     * @param distributeInfo
     * @return
     */
    @RequestMapping(value = "/distribution/addDistributeInvoice", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject addDistributeInvoice(String accessToken, String distributeInfo){
        return null;
    }

    /**
     * 获取商品信息
     *
     * @param accessToken       令牌
     * @param month             月份
     * @param procurecatalogIds 商品ID
     * @param currentPageNumber 当前页码
     * @return
     */
    @RequestMapping(value = "/procurecatalog/getProcurecatalog", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getProcurecatalog(String accessToken, String month, String procurecatalogIds, String currentPageNumber){
        return null;
    }

    /**
     * 获取生产企业
     *
     * @param accessToken       令牌
     * @param companyIds        企业编号
     * @param month             月份
     * @param currentPageNumber 当前页码
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/company/getCompany", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getCompany(String accessToken, String companyIds, String month, String currentPageNumber){
        return null;
    }

    /**
     * 获取医疗机构
     *
     * @param accessToken       令牌
     * @param hospitalIds       医疗机构编号
     * @param month             月份
     * @param currentPageNumber 页码
     * @return
     */
    @RequestMapping(value = "/hospital/getHospital", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getHospital(String accessToken, String hospitalIds, String month, String currentPageNumber){
        return null;
    }
}
