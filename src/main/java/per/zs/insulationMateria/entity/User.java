package per.zs.insulationMateria.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
* @author zs 
* @version 创建时间�?2019�?7�?8�? 上午9:52:25 
* @Description 用户信息实体�?
 */
@Document(collection = User.COLLECTIONNAME)
public class User {
    public static final String COLLECTIONNAME = "user";

    @Id
    private String id;
    private String account;
    private String password;
    private String role;
    private String lastLoginTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User(String account, String password, String role){
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
