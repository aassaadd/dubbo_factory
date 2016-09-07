package {{groupid}}.{{name}}_service.token;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenManager implements TokenManager {

	private Long TOKEN_EXPIRES_HOUR = 5L;// HOURS小时
	@Autowired
	private StringRedisTemplate redisTemplate;

	
//	public void setRedis(RedisTemplate redis) {
//
//		this.redisTemplate = redis;
//
//		// 泛型设置成Long后必须更改对应的序列化方案
//
//		redis.setKeySerializer(new StringRedisSerializer());
//		
//
//	}

	public TokenModel createToken(Integer userId) {

		// 使用uuid作为源token

		String token = UUID.randomUUID().toString().replace("-", "");

		TokenModel model = new TokenModel(userId, token);

		// 存储到redis并设置过期时间

		redisTemplate.boundValueOps(userId.toString()).set(token, TOKEN_EXPIRES_HOUR,
				TimeUnit.HOURS);

		return model;

	}

	public TokenModel getToken(String authentication) {

		if (authentication == null || authentication.length() == 0) {

			return null;

		}

		String[] param = authentication.split("_");

		if (param.length != 2) {

			return null;

		}

		// 使用userId和源token简单拼接成的token，可以增加加密措施

		Integer userId = Integer.parseInt(param[0]);

		String token = param[1];

		return new TokenModel(userId, token);

	}

	public boolean checkToken(TokenModel model) {

		if (model == null) {

			return false;

		}

		String token = (String) redisTemplate.boundValueOps(model.getUserId().toString()).get();

		if (token == null || !token.equals(model.getToken())) {

			return false;

		}

		// 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间

		redisTemplate.boundValueOps(model.getUserId().toString()).expire(TOKEN_EXPIRES_HOUR,
				TimeUnit.HOURS);

		return true;

	}

	public void deleteToken(Integer userId) {

		redisTemplate.delete(userId.toString());

	}

}