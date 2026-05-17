package sk.fsa.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.UserMapper;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.NotificationPreferenceFacade;
import sk.fsa.rental.rest.api.NotificationPreferencesApi;
import sk.fsa.rental.rest.dto.UpdateNotificationPreferencesRequestDto;
import sk.fsa.rental.rest.dto.UserDto;
import sk.fsa.rental.security.CurrentUserDetailService;

@RestController
public class NotificationPreferenceRestController implements NotificationPreferencesApi {

    private final NotificationPreferenceFacade notificationPreferenceFacade;
    private final CurrentUserDetailService currentUserDetailService;
    private final UserMapper userMapper;

    public NotificationPreferenceRestController(NotificationPreferenceFacade notificationPreferenceFacade,
                                                CurrentUserDetailService currentUserDetailService,
                                                UserMapper userMapper) {
        this.notificationPreferenceFacade = notificationPreferenceFacade;
        this.currentUserDetailService = currentUserDetailService;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> updateNotificationPreferences(UpdateNotificationPreferencesRequestDto body) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        User updated = notificationPreferenceFacade.update(
                currentUser,
                body.getMessageEmailNotifications(),
                body.getViewingEmailNotifications(),
                body.getViewingRequestEmailNotifications()
        );
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
}
