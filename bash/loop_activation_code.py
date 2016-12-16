#!/usr/bin/python
# -*- coding: utf-8 -*
'''
批量生成激活码
'''

import sys
import uuid
import MySQLdb


def mysql_conn():
    global db
    global cur
    db = MySQLdb.connect(host="192.168.2.110", user="root", passwd="root", db="am_plus", charset="utf8")
    cur = db.cursor()


def mysql_conn_close():
    if not cur == None:
        cur.close()
    if not db == None:
        db.close()


def loop_save_activation_code():
    # 默认生成1000
    nums = 1000
    if len(sys.argv) >= 2:
        nums = sys.argv[1]
    values = []
    for num in range(1, int(nums) + 1):
        # 生成永不重复唯一的标识
        code = uuid.uuid4()
        # print codeNo
        values.append((code,))
    print '生成的编号:' + str(values)

    try:
        # 往数据库表中存入二维码的编号
        sql = "insert into am_activation_code(code,status) VALUES (%s,1)"
        mysql_conn()
        cur.executemany(sql, values)
        db.commit()
    except MySQLdb.Error, e:
        db.rollback()
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    finally:
        mysql_conn_close()
        print '关闭数据库连接'


if __name__ == '__main__':
    loop_save_activation_code()
