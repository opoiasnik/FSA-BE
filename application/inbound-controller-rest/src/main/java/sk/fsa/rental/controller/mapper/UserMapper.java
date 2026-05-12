package sk.fsa.rental.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.rest.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarUrl", expression = "java(toAvatarUrl(user))")
    UserDto toDto(User user);

    default String toAvatarUrl(User user) {
        if (user == null || user.getAvatarPhoto() == null || user.getAvatarPhoto().getId() == null) {
            return null;
        }
        return "/api/photos/" + user.getAvatarPhoto().getId() + "/content";
    }
}
