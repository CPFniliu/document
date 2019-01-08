# sql 优化

1. 使用表的别名
   当在SQL语句中连接多个表时，请使用表的别名并把别名前缀于每个列名上。这样就可以减少解析的时间并减少哪些友列名歧义引起的语法错误。

2. 避免在索引上使用计算
   在where字句中，如果索引列是计算或者函数的一部分，DBMS的优化器将不会使用索引而使用全表查询,函数属于计算的一种

   ```sql
      -- 效率低：
   select * from person where salary*12 > 25000(salary是索引列)
      -- 效率高：
   select * from person where salary > 25000/12(salary是索引列)
   ```

3. 用 union all 替换 union
   当SQL语句需要union两个查询结果集合时，即使检索结果中不会有重复的记录，如果使用union这两个结果集同样会尝试进行合并，然后在输出最终结果前进行排序，因此如果可以判断检索结果中不会有重复的记录时候，应该用union all，这样效率就会因此得到提高。

4. 避免SQL中出现隐式类型转换
   当某一张表中的索引字段在作为where条件的时候，如果进行了隐式类型转换，则此索引字段将会不被识别，因为隐式类型转换也属于计算，所以此时DBMS会使用全表扫面。

5. 防止检索范围过宽
   如果DBMS优化器认为检索范围过宽，那么将放弃索引查找而使用全表扫描。下面几种可能造成检索范围过宽的情况。
   - 使用is not null或者不等于判断，可能造成优化器假设匹配的记录数太多。
   - 使用like运算符的时候，“a%”将会使用索引，而“a%c”和“%a”则会使用全表扫描，因此“a%c”和“%a”不能被有

## EXIST & IN

> http://www.cnblogs.com/xuanhai/p/5810918.html

`select * from A where id in(select id from B)`

`select a.* from A a where exists(select 1 from B b where a.id=b.id)`

EXISTS 指定一个子查询，检测 行 的存在。子查询返回的是一个boolean值, 如果子查询包含行，则返回 TRUE ，否则返回 FLASE 。
一种通俗的可以理解为：将外查询表的每一行，代入内查询作为检验，如果内查询返回的结果取非空值，则EXISTS子句返回TRUE，这一行行可作为外查询的结果行，否则不能作为结果。

NOT EXISTS 的作用与 EXISTS 正好相反。如果子查询没有返回行，则满足了 NOT EXISTS 中的 WHERE 子句。

```sql
-- 在子查询中使用 NULL 仍然返回结果集
-- 下面两条语句等同
select * from TableIn where exists(select null);
select * from TableIn;

-- 2. 比较使用 EXISTS 和 IN 的查询。注意两个查询返回相同的结果。
-- in()适合B表比A表数据小的情况
-- exists()适合B表比A表数据大的情况
-- 当in和exists在查询效率上比较时，in查询的效率快于exists的查询效率
-- 当A表数据与B表数据一样大时,in与exists效率差不多,可任选一个使用.
select * from TableIn where exists(select BID from TableEx where BNAME=TableIn.ANAME)
select * from TableIn where ANAME in(select BNAME from TableEx)

-- 3. 比较使用 EXISTS 和 = ANY 的查询。注意两个查询返回相同的结果。
select * from TableIn where exists(select BID from TableEx where BNAME=TableIn.ANAME)
select * from TableIn where ANAME=ANY(select BNAME from TableEx)

-- 4. 在插入记录前，需要检查这条记录是否已经存在，只有当记录不存在时才执行插入操作，可以通过使用 EXISTS 条件句防止插入重复记录。
INSERT INTO TableIn (ANAME,ASEX) SELECT top 1 '张三', '男' FROM TableIn WHERE not exists (select * from TableIn where TableIn.AID = 7);
```

### in、not in、exists和not exists的区别：

先谈谈in和exists的区别：
exists后面一般都是子查询，当子查询返回行数时，exists返回true。
select * from class where exists (select 'x" form stu where stu.cid=class.cid)
当in和exists在查询效率上比较时，in查询的效率快于exists的查询效率
exists(xxxxx)后面的子查询被称做相关子查询, 他是不返回列表的值的, 只是返回一个ture或false的结果(这也是为什么子查询里是select 'x'的原因 当然也可以

select任何东西) 也就是它只在乎括号里的数据能不能查找出来，是否存在这样的记录。
其运行方式是先运行主查询一次 再去子查询里查询与其对应的结果 如果存在，返回ture则输

出,反之返回false则不输出,再根据主查询中的每一行去子查询里去查询.

exists执行顺序如下：
1.首先执行一次外部查询
2.对于外部查询中的每一行分别执行一次子查询，而且每次执行子查询时都会引用外部查询中当

前行的值。
3.使用子查询的结果来确定外部查询的结果集。
如果外部查询返回100行，SQL   就将执行101次查询，一次执行外部查询，然后为外部查询返回的每一行执行一次子查询。

in：包含
查询和所有女生年龄相同的男生
select * from stu where sex='男' and age in(select age from stu where sex='女')
in()后面的子查询 是返回结果集的,换句话说执行次序和exists()不一样.子查询先产生结果集,
然后主查询再去结果集里去找符合要求的字段列表去.符合要求的输出,反之则不输出.


not in和not exists的区别：
not in 只有当子查询中，select 关键字后的字段有not null约束或者有这种暗示时用not in,另外如果主查询中表大，子查询中的表小但是记录多，则应当使用not in,
例如:查询那些班级中没有学生的，
select * from class where cid not in(select distinct cid from stu)
当表中cid存在null值，not in 不对空值进行处理
解决:select * from class

where cid not in

(select distinct cid from stu where cid is not null)


not in的执行顺序是：是在表中一条记录一条记录的查询(查询每条记录）符合要求的就返回结果集，不符合的就继续查询下一条记录，直到把表中的记录查询完。也就是说为了证明找不到，所以只能查询全部记录才能证明。并没有用到索引。
not exists：如果主查询表中记录少，子查询表中记录多，并有索引。
例如:查询那些班级中没有学生的，
select * from class2

where not exists

(select * from stu1 where stu1.cid =class2.cid)


not exists的执行顺序是：在表中查询，是根据索引查询的，如果存在就返回true，如果不存在就返回false，不会每条记录都去查询。
之所以要多用not exists，而不用not in，也就是not exists查询的效率远远高与not in查询的效率。

 

 

 实例：

exists,not exists的使用方法示例，需要的朋友可以参考下。
 
学生表：create table student
(
 id number(8) primary key,
 name varchar2(10),deptment number(8)
)
选课表：create table select_course
(
  ID         NUMBER(8) primary key,
  STUDENT_ID NUMBER(8) foreign key (COURSE_ID) references course(ID),
  COURSE_ID  NUMBER(8) foreign key (STUDENT_ID) references student(ID)
)
课程表：create table COURSE
(
  ID     NUMBER(8) not null,
  C_NAME VARCHAR2(20),
  C_NO   VARCHAR2(10)
)
student表的数据：
        ID NAME            DEPTMENT_ID
---------- --------------- -----------
         1 echo                   1000
         2 spring                 2000
         3 smith                  1000
         4 liter                  2000
course表的数据：
        ID C_NAME               C_NO
---------- -------------------- --------
         1 数据库               data1
         2 数学                 month1
         3 英语                 english1
select_course表的数据：
        ID STUDENT_ID  COURSE_ID
---------- ---------- ----------
         1    1         1
         2    1         2
         3    1         3
         4    2         1
         5    2         2
         6    3         2
1.查询选修了所有课程的学生id、name:（即这一个学生没有一门课程他没有选的。）
分析：如果有一门课没有选，则此时(1)select * from select_course sc where sc.student_id=ts.id 
and sc.course_id=c.id存在null，
这说明(2)select * from course c 的查询结果中确实有记录不存在(1查询中)，查询结果返回没有选的课程，
此时select * from t_student ts 后的not exists 判断结果为false，不执行查询。
SQL> select * from t_student ts where not exists
	 (select * from course c where not exists
  		(select * from select_course sc where sc.student_id=ts.id and sc.course_id=c.id));        
        ID NAME            DEPTMENT_ID
---------- --------------- -----------
         1 echo                   1000
2.查询没有选择所有课程的学生，即没有全选的学生。（存在这样的一个学生，他至少有一门课没有选），
分析：只要有一个门没有选，即select * from select_course sc where student_id=t_student.id and course_id
=course.id 有一条为空，即not exists null 为true,此时select * from course有查询结果（id为子查询中的course.id ），
因此select id,name from t_student 将执行查询（id为子查询中t_student.id ）。
SQL> select id,name from t_student where exists
	(select * from course where not exists
		(select * from select_course sc where student_id=t_student.id and course_id=course.id));
        ID NAME
---------- ---------------
         2 spring
         3 smith
         4 liter
3.查询一门课也没有选的学生。（不存这样的一个学生，他至少选修一门课程），
分析：如果他选修了一门select * from course结果集不为空，not exists 判断结果为false;
select id,name from t_student 不执行查询。
SQL> select id,name from t_student where not exists
	(select * from course where exists
		(select * from select_course sc where student_id=t_student.id and course_id=course.id));
        ID NAME
---------- ---------------
         4 liter
4.查询至少选修了一门课程的学生。
SQL> select id,name from t_student where exists
	(select * from course where  exists
		(select * from select_course sc where student_id=t_student.id and course_id=course.id));
        ID NAME
---------- ---------------
         1 echo
         2 spring
         3 smith