package com.yongxin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yongxin.dto.Pager;
import com.yongxin.entityerp.Usertest;
import com.yongxin.service.UserRelationService;
import com.yongxin.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yongxin.entity.User;
import com.yongxin.entity.UserGrade;
import com.yongxin.service.UserGradeService;
import com.yongxin.service.UserService;

/**
 * @author mry
 *2017年12月2日 上午10:30:52 修改
 */
@SessionAttributes(value = "username")
@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserGradeService userGradeService;
	@Autowired
	UserRelationService userRelationService;
	@Autowired
	StandardScaleService standardScaleService;

	/**
	 * 客户列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userClientList")
	public String userClientList(HttpServletRequest request, ModelMap modelMap) {
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
//		int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
		int pageNum = 0;
		int userId = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
		} catch (NumberFormatException e) {
			modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
			return "page_400";
		}
		User userMap = userService.getUserById(userId);
		HashMap<String, String> map1 = new HashMap<String, String>();
		if (userMap != null) {
			if (userMap.getUserGrade().getUserGrade() != 1) {
				return "page_403";
			}
			List<User> list = userService.getUserListByPermission(2, pageNum);
			List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("userAccount", list.get(i).getUserAccount());
				map.put("userName", list.get(i).getUserName());
				map.put("permissionTime", TimeUtil.dateToStrNoTime(list.get(i).getPermissionTime()));
				map.put("userGradeId", String.valueOf(list.get(i).getUserGrade().getId()));
				map.put("userGradeType", String.valueOf(list.get(i).getUserGrade().getUserGrade()));
				map.put("userGradeName", list.get(i).getUserGrade().getUserGradeName());
				map.put("id", String.valueOf(list.get(i).getId()));
				mlist.add(map);
			}
			System.out.println("user分页list=" + list.toString());
			modelMap.addAttribute("list", mlist);
			modelMap.addAttribute("pageNums", userService.getPageNumByPermission(2));
			modelMap.addAttribute("pageNum", pageNum);
			map1.put("userId", String.valueOf(userMap.getId()));
			map1.put("userAccount", userMap.getUserAccount());
			map1.put("userName", userMap.getUserName());
			map1.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
			map1.put("userTypeName", userMap.getUserGrade().getUserGradeName());
			modelMap.addAttribute("map", map1);
			return "client_list";
		} else {
			return "page_403";
		}
	}

	/**
	 * 跳转到添加客户页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/toAddClient")
	public String toAddClient(HttpServletRequest request, ModelMap modelMap) {
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


			//获取用户等级
			List<UserGrade> userGradeList = userGradeService.getUserGrades();
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < userGradeList.size(); i++) {
				if (userGradeList.get(i).getUserGradeName().equals("客户") || userGradeList.get(i).getUserGrade() == 2) {
					map.put("id", String.valueOf(userGradeList.get(i).getId()));
					map.put("userGrade", String.valueOf(userGradeList.get(i).getUserGrade()));
					map.put("userGradeName", userGradeList.get(i).getUserGradeName());
					modelMap.addAttribute("map1", map);
					return "client_add";
				}
			}
			map.put("id", String.valueOf(2));
			map.put("userGrade", String.valueOf(2));
			map.put("userGradeName", "客户");
			modelMap.addAttribute("map1", map);
			return "client_add";
		} else {
			return "page_403";
		}
	}

	/**
	 * 添加客户
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addClient")
	public String addClient( HttpServletRequest request,ModelMap modelMap){
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

			String userAccount = request.getParameter("inputUserAccount");
			String userName = request.getParameter("inputName");
			String password = request.getParameter("inputPassword");
			//使用期限，月数
			int permissionMonth = Integer.parseInt(request.getParameter("permissionMonth"));
			int userUserGradeId = Integer.parseInt(request.getParameter("userUserGradeId"));
			User userCheck = userService.getUserByAccount(userAccount);
			if (userCheck != null) {
				modelMap.put("reason", "已存在该账号，请重新输入账号！");
				return "page_400";
			}else {
				User user = new User();
				user.setUserName(userName);
				user.setPassword(password);
				user.setUserAccount(userAccount);
				user.setUserGrade(userGradeService.getUserGradeById(userUserGradeId));
				//设置使用期天数
				Date d=new Date();
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				System.out.println("今天的日期："+df.format(d));
				if (permissionMonth >= 12) {
					d.setYear(d.getYear() + (permissionMonth / 12));
					d.setMonth(d.getMonth() + permissionMonth % 12);
					user.setPermissionTime(new Date(d.getTime()));
				} else {
					d.setMonth(d.getMonth() + permissionMonth);
					user.setPermissionTime(new Date(d.getTime()));
				}
				user.setPermission(2);
				userService.addUser(user);
				return "redirect:/toAddClient?userId=" + CyptoUtils.encode(String.valueOf(userId));
			}
		} else {
			return "page_403";
		}
	}

	/**
	 * 管理员通过用户ID跳转到更新客户信息页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/toManagerUpdateClientJsp")
	public String toManagerUpdateClientJsp(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageNum = 0;
		int userId = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
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
			HashMap<String, String> mapU = new HashMap<String, String>();
			mapU.put("userId", String.valueOf(userMap.getId()));
			mapU.put("userAccount", userMap.getUserAccount());
			mapU.put("userName", userMap.getUserName());
			mapU.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
			mapU.put("userTypeName", userMap.getUserGrade().getUserGradeName());
			modelMap.addAttribute("map", mapU);


			HashMap<String, String> map = new HashMap<String, String>();
			if (id != null) {
				User user = userService.getUserById(Integer.parseInt(id));
//			map.put("password", user.getPassword());
				map.put("id", String.valueOf(user.getId()));
				map.put("userAccount", user.getUserAccount());
				map.put("userName", user.getUserName());
				map.put("userGradeId", String.valueOf(user.getUserGrade().getId()));
				map.put("userGrade", String.valueOf(user.getUserGrade().getUserGrade()));
				map.put("userGradeName", user.getUserGrade().getUserGradeName());

			} else {
				map.put("id", "0");
				map.put("userAccount", "账户");
				map.put("userName", "姓名");
				map.put("userGradeId", "等级ID");
				map.put("userGrade", "");
				map.put("userGradeName", "等级名称");
			}
			modelMap.addAttribute("map1", map);
			modelMap.addAttribute("pageNum", pageNum);
			return "client_update";
		} else {
			return "page_403";
		}

	}

	/**
	 * 管理员更新客户信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/managerUpdateClient")
	public String managerUpdateClient(HttpServletRequest request,ModelMap modelMap){
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

			User user = new User();
			String id = request.getParameter("id");
			String userName = request.getParameter("inputName");
			user.setId(Integer.parseInt(id));
			user.setUserName(userName);
//			userService.addUser(user);

			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			if (userService.update(user)) {
				//成功
				return "redirect:/userClientList?pageNum=" + pageNum+"&userId="+CyptoUtils.encode(String.valueOf(userId));
			}
			modelMap.put("reason", "修改失败，请确认您的操作？");
			return "page_400";
		} else {
			return "page_403";
		}

	}

	/**
	 * 删除客户,先删除关系再删除客户
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/clientDel")
	public String clientDel(HttpServletRequest request, ModelMap modelMap) {
		int pageNum = 0;
		int userId = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
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

			String id = request.getParameter("id");
			//1、先删除用户关系
			userRelationService.deleteUserRelationByFatherId(Integer.parseInt(id));
			//2、再删除用户，只是修改权限为-1
			if (userService.updateUserPermission(Integer.parseInt(id),-1) ){
				return "redirect:/userClientList?pageNum=" + pageNum+"&userId="+CyptoUtils.encode(String.valueOf(userId));
			}else {
				modelMap.put("reason", "删除失败，请确认您的操作！");
				return "page_400";
			}
		} else {
			return "page_403";
		}
	}

	/**
	 * 跳转到更新客户密码界面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/toUpdateClientPSWJsp")
	public String toUpdateClientPSWJsp(HttpServletRequest request, ModelMap modelMap) {
		int pageNum = 0;
		int userId = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
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


			String id = request.getParameter("id");
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			HashMap<String, String> map = new HashMap<String, String>();
			if (id != null) {
				User user = userService.getUserById(Integer.parseInt(id));
				map.put("userName", user.getUserName());
				map.put("id", String.valueOf(user.getId()));

			} else {
				map.put("userName", "用户名");
				map.put("userGradeId", "");
			}
			modelMap.addAttribute("map1", map);
			modelMap.addAttribute("pageNum", pageNum);
			return "clientpsw_update";
		} else {
			return "page_403";
		}

	}

	/**
	 * 更新客户密码
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/updateClientPSW")
	public String updateClientPSW(HttpServletRequest request, ModelMap modelMap) {
		int pageNum = 0;
		int userId = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
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

			String id = request.getParameter("id");
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			if (userService.checkUserPsw(Integer.parseInt(id),oldPassword)) {
				//旧密码验证成功,更新密码
				if (userService.updateUserPsw(Integer.parseInt(id), newPassword)) {
					//密码修改成功
					return "redirect:/userClientList?pageNum=" + pageNum+"&userId="+CyptoUtils.encode(String.valueOf(userId));
				}else {
					modelMap.put("reason", "密码验证成功但修改失败，请重试！");
					return "page_400";
				}
			}else {
				modelMap.put("reason", "密码验证失败，请确认您的密码！");
				return "page_400";
			}
		} else {
			return "page_403";
		}


	}

	/**
	 * 获取个人用户信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getuser")
	public String getUser(HttpServletRequest request, ModelMap modelMap) {
//		int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("id")));
		int userId = 0;
		try {
			userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("id")));
		} catch (NumberFormatException e) {
			modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
			return "page_400";
		}
		HashMap<String, String> map = new HashMap<String, String>();

		User user = userService.getUserById(userId);
		if (user != null) {

//			map.put("password", user.getPassword());
			map.put("id", String.valueOf(user.getId()));
			map.put("userAccount", user.getUserAccount());
			map.put("userName", user.getUserName());
			map.put("userGradeId", String.valueOf(user.getUserGrade().getId()));
			map.put("userGrade", String.valueOf(user.getUserGrade().getUserGrade()));
			map.put("userGradeName", user.getUserGrade().getUserGradeName());
			modelMap.addAttribute("map", map);
			return "info_page";
		}else {
			return "page_403";
		}
	}
	/**
	 * 跳转到修改个人用户信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/toUpdateMyInfo")
	public String toUpdateMyInfo(HttpServletRequest request, ModelMap modelMap) {
//		String id = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(CyptoUtils.decode(request.getParameter("id")));
		} catch (NumberFormatException e) {
			modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
			return "page_400";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		User user = userService.getUserById(id);
		if (user != null) {

//			map.put("password", user.getPassword());
			map.put("id", String.valueOf(user.getId()));
			map.put("userAccount", user.getUserAccount());
			map.put("userName", user.getUserName());
			map.put("userGradeId", String.valueOf(user.getUserGrade().getId()));
			map.put("userGrade", String.valueOf(user.getUserGrade().getUserGrade()));
			map.put("userGradeName", user.getUserGrade().getUserGradeName());
			modelMap.addAttribute("map", map);
			return "update_myinfo";
		} else {
			return "page_403";
		}
	}

	/**
	 * 更新个人信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/updateMyInfo")
	public String updateMyInfo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		String path = request.getContextPath();
		String id = request.getParameter("id");
		String username = request.getParameter("inputName");
		if (userService.updateUserInfo(Integer.parseInt(id), username)) {
			//信息修改成功

			return "redirect:/index?pageNum=1&userId=" + CyptoUtils.encode(id);
		}else {
			modelMap.put("reason", "信息修改失败，请重试！");
			return "page_400";
		}

	}
	/**
	 * 跳转到修改个人用户密码
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/toUpdateMyPsw")
	public String toUpdateMyPsw(HttpServletRequest request, ModelMap modelMap) {
//		String id = request.getParameter("id");
		String id = "";
		try {
			id = CyptoUtils.decode(request.getParameter("id"));
		} catch (NumberFormatException e) {
			modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
			return "page_400";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		User user = userService.getUserById(Integer.parseInt(id));
		if (user != null) {
//			map.put("password", user.getPassword());
			map.put("id", String.valueOf(user.getId()));
			map.put("userAccount", user.getUserAccount());
			map.put("userName", user.getUserName());
			map.put("userGradeId", String.valueOf(user.getUserGrade().getId()));
			map.put("userGrade", String.valueOf(user.getUserGrade().getUserGrade()));
			map.put("userGradeName", user.getUserGrade().getUserGradeName());

			modelMap.addAttribute("map", map);
			return "update_mypsw";
		} else {
			return "page_403";
		}
	}

	/**
	 * 更新个人密码
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/updateMyPsw")
	public String updateMyPsw(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		if (userService.checkUserPsw(Integer.parseInt(id),oldPassword)) {
			//旧密码验证成功,更新密码
			if (userService.updateUserPsw(Integer.parseInt(id), newPassword)) {
				//密码修改成功
				return "redirect:/index?pageNum=1&userId="+CyptoUtils.encode(id);
//				return "index";
			}else {
				modelMap.put("reason", "密码验证成功但修改失败，请重试！");
				return "page_400";
			}
		}else {
			modelMap.put("reason", "密码验证失败，请确认您的密码！");
			return "page_400";
		}
	}


	/**
	 *  用户登录
	 *  输入账号密码
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userLogin")
	public String userLogin(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		String path = request.getContextPath();
		String account = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.loginUser(account);
		if (user != null) {
			//账号正确
			if (user.getPermission() != -1) {
				//用户没被禁用
				if (user.getPassword().equals(password)) {
					User user1 = userService.getUserByAccount(account);

//					String s=this.getClass().getClassLoader().getResource("").getPath();
//					String beans = PermissionKeeper.xml2Doc(s+"com/yongxin/util/bean.xml").getDocumentElement().getTextContent();
//					System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ"+beans+"QQQQQQQQQQQQQQQQQQQQQQ");
//					if(beans == null||beans.equals("")){
//						//String endPermissionTime = UTCTime.getStartPermissionTime("http://www.beijing-time.org");
//                        Date date = new Date();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
//                        String endPermissionTime = String.valueOf(dateFormat.format(date));
//                        String setPermissionTime=null;
////						SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd");
//						try{
//                            System.out.println("时间："+endPermissionTime);
//							setPermissionTime = TimeUtil.dateToStamp(endPermissionTime);
//							long endPermissionTime1 = Long.parseLong(setPermissionTime);
//							endPermissionTime1 = endPermissionTime1 + 31536000000L;//加一天86400000毫秒
//							setPermissionTime = TimeUtil.stampToDate(String.valueOf(endPermissionTime1));
//							String encodeEndPermissionTime = CyptoUtils.encode(setPermissionTime);
//                            System.out.println("ssss:"+encodeEndPermissionTime);
//							Document doc1 = PermissionKeeper.string2Doc("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"<bean>"+encodeEndPermissionTime+"</bean>");
//							PermissionKeeper.doc2XML(doc1,s+"com/yongxin/util/bean.xml");
//							HashMap<String, String> map = new HashMap<String, String>();
//							map.put("userId", String.valueOf(user1.getId()));
//							map.put("userAccount", account);
//							map.put("userName", user1.getUserName());
//							map.put("userType", String.valueOf(user1.getUserGrade().getUserGrade()));
//							map.put("userTypeName", user1.getUserGrade().getUserGradeName());
//							List<StandardScale> standardScaleList = standardScaleService.getStandardScaleByIdAndPermission(user1.getId(), 1);
//							Pager<StandardScale> pager = new Pager<StandardScale>(1, 16, standardScaleList);
//							List<StandardScale> standardScales = pager.getDataList();
//							List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//							for (int i = 0; i < standardScales.size(); i++) {
//								HashMap<String, String> map1 = new HashMap<String, String>();
//								map1.put("productId", String.valueOf(standardScales.get(i).getId()));
//								map1.put("productName", standardScales.get(i).getProductName());
//								map1.put("barcodeNumber", standardScales.get(i).getBarcodeNumber());
//								map1.put("productDescription", standardScales.get(i).getProductDescription());
//								map1.put("productPicture", standardScales.get(i).getProductPicture());
//								list.add(map1);
//							}
//							modelMap.addAttribute("standardScaleList", list);
//							modelMap.addAttribute("pageNum", pager.getCurrentPage());
//							modelMap.addAttribute("pageNums", pager.getTotalPage());
//							modelMap.addAttribute("map", map);
//							return "index";
//						}catch (Exception e){
//							//账号异常+
//							modelMap.put("reason", "账号异常请联系管理员！！！");
//							return "page_400";
//						}
//					}else{
//						//String localTime = UTCTime.getStartPermissionTime("http://www.beijing-time.org");
//                        Date date = new Date();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
//                        String localTime = String.valueOf(dateFormat.format(date));
//                        String encodeEndPermissionTime = PermissionKeeper.xml2Doc(s+"com/yongxin/util/bean.xml").getDocumentElement().getTextContent();
//						String endPermissionTime = CyptoUtils.decode(encodeEndPermissionTime);
//						try {
//							String localTime1 = TimeUtil.dateToStamp(localTime);
//							String endPermissionTime1 = TimeUtil.dateToStamp(endPermissionTime);
//							//判断当前时间是否到期
//							if(Long.parseLong(localTime1 )<Long.parseLong( endPermissionTime1)){
//								HashMap<String, String> map = new HashMap<String, String>();
//								map.put("userId", String.valueOf(user1.getId()));
//								map.put("userAccount", account);
//								map.put("userName", user1.getUserName());
//								map.put("userType", String.valueOf(user1.getUserGrade().getUserGrade()));
//								map.put("userTypeName", user1.getUserGrade().getUserGradeName());
//								List<StandardScale> standardScaleList = standardScaleService.getStandardScaleByIdAndPermission(user1.getId(), 1);
//								Pager<StandardScale> pager = new Pager<StandardScale>(1, 16, standardScaleList);
//								List<StandardScale> standardScales = pager.getDataList();
//								List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//								for (int i = 0; i < standardScales.size(); i++) {
//									HashMap<String, String> map1 = new HashMap<String, String>();
//									map1.put("productId", String.valueOf(standardScales.get(i).getId()));
//									map1.put("productName", standardScales.get(i).getProductName());
//									map1.put("barcodeNumber", standardScales.get(i).getBarcodeNumber());
//									map1.put("productDescription", standardScales.get(i).getProductDescription());
//									map1.put("productPicture", standardScales.get(i).getProductPicture());
//									list.add(map1);
//								}
//								modelMap.addAttribute("standardScaleList", list);
//								modelMap.addAttribute("pageNum", pager.getCurrentPage());
//								modelMap.addAttribute("pageNums", pager.getTotalPage());
//								modelMap.addAttribute("map", map);
//								return "index";
//							}else{
//								//账号被禁用
//								modelMap.put("reason", "账号已被禁用请联系管理员1！！！");
//								return "page_400";
//							}
//						} catch (Exception e) {
//							//账号异常+
//							modelMap.put("reason", "账号异常请联系管理员1！！！");
//                            System.out.println(e);
//							return "page_400";
//						}
//					}
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("userId", String.valueOf(user1.getId()));
					map.put("userAccount", account);
					map.put("userName", user1.getUserName());
					map.put("userType", String.valueOf(user1.getUserGrade().getUserGrade()));
					map.put("userTypeName", user1.getUserGrade().getUserGradeName());
					List<StandardScale> standardScaleList = standardScaleService.getStandardScaleByIdAndPermission(user1.getId(), 1);
					Pager<StandardScale> pager = new Pager<StandardScale>(1, 16, standardScaleList);
					List<StandardScale> standardScales = pager.getDataList();
					List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
					for (int i = 0; i < standardScales.size(); i++) {
						HashMap<String, String> map1 = new HashMap<String, String>();
						map1.put("productId", String.valueOf(standardScales.get(i).getId()));
						map1.put("productName", standardScales.get(i).getProductName());
						map1.put("barcodeNumber", standardScales.get(i).getBarcodeNumber());
						map1.put("productDescription", standardScales.get(i).getProductDescription());
						map1.put("productPicture", standardScales.get(i).getProductPicture());
						list.add(map1);
					}
					modelMap.addAttribute("standardScaleList", list);
					modelMap.addAttribute("pageNum", pager.getCurrentPage());
					modelMap.addAttribute("pageNums", pager.getTotalPage());
					modelMap.addAttribute("map", map);
					return "index";
				}else {
					//密码错误
					modelMap.put("reason", "密码错误，请检查您的密码是否正确？");
					return "page_400";
				}
			} else {
				//用户被禁用
				modelMap.put("reason", "账号已被禁用，请联系管理员！");
				return "page_400";
			}
		}else {
			//账号错误
			modelMap.put("reason", "账号错误，请检查您的账号是否正确？");
			return "page_400";
		}

	}

	/**
	 *  客户注册接口
	 *  可以在这里添加手机号码注册验证码确认
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/registerUser")
	public String registerUser(HttpServletRequest request, ModelMap modelMap) {
		String userAccount = request.getParameter("account");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		User userCheck = userService.getUserByAccount(userAccount);
		if (userCheck != null) {
			modelMap.put("reason", "已存在该账号，请重新输入账号！");
			return "page_400";
		}else {
			//获取用户等级
			List<UserGrade> userGradeList = userGradeService.getUserGrades();
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < userGradeList.size(); i++) {
				if ( userGradeList.get(i).getUserGrade() == 2 || userGradeList.get(i).getUserGradeName().equals("客户") ) {
					User user = new User();
					user.setUserName(userName);
					user.setUserAccount(userAccount);
					user.setPassword(password);
					user.setPermission(0);
					user.setUserGrade(userGradeList.get(i));

					userService.addUser(user);
				}
			}

		}

		return "login";
	}

	/**
	 * 注册审核列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/clientRegisterList")
	public String clientRegisterList(HttpServletRequest request, ModelMap modelMap) {
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		List<User> list = userService.getUserListByPermission(0,pageNum);
		List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userAccount", list.get(i).getUserAccount());
			map.put("userName", list.get(i).getUserName());
			map.put("userGradeId", String.valueOf(list.get(i).getUserGrade().getId()));
			map.put("userGradeType", String.valueOf(list.get(i).getUserGrade().getUserGrade()));
			map.put("userGradeName", list.get(i).getUserGrade().getUserGradeName());
			map.put("id", String.valueOf(list.get(i).getId()));
			mlist.add(map);
		}
		System.out.println("user分页list="+list.toString());
		modelMap.addAttribute("list", mlist);
		modelMap.addAttribute("pageNums", userService.getPageNumByPermission(0));
		modelMap.addAttribute("pageNum", pageNum);
		return "registercheck_list";
	}

	/**
	 *
	 * 审核注册用户，是否通过注册
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/updateClientPerms")
	public String updateClientPerms(HttpServletRequest request,ModelMap modelMap) {
		int id = Integer.parseInt(request.getParameter("id"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));//页码
		int permission = Integer.parseInt(request.getParameter("permission"));
		if (userService.isOrNotAgreeAdd(id, permission)) {
			//通过申请成功
			List<User> list = userService.getUserListByPermission(0, pageNum);
			List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("userAccount", list.get(i).getUserAccount());
				map.put("userName", list.get(i).getUserName());
				map.put("userGradeId", String.valueOf(list.get(i).getUserGrade().getId()));
				map.put("userGradeType", String.valueOf(list.get(i).getUserGrade().getUserGrade()));
				map.put("userGradeName", list.get(i).getUserGrade().getUserGradeName());
				map.put("id", String.valueOf(list.get(i).getId()));
				mlist.add(map);
			}
			System.out.println("user分页list=" + list.toString());
			modelMap.addAttribute("list", mlist);
			modelMap.addAttribute("pageNums", userService.getPageNumByPermission(0));
			modelMap.addAttribute("pageNum", pageNum);
			return "registercheck_list";
		} else {
			modelMap.put("reason", "操作失败，请确认操作！");
			return "page_400";
		}
	}

	/**
	 * 审核不通过和已禁用用户列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/clientRegisterOUtList")
	public String clientRegisterOUtList(HttpServletRequest request, ModelMap modelMap) {
		int pageNum = 0;
		int userId = 0;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
		} catch (NumberFormatException e) {
			modelMap.addAttribute("reason", "对不起，您的跳转链接有错，请检查！");
			return "page_400";
		}
//		int userId = Integer.parseInt(CyptoUtils.decode(request.getParameter("userId")));
		User userMap = userService.getUserById(userId);
		HashMap<String, String> mapUser = new HashMap<String, String>();
		if (userMap != null) {
			if (userMap.getUserGrade().getUserGrade() != 1) {
				return "page_403";
			}
			mapUser.put("userId", String.valueOf(userMap.getId()));
			mapUser.put("userAccount", userMap.getUserAccount());
			mapUser.put("userName", userMap.getUserName());
			mapUser.put("userType", String.valueOf(userMap.getUserGrade().getUserGrade()));
			mapUser.put("userTypeName", userMap.getUserGrade().getUserGradeName());
			modelMap.addAttribute("map", mapUser);

			List<User> list = userService.getUserListByPermission(-1,pageNum);
			List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("userAccount", list.get(i).getUserAccount());
				map.put("userName", list.get(i).getUserName());
				map.put("userGradeId", String.valueOf(list.get(i).getUserGrade().getId()));
				map.put("userGradeType", String.valueOf(list.get(i).getUserGrade().getUserGrade()));
				map.put("userGradeName", list.get(i).getUserGrade().getUserGradeName());
				map.put("id", String.valueOf(list.get(i).getId()));
				mlist.add(map);
			}
			System.out.println("user分页list="+list.toString());
			modelMap.addAttribute("list", mlist);
			modelMap.addAttribute("pageNums", userService.getPageNumByPermission(-1));
			modelMap.addAttribute("pageNum", pageNum);
			return "regcheck_outlist";
		}else {
			return "page_403";
		}
	}

	/**
	 *  退出登录
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userOutLogin")
	public void userOutLogin(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
//		List<User> userList=userService.getUsers();
		List<Usertest> userList=userService.getUsertestList();
		ResponseUtils.renderJson(response,userList.toString());
//		return "login";
	}

}
