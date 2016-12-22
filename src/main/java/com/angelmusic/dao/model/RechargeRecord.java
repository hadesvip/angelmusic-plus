package com.angelmusic.dao.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 充值流水
 * Created by wangyong on 16-12-21.
 */
public class RechargeRecord extends Model<RechargeRecord> {

    public final static RechargeRecord ME = new RechargeRecord();


    /**
     * 保存充值流水
     *
     * @param linkId       订单号
     * @param chargeStatus 支付状态
     * @param chargeMsg    支付状态描述
     * @param price        价格
     * @param cpParam      订单号
     * @param payType      支付类型
     * @return
     */
    public boolean saveRechargeRecord(String linkId, String chargeStatus, String chargeMsg, long price, String cpParam, String payType) {

        return ME
                .set("link_id", linkId)
                .set("order_id", cpParam)
                .set("charge_status", chargeStatus)
                .set("charge_msg", chargeMsg)
                .set("price", price)
                .set("pay_type", payType)
                .save();
    }
}
