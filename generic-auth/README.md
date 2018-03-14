        
               ______________________________________
      ________|                                      |_______
      \       |                Generic               |      /
       \      |       2018-01-17 20:08 Mel Liu       |     /
       /      |______________________________________|     \
      /__________)                                (_________\
      
    
Spring OAuth2.0 Server
======================

术语
---

1. Authentication   鉴别  
判断使用者是不是他所宣称的那个人（如：通过用户名/密码登录）
2. Authorization    授权  
判断当前使用者所拥有对系统资源存取的权限
3. Identification   识别  
判断使用者是谁，Identification 必须是独一无二的

API
---
* Authorization Request  授权请求
<pre>API: /oauth/authorize</pre>
![Authorization Request](doc/image/Authorization-Request.png)

* Access Token
<pre>API: /oauth/token</pre>
![Authorization Request](doc/image/Access-Token.png)









Spring Boot
===========


    @GetMapping("/user/{id:\\d+}")//表达式：限定只能是数字
    
    @PageableDefault(page = 1, size = 15, sort = "firstName, asc") Pageable page
    
    Spring Boot Test 中 jsonpath 表达式参考 https://github.com/json-path/JsonPath
    
    LocalDateTime jdk8
    
    BasicErrorController Spring Boot 默认错误处理器
    
JsonView
--------
选择性显示数据
1. 使用接口声明多个视图
2. 在值对象的 get 方法上指定视图
3. 在 Controller 方法上指定视图

<pre>
        class UserDTO {
            
            <i><b>interface SimpleView {};</b></i>
            
            <i><b>interface DetailView extends SimpleView {}</b></i>
            
            private String firstName;
            
            private String lastName;
            
            private String password;
            
            .......
            <i><b>@JsonView(SimpleView.class)</b></i> //指定视图
            public String getFirstName (){}
            
            <i><b>@JsonView(SimpleView.class)</b></i>
            public String getLastName (){}
            
            <i><b>@JsonView(DetailView.class)</b></i>
            public String getPasswordName (){}
            
            .........
        
        }
        
        
        class UserController {
            @GetMapping("/user")
            <i><b>@JsonView(User.SimpleView.class)</b></i>//不显示密码
            public List<UserDTO> query(){
                ...
            }
            
            @GetMapping("/user/{id}")
            <i><b>@JsonView(User.DetailView.class)</b></i>//显示密码
            public UserDTO getInfo(@PathVariable Long id){
                ...
            }
        }
</pre>

@Valid & BindingResult
----------------------
1. @Valid   
    启用 @NotBlank 等校验注解
2. BingingResult    
    手动处理校验结果
<pre>
        class UserDTO {
            @NotBlank(message="姓名不能为空")
            private String firstName;
            
            private String lastName;
            
            private String password;
            
            @Past(message="生日日期错误") //必须是过去的一个时间
            private Date birthday;
            
            .........
        
        }
        
        
        class UserController {
        
            @PostMapping("/user")
            public void create(<b><i>@Valid</i></b> @RequestBody UserDTO user<b><i>, BingingResult errors</i></b>){
                if (errors.hasErrors()){
                    errors.getAllErrors().stream().forEach(error -> {
                        FieldError fieldError = (FieldError)error;
                        System.err.println(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
                    });
                    
                    //handle parameter error
                }
                ...
            }
                    
        }
</pre>

自定义服务器异常
-------------
<pre>
public abstract class GenericException extends RuntimeException {

    public static GenericException newInstance(String code, String message) {
        return new GenericException() {
            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getCode() {
                return code;
            }
        };
    }

    public abstract String getMessage();

    public abstract String getCode();
}



@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(GenericException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerGenericException(GenericException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ex.getCode());
        result.put("message", ex.getMessage());
        return result;
    }
}

</pre>

RESTful API 的拦截
-----------------
1. 过滤器（Filter）
    <pre>
        @Component
        public class TimeFilter implements javax.serlvet.Filter {
        
        }
    </pre>
    如果使用第三方过滤器，无法使用 @Component 注解的时候
    <pre>
        @Configuration
        public class WebConfig {
            @Bean
            public FilterRegistrationBean timeFilter(){
                FilterRegistrationBean b = new FilterRegistrationBean();
                TimeFilter f = new TimeFilter();
                b.setFilter(f);
                
                //指定生效路径
                List<String>urls = new ArrayList<>();
                urls.add("/*");
                b.setUrlPatterns(urls);
                
                return b;
            }
        }
    </pre>
2. 拦截器（Interceptor）      
    可以解决第一种无法获得控制器信息的问题
    <pre>
        @Component
        public class TimeIntercepter implements HandlerIntercepter {
            
        }
    </pre>
    要在 WebMvcConfigureAdapter 中注册
    
3. 切片（Aspect）           
    第二种 无法拿到 参数等信息
    * 切入点
        - 在哪些方法上起作用
        - 在什么时候起作用
    * 增强（方法）
    <pre>
    @Aspect
    @Component
    public class TimeAspect {
      @Around("execution(* org.mel.generic.system.controller.UserController.*(..))")
             public Object handler(ProceedingJoinPoint pjp){
                 <b><i>pjp.getArgs();</i></b>
                 pjp.proceed();
             }
      
    }
    </pre>
    
使用 swagger 自动生成文档
----------------------
<pre>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.8.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.8.0</version>
</dependency>
</pre>

<pre>
    @SpringBootApplication
    <b><i>@EnableSwagger2</i></b>
    public class XXXApplication {
    
    }
</pre>

<pre>
    @ApiOperation("用户查询服务") // 添加 API 方法名
    
    @ApiModelProperty("名") // 包装对象参数注释
    
    @ApiParam("编号") // 简单参数
</pre>

使用 WireMock 伪造 RESTful 服务
----------------------------    

Spring Security
---------------
1. 认证
2. 授权
3. 攻击防护（伪造身份）

<pre>
#开启基本验证
security.basic.enabled=true
#设置用户 user 的密码
security.user.password=password
</pre>

* 基本原理  
                                                                                                                   |--------------------|


<pre>
                                                                                                                   |--------------------|
                                                                                                                   |                    |
                |---------------------|  |---------------------|  |-----|  |-------------|  |----------------|     |                    |
  request-------|---------------------|--|---------------------|--|-----|--|-------------|--|----------------|-----|--------------------|
                |  UserName Password  |  | Basic Authentication|  |     |  |  Exception  |  | FilterSecurity |     |    RESTfull API    |
                |Authentication Filter|  |       Filter        |  | ... |  | Translation |  |   Intercepter  |     |                    |
                |                     |  |                     |  |     |  |   Filter    |  |                |     |                    |
  response------|---------------------|--|---------------------|--|-----|--|-------------|--|----------------|-----|--------------------|
                |_____________________|  |_____________________|  |_____|  |_____________|  |________________|     |                    |
                                                                                                                   |                    |
                                                                                                                   |--------------------|

</pre>



    
- UserName Password Authentication Filter   
检查当前访问的是否是登录页面， 
如果是，检查是否有用户名密码参数，   
如果有，尝试登录。   
如果不成功，则交给下一个过滤器 
- Basic Authetication Filter    
查看是否有 Authorization:Basic dXNlcjpwYXNzd29yZA==  
如果有，则解码登录   
- 其它过滤器     
- Exception Translation Filter      
捕获后面 Filter 异常  
- FilterSecurity Intercepter        
根据 WebSecurityConfigurerAdapter.configure(HttpSecurity http) 的配置检测是否需要通过前面过滤器   

UserDetailService
-----------------
* 自定义认证（用户名/密码登录）    
* 用户信息获取    
* 处理密码加密解密      




1、获得授权码 - 浏览器访问
http://127.0.0.1:9000/generic-auth/oauth/authorize?response_type=code&client_id=generic-admin&redirect_uri=http://example.com&scope=all

2、获得 token
curl -i -X POST \
   -d grant_type=authorization_code \
   -d client_id=generic-admin \
   -d code=ooGlsC \
   -d redirect_uri=http://example.com \
   generic-admin:secret@127.0.0.1:9000/generic-auth/oauth/token
   
curl -i -X POST \
   -d grant_type=password \
   -d username=admin \
   -d password=admin \
   generic-admin:secret@127.0.0.1:9000/generic-auth/oauth/token

3、访问资源
curl -i -X GET \
    -H "Authorization:bearer 8109b30b-d8db-4804-9958-66e256ad49c1" \
    http://127.0.0.1:9000/generic-auth/api/v1/status

curl -i -X GET \
    -H "Authorization:bearer 8109b30b-d8db-4804-9958-66e256ad49c1" \
    http://127.0.0.1/generic-admin/api/v1/status

	

