package per.zs.insulationMateria.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import per.zs.insulationMateria.entity.User;
import per.zs.insulationMateria.service.impl.UserServiceImpl;
/**
* @author zs 
* @version 创建时间�?2019�?7�?8�? 上午9:50:55 
* @Description 自定�? Realm
 */
@Component
public class CustomRealm extends AuthorizingRealm {
	@Autowired
	UserServiceImpl userService;

    /**
     * 获取身份验证信息
     * Shiro中，�?终是通过 Realm 来获取应用程序中的用户�?�角色及权限信息的�??
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("—�?��?��?�身份认证方法�?��?��?��??");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        User user = userService.findByAccount(token.getUsername());
        if (null == user) {
            throw new AccountException("用户名不正确");
        } else if (!user.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正�?");
        }
        System.out.println("—�?��?��?�身份认证�?�过—�?��?��??");
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("—�?��?��?�权限认证�?��?��?��??");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角�?
        String[] roles = userService.findByAccount(username).getRole().split("/");
        Set<String> set = new HashSet<>();
        //�?要将 role 封装�? Set 作为 info.setRoles() 的参�?
		if (roles != null && !StringUtils.isEmpty(roles)) {
			for (String role : roles) {
				set.add(role);
			}
		}
        //设置该用户拥有的角色
        info.setRoles(set);
        System.out.println("—�?��?��?�权限认证完成�?��?��?��??");
        return info;
    }
}
