package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * FileName: GoodsController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 16:08
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Value("${upload.path}")
    private String uploadPath;

    @Reference
    private IGoodsService goodsService;

    /**
     * 查询所有所有商品信息
     * @param model
     * @return
     */
    @RequestMapping("/goodsList")
    public String goodsList(Model model){
        List<Goods> goodsList = goodsService.queryAllGoods();
        model.addAttribute("goodsList",goodsList);
        return "goodsList";
    }

    /**
     * 跳转到添加商品页面
     * @return
     */
    @RequestMapping("toAddGoods")
    public String toAddGoods(){
        return "addGoods";
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    public String addGoods(Goods goods){
        goodsService.addGoods(goods);
        return "redirect:/goods/goodsList";
    }

    /**
     * 上传商品图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile file){

        //截取源图片的后缀
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);

        //生成文件名称
        String fileName = UUID.randomUUID().toString() + suffix;
        //设置上传的文件路径
        String uploadFile = uploadPath + fileName;
        try(
                //输入流
                InputStream inputStream = file.getInputStream();
                //输出流
                OutputStream outputStream = new FileOutputStream(uploadFile)
        ) {

            IOUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"filePath\":\"" + uploadFile + "\"}";
    }

    /**
     * 获取服务器的图片进行回显
     * @param imgPath
     * @param response
     */
    @RequestMapping("/getImg")
    public void getImg(String imgPath, HttpServletResponse response){
        //查询本地的文件
        File file = new File(imgPath);

        try (
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = response.getOutputStream()
        ){

            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
