package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

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

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

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

        String uploadFile = upload(file);
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

    /**
     * 上传富文本框的图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadText")
    @ResponseBody
    public String uploadText(MultipartFile file){

        String uploadFile = upload(file);
        return "{\"error\" : 0, \"url\" : \"http://192.168.245.199/"+uploadFile+"\"}";
    }

    /**
     * 查看商品描述
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toGoodsInfo")
    public String toGoodsInfo(Integer id, Model model){
        Goods goods = goodsService.queryGoodsById(id);
        model.addAttribute("goods", goods);
        return "goodsInfo";
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public String upload(MultipartFile file){
        //截取源图片的后缀
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index + 1);

        String uploadFile = "";
        try {
            StorePath storePath =fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(),suffix,null);
            uploadFile = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadFile;
    }

}
