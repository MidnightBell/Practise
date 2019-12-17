package per.zs.insulationMateria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import per.zs.insulationMateria.entity.ResponseBody;
import per.zs.insulationMateria.entity.User;
import per.zs.insulationMateria.service.impl.UserServiceImpl;

@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/getOneAccount")
	public User getOneUser(String account) {
		return userService.findByAccount(account);
	}
	
	@GetMapping("/getAccounts")
	public List<User> getAllUser() {
		return userService.findAllAccount();
	}
	
	@GetMapping("/addUserRoles")
	public ResponseBody addUserRoles(String id) {
		return userService.addUserRoles(id);
	}
	
	@GetMapping("/removeUserRoles")
	public ResponseBody removeUserRoles(String id) {
		return userService.removeUserRoles(id);
	}
	
}
