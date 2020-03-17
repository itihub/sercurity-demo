#!/bin/sh

echo "==== starting to flyway ===="
# 升级脚本
mvn -N -Psecuritydb flyway:migrate

# 清除指定schema下所有的对象，包括table、view、triggers...
#mvn -N -Psecuritydb flyway:clean
# 显示指定schema的升级状态，当前的数据库的版本信息
#mvn -N -Psecuritydb flyway:info
# 用于校验，范围包括已升级的脚本是否改名，已升级的脚本内容是否修改
#mvn -N -Psecuritydb flyway:validate
# 用户从一个已有的数据库导出脚本，作为flyway的升级脚本。已存在的数据库是不需要升级的。
#mvn -N -Psecuritydb flyway:baseline
