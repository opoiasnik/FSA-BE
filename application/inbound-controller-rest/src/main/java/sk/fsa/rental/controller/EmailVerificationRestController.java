package sk.fsa.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.UserMapper;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.EmailVerificationFacade;
import sk.fsa.rental.rest.api.EmailVerificationApi;
import sk.fsa.rental.rest.dto.ConfirmEmailVerificationRequestDto;
import sk.fsa.rental.rest.dto.UserDto;
import sk.fsa.rental.security.CurrentUserDetailService;

@RestController
public class EmailVerificationRestController implements EmailVerificationApi {

    private final EmailVerificationFacade emailVerificationFacade;
    private final CurrentUserDetailService currentUserDetailService;
    private final UserMapper userMapper;

    public EmailVerificationRestController(EmailVerificationFacade emailVerificationFacade,
                                           CurrentUserDetailService currentUserDetailService,
                                           UserMapper userMapper) {
        this.emailVerificationFacade = emailVerificationFacade;
        this.currentUserDetailService = currentUserDetailService;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> requestEmailVerification() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        emailVerificationFacade.request(currentUser);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> confirmEmailVerification(ConfirmEmailVerificationRequestDto body) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        User updated = emailVerificationFacade.confirm(currentUser, body.getCode());
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
}
