package per.zs.insulationMateria.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import per.zs.insulationMateria.entity.ResponseBody;
import per.zs.insulationMateria.entity.User;
import per.zs.insulationMateria.service.impl.UserServiceImpl;


@RestController
@CrossOrigin
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class); 
	@Autowired
	UserServiceImpl userService;
	@RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public ResponseBody notLogin() {
        return new ResponseBody(1001,"您尚未登陆！");
    }

    @RequestMapping(value = "/notRole", method = RequestMethod.GET)
    public ResponseBody notRole() {
        return new ResponseBody(1001,"您没有权限！");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseBody logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
		return new ResponseBody(1000,"成功注销�?");
    }

    @PostMapping("/register")
    public ResponseBody register(String account, String password) throws Exception {
    	if(StringUtils.isEmpty(account)||StringUtils.isEmpty(password)) {
    		throw new Exception("用户名及密码不能为空");
    	}else {
    		String role = "public";
    		User user = new User(account, password, role);
    		return userService.insert(user);
    	}
    }
    
    /**
     * 登陆
     *
     * @param
     * @param
     */
    @PostMapping("/login")
    public ResponseBody login(@RequestBody JSONObject param) {
    	String account = param.getString("account");
    	String password = param.getString("password");
    	log.info("login:account="+account+" password="+password);
        // 从SecurityUtils里边创建�?�? subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        // 执行认证登陆
        subject.login(token);
        //根据权限，指定返回数�?
        String roles = userService.findByAccount(account).getRole();
        if (roles.contains("admin")) {
        	log.info("管理员登�?");
        	return new ResponseBody(1000,"管理员登�?");
        }else if (roles.contains("user")) {
        	log.info("用户登陆");
        	return new ResponseBody(1000,"用户登陆");
        }else if(roles.contains("public")){
        	log.info("游客登录");
        	return new ResponseBody(1000,"游客登陆");
        }else {
        	log.info("权限出错");
        	return new ResponseBody(1002,"权限错误");
        }
    } 
}
