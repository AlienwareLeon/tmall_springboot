#概要

----------
基于springboot单体架构的商城项目，仓库trend_springcloud也有基于springcloud的微服务尝鲜项目。。。
> 因为写代码过程中有了很多疑惑引申出了好多问题。。所以项目的一些技术原理值得土人用土话写文章总结和展望，例如mybaits和jpa的技术选型到到底啥是DDD，AOP，IOC，JDK动态代理，观察者模式，工厂模式，单例模式，单体架构到servermesh的发展到SOA和RPC究竟是什么，redis，ES搜索引擎分布式架构原理，数据库分库分表，数据库存储过程和读写分离，shiro权限应用。。。坑已经挖好，博客园会努力填坑。。。
	
	单体架构到servermesh的发展：
	
	啥是SOA，啥是RPC:
	
	AOP:
	
	IOC:
	
	动态代理：
	
	工厂模式：
	
	观察者模式：

	单例模式：
	
	mybaits和jpa的技术选型到到底啥是DDD：

	
# 技术栈 

----------

## 前端：

**html，CSS，Javascript，JSON，AJAX，JQuery，Bootstrap，Vue.js,Thymeleaf**

## 后端：
**spring,springmvc,springboot**

## 中间件：
**redis，nginx，elasticsearch，shiro**

## 数据库：
**MySQL**

## 开发工具：
**Intellij IDEA，Maven，Git**
	

# 数据库表结构设计

----------


# 一、 后台管理

## 1. 后台分类(Category)管理 CRUD

### 以查询流程为例：
1. 首先浏览器端访问/admin 

2. 路径被AdminPageController的admin方法获取，客户端跳转到admin_category_list

3. admin_category_list被AdminPageController的listCategory方法匹配，服务端跳转到admin/listCategory.html

4. listCategory.html通过http协议传输到浏览器端

5. 浏览器根据listCategory.html的js代码，利用ajax异步调用categories地址，CategoryController获取到categories到Mysql中查出所有分类数据，转换为JSON数组返回给浏览器

6. 浏览器根据JSON数据，通过vue的v-for方法遍历到tr元素

7. 用户看到表格中的数据

### Restful标准

1. 资源名称用复数，而非单数。
即使用 /categories 而不是用 /category

2. CRUD 分别对应：
增加： post
删除： delete
修改： put	 不能注入request.getParameter("name")
查询： get

3. id 参数的传递都用 /id方式。 
如编辑和修改：
/categories/123

4. 其他参数采用?name=value的形式
如分页参数 /categories?start=5

5. 返回数据
查询多个返回 json 数组
增加，查询一个，修改 都返回当前 json 数组
删除，返回空，跳转到首页vue.list(0);

	
### ※注解
	
**pojo:**

@Entity: **SpringBean,工厂模式，IOC**

@Table(name = "category")：数据库表名

@JsonIgnoreProperties:JSON忽略

@GeneratedValue(strategy = GenerationType.IDENTITY) 主键自增长

@Id 数据库列主键

@Column(name = "id") 主键字段名称
    
    productsByRow这个属性的类型是List<List<Product>> 
	
	productsByRow即一个分类又对应多个 List<Product>

	，提供属性，是为了在首页竖状导航的分类名称右边显示推荐产品列表相当于@JsonIgnore

**service:**

@Service: 服务类，进行调用DAO的JPA进行CRUD
				
@Autowired: 自动装配DAO的对象，调用DAO的CRUD

**web:**	

@Controller: 控制类，数据通过**※RESTFUL**接口来取，页面跳转

@GetMapping(value="/admin")

@RestController: 返回值转换为JSON格式

@Autowired：自动装配，引用指向装配类，**动态代理**

@GetMapping("/categories") 访问categories，获取对象集合，以JSON格式返回集合，抛给浏览器前端，GETMAPPING其实就是做了获取，跳转的功能
	
@RequestParam ：将请求参数绑定至方法参数，绑定vue参数
	
1. value：请求参数名（必须配置）

2. required：是否必需，默认为 true，即 请求中必须包含该参数，如果没有包含，将会抛出异常（可选配置）

3. defaultValue：默认值，如果设置了该值，required 将自动设为 false，
无论你是否配置了required，配置了什么值，都是 false（可选配置）


**启动类：**

@SpringBootApplication

**※config:**

		
extends WebMvcConfigurerAdapter :允许所有请求跨域

    
    `
    	@Configuration //配置类
    	public class CORSConfiguration extends WebMvcConfigurerAdapter{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    //所有请求都允许跨域
    registry.addMapping("/**")
    .allowedOrigins("*")
    .allowedMethods("*")
    .allowedHeaders("*");
    }
    }
    `

**※exception:**

    @RestController  
    @ControllerAdvice  //※
    public class GloabalExceptionHandler {
    @ExceptionHandler(value = Exception.class)  //异常处理，处理删除父类信息，外键约束的存在，导致违反约束
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    e.printStackTrace();
    Class constraintViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
    if(null!=e.getCause()  && constraintViolationException==e.getCause().getClass()) {
    return "违反了约束，多半是外键约束";
    }
    return e.getMessage();
    }
     
    }
   

