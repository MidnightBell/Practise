package per.zs.insulationMateria.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import per.zs.insulationMateria.entity.ResponseBody;
import per.zs.insulationMateria.entity.User;
import per.zs.insulationMateria.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public ResponseBody insert(User user) {
        mongoTemplate.insert(user);
        return new ResponseBody(1000, "新增账号成功");
    }

    @Override
    public User findByAccount(String account) {
        Query queryByAccount = new Query();
        queryByAccount.addCriteria(Criteria.where("account").is(account));
        return mongoTemplate.findOne(queryByAccount, User.class);
    }

    @Override
    public List<User> findAllAccount() {
        Query query = new Query();
        List<User> list = mongoTemplate.find(query, User.class);
        return list;
    }

    @Override
    public ResponseBody addUserRoles(String id) {
        Query queryById = new Query();
        queryById.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        User user = mongoTemplate.findOne(queryById, User.class);
        String roles = user.getRole();
        if (!roles.contains("user")) {
            roles = roles + "/user";
            user.setRole(roles);
            mongoTemplate.save(user);

            return new ResponseBody(1000, "添加user角色成功�?");
        } else {
            return new ResponseBody(1003, "该用户已有user角色�?");
        }
    }

    @Override
    public ResponseBody removeUserRoles(String id) {
        Query queryById = new Query();
        queryById.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        User user = mongoTemplate.findOne(queryById, User.class);
        String[] roles = user.getRole().split("/");
        List<String> oldRoles = new ArrayList<>();
        String newRoles = "";
        if (roles != null && !StringUtils.isEmpty(roles)) {
            for (String role : roles) {
                oldRoles.add(role);
            }
        }
        if (oldRoles.contains("admin")) {
            return new ResponseBody(1003, "不能操作其他的管理员账号信息");
        }
        if (oldRoles.contains("user")) {
            oldRoles.remove("user");
            for (String role : oldRoles) {
                newRoles = newRoles + "/" + role;
            }
            user.setRole(newRoles.substring(1));
            mongoTemplate.save(user);
            return new ResponseBody(1000, "删除user角色成功�?");
        } else {
            return new ResponseBody(1003, "该用户无user角色�?");
        }
    }
}
