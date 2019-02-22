# infomation_schema

```sql
-- 查询 smp_db 数据库中所有的数据表 INFORMATION_SCHEMA.TABLES
select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='smp_db'

-- 查询smp_db数据库中smp_import_db表所有的字段information_schema.`COLUMNS`
select COLUMN_NAME from information_schema.`COLUMNS` where TABLE_SCHEMA='smp_db' and TABLE_NAME='smp_import_db'
```


### ALTER

操作 | 语句
-|-
添加字段 | ALTER TABLE test_tbl ADD i INT
移除字段 | ALTER TABLE test_tbl DROP i
修改字段 | ALTER TABLE test_tbl MODIFY c CHAR(10);
修改字段 | ALTER TABLE test_tbl CHANGE j j INT;
添加字段 | ALTER TABLE test_tbl MODIFY j BIGINT NOT NULL DEFAULT 100;
设置默认 | ALTER TABLE test_tbl ALTER i SET DEFAULT 1000;
移除默认 | ALTER TABLE test_tbl ALTER i DROP DEFAULT;
修改表名 | ALTER TABLE test_tbl RENAME TO alter_tbl;
