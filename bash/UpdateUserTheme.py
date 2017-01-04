# -*- coding: utf-8 -*-
'''
Created on 2017-01-03
@todo: xxxx-xx-xx 01:00:00
@summary: update user theme
@author: ningxuwen
'''
import MySQLdb
import MySQLdb.cursors
import time
import datetime
import os
import shutil
import sys

reload(sys)
sys.setdefaultencoding('utf8')


def connDB():
    global conn
    global cur
    try:
        conn = MySQLdb.connect(host='localhost', user='root', passwd='abc123', port=3306, charset="utf8",
                               cursorclass=MySQLdb.cursors.DictCursor)
        cur = conn.cursor()
        conn.select_db('am_plus')
    except MySQLdb.Error, e:
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])


def closeDB():
    cur.close()
    conn.close()


def getTheme(d, n):
    dy = d.year
    dm = d.month
    dd = d.day

    # n = datetime.date.today()
    ny = n.year
    nm = n.month

    nd = n.day
    fy = ny - dy
    fm = nm - dm

    themeCount = 0 if (fy * 12 + fm - 1) < 0 else (fy * 12 + fm)
    if (nd >= dd):
        themeCount = themeCount + 1
    return themeCount


def updateTheme(pageSize):
    try:
        print '-----开始更新用户主题-----'
        i = 0;
        while 1 == 1:
            i = i + 1
            # 分页查询100条记录
            selectSql = "SELECT * FROM am_order_record ORDER BY order_date DESC LIMIT " + str((i - 1) * pageSize) + "," + str(pageSize)
            cur.execute(selectSql)
            results = cur.fetchall()
            # 不为空的时候
            if (len(results) > 0):
                for r in results:
                    print "  ------开始更新" + r['account'] + "的用户主题";
                    # 查询该用户下有多少交易记录
                    orderSql = "SELECT * from am_order_record where account = %s order by order_date desc" % (r['account'])
                    cur.execute(orderSql);
                    orderRes = cur.fetchall()

                    if (len(orderRes)) > 0:
                        # 判断第一条记录是否和最外面的记录的订单号一致  不一致。证明前面已经处理过 这边就不做任何的处理了,直接跳出
                        if(orderRes[0]['order_id'] != r['order_id']):
                            continue;
                        else:
                            themCount = 0;
                            m = 0;
                            themCount = themCount + getTheme(orderRes[0]['order_date'], datetime.date.today())
                            for r1 in orderRes:
                                #从第二条开始进行循环增加
                                if m > 0:
                                    #激活码
                                    if r1['type'] == 1:
                                        themCount = themCount + 12
                                    #大礼包
                                    elif r1['type'] == 2:
                                        packSql = "select * from am_gift_pack WHERE gift_pack_id = '%s'" %(r1['product'])
                                        cur.execute(packSql)
                                        packResu = cur.fetchone()
                                        if packResu is not None:
                                            #加大礼包的解锁主题数
                                            themCount = themCount + packResu['effective_time']
                                m = m + 1

                            #接下来操作用户的已解锁主题数
                            selectUserSql = "select * from am_user_topic WHERE account = '%s' ORDER BY id DESC LIMIT  1" % (r['account'])
                            cur.execute(selectUserSql)
                            ut = cur.fetchone();
                            if ut is not None:
                                print  "    账户" + r['account'] + "存在,执行更新操作"
                                updateSql = "UPDATE am_user_topic SET topic_count = '%s' WHERE id = '%s'"
                                cur.execute(updateSql, (themCount, ut['id']))
                            else:
                                print  "    账户" + r['account'] + "不存在,执行更新操作"
                                # insertSql = "insert into am_user_topic(account,topic_count) VALUES (" + r['account'] + ","+ str(themCount) +")"
                                insertSql = "INSERT INTO am_user_topic(account,topic_count) VALUES (%s,%s)"
                                print  insertSql
                                cur.execute(insertSql, (r['account'], themCount))
                    print '  ------结束更新' + r['account'] + '的用户主题';
            else:
                break;
        print '-----结束更新用户主题-----'
    except MySQLdb.Error, e:
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    finally:
        conn.commit()


if __name__ == '__main__':
    connDB()
    updateTheme(100)
    closeDB()
