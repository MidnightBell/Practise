package per.zs.insulationMateria.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.zs.insulationMateria.shiro.CustomRealm;

/**
* @author zs 
* @version 创建时间�?2019�?7�?8�? 上午9:51:24 
* @Description shiro 配置
 */
@Configuration
public class ShiroConfig {
    /**
     * 过滤器默认权限表 {anon=anon, authc=authc, authcBasic=authcBasic, logout=logout,
     * noSessionCreation=noSessionCreation, perms=perms, port=port,
     * rest=rest, roles=roles, ssl=ssl, user=user}
     * <p>
     * anon, authc, authcBasic, user 是第�?组认证过滤器
     * perms, port, rest, roles, ssl 是第二组授权过滤�?
     * <p>
     * user �? authc 的不同：当应用开启了rememberMe�?, 用户下次访问时可以是�?个user, 但绝不会是authc,
     * 因为authc是需要重新认证的, user表示用户不一定已通过认证, 只要曾被Shiro记住过登录状态的用户就可以正常发起请�?,比如rememberMe
     * 以前的一个用户登录时�?启了rememberMe, 然后他关闭浏览器, 下次再访问时他就是一个user, 而不会authc
     *
     * @param securityManager 初始�? ShiroFilterFactoryBean 的时候需要注�? SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置�?�，默认会自动寻找Web工程根目录下�?"/login.jsp"页面 �? "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        // 设置无权限时跳转�? url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 设置拦截�?
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //游客，需要角色权�?"admin,user,public"
        filterChainDefinitionMap.put("/public/**", "roles[admin,user,public]");
        //用户，需要角色权�? “admin,user�?
        filterChainDefinitionMap.put("/user/**", "roles[admin,user]");
        //管理员，�?要角色权�? “admin�?
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //�?放登陆与注册接口
        filterChainDefinitionMap.put("/index.html", "anon");
        filterChainDefinitionMap.put("/*.js", "anon");
        filterChainDefinitionMap.put("/*.woff", "anon");
        filterChainDefinitionMap.put("/*.ttf", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        //其余接口�?律拦�?
        //主要这行代码必须放在�?有权限设置的�?后，不然会导致所�? url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(customRealm);
        return securityManager;
    }
}