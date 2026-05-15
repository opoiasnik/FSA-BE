package sk.fsa.rental.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.rest.dto.UserDto;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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

    default OffsetDateTime toOffsetDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.UTC);
    }
}
