package sk.fsa.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sk.fsa.rental.mapper.UserMapper;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.rest.api.UserApi;
import sk.fsa.rental.rest.dto.UpdateUserProfileRequestDto;
import sk.fsa.rental.rest.dto.UserDto;
import sk.fsa.rental.security.CurrentUserDetailService;
import sk.fsa.rental.validation.PhotoUploadValidator;

import java.io.IOException;

@RestController
public class UserRestController implements UserApi {

    private final UserFacade userFacade;
    private final UserMapper userMapper;
    private final CurrentUserDetailService currentUserDetailService;

    public UserRestController(UserFacade userFacade, UserMapper userMapper,
                              CurrentUserDetailService currentUserDetailService) {
        this.userFacade = userFacade;
        this.userMapper = userMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> getCurrentUser() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> updateUserProfile(UpdateUserProfileRequestDto body) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        User updated = userFacade.updateProfile(
                currentUser,
                body.getName(),
                body.getSurname(),
                body.getEmail(),
                body.getPhone(),
                body.getBio()
        );
        return ResponseEntity.ok(userMapper.toDto(updated));
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> uploadUserAvatar(MultipartFile file) {
        PhotoUploadValidator.validate(file);
        User currentUser = currentUserDetailService.getFullCurrentUser();
        try {
            User updated = userFacade.updateAvatar(
                    currentUser,
                    file.getBytes(),
                    file.getContentType(),
                    file.getOriginalFilename()
            );
            return ResponseEntity.ok(userMapper.toDto(updated));
        } catch (IOException ex) {
            throw new RentalException(RentalException.Type.VALIDATION, "Unable to read uploaded avatar.", "file");
        }
    }
}
