package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 激活码
 * Created by wangyong on 16-12-15.
 */
public class ActivationCode extends Model<ActivationCode> {

    public static final ActivationCode ME = new ActivationCode();

    /**
     * 根据激活码获取激活码信息
     *
     * @return
     */
    public ActivationCode getActivationCodeByCode(String code) {
        return ME.findFirst(PlusSqlKit.sql("activationCode.getActivationCodeByCode"), code.trim());
    }

    /**
     * 更新激活码状态已激活
     */
    public boolean updateActivationCodeStatus(String code) {
        return Db.update(PlusSqlKit.sql("activationCode.updateActivationCodeStatus"), code.trim(), Constant.ACTIVATION_CODE_ACTIVATED) > 0;
    }


}
