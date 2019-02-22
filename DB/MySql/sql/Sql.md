# SQL(Structured Query Language)

[TOC]

## 多表联合

### mysql 多表删除

   ```sql
   DELETE ity_user_role, ity_user from
      ity_user_role INNER JOIN ity_user on ity_user_role.uuid = ity_user.uuid
      where ity_user.Mobile_Phone = '19951235679';
   ```

### 在查询的时候进行更新或删除

   在查询的时候进行更新或删除

   ```sql
   -- 删除 ity_user 表中 mobile_phone 重复的数据
   -- 执行不成功会出现 [Err] 1093 - You can't specify target table 'ity_user' for update in FROM clause
   DELETE from ity_user WHERE mobile_phone IN (
      SELECT mobile_phone from ity_user GROUP BY mobile_phone HAVING COUNT(1) > 1
   )
   ```

   但是如果改为下面的语句, 就可以执行成功.

   ```sql
   DELETE from ity_user WHERE mobile_phone IN (
      SELECT mobile_phone from
      (SELECT mobile_phone from ity_user GROUP BY mobile_phone HAVING COUNT(1) > 1) s
   )
   ```
