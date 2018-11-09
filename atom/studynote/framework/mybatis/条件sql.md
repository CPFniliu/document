#### mybatis 中条件OGNL 表达式中string类型在验证时加引号
   ```xml
   <if test="gender != null and gender != '' and (gender == 1 or gender == 0)">
      GENDER = #{gender}
   </if>
   ```
