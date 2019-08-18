# 概要

----------
基于springboot单体架构的商城项目，仓库trend_springcloud也有基于springcloud的微服务尝鲜项目。。。
> 因为写代码过程中有了很多疑惑引申出了好多问题。。所以项目的一些技术原理值得土人用土话写文章总结和展望，例如mybaits和jpa的技术选型到到底啥是DDD，AOP，IOC，JDK动态代理，观察者模式，工厂模式，单例模式，单体架构到servermesh的发展到SOA和RPC究竟是什么，redis，ES搜索引擎分布式架构原理，数据库分库分表，数据库存储过程和读写分离，shiro权限应用。。。坑已经挖好，博客园会努力填坑。。。


----------
>redis原理及简单应用:

>ES搜索引擎架构原理及简单应用：

>数据库分库分表，数据库存储过程、事务管理、读写分离、调优：

>shiro权限应用:

>单体架构到servermesh的发展：https://www.cnblogs.com/TeaPolyphenols/p/11330228.html
	
>啥是SOA，啥是RPC:
	
>设计模式里的AOP、IOC、工厂模式、单例模式、观察者模式、动态管理、：

>SpringCloud组件的应用及DDD的微服务商城里的坑：

>mybaits和jpa的技术选型到到底啥是DDD：https://www.cnblogs.com/TeaPolyphenols/p/11323783.html

>全局异常：

>日志：

>单元测试：


	
# 技术栈 

----------

## 前端：

**html，CSS，Javascript，JSON，AJAX，JQuery，Bootstrap，Vue.js,Thymeleaf**

## 后端：
**spring,springmvc,springboot**

## 中间件：
**redis，nginx，elasticsearch，shiro, docker**

## 数据库：
**MySQL**

## 开发工具：
**Intellij IDEA，Maven，Git**
	

# 数据库表结构设计

