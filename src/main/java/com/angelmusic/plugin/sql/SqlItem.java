package com.angelmusic.plugin.sql;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by wangyong on 16-12-14.
 */
@XmlRootElement
class SqlItem {
    @XmlAttribute
    String id;

    @XmlValue
    String value;

}