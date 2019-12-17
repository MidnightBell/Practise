package per.zs.insulationMateria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

/**
* @author zs 
* @version 创建时间�?2019�?7�?8�? 上午9:51:43 
* @Description Mongodb 信息配置�?
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration{

	@Value("${spring.data.mongodb.database}")
    private String dbName;
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.connectionsPerHost}")
    private int connectionsPerHost;
    @Value("${spring.data.mongodb.threadsAllowedToBlockForConnectionMultiplier}")
    private int threadAllowedToBlockForConnectionMultiplier;
    @Value("${spring.data.mongodb.connectTimeout}")
    private int connectTimeout;
    @Value("${spring.data.mongodb.maxWaitTime}")
    private int maxWaitTime;
    @Value("${spring.data.mongodb.socketKeepAlive}")
    private boolean socketKeepAlive;
    @Value("${spring.data.mongodb.socketTimeout}")
    private int socketTimeout;
    @Value("${spring.data.mongodb.maxConnectionIdleTime}")
    private int maxConnectionIdleTime;
    @Value("${spring.data.mongodb.maxConnectionLifeTime}")
    private int maxConnectionLifeTime;
    @Autowired
    private ApplicationContext context;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClientOptions.Builder builder = MongoClientOptions.builder()
                .connectionsPerHost(connectionsPerHost)
                .threadsAllowedToBlockForConnectionMultiplier(threadAllowedToBlockForConnectionMultiplier)
                .connectTimeout(connectTimeout)
                .maxWaitTime(maxWaitTime)
                .socketKeepAlive(socketKeepAlive)
                .socketTimeout(socketTimeout)
                .maxConnectionIdleTime(maxConnectionIdleTime)
                .maxConnectionLifeTime(maxConnectionLifeTime);

        MongoClientURI mongoClientURI = new MongoClientURI(uri, builder);
        return new MongoClient(mongoClientURI);
    }

    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        MongoDbFactory factory = mongoDbFactory();

        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setApplicationContext(context);

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
        //去掉生成�?"_class"字段
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(factory, converter);
    }
}