1.用户表
    
    CREATE TABLE user (
      id int(11) NOT NULL AUTO_INCREMENT,
      name varchar(255) DEFAULT NULL,
      password varchar(255) DEFAULT NULL,
      salt varchar(255) DEFAULT NULL,
      PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

2.分类表

    CREATE TABLE category (
      id int(11) NOT NULL AUTO_INCREMENT,
      name varchar(255) DEFAULT NULL,
      PRIMARY KEY (id)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

3.属性表
	
	//外键cid，指向分类表的id字段
    CREATE TABLE property (
      id int(11) NOT NULL AUTO_INCREMENT,
      cid int(11) DEFAULT NULL,
      name varchar(255) DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_property_category FOREIGN KEY (cid) REFERENCES category (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

4.产品表
    
	//name: 产品名称
	//subTitle: 小标题
	//originalPrice: 原始价格
	//promotePrice: 优惠价格
	//stock: 库存
	//createDate: 创建日期
	//外键cid，指向分类表的id字段

    CREATE TABLE product (
      id int(11) NOT NULL AUTO_INCREMENT,
      name varchar(255) DEFAULT NULL,
      subTitle varchar(255) DEFAULT NULL,
      originalPrice float DEFAULT NULL,
      promotePrice float DEFAULT NULL,
      stock int(11) DEFAULT NULL,
      cid int(11) DEFAULT NULL,
      createDate datetime DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_product_category FOREIGN KEY (cid) REFERENCES category (id)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

5.属性值表
	
	//外键ptid，指向属性表的id字段
	//外键pid，指向产品表的id字段

    CREATE TABLE propertyvalue (
      id int(11) NOT NULL AUTO_INCREMENT,
      pid int(11) DEFAULT NULL,
      ptid int(11) DEFAULT NULL,
      value varchar(255) DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_propertyvalue_property FOREIGN KEY (ptid) REFERENCES property (id),
      CONSTRAINT fk_propertyvalue_product FOREIGN KEY (pid) REFERENCES product (id)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

6.产品图片表

		//type表示类型，产品图片分单个图片和详情图片两种
		//外键pid，指向产品表的id字段
    	CREATE TABLE productimage (
      id int(11) NOT NULL AUTO_INCREMENT,
      pid int(11) DEFAULT NULL,
      type varchar(255) DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_productimage_product FOREIGN KEY (pid) REFERENCES product (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

7.评价表
		
		//外键pid，指向产品表的id字段
		//外键uid，指向用户表的id字段
    	CREATE TABLE review (
      id int(11) NOT NULL AUTO_INCREMENT,
      content varchar(4000) DEFAULT NULL,
      uid int(11) DEFAULT NULL,
      pid int(11) DEFAULT NULL,
      createDate datetime DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_review_product FOREIGN KEY (pid) REFERENCES product (id),
    CONSTRAINT fk_review_user FOREIGN KEY (uid) REFERENCES user (id)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

8.订单表


    /*orderCode： 订单号
    address:收货地址
    post: 邮编
    receiver: 收货人信息
    mobile: 手机号码
    userMessage: 用户备注信息
    createDate: 订单创建日期
    payDate: 支付日期
    deliveryDate: 发货日期
    confirmDate：确认收货日期
    status: 订单状态
    外键uid，指向用户表id字段*/
    		CREATE TABLE order_ (
      id int(11) NOT NULL AUTO_INCREMENT,
      orderCode varchar(255) DEFAULT NULL,
      address varchar(255) DEFAULT NULL,
      post varchar(255) DEFAULT NULL,
      receiver varchar(255) DEFAULT NULL,
      mobile varchar(255) DEFAULT NULL,
      userMessage varchar(255) DEFAULT NULL,
      createDate datetime DEFAULT NULL,
      payDate datetime DEFAULT NULL,
      deliveryDate datetime DEFAULT NULL,
      confirmDate datetime DEFAULT NULL,
      uid int(11) DEFAULT NULL,
      status varchar(255) DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_order_user FOREIGN KEY (uid) REFERENCES user (id)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


9.订单项表


    /*外键pid，指向产品表id字段
    外键oid，指向订单表id字段
    外键uid，指向用户表id字段
    number字段表示购买数量*/
    
    CREATE TABLE orderitem (
      id int(11) NOT NULL AUTO_INCREMENT,
      pid int(11) DEFAULT NULL,
      oid int(11) DEFAULT NULL,
      uid int(11) DEFAULT NULL,
      number int(11) DEFAULT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_orderitem_user FOREIGN KEY (uid) REFERENCES user (id),
      CONSTRAINT fk_orderitem_product FOREIGN KEY (pid) REFERENCES product (id),
      CONSTRAINT fk_orderitem_order FOREIGN KEY (oid) REFERENCES order_ (id)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
    
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

# 二、前台-无需登录

**web**
ForeRESTController：对应前台页面的路径

ForePageController ：对应前台页面的跳转

## 1. 注册register()

**service**

UserService()的isExist(String name)方法判断用户名是否使用

**web**

    ForeRESTController.register()

    @PostMapping("/foreregister")
    public Object register(@RequestBody User user) {
    String name =  user.getName();
    String password = user.getPassword();
    name = HtmlUtils.htmlEscape(name);
    user.setName(name);
    boolean exist = userService.isExist(name);
     
    if(exist){
    String message ="用户名已经被使用,不能使用";
    return Result.fail(message);
    }
     
    user.setPassword(password);
     
    userService.add(user);
     
    return Result.success();
    }   


    registerPage.html 的 axios.js 提交数据到路径 foreregister,导致ForeRESTController.register()方法被调用
    
    1. 通过参数User获取浏览器提交的账号密码
    2. 通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
    3. 判断用户名是否存在
    3. 1. 如果已经存在，就返回Result.fail,并带上 错误信息
    3. 2. 如果不存在，则加入到数据库中，并返回 Result.success()

## 2. 登陆login()
	
    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
    String name =  userParam.getName();
    name = HtmlUtils.htmlEscape(name);
     
    User user =userService.get(name,userParam.getPassword());
    if(null==user){
    String message ="账号密码错误";
    return Result.fail(message);
    }
    else{
    session.setAttribute("user", user);
    return Result.success();
    }
    }
    
    loginPage.html的 axios.js 提交数据到路径 forelogin,导致ForeRESTController.login()方法被调用
    
1. 账号密码注入到 userParam 对象上

2.  把账号通过HtmlUtils.htmlEscape进行转义
   
3. 根据账号和密码获取User对象

4. 如果对象为空，则返回错误信息
5. 如果对象存在，则把用户对象放在 session里，并且返回成功信息


## 3. 退出logout()
通过访问http://127.0.0.1:8080/tmall_springboot/forelogout
退出路径


导致ForePageController.logout()方法被调用，在session中去掉"user"

客户端跳转到首页:


    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
    session.removeAttribute("user");
    return "redirect:home";
    }

## 4. 产品页面
	
通过访问地址
 :http://127.0.0.1:8080/tmall_springboot/product?pid=844
 

导致ForeRESTController.product() 方法被调用

1. 获取参数pid
2. 根据pid获取Product 对象product
3. 根据对象product，获取这个产品对应的单个图片集合
4. 根据对象product，获取这个产品对应的详情图片集合
5. 获取产品的所有属性值
6. 获取产品对应的所有的评价
7. 设置产品的销量和评价数量
8. 把上述取值放在 map 中
9. 通过 Result 把这个 map 返回到浏览器去

返回出去的数据是多个集合，而非一个集合，所以通过 map返回给浏览器，浏览器更容易识别


    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid) {
    Product product = productService.get(pid);
     
    List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
    List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);

    product.setProductSingleImages(productSingleImages);
    product.setProductDetailImages(productDetailImages);
     
    List<PropertyValue> pvs = propertyValueService.list(product);
    List<Review> reviews = reviewService.list(product);
    productService.setSaleAndReviewNumber(product);
    productImageService.setFirstProdutImage(product);
     
    Map<String,Object> map= new HashMap<>();
    map.put("product", product);
    map.put("pvs", pvs);
    map.put("reviews", reviews);
     
    return Result.success(map);
    }

## 5. 产品页面未登录加入购物车的用户登陆页


ajax访问路径/forecheckLogin会导致ForeRESTController.checkLogin()方法被调用

获取session中的"user"对象
如果不为空，即表示已经登录，返回 Result.success()
如果为空，即表示未登录，返回 Result.fail("未登录");<-->modeal4login.html


    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
    User user =(User)  session.getAttribute("user");
    if(null!=user)
    return Result.success();
    return Result.fail("未登录");
    }

## 6. 产品分类页（comparator）

**comparator**

implements Comparator<Product>

1. ProductAllComparator 综合比较器
把 销量x评价 高的放前面
2. ProductReviewComparator 人气比较器
把 评价数量多的放前面
3. ProductDateComparator 新品比较器
把 创建日期晚的放前面
4. ProductSaleCountComparator 销量比较器
把 销量高的放前面
5. ProductPriceComparator 价格比较器
把 价格低的放前面


ForeRESTController.category()

1. 获取参数cid
2. 根据cid获取分类Category对象 c
3. 为c填充产品
4. 为产品填充销量和评价数据
5. 获取参数sort
6. 如果sort==null，即不排序
7. 如果sort!=null，则根据sort的值，从5个Comparator比较器中选择一个对应的排序器进行排序
8. 返回对象 c
     

## 7. 搜索

searchPage.html 中的请求提交后，ForeRESTController.search()方法被调用

1. 获取参数keyword
2. 根据keyword进行模糊查询，获取满足条件的前20个产品
3. 为这些产品设置销量和评价数量
4. 返回这个产品集合

**service**

ProductService.search方法进行模糊查询

List<Product> products =productDAO.findByNameLike("%"+keyword+"%",pageable);  

----------

# 三、前台-需要登陆

## 1. 立即购买

ForeRESTController.buyone()/buyoneAndAddCart()

1. 获取参数pid
2. 获取参数num
3. 根据pid获取产品对象p
4. 从session中获取用户对象user

新增订单项OrderItem， 新增订单项要考虑两个情况

a. 如果已经存在这个产品对应的OrderItem，并且还没有生成订单，即还在购物车中。 那么就应该在对应的OrderItem基础上，调整数量

a.1 基于用户对象user，查询没有生成订单的订单项集合

a.2 遍历这个集合

a.3 如果产品是一样的话，就进行数量追加

a.4 获取这个订单项的 id

b. 如果不存在对应的OrderItem,那么就新增一个订单项OrderItem

b.1 生成新的订单项

b.2 设置数量，用户和产品

b.3 插入到数据库

b.4 获取这个订单项的 id

5.返回当前订单项id

6.在页面上，拿到这个订单项id(oiid)，就跳转到 location.href="buy?oiid="+oiid;	
buy 结算页面


## 2. 结算页面

在 buyPage.html中，访问路径： "forebuy?oiid="+oiid;
 
http://127.0.0.1:8080/tmall_springboot/forebuy?oiid=1

导致ForeRESTController.buy()方法被调用

1. 通过字符串数组获取参数oiid

2.用字符串数组试图获取多个oiid，而不是int类型仅仅获取一个oiid, 因为根据购物流程环节与表关系，结算页面还需要显示在购物车中选中的多条OrderItem数据，所以为了兼容从购物车页面跳转过来的需求，要用字符串数组获取多个oiid

3. 准备一个泛型是OrderItem的集合ois

4. 根据前面步骤获取的oiids，从数据库中取出OrderItem对象，并放入ois集合中

5. 累计这些ois的价格总数，赋值在total上

6. 把订单项集合放在session的属性 "ois" 上

7. 把订单集合和total 放在map里

8. 通过 Result.success 返回


## 3. 加入购物车

addCart()方法和立即购买中的 ForeRESTController.buyone()步骤做的事情是一样的，因为都是调用 buyoneAndAddCart 方法

## 4. 查看购物车页面
ForeRESTController.cart()

访问地址/forecart导致ForeRESTController.cart()方法被调用

1. 通过session获取当前用户
登录才访问，否则拿不到用户对象,会报错
2. 获取为这个用户关联的订单项集合 ois
3. 设置图片
4. 返回这个订单项集合

## 5. 登陆状态拦截器

查询购物车之前，进行登陆操作，创建拦截器，访问需要登陆才能做的页面：如
购买行为、加入购物车行为、查看购物车、查看我的订单

进行是否登录的判断，如果不通过，跳转倒login.html提示确保用户登陆


**interceptor**

LoginInterceptor

拦截器判断如果不是注册，登录，产品这些，就进行登录校验

1. 准备字符串数组 requireAuthPages，存放需要登录才能访问的路径
2. 获取uri
3. 去掉前缀/tmall_springboot
4. 判断是否是以 requireAuthPages 里的开头的

4.1 如果是就判断是否登陆，未登陆就跳转到 login 页面

4.2 如果不是就放行

**config配置拦截器**

	@Configuration
	class WebMvcConfigurer extends WebMvcConfigurerAdapter{
     
    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }
     
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginIntercepter())
        .addPathPatterns("/**");      
    }
}


## 6. 购物车页面操作

ForeRESTController.changeOrderItem()

点击增加或者减少按钮后，根据 cartPage.html 中的js代码，会通过Ajax访问/forechangeOrderItem路径，导致ForeRESTController.changeOrderItem()方法被调用

1. 判断用户是否登录
2. 获取pid和number
3. 遍历出用户当前所有的未生成订单的OrderItem
4. 根据pid找到匹配的OrderItem，并修改数量后更新到数据库
5. 返回 Result.success()

ForeRESTController.deleteOrderItem

点击删除按钮后，根据 cartPage.html 中的js代码，会通过axios访问/foredeleteOrderItem路径，导致ForeRESTController.deleteOrderItem方法被调用

1. 判断用户是否登录
2. 获取oiid
3. 删除oiid对应的OrderItem数据
4. 返回字符串 Result.success


## 7. 生成订单/提交订单/结算操作

首先通过立即购买或者购物车的提交到结算页面 进入结算页面，然后点击提交订单

OrderService

增加 add(Order o, List<OrderItem> ois)方法，该方法通过注解进行事务管理
@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")

抛出异常代码用来模拟当增加订单后出现异常，观察事务管理是否预期发生
 
if(false)
throw new RuntimeException();

ForeRESTController.createOrder

提交订单访问路径 /forecreateOrder, 导致ForeRESTController.createOrder 方法被调用

1. 从session中获取user对象
2. 根据当前时间加上一个4位随机数生成订单号
3. 根据上述参数，创建订单对象
4. 把订单状态设置为等待支付
5. 从session中获取订单项集合 ( 在结算功能的ForeRESTController.buy() ，订单项集合被放到了session中 )
7. 把订单加入到数据库，并且遍历订单项集合，设置每个订单项的order，更新到数据库
8. 统计本次订单的总金额
9. 返回总金额



 forepayed地址导致 payed方法被调用：

1. 获取参数oid

2. 根据oid获取到订单对象order

3. 修改订单对象的状态和支付时间

4. 更新这个订单对象到数据库

5. 返回订单


## 8. 评价产品

**评价页面**

1. 通过点击评价按钮，来到路径/review，返回 review.html

2. review.html 访问 forereview 地址

3. ForeRESTController.review() 被调用

3.1 获取参数oid

3.2 根据oid获取订单对象o

3.3 为订单对象填充订单项

3.4 获取第一个订单项对应的产品,因为在评价页面需要显示一个产品图片，那么就使用这第一个产品的图片

3.5 获取这个产品的评价集合

3.6 为产品设置评价数量和销量

3.7 把产品，订单和评价集合放在map上

3.8 通过 Result 返回这个map

**提交评价**

在评价产品页面点击提交评价，就把数据提交到了/foredoreview路径，导致ForeRESTController.doreview方法被调用

1. ForeRESTController.doreview()

1.1 获取参数oid

1.2 根据oid获取订单对象o

1.3 修改订单对象状态

1.4 更新订单对象到数据库

1.5 获取参数pid

1.6 根据pid获取产品对象

1.7 获取参数content (评价信息)

1.8 对评价信息进行转义，道理同注册ForeRESTController.register()

1.9 从session中获取当前用户

1.10 创建评价对象review

1.11 为评价对象review设置 评价信息，产品，时间，用户

1.12 增加到数据库

1.13.返回成功


----------

# 四、Redis+Shiro+ElasticSearch

## 1. Shiro

**Realm**

提供通过 JPA 进行验证的 Realm

**cofig**

进行 Shiro配置，其中的 bean 都是常规配置

1. getJPARealm() 指定了 Realm 使用 JPARealm
2. hashedCredentialsMatcher() 指定了 加密算法使用 md5,并且混进行2次加密

**注册**

ForeRESTController 的 register 方法

其中注册时候的时候，会通过随机方式创建盐， 并且加密算法采用 "md5", 除此之外还会进行 2次加密。

这个盐，如果丢失了，就无法验证密码是否正确了，所以会数据库里保存起来

**登录**

登陆的时候， 通过 Shiro的方式进行校验

**退出**

退出的时候，通过 subject.logout 退出

**拦截器判断是否登录**

拦截器里会判断是否登陆，切换为 Shiro 方式：
subject.isAuthenticated()

**前台判断是否登录**

在产品页面点击立即购买或者加入购物车的时候，需要判断是否登录


## 2. Redis

**config**

配置 Redis,RedisConfig extends CachingConfigurerSupport配置的作用主要是使得保存在 redis 里的key和value转换为如图所示的具有可读性的字符串，否则会是乱码，很不便于观察