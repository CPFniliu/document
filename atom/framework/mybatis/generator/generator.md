#### 配置在项目中
1. 添加 pom 配置
   ```XML
      <build>
          <plugins>
              <plugin>
                  <groupId>org.mybatis.generator</groupId>
                  <artifactId>mybatis-generator-maven-plugin</artifactId>
                  <version>1.3.7</version>
                  <configuration>
                      <configurationFile>src/main/resources/mybatis/generatorConfig.xml</configurationFile>
                  </configuration>
              </plugin>
          </plugins>
      </build>
   ```
2. 配置 generatorConfig.xml文件
3. 执行
`mvn mybatis-generator:generate`
4. 生成Bean 和 Example
