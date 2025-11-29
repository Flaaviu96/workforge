package dev.workforge.app.WorkForge.service.user_permission.impl;

import dev.workforge.app.WorkForge.dto.UserViewDTO;
import dev.workforge.app.WorkForge.exceptions.UserException;
import dev.workforge.app.WorkForge.mapper.UserMapper;
import dev.workforge.app.WorkForge.model.AppUser;
import dev.workforge.app.WorkForge.repository.UserRepository;
import dev.workforge.app.WorkForge.service.user_permission.UserService;
import dev.workforge.app.WorkForge.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<AppUser> getUsersByIds(List<Long> usersIds) {
        if (usersIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<AppUser> appUsers = userRepository.findUsersByIds(usersIds);
        if (appUsers.isEmpty()) {
            throw new UserException(ErrorMessages.USERS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return appUsers;
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<UserViewDTO> getUsersByPrefix(String prefix) {
        List<AppUser> appUsers = userRepository.findUsersByPrefix(prefix.trim());
        return appUsers.isEmpty() ? List.of() : userMapper.toDTOList(appUsers);
    }

    @Override
    public AppUser getUserByUUID(UUID uuid) {
        return userRepository.userExists(uuid);
    }
}
