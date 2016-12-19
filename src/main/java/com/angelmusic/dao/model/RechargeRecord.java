package com.angelmusic.dao.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 充值记录
 * Created by wangyong on 16-12-19.
 */
@TableBind(pkName = "recharge_record_id", tableName = "am_recharge_record")
public class RechargeRecord extends Model<RechargeRecord> {

    public static final RechargeRecord ME = new RechargeRecord();

}
