package dev.workforge.app.WorkForge.service.usersession.impl;

import dev.workforge.app.WorkForge.repository.UserRedisRepository;
import dev.workforge.app.WorkForge.security.model.UserPrincipal;
import dev.workforge.app.WorkForge.security.user.PermissionContext;
import dev.workforge.app.WorkForge.service.usersession.UserPermissionStore;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserPermissionStoreImpl implements UserPermissionStore {
    private static final String USER_PREFIX = "USER_PERMISSION:";
    private final UserRedisRepository redisRepository;

    public UserPermissionStoreImpl(UserRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void save(String username, PermissionContext permissionContext) {
        redisRepository.set(USER_PREFIX + username, permissionContext, 30, TimeUnit.MINUTES);
    }

    @Override
    public UserPrincipal find(String username) {
        return redisRepository.get(USER_PREFIX + username, UserPrincipal.class);
    }

    @Override
    public void delete(String username) {
        redisRepository.delete(USER_PREFIX + username);
    }

    @Override
    public boolean hasKey(String username) {
        return redisRepository.exists(USER_PREFIX + username);
    }
}
