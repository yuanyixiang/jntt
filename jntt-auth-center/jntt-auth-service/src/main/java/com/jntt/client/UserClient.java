package com.jntt.client;

import com.jntt.api.UserAPi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-service")
public interface UserClient extends UserAPi {
}
