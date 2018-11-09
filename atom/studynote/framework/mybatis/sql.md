### mapper
```XML
<mapper namespace="cn.cpf.exercise.mybatis.inte.StudentMapper">
    <resultMap id="studentMap" type="cn.cpf.commons.entity.Student">
        <id column="stu_no" property="stuNo" jdbcType="INTEGER"/>
    </resultMap>

    <select id="findByStuNo" parameterType="string" resultMap="studentMap">
        select * from Student where stu_no = #{stuNo}
        <!-- SELECT id stu_no, name, account age, gender from sys_user where id = 1 -->
    </select>
</mapper>
```
>- column：数据库中的字段名，property：实体对象的属性名，jdbcType ：JdbcType枚举型代表字段在数据库中的类型。
- select 查询语句中可以没有参数。
- parameterType :
>
>Alias | Mapped Type
   -|-
   \_byte | byte
   \_long | long
   \_short | short
   \_int | int
   \_integer | int
   \_double | double
   \_float | float
   \_boolean | boolean
   string | String
   byte | Byte
   long | Long
   short | Short
   int | Integer
   integer | Integer
   double | Double
   float | Float
   boolean | Boolean
   date | Date
   decimal | BigDecimal
   bigdecimal | BigDecimal
   object | Object
   map | Map
   hashmap | HashMap
   list | List
   arraylist | ArrayList
   collection | Collection
   iterator | Iterator

---
### '$' & '#'
1. 使用 $ 传递参数时，若参数为单值类型，且只有一个参数，那么结果必须用value进行占位。
```XML
    <select id="findListByNameDela" parameterType="string" resultMap="studentMap">
        SELECT * FROM STUDENT WHERE NAME LIKE ${value}
    </select>
```


### 多参数传递
1. 使用0，1，2，3，等

   类似于如下的错误:
   Error querying database.  Cause: org.apache.ibatis.binding.BindingException: Parameter '1' not found. Available parameters are [arg1, arg0, param1, param2]
   Cause: org.apache.ibatis.binding.BindingException: Parameter '1' not found. Available parameters are [arg1, arg0, param1, param2]

   这句话的意思就是id找不到，可用的参数是[arg1, arg0, param1, param2]。所以可使用参数出现的顺序号码引用参数，第一个参数用arg0或param1表示，第二个参数用arg1或param2表示，以此类推（arg从0开始计数，param从1开始计数）。

   修改xml为如下方式：
   ```XML
   <!-- arg自0开始 -->
   <select id="select" resultType="model.User">
       select * from `user` where name = #{arg0} and age =#{arg1}
   </select>
   // or
   <!-- param自1开始 -->
   <select id="select" resultType="model.User">
       select * from `user` where name = #{param1} and age =#{param2}
   </select>
   ```

2. 使用注解
   ```JAVA
    List<Student> findListOrder(@Param("field") String field, @Param("value") String value);
    ```
    ```XML
    <select id="findListOrder" parameterType="string" resultMap="studentMap">
        SELECT * FROM STUDENT WHERE ${field} LIKE #{value} ORDER BY STU_NO DESC
    </select>
    ```

3. 使用map形式

   ```JAVA
    List<Student> findListBy(Map<String, Object> paramsMap);

    @Test
    public void testFindListBy() {
        mapper = session.getMapper(StudentMapper.class);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("field", "NAME");
        paramsMap.put("value", "%c%");
        List<Student> cpf = mapper.findListBy(paramsMap);
    }
    ```
    ```XML
    <select id="findListOrder" parameterType="string" resultMap="studentMap">
        SELECT * FROM STUDENT WHERE ${field} LIKE #{value} ORDER BY STU_NO DESC
    </select>
    ```
4. 把参数封装在Javabean中
   这个应该是包含第三种的，只要传入一个对象，这个对象有field的get方法，而无论get方法是私有private还是静态static，都能够成功注入参数。
   ```XML
   <select id="findListOrder" parameterType="string" resultMap="studentMap">
      SELECT * FROM STUDENT WHERE ${field} LIKE #{value} ORDER BY STU_NO DESC
   </select>
   ```


### insert
> [***深入浅出MyBatis技术原理和实战(四)***](https://blog.csdn.net/humian151/article/details/62215671)

1、插入，insert 元素有一下属性：
　　id：同 select
　　parameterType：同 select
　　flushCache：同 select
　　timeout：同 select
　　statemeterType：同 select
　　keyProperty：表示以哪个列作为属性的主键。不能和 keyColumn 同时使用。联合主键可以用逗号隔开。
　　useGeneratedKeys：这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出数据库内部生成的主键，使用该属性后就必须使用keyColumn或者KeyProperty属性中的一个。取值为 boolean 值，默认 false。
　　keyColumn：指明第几列是主键。不能和 keyProperty 同时使用。联合主键可以用逗号隔开。只接受整形参数。
　　databaseId：同 select
　　lang：使用很少

返回主键
```XML
    <insert id="insert" parameterType="cn.cpf.commons.entity.Student">
        <selectKey keyProperty="stuNo" resultType="String" order="BEFORE" >
            SELECT UUID()
        </selectKey>
        INSERT INTO STUDENT(STU_NO, NAME, AGE, GENDER) VALUES(#{stuNo}, #{name}, #{age}, #{gender})
    </insert>
```
(https://blog.csdn.net/ask_rent/article/details/6320326)
