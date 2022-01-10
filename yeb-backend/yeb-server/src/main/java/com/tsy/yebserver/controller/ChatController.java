package com.tsy.yebserver.controller;

import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.service.IAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Steven.T
 * @date 2022/1/10
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private IAdminService adminService;

    public List<Admin> listAdmin(String keywords){
        return adminService.getAdminByKeywords(keywords);
    }

}
