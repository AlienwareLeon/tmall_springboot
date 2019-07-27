package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5")int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Category> page = categoryService.list(start, size, 5);//5表示导航分页最多有5个，像[1,2,3,4,5]这样
        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws  Exception {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
                throws IOException{
        File imageFolder = new File(request.getServletContext().getRealPath("img/categroy"));
        File file = new File(imageFolder, bean.getId()+ ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
    }

    @DeleteMapping("/categories/{id}")
    public String delete (@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        categoryService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+ ".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category bean = categoryService.get(id);
        return bean;
    }

    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);

        if (image != null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }
}
/*
* 提供RESTFUL服务器控制器，每个方法的返回值直接转换为JSON数据格式
*对categories的访问，会获取所有的Category对象集合，并返回集合
*因为声明为@RestController，集合会自动转换为JSON数组抛给浏览器
*
* list/add对应的映射路径都是categories但是一个是GetMapping一个是PostMapping,REST规范就是
* 通过method区别来辨别获取/增加
* 提供增加方法add：
* 1.首先通过CategoryService保存到数据库
* 2.接受上传图片，并保存到img/category目录下
* 3.文件名使用新增分类的id
* 4.目录不存在需要创建
* 5.image.transferTo进行文件复制
* 6.调用ImageUtil的change2jpg进行文件类型强制转换成 jpg格式
* 7.保存图片
*
* */