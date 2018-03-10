package com.yongxin.controller;

import com.yongxin.entity.UserGrade;
import com.yongxin.entity.User;
import com.yongxin.service.UserGradeService;
import com.yongxin.service.UserService;
import com.yongxin.util.CyptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wnf on 2017-12-29.
 */
@Controller
public class UserGradeController {
    @Autowired
    UserGradeService userGradeService;
    @Autowired
    UserService userService;

    /**
     * 用户等级列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/userGradeList")
    public String usertypeList(HttpServletRequest request, ModelMap modelMap){
        int userId = 0;
        try {
            userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
        } catch (NumberFormatException e) {
            modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
            return "page_400";
        }
        User userMap = userService.getUserById(userId);
        if (userMap != null) {
            if (userMap.getUserGrade().getUserGrade() != 1) {
                return "page_403";
            }
            HashMap<String, String> mapUser = new HashMap<String, String>();
            mapUser.put("userId", String.valueOf(userMap.getId()));
            mapUser.put("userAccount", userMap.getUserAccount());
            mapUser.put("userName", userMap.getUserName());
            mapUser.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
            mapUser.put("userTypeName", userMap.getUserGrade().getUserGradeName());
            modelMap.addAttribute("map", mapUser);

            List<UserGrade> userGradeList = userGradeService.getUserGrades();
            List<HashMap<String,String>> myList = new ArrayList<HashMap<String,String>>();
            for(int i = 0; i < userGradeList.size();i++){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("id", String.valueOf(userGradeList.get(i).getId()));
                map.put("gradetype", String.valueOf(userGradeList.get(i).getUserGrade()));
                map.put("gradename", userGradeList.get(i).getUserGradeName());
                myList.add(map);
            }
            modelMap.addAttribute("userTypeList", myList);
            return "usergrade_list";
        } else {
            return "page_403";
        }
    }

    /**
     * 跳转到添加用户等级界面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toUserGradeAdd")
    public String toUserGradeAdd(HttpServletRequest request,ModelMap modelMap) {
        int userId = 0;
        try {
            userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
        } catch (NumberFormatException e) {
            modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
            return "page_400";
        }
        User userMap = userService.getUserById(userId);
        if (userMap != null) {
            if (userMap.getUserGrade().getUserGrade() != 1) {
                return "page_403";
            }
            HashMap<String, String> mapUser = new HashMap<String, String>();
            mapUser.put("userId", String.valueOf(userMap.getId()));
            mapUser.put("userAccount", userMap.getUserAccount());
            mapUser.put("userName", userMap.getUserName());
            mapUser.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
            mapUser.put("userTypeName", userMap.getUserGrade().getUserGradeName());
            modelMap.addAttribute("map", mapUser);
            return "usergrade_add";
        } else {
            return "page_403";
        }

    }

    /**
     * 添加用户等级，成功返回到用户等级列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addUserGrade")
    public String addUserGrade(HttpServletRequest request,ModelMap modelMap){
        int userId = 0;
        try {
            userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
        } catch (NumberFormatException e) {
            modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
            return "page_400";
        }
        User userMap = userService.getUserById(userId);
        if (userMap != null) {
            if (userMap.getUserGrade().getUserGrade() != 1) {
                return "page_403";
            }
            HashMap<String, String> mapUser = new HashMap<String, String>();
            mapUser.put("userId", String.valueOf(userMap.getId()));
            mapUser.put("userAccount", userMap.getUserAccount());
            mapUser.put("userName", userMap.getUserName());
            mapUser.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
            mapUser.put("userTypeName", userMap.getUserGrade().getUserGradeName());
            modelMap.addAttribute("map", mapUser);


            int gradeType = Integer.parseInt(request.getParameter("gradetype"));
            String gradeName = request.getParameter("gradename");
            UserGrade userGrade = userGradeService.getUserGradeByType(gradeType);
            if (userGrade!=null) {
                modelMap.put("reason", "已存在该用户等级，请确认您的操作！");
                return "page_400";
            }
            UserGrade userGrade1Add = new UserGrade();
            userGrade1Add.setUserGrade(gradeType);
            userGrade1Add.setUserGradeName(gradeName);
            userGradeService.addUserGrade(userGrade1Add);

            return "redirect:/userGradeList?userId="+CyptoUtils.encode(String.valueOf(userId));
        } else {
            return "page_403";
        }
    }

    /**
     * 通过ID获取用户等级，跳转到修改等级名称页面
     * @param request
     * @param model
     * @return 页面切换获取数据
     */
    @RequestMapping(value = "/toUpdateUserGradeJsp")
    public String toUpdateUserGradeJsp(HttpServletRequest request, ModelMap model) {
        int userId = 0;
        try {
            userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
        } catch (NumberFormatException e) {
            model.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
            return "page_400";
        }
        User userMap = userService.getUserById(userId);
        if (userMap != null) {
            if (userMap.getUserGrade().getUserGrade() != 1) {
                return "page_403";
            }
            HashMap<String, String> mapUser = new HashMap<String, String>();
            mapUser.put("userId", String.valueOf(userMap.getId()));
            mapUser.put("userAccount", userMap.getUserAccount());
            mapUser.put("userName", userMap.getUserName());
            mapUser.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
            mapUser.put("userTypeName", userMap.getUserGrade().getUserGradeName());
            model.addAttribute("map", mapUser);

            int userGradeId = Integer.parseInt(request.getParameter("id"));
            UserGrade userGrade = userGradeService.getUserGradeById(userGradeId);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(userGrade.getId()));
            map.put("gradetype", String.valueOf(userGrade.getUserGrade()));
            map.put("gradename", String.valueOf(userGrade.getUserGradeName()));
            model.addAttribute("map1", map);
            return "usergrade_update";
        } else {
            return "page_403";
        }
    }

    /**
     * 修改用户类型
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateUserGrade")
    public String updateUserGrade(HttpServletRequest request,ModelMap modelMap){
        int userId = 0;
        try {
            userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
        } catch (NumberFormatException e) {
            modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
            return "page_400";
        }
        User userMap = userService.getUserById(userId);
        if (userMap != null) {
            if (userMap.getUserGrade().getUserGrade() != 1) {
                return "page_403";
            }
            HashMap<String, String> mapUser = new HashMap<String, String>();
            mapUser.put("userId", String.valueOf(userMap.getId()));
            mapUser.put("userAccount", userMap.getUserAccount());
            mapUser.put("userName", userMap.getUserName());
            mapUser.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
            mapUser.put("userTypeName", userMap.getUserGrade().getUserGradeName());
            modelMap.addAttribute("map", mapUser);

            UserGrade userGrade = new UserGrade();
            int id = Integer.parseInt(request.getParameter("id"));
            String typename = request.getParameter("gradename");
            userGrade.setId(id);
            userGrade.setUserGradeName(typename);
            if (userGradeService.update(userGrade)) {
                //修改成功，跳转到等级列表
                return "redirect:/userGradeList?userId="+CyptoUtils.encode(String.valueOf(userId));
            }
            modelMap.put("reason", "修改用户等级失败，请确认您的操作！");
            return "page_400";
        } else {
            return "page_403";
        }
    }


}
