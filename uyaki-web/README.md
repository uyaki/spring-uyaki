# uyaki-web

## 功能清单
1. cors
2. liquibase
3. swagger
4. mybatis-plus

## cors

```java
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * Cors filter cors filter.
     *
     * @return the cors filter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
```

## liquibase

- 引入依赖

    ```xml
    <!-- https://mvnrepository.com/artifact/org.liquibase/liquibase-core -->
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
        <version>3.10.0</version>
    </dependency>
    ```

- 添加配置文件

    ```java
    @Configuration
    public class LiquibaseConfig {
        /**
         * Liquibase spring liquibase.
         *
         * @param dataSource the data source
         * @return the spring liquibase
         */
        @Bean
        public SpringLiquibase liquibase(DataSource dataSource) {
            SpringLiquibase springLiquibase = new SpringLiquibase();
            // 数据源直接在application.yml中配置
            springLiquibase.setDataSource(dataSource);
            springLiquibase.setChangeLog("classpath:liquibase/sql/uyaki-web.sql");
            springLiquibase.setContexts("myself,develop");
            springLiquibase.setShouldRun(true);
            return springLiquibase;
        }
    }
    ```

- 添加sql脚本

    > 在`resource.liquibase.sql` 文件夹下新建 `uayki-web.sql`

    ```sql 
    --liquibase formatted sql
    
    --changeset nvoxland:1
    CREATE TABLE IF NOT EXISTS `user`  (
      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
      `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
      `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
      `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
      `is_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态。0：禁用；1：正常；',
      `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已删除。0：未删除；1：已删除；',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT='用户表';
    ```
    
## swagger

- 引入依赖

    ```xml
    <properties>
        <springfox-swagger.version>2.9.2</springfox-swagger.version>
    </properties>
    <dependencies>
        <!-- swagger -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${springfox-swagger.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>io.swagger</groupId>
                    <artifactId>swagger-models</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox-swagger.version}</version>
        </dependency>
        <dependency>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-models</artifactId>
            <version>1.5.21</version>
        </dependency>
    </dependencies>
    ```
    
    > swagger 2.9.2版本的swagger-models的version为1.5.20，有bug，会导致打开ui页面时抛出i.s.m.p.AbstractSerializableParameter : Illegal DefaultValue null for parameter type integer 
    > java.lang.NumberFormatException: For input string: ""
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
- 添加配置文件

    ```java
    @Configuration
    @EnableSwagger2
    public class SwaggerConfig {
    
        @Value(value = "${swagger-host:localhost:8080}")
        private String host;
    
        /**
         * Api docket.
         *
         * @return the docket
         */
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    //函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现,除了被@ApiIgnore指定的请求
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.uyaki.web"))
                    .paths(PathSelectors.any())
                    .build()
                    .host(host);
        }
    
        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("uyaki-web")
                    .description("整合spring-boot-web + mybatis-plus + swagger + liquibase")
                    .version("v1")
                    .build();
        }
    }
    ```                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

## mybatis-plus

- 引入依赖

    ```xml
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.3.2</version>
    </dependency>
    ```

- 配置文件
    
    ```yml
    dev_host: xxx.xxx.xxx.xxx
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${dev_host}:xxxx/xxxx?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8
        username: xxx
        password: xxxxxx
    mybatis-plus:
      configuration:
        cache-enabled: true
        use-generated-keys: true
        default-executor-type: reuse
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
        map-underscore-to-camel-case: true
      type-aliases-package: com.uyaki.web.entity
      mapper-locations: classpath:mapper/**/*Mapper.xml
      global-config:
        db-config:
          logic-delete-field: isDeleted  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。
          logic-delete-value: true # 逻辑已删除值(默认为 1)
          logic-not-delete-value: false # 逻辑未删除值(默认为 0)
    ```

- 插件

    ```java
    /**
     * MyBatis-Plus 自动插入
     */
    @Slf4j
    @Component
    public class MyMetaObjectHandler implements MetaObjectHandler {
    
        @Override
        public void insertFill(MetaObject metaObject) {
            this.strictInsertFill(metaObject, "createdAt", Date.class, new Date());
        }
    
        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "updatedAt", Date.class, new Date());
        }
    }
    ```

    ```java
    @Configuration
    @EnableTransactionManagement
    @MapperScan("com.uyaki.web.**.mapper")
    public class MybatisPlusConfig {
    
        /**
         * 配置分页插件
         *
         * @return the pagination interceptor
         */
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
            // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
            // paginationInterceptor.setOverflow(false);
            // 设置最大单页限制数量，默认 500 条，-1 不受限制
            // paginationInterceptor.setLimit(500);
            // 开启 count 的 join 优化,只针对部分 left join
            paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
            return paginationInterceptor;
        }
    }
    ```
                                         
                                         