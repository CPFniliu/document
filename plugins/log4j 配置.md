# log4j

## 基础知识

### Log4j 简介

> Log4j是Apache的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件，甚至是套接口服务器、NT的事件记录器、UNIX Syslog守护进程等；我们也可以控制每一条日志的输出格式；通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程。最令人感兴趣的就是，这些可以通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。

### Log4j 构成

#### Loggers(日志记录器)

   控制要启用或禁用哪些日志记录语句，并对日志信息进行级别限制.
   > Logger被指定为实体，由一个String类的名字识别。Logger的名字是大小写敏感的，且名字之间具有继承关系，子名用父名作为前缀，用点“.”分隔，例如x.y是x.y.z的父亲。

   root Logger(根Logger)是所有Logger的祖先，它有如下属性：
      - 它总是存在的。
      - 它不可以通过名字获得。
   root Logger可以通过以下语句获得：

   ```java
   public static Logger Logger.getRootLogger();
   public static Logger Logger.getLogger(Class clazz)
   ```

#### Appenders(输出端)

   用来指定日志信息输出到哪个地方，可以同时指定多个输出目的地。Log4j允许将信息输出到许多不同的输出设备中，一个log信息输出目的地就叫做一个Appender。
   每个Logger都可以拥有一个或多个Appender，每个Appender表示一个日志的输出目的地。可以使用Logger.addAppender(Appender app)为Logger增加一个Appender，也可以使用Logger.removeAppender(Appender app)为Logger删除一个Appender。

   Log4j几种常用的输出目的地。

   Appenders 级别 | 含义
   - | -
   org.apache.log4j.ConsoleAppender | 将日志信息输出到控制台。
   org.apache.log4j.FileAppender | 将日志信息输出到一个文件。
   org.apache.log4j.DailyRollingFileAppender | 将日志信息输出到一个日志文件，并且每天输出到一个新的日志文件。
   org.apache.log4j.RollingFileAppender | 将日志信息输出到一个日志文件，并且指定文件的尺寸，当文件大小达到指定尺寸时，会自动把文件改名，同时产生一个新的文件。
   org.apache.log4j.WriteAppender | 将日志信息以流格式发送到任意指定地方。
   org.apache.log4j.jdbc.JDBCAppender | 通过JDBC把日志信息输出到数据库中。

#### Layout(日志格式化器)

   控制日志信息的显示格式

   1. HTMLLayout : 格式化日志输出为HTML表格形式如下
   2. SimpleLayout : 以一种非常简单的方式格式化日志输出，它打印三项内容：级别-信息
   3. PatternLayout : 根据指定的转换模式格式化日志输出，或者如果没有指定任何转换模式，就使用默认的转化模式格式。
   4. TTCCLayout : 包含日志产生的时间、线程、类别等等信息

### 日志级别

   A：off      最高等级，用于关闭所有日志记录。
   B：fatal    指出每个严重的错误事件将会导致应用程序的退出。
   C：error    指出虽然发生错误事件，但仍然不影响系统的继续运行。
   D：warm     表明会出现潜在的错误情形。
   E：info     一般和在粗粒度级别上，强调应用程序的运行全程。
   F：debug    一般用于细粒度级别上，对调试应用程序非常有帮助。
   G：all      最低等级，用于打开所有日志记录。

## 配置文件详解

1. 配置根Logger()

   log4j.rootLogger = INFO, appenderName1, appenderName2

   > log4j.rootLogger = [level],appenderName,appenderName2,...
   > level是日志记录的优先级，分为OFF,TRACE,DEBUG,INFO,WARN,ERROR,FATAL,ALL
   > #Log4j建议只使用四个级别，优先级从低到高分别是DEBUG,INFO,WARN,ERROR
   > 通过在这里定义的级别，您可以控制到应用程序中相应级别的日志信息的开关, 比如在这里定义了INFO级别，则应用程序中所有DEBUG级别的日志信息将不被打印出来
   > appenderName就是指定日志信息输出到哪个地方。可同时指定多个输出目的

