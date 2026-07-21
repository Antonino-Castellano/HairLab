package com.generation.hairlab.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target="password", ignore=true)
    UserDto toDto(User entity);

    User toEntity(UserDto dto);

    List<User> toEntity(List<UserDto> dtos);

    List<UserDto> toDto(List<User> entities);
}