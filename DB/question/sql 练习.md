# SQL 练习

1. 一个叫 team 的表，里面只有一个字段name, 一共有4 条纪录，分别是a,b,c,d, 对应四个球对，现在四个球对进行比赛，用一条sql 语句显示所有可能的比赛组合.
2. 学生表分数表 SCORE 如下: 删除除了自动编号不同, 其他都相同的学生冗余信息.

   ID | SNO | NAME | course | score
   -|-
   01 | 2005001 | 张三 | 数学 | 69
   02 | 2005002 | 李四 | 数学 | 89
   03 | 2005001 | 张三 | 数学 | 69

## answer

```sql
# question 1
select a.name, b.name from team a, team b where a.name < b.name

# question 2
DELETE FROM SCORE WHERE ID NOT IN(
   SELECT ID FROM SCORE GROUP BY SNO, NAME, COURSE, SCORE
)

```
