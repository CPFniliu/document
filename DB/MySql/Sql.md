### SQL(Structured Query Language)

> MYSQL 中 int 类型和 Integer 类型没有区别。

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
