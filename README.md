# NutzCodeInsight
### 1、在 Nutz Action Module 中点击 @Ok 前面的jsp图标即可快速打开或切换至已经打开的jsp文件  
### 2、支持beetl模版中资源文件的快速定位
#
idea插件仓库[https://plugins.jetbrains.com/plugin/10311-nutzcodeinsight](https://plugins.jetbrains.com/plugin/10311-nutzcodeinsight "真实项目")
####  完成
    - 支持自定义模版配置
#### 兼容
```java
  //模式1  jsp模版（默认支持）
  @Ok("jsp:btl.demo.manager")
  //模式2  beetl模版 （默认支持）
  @Ok("btl:btl.demo.manager")
  @Ok("beetl:btl.demo.manager")
  //模式3 （适用于改造后得视图返回器，我自己使用的） 
  @Ok("btl:WEB-INF/btl/demo/manager.html")
```
### 添加自定义配置
 - File >> Settings >> NutzCodeInsight
 - File >> Settings >> Other Settings >> NutzCodeInsight

# java类中

![NutzCodeInsight](image/NutzCodeInsight.gif)

# beetl模版中
![htmldemo.png](image/htmldemo.png)