### ※util 
	
	Page4Navigator

	

	//jpa 传递出来的分页对象， Page4Navigator 类就是对它进行“封装”以达到扩展的效果，mybatis有pageHelper提供方法
	Page<T> pageFromJPA;
 
	//分页的时候 ,如果总页数比较多，那么显示出来的分页超链一个有几个。 比如如果分页出来的超链是这样的： [8,9,10,11,12], 那么 navigatePages 就是5
	int navigatePages;
  
	//总页面数
	int totalPages;
 
	//第几页（基0）
	int number;
  
	//总共有多少条数据
	long totalElements;
  
	//一页最多有多少条数据
	int size;
 
	//当前页有多少条数据 (与 size，不同的是，最后一页可能不满 size 个)
	int numberOfElements;
 
	//数据集合
	List<T> content;
 
	//是否有数据
	boolean isHasContent;
 
	//是否是首页
	boolean first;
 
	//是否是末页
	boolean last;
  
	//是否有下一页
	boolean isHasNext;
 
	//是否有上一页
	boolean isHasPrevious;
  
	//分页的时候 ,如果总页数比较多，那么显示出来的分页超链一个有几个。 比如如果分页出来的超链是这样的： [8,9,10,11,12]，那么 navigatepageNums 就是这个数组：[8,9,10,11,12]，这样便于前端展示
	int[] navigatepageNums;
	
	//return new Page4Navigator<>(pageFromJPA,navigatePages);
	返回Page4Navigator对象，vue通过对象的.content方法获取数据
	



ImageUtil
	
	1. change2jpg确保图片文件的二进制格式是jpg
	2. resizeImage用于上传产品图片改变图片大小

		


## 2. 后台属性(Property)管理CRUD

**pojo**

@ManyToOne：对Category

@JoinColumn(name="cid") 外键

@Override：重写了toString方法，返回"id,name,category"

**dao**

	Page<Property> findByCategory(Category category, Pageable pageable);

	基于Category进行查询， Pageable ，支持分页


@RequestBody：接收的是请求体里面的数据

@RequestParam：接收的是key-value里面的参数


## 3. 后台产品(Product)管理CRUD

**pojo**

@ManyToOne
@JoinColumn(name="cid")
private Category category;

**service**

public void fillByRow(List<Category> categorys);
为多个分类填充推荐产品集合，即把分类下的产品集合，按照8个为一行，拆成多行
## 4. 后台产品图片(ProductImage)管理

**pojo**
    
    @ManyToOne
    @JoinColumn(name="pid")
    @JsonBackReference
    private Product product;

@Transient
如果既没有指明关联到哪个Column,又没有明确要用@Transient忽略，那么就会自动关联到表对应的同名字段

@JsonBackReference
jackson中的@JsonBackReference和@JsonManagedReference，以及@JsonIgnore均是为了解决对象中存在双向(双向关联)引用导致的无限递归（infinite recursion）问题

@JsonBackReference标注的属性在序列化（serialization，即将对象转换为json数据）时，会被忽略（即结果中的json数据不包含该属性的内容）

前后端分离，而前后端数据交互用的是 json 格式。 那么 Category 对象就会被转换为 json 数据。 而本项目使用 jpa 来做实体类的持久化，jpa 默认会使用 hibernate, 在 jpa 工作过程中，就会创造代理类来继承 Category ，并添加 handler 和 hibernateLazyInitializer 这两个无须 json 化的属性，所以这里需要用 JsonIgnoreProperties 把这两个属性忽略掉。

双向关联就是，产品里有分类，分类里有产品，产品里又有分类，分类里又有产品，无限循环

**service**

axios,js 上传图片要用 FormData 的方式

**ProductController**

productImageService.setFirstProdutImages(page.getContent()); 查询产品界面设置上图片


## 5. 后台产品属性值（PropertyValue）管理CRUD

**pojo**

    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;
    
	@ManyToOne 
    @JoinColumn(name="ptid")   
    private Property property;


在创建表结构的时候，有外键约束，导致当存在从表数据的时候，主表数据无法被删除。

假设即使有从表数据，主表也允许被删除，那么那些从表数据就变成脏数据了。

对于这个问题通常有两种解决办法：

1. 使用级联删除。即删除主表的时候，从表自动删除。 这样做在技术上最简单，但是在业务上最危险，不推荐。

2. 删除有从表数据的主表时，提醒用户依然有从表数据，建议用户一条一条删除从表数据，再删除主表数据。 这样技术上无改动，业务上最安全。 建议采纳此种方案。

附：主从表概念——以分类和产品而言，他们是一对多关系，分类就是主表，产品就是从表。

## 6. 后台用户(User)管理

**pojo**
    @Transient
    private String anonymousName;

## 7. 后台订单(OrderItem)管理

**pojo**    

    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;
     
    @ManyToOne
    @JoinColumn(name="oid")
    private Order order;
     
    @ManyToOne
    @JoinColumn(name="uid")
    private User user;



**service**

OrderItemService提供fill()方法对OrderItem的业务操作,从数据库中取出来的 Order 没有 OrderItem集合，这里通过 OrderItemDAO 取出来再放在 Order的 orderItems属性上

OrderService提供了removeOrderFromOrderItem()方法，相当于 未用的@JsonIgnoreProperties，整合Redis标记@JsonIgnoreProperties会有BUG

**util**

Result统一的 REST响应对象,包含是否成功，错误信息，数据，方便前端人员识别和显示给用户可识别信息。


----------

# 二、前台-首页-无需登录

**web**
ForeRESTController：对应前台页面的路径

ForePageController ：对应前台页面的跳转





----------

# 三、前台-需要登陆

