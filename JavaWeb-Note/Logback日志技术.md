## 日志输出技术

- Spring会提供一个默认的日志输出配置
- 在需要进行自定义的日志配置时，则需要在`resource包`下创建一个`logback.xml`文件，并写下以下配置，然后自行修改配置

### 日志级别
| 日志级别 | 说明                                                                       | 记录方式           |
| -------- | -------------------------------------------------------------------------- | ------------------ |
| trace    | 追踪，记录程序运行轨迹【使用很少】                                         | `log.trace("...")` |
| debug    | 调试，记录程序调试过程中的信息，实际应用中一般将其视为最低级别【使用较多】 | `log.debug("...")` |
| info     | 记录一般信息，描述程序运行的关键事件，如：网络连接、io操作【使用较多】     | `log.info("...")`  |
| warn     | 警告信息，记录潜在有害的情况【使用较多】                                   | `log.warn("...")`  |
| error    | 错误信息【使用较多】                                                       | `log.error("...")` |

-------------------

```xml
<configuration>
<!--  控制台输出  -->
<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
<!-- 格式化输出：%d 表示日期，%thread 表示线程名，%-5level表示级别从左显示5个字符宽度，%logger显示日志记录器的名称， %msg表示日志消息，%n表示换行符  -->
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50}-%msg%n</pattern>
</encoder>
</appender>
<!--  系统文件输出  -->
<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
<rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
<!--  日志文件输出的文件名, %i表示序号  -->
<FileNamePattern>D:/tlias-%d{yyyy-MM-dd}-%i.log</FileNamePattern>
<!--  最多保留的历史日志文件数量  -->
<MaxHistory>30</MaxHistory>
<!--  最大文件大小，超过这个大小会触发滚动到新文件，默认为 10MB  -->
<maxFileSize>10MB</maxFileSize>
</rollingPolicy>
<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
<!-- 格式化输出：%d 表示日期，%thread 表示线程名，%-5level表示级别从左显示5个字符宽度，%msg表示日志消息，%n表示换行符  -->
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50}-%msg%n</pattern>
</encoder>
</appender>
<!--  日志输出级别  -->
<root level="ALL">  <!-- 输出大于等于此处日志级别的日志，用ALL会导致底层代码日志一并输出的问题-->
<appender-ref ref="STDOUT"/>
<appender-ref ref="FILE"/>
</root>
```