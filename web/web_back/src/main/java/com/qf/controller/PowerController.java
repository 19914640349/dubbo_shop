package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * FileName: PowerController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/2 16:15
 */
@Controller
@RequestMapping("/power")
public class PowerController {

    @Reference
    private IPowerService powerService;

    /**
     * 查询所有菜单权限
     * @param model
     * @return
     */
    @RequestMapping("/powerList")
    public String powerList(Model model){
        List<Power> powers = powerService.queryAllPower();
        model.addAttribute("powers",powers);
        return "powerList";
    }

    /**
     * 查询所有菜单权限，并返回json数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/ajaxPowerList")
    public List<Power> ajaxPowerList(){
        return powerService.queryAllPower();
    }

    /**
     * 添加一个权限
     * @param power
     * @return
     */
    @RequestMapping("/addPower")
    public String addPower(Power power){
        powerService.addPower(power);
        return "redirect:/power/powerList";
    }

    /**
     * 根据角色的id查询所有权限
     * @param rid
     * @return
     */
    @ResponseBody
    @RequestMapping("/ajaxPowerListByRid")
    public List<Power> ajaxPowerListByRid(Integer rid){
        return powerService.queryPowersByRid(rid);
    }

}