2. 配置日志信息输出目的地 Appender

   ```properties
   # 语法
   log4j.appender.appenderName = fully.qualified.name.of.appender.class
   # 示例
   log4j.appender.appenderName.optionN = valueN
   ```

   Appender | 含义 | 可选值
   -|-|-
   org.apache.log4j.ConsoleAppender | 输出到控制台 | Threshold, ImmediateFlush, Target
   org.apache.log4j.FileAppender | 输出到文件 | Threshold, ImmediateFlush, File, Append, Encoding
   org.apache.log4j.DailyRollingFileAppender | 每天产生一个日志文件 | Threshold, ImmediateFlush, File, Append, Encoding, DatePattern
   org.apache.log4j.RollingFileAppender | 文件大小到达指定尺寸的时候产生一个新的文件 | Threshold, ImmediateFlush, File, Append, MaxFileSize, MaxBackupIndex, Encoding
   org.apache.log4j.WriterAppender | 将日志信息以流格式发送到任意指定的地方 | 

   valueN 可选值 | 含义 | values
   -|-
   Threshold | 指定日志消息的输出最低层次 | DEBUG, INFO, WARNING, ERROR
   ImmediateFlush | 默认值是true,所有的消息都会被立即输出 | true, false
   Target | 输出到控制台调用函数(默认值System.out) | System.err, System.out
   File | 输出文件路径 | eg : C:\log4j.log
   Append | true将消息追加到指定文件中，false指将消息覆盖指定的文件内容 | true, false
   Encoding | 指定文件编码格式 | UTF-8
   MaxFileSize | 日志文件到达该大小时,将会自动滚动 | eg : 100KB/MB/GB
   MaxBackupIndex | 指定可以产生的滚动文件的最大数 | 整数
   DatePattern | 每个多长时间产生一个新文件 | yyyy-ww:每周, yyyy-MM:每月, yyyy-ww:每周, yyyy-MM-dd:每天, yyyy-MM-dd-a:每天两次, yyyy-MM-dd-HH:每小时, yyyy-MM-dd-HH-mm:每分钟

3. 配置日志信息的格式(布局)

   ```properties
   log4j.appender.appenderName.layout = fully.qualified.name.of.layout.class
   log4j.appender.appenderName.layout.optionN = valueN
   ```

   layout | 含义 | 可选值
   -|-|-
   org.apache.log4j.HTMLLayout | 以HTML表格形式布局 | LocationInfo, Title
   org.apache.log4j.PatternLayout | 可以灵活地指定布局模式 | ConversionPattern
   org.apache.log4j.SimpleLayout | 包含日志信息的级别和信息字符串 |
   org.apache.log4j.TTCCLayout | 包含日志产生的时间、线程、类别等等信息 |
   org.apache.log4j.xml.XMLLayout | 以XML形式布局 | LocationInfo

   valueN 可选值 | 含义
   -|-
   LocationInfo | TRUE:默认值false,输出java文件名称和行号
   Title | Struts Log Message:默认值 Log4J Log Messages
   ConversionPattern | %m%n:格式化指定的消息(参数意思下面有)

   > Log4J采用类似C语言中的printf函数的打印格式格式化日志信息，打印参数如下：
   > 字符 | 含义
   > -|-
   > %m | 输出代码中指定的消息
   > %p | 输出优先级，即DEBUG,INFO,WARN,ERROR,FATAL
   > %r | 输出自应用启动到输出该log信息耗费的毫秒数
   > %c | 输出所属的类目,通常就是所在类的全名
   > %t | 输出产生该日志事件的线程名
   > %n | 输出一个回车换行符，Windows平台为“\r\n”，Unix平台为“\n”
   > %d | 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式, 如：%d{yyyy年MM月dd日 HH:mm:ss,SSS}，输出类似：2012年01月05日 22:10:28,921
   > %l | 输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数, 如：Testlog.main(TestLog.java:10)
   > %F | 输出日志消息产生时所在的文件名称
   > %L | 输出代码中的行号
   > %x | 输出和当前线程相关联的NDC(嵌套诊断环境),像java servlets多客户多线程的应用中
   > %% | 输出一个"%"字符
   > 可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度、和文本的对齐方式。如：
   > 字符 | 含义
   > -|-
   > %5c | 输出category名称，最小宽度是5，category<5，默认的情况下右对齐
   > %-5c | 输出category名称，最小宽度是5，category<5，"-"号指定左对齐,会有空格
   > %.5c | 输出category名称，最大宽度是5，category>5，就会将左边多出的字符截掉，<5不会有空格
   > %20.30c | category名称<20补空格，并且右对齐，>30字符，就从左边交远销出的字符截掉

### 指定特定包的输出特定的级别

