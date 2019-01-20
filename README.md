# java-spring-play
play with spring boot

## 1. 快速开始

可以直接使用 [start.spring.io](https://start.spring.io/) 中的引导来初始化项目，下载然后导入IDEA，开始体验。

IDEA里面也可以直接安装spring相关插件，然后通过新建项目来创建新项目。

一般来说，会选择创建Maven项目，使用pom.xml来管理项目依赖。IDEA里面内置了Maven，也省去了安装的麻烦。

不过新建项目后，会通过Maven下载依赖包。为了加快下载速度，建议先将Maven镜像切换到国内镜像。

具体操作为：创建或修改文件 `$HOME/.m2/settings.xml`，参考下面的配置，添加aliyun maven镜像

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <localRepository/>
    <interactiveMode/>
    <offline/>
    <pluginGroups/>
    <servers/>
    <mirrors>
        <mirror>
            <id>alimaven</id>
            <name>aliyun maven</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
            <mirrorOf>central</mirrorOf>
        </mirror>
    </mirrors>
    <proxies/>
    <profiles/>
    <activeProfiles/>
</settings>
```


## 2. 运行项目

项目建立之后，一般有如下结构：

```
> tree .
.
├── java-spring-play.iml
├── pom.xml                                        # maven配置，包括项目的依赖，以及spring-boot的基本设置
├── src                                            # 项目源代码
│   ├── main
│   │   ├── java
│   │   │   └── play                               # 新建项目时指定的命名空间
│   │   │       └── JavaSpringPlayApplication.java # 程序的执行入口
│   │   └── resources
│   │       ├── application.property               # spring-boot默认配置，可以改成使用yml格式
│   │       ├── static                             # web静态资源
│   │       └── templates                          # 页面模板
│   └── test
│       └── java
│           └── play
│               └── JavaSpringPlayApplicationTests.java
```

在IDE里面，可以通过执行入口类 JavaSpringPlayApplication 来启动程序。

也可以使用`mvn package`命令来将代码编译打包为jar文件，然后通过`java -jar`命令来执行。
这一项依赖了`spring-boot-maven-plugin`插件，需要在`pom.xml`中指定该插件。

> 通过 `SpringBootApplication` 标注，可以代替 `EnableAutoConfiguration`、`ComponentScan` 标注，
> 可以开启spring自动配置和组件扫描功能。
> 后续功能都会依赖于该项配置。


## 3. 控制器和路由

新建类和方法，然后：
* 通过 `RestController` 标注类为控制器
* 通过 `RequestMapping` 标注方法来添加路由
* `GetMapping`、`PostMapping` 只是添加路由对应的方法为GET、POST

具体代码，参考 `play.controller.HelloController`。

重启应用，即可访问新增接口了。


## 4. 接口文档和测试

引入swagger，方便接口开发和测试。
swagger会自动扫描代码中的标注，生成基本的接口文档，并且可以文档中直接的调用HTTP接口。

在`pom.xml`中，加入以下依赖：

```
<!-- 接口文档 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.4.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.4.0</version>
</dependency>
```

然后，新建类 `SwaggerConfig`，添加代码：

```java
@Configuration  // 标注该文件为java配置
@Profile("dev") // 可以指定特定profile才开启文档
@EnableSwagger2 // 开启Swagger功能
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .build();
    }
}
```

重启应用，通过URL访问接口文档：http://127.0.0.1:8088/swagger-ui.html

更详细的使用方法，请参考文档：http://springfox.github.io/springfox/
