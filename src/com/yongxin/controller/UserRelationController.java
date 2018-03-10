package com.yongxin.controller;

import com.google.gson.Gson;
import com.yongxin.dto.Pager;
import com.yongxin.entity.UserRelation;
import com.yongxin.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */
@Controller
public class UserRelationController {
    @Autowired
    UserRelationService userRelationService;

    /**
     * 我的下一级用户列表，不分页
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/myFollowUserList")
    public String myFollowUserList(HttpServletRequest request, ModelMap modelMap) {
        //int myId = Integer.parseInt(request.getParameter("fatherId"));
        //获取当前用户等级
        Cookie cookie=null;
        Cookie[] cookies=null;
        cookies=request.getCookies();
        String myId = "";
        if (cookies!=null) {
            try {
                for (int i = 0; i < cookies.length-1; i++) {
                    cookie=cookies[i];
                    if (cookie.getName().equals("userId")) {
                        myId = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        List<UserRelation> userRelationList = userRelationService.getUserRelationByFatherId(Integer.parseInt(myId));
        List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < userRelationList.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(userRelationList.get(i).getId()));
//            map.put("fatherName", String.valueOf(userRelationList.get(i).getUserF().getUserName()));
            map.put("sonId", String.valueOf(userRelationList.get(i).getUserS().getId()));
            map.put("userAccount", userRelationList.get(i).getUserS().getUserAccount());
            map.put("userName", userRelationList.get(i).getUserS().getUserName());
            map.put("userGradeId", String.valueOf(userRelationList.get(i).getUserS().getUserGrade().getId()));
            map.put("userGradeType", String.valueOf(userRelationList.get(i).getUserS().getUserGrade().getUserGrade()));
            map.put("userGradeName", userRelationList.get(i).getUserS().getUserGrade().getUserGradeName());
            mlist.add(map);
        }
        System.out.println("user分页list="+userRelationList.toString());
        modelMap.addAttribute("list", mlist);
        return "next_followuser_list";
    }

    /**
     * 根据传过来的用户id，显示下一级用户列表，不分页
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/peopleFollowUserList")
    public String peopleFollowUserList(HttpServletRequest request, ModelMap modelMap) {
        int fatherId = Integer.parseInt(request.getParameter("fatherId"));
        String fatherName = request.getParameter("fatherName");
        List<UserRelation> userRelationList = userRelationService.getUserRelationByFatherId(fatherId);
        List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < userRelationList.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
//            fatherName = userRelationList.get(0).getUserF().getUserName();
            map.put("id", String.valueOf(userRelationList.get(i).getId()));
            map.put("sonId", String.valueOf(userRelationList.get(i).getUserS().getId()));
            map.put("userAccount", userRelationList.get(i).getUserS().getUserAccount());
            map.put("userName", userRelationList.get(i).getUserS().getUserName());
            map.put("userGradeId", String.valueOf(userRelationList.get(i).getUserS().getUserGrade().getId()));
            map.put("userGradeType", String.valueOf(userRelationList.get(i).getUserS().getUserGrade().getUserGrade()));
            map.put("userGradeName", userRelationList.get(i).getUserS().getUserGrade().getUserGradeName());
            mlist.add(map);
        }
        System.out.println("user分页list="+userRelationList.toString());
        modelMap.addAttribute("list", mlist);
        modelMap.addAttribute("fatherName", fatherName);
        return "people_followuser_list";
    }

    /**
     * 解除用户关系
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/delFollowUserRelation")
    public String delFollowUserRelation(HttpServletRequest request, ModelMap modelMap) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (userRelationService.deleteUserRelationById(id)) {
            //解除关系成功
            //获取当前用户等级
            Cookie cookie=null;
            Cookie[] cookies=null;
            cookies=request.getCookies();
            String myId = "";
            if (cookies!=null) {
                try {
                    for (int i = 0; i < cookies.length-1; i++) {
                        cookie=cookies[i];
                        if (cookie.getName().equals("userId")) {
                            myId = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            List<UserRelation> userRelationList = userRelationService.getUserRelationByFatherId(Integer.parseInt(myId));
            List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < userRelationList.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(userRelationList.get(i).getId()));
                map.put("sonId", String.valueOf(userRelationList.get(i).getUserS().getId()));
                map.put("userAccount", userRelationList.get(i).getUserS().getUserAccount());
                map.put("userName", userRelationList.get(i).getUserS().getUserName());
                map.put("userGradeId", String.valueOf(userRelationList.get(i).getUserS().getUserGrade().getId()));
                map.put("userGradeType", String.valueOf(userRelationList.get(i).getUserS().getUserGrade().getUserGrade()));
                map.put("userGradeName", userRelationList.get(i).getUserS().getUserGrade().getUserGradeName());
                mlist.add(map);
            }
            System.out.println("user分页list="+userRelationList.toString());
            modelMap.addAttribute("list", mlist);
            return "next_followuser_list";
        }
        modelMap.put("reason", "解除关系失败，请确认您的操作！");
        return "page_400";
    }
    
    @RequestMapping(value = "/getUserRelationList")
    public String getUserRelationList(HttpServletRequest request, HttpServletResponse response) {
//        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
//        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageNum = 2;
        int pageSize = 3;
        List<UserRelation> userRelationList = userRelationService.getUserRelationList();
        Pager<UserRelation> pager = new Pager<>(pageNum,pageSize,userRelationList);
        Gson gson=new Gson();
        String jsonStr= String.valueOf(gson.toJsonTree(pager));
        System.out.println(pager.toString());
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(jsonStr);
        out.flush();
        out.close();
        return null;
    }
}