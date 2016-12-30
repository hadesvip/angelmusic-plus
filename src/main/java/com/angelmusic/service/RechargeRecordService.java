package com.angelmusic.service;

import com.angelmusic.dao.model.RechargeRecord;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 充值流水服务接口
 * Created by wangyong on 16-12-22.
 */
public class RechargeRecordService {

    public static final RechargeRecordService ME = new RechargeRecordService();


    /**
     * 保存充值流水
     *
     * @param linkId       订单号
     * @param chargeStatus 订单状态
     * @param chargeMsg    订单状态描述
     * @param price        金额（分）
     * @param cpParam      透传参数(订单号)
     * @param payType      支付类型
     */
    @Before(Tx.class)
    public void saveRechargeRecord(String linkId, String chargeStatus, String chargeMsg, long price, String cpParam, String payType) {
        //保存充值流水
        RechargeRecord.ME.saveRechargeRecord(linkId, chargeStatus, chargeMsg, price / 10, cpParam, payType);
    }
}
