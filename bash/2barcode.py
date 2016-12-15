#!/usr/bin/python
# -*- coding: utf-8 -*
'''
批量生成二维码
'''
import qrcode
import MySQLdb
import sys
import uuid
import os


# import json

# 生成二维码
def create_2barcode(code):
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    # json_str = json.dumps()
    # print json_str
    qr.add_data(str(code[0]))
    qr.make(fit=True)
    img = qr.make_image()
    if not os.path.exists('codes'): os.makedirs('codes')
    img.save('codes/' + str(code[0]) + ".png")


# 默认生成5W的图片数量
nums = 50000
if len(sys.argv) >= 2:
    nums = sys.argv[1]

print '准备生成二维码图片数量' + str(nums)

values = []
for num in range(1, int(nums) + 1):
    # 生成永不重复唯一的标识
    codeNo = uuid.uuid4()
    # print codeNo
    values.append((codeNo,))
print '生成的二维码编号:' + str(values)

print '开始连接数据库'

db = MySQLdb.connect("192.168.2.110", "root", "root", "am_secondary", 3306)

try:
    # 往数据库表中存入二维码的编号
    sql = "insert into am_qrcode(qrcode_no) VALUES (%s)"
    cursor = db.cursor()
    cursor.executemany(sql, values)
    db.commit()
    # 根据二维码编码生成二维码
    for code in values:
        create_2barcode(code)
except MySQLdb.Error, e:
    db.rollback()
    print "Mysql Error %d: %s" % (e.args[0], e.args[1])
finally:
    cursor.close()
    db.close()
    print '关闭数据库连接'
print '执行结束'
