#### 配置一定要放对位置（否则会出现报错）
      The content of element type "configuration" must match "(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)".

#### 列名和属性名不一致的情况

   plan1. 数据库查询起别名
   plan2. 使用 resultMapa


#### 定义别名、
   在mybatis-config.xml 中配置别名， 在映射文件中可以直接使用 alias 配置的值
   ```XML
   <!--定义别名-->
   <typeAliases>
      <typeAlias alias="Student" type="cn.cpf.commons.entity.Student"/>
   </typeAliases>
   ```
####
