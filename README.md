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


## 1. 分类管理CRUD

### EX:查询流程：/admin ——> AdminPageController(admin->admin_category_list->listCategory) ——> admin/listCategory.html——>http(js->ajax->categories) ——> CategoryController(categories->Mysql) ——(JSON)——> vue(v-for->tr)**
	
### ※注解
	
**pojo:**

@Entity: **SpringBean,工厂模式，IOC**

@GeneratedValue(strategy = GenerationType.IDENTITY) 主键自增长

@Id 数据库列主键

@Column(name = "id") 主键字段名称

**service:**

@Service: 服务类，进行调用DAO的JPA进行CRUD
				
@Autowired: 自动装配DAO的对象，调用DAO的CRUD

**web:**	

@Controller: 控制类，数据通过**※RESTFUL**接口来取

@GetMapping(value="/admin")

@RestController: 返回值转换为JSON格式

@Autowired：自动装配service

@GetMapping("/categories") 访问categories，获取对象集合，以JSON格式返回集合，抛给浏览器前端，GETMAPPING其实就是做了获取，跳转的功能

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
   

### ※分页 
	Page4Navigator

	//jpa 传递出来的分页对象， Page4Navigator 类就是对它进行封装以达到扩展的效果
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


**注解**
	
@RequestParam ：将请求参数绑定至方法参数
	
1. value：请求参数名（必须配置）

2. required：是否必需，默认为 true，即 请求中必须包含该参数，如果没有包含，将会抛出异常（可选配置）

3. defaultValue：默认值，如果设置了该值，required 将自动设为 false，
无论你是否配置了required，配置了什么值，都是 false（可选配置）

