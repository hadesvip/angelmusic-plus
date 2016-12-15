package com.angelmusic.plugin.sql;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangyong on 16-12-14.
 */
@XmlRootElement(name = "root")
public class Root {

    @XmlElement(name = "sqlGroup")
    List<SqlGroup> sqlGroupList = Lists.newArrayList();
    

}