```properties
#log4j.logger.org.springframework=DEBUG
################################################################################

#OFF,systemOut,logFile,logDailyFile,logRollingFile,logMail,logDB,ALL
log4j.rootLogger =ALL,systemOut,logFile,logDailyFile,logRollingFile,logMail,logDB

#输出到控制台
log4j.appender.systemOut = org.apache.log4j.ConsoleAppender
log4j.appender.systemOut.layout = org.apache.log4j.PatternLayout
log4j.appender.systemOut.layout.ConversionPattern = [%-5p][%-22d{yyyy/MM/dd HH:mm:ssS}][%l]%n%m%n
log4j.appender.systemOut.Threshold = DEBUG
log4j.appender.systemOut.ImmediateFlush = TRUE
log4j.appender.systemOut.Target = System.out

#输出到文件
log4j.appender.logFile = org.apache.log4j.FileAppender
log4j.appender.logFile.layout = org.apache.log4j.PatternLayout
log4j.appender.logFile.layout.ConversionPattern = [%-5p][%-22d{yyyy/MM/dd HH:mm:ssS}][%l]%n%m%n
log4j.appender.logFile.Threshold = DEBUG
log4j.appender.logFile.ImmediateFlush = TRUE
log4j.appender.logFile.Append = TRUE
log4j.appender.logFile.File = ../Struts2/WebRoot/log/File/log4j_Struts.log
log4j.appender.logFile.Encoding = UTF-8

#按DatePattern输出到文件
log4j.appender.logDailyFile = org.apache.log4j.DailyRollingFileAppender
log4j.appender.logDailyFile.layout = org.apache.log4j.PatternLayout
log4j.appender.logDailyFile.layout.ConversionPattern = [%-5p][%-22d{yyyy/MM/dd HH:mm:ssS}][%l]%n%m%n
log4j.appender.logDailyFile.Threshold = DEBUG
log4j.appender.logDailyFile.ImmediateFlush = TRUE
log4j.appender.logDailyFile.Append = TRUE
log4j.appender.logDailyFile.File = ../Struts2/WebRoot/log/DailyFile/log4j_Struts
log4j.appender.logDailyFile.DatePattern = '.'yyyy-MM-dd-HH-mm'.log'
log4j.appender.logDailyFile.Encoding = UTF-8

#设定文件大小输出到文件
log4j.appender.logRollingFile = org.apache.log4j.RollingFileAppender
log4j.appender.logRollingFile.layout = org.apache.log4j.PatternLayout
log4j.appender.logRollingFile.layout.ConversionPattern = [%-5p][%-22d{yyyy/MM/dd HH:mm:ssS}][%l]%n%m%n
log4j.appender.logRollingFile.Threshold = DEBUG
log4j.appender.logRollingFile.ImmediateFlush = TRUE
log4j.appender.logRollingFile.Append = TRUE
log4j.appender.logRollingFile.File = ../Struts2/WebRoot/log/RollingFile/log4j_Struts.log
log4j.appender.logRollingFile.MaxFileSize = 1MB
log4j.appender.logRollingFile.MaxBackupIndex = 10
log4j.appender.logRollingFile.Encoding = UTF-8

#用Email发送日志
log4j.appender.logMail = org.apache.log4j.net.SMTPAppender
log4j.appender.logMail.layout = org.apache.log4j.HTMLLayout
log4j.appender.logMail.layout.LocationInfo = TRUE
log4j.appender.logMail.layout.Title = Struts2 Mail LogFile
log4j.appender.logMail.Threshold = DEBUG
log4j.appender.logMail.SMTPDebug = FALSE
log4j.appender.logMail.SMTPHost = SMTP.163.com
log4j.appender.logMail.From = xly3000@163.com
log4j.appender.logMail.To = xly3000@gmail.com
#log4j.appender.logMail.Cc = xly3000@gmail.com
#log4j.appender.logMail.Bcc = xly3000@gmail.com
log4j.appender.logMail.SMTPUsername = xly3000
log4j.appender.logMail.SMTPPassword = 1234567
log4j.appender.logMail.Subject = Log4j Log Messages
#log4j.appender.logMail.BufferSize = 1024
#log4j.appender.logMail.SMTPAuth = TRUE

#将日志登录到MySQL数据库
log4j.appender.logDB = org.apache.log4j.jdbc.JDBCAppender
log4j.appender.logDB.layout = org.apache.log4j.PatternLayout
log4j.appender.logDB.Driver = com.mysql.jdbc.Driver
log4j.appender.logDB.URL = jdbc:mysql://127.0.0.1:3306/xly
log4j.appender.logDB.User = root
log4j.appender.logDB.Password = 123456
log4j.appender.logDB.Sql = INSERT INTOT_log4j(project_name,create_date,level,category,file_name,thread_name,line,all_category,message)values('Struts2','%d{yyyy-MM-ddHH:mm:ss}','%p','%c','%F','%t','%L','%l','%m')
```