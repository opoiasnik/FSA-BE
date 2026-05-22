package sk.fsa.rental.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void newUserHasEmailNotificationsEnabledByDefault() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);

        assertTrue(user.isMessageEmailNotifications());
        assertTrue(user.isViewingEmailNotifications());
        assertTrue(user.isViewingRequestEmailNotifications());
    }

    @Test
    void updateNotificationPreferencesStoresAllEmailChannels() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);
        verify(user);

        user.updateNotificationPreferences(false, true, false);

        assertFalse(user.isMessageEmailNotifications());
        assertTrue(user.isViewingEmailNotifications());
        assertFalse(user.isViewingRequestEmailNotifications());
    }

    @Test
    void updateNotificationPreferencesRejectsEnabledEmailsWhenEmailIsNotVerified() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);

        RentalException ex = assertThrows(RentalException.class,
                () -> user.updateNotificationPreferences(true, false, false));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
        assertEquals("emailVerified", ex.getField());
    }

    @Test
    void updateNotificationPreferencesAllowsDisablingEmailsWhenEmailIsNotVerified() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);

        user.updateNotificationPreferences(false, false, false);

        assertFalse(user.canReceiveMessageEmailNotifications());
        assertFalse(user.canReceiveViewingEmailNotifications());
        assertFalse(user.canReceiveViewingRequestEmailNotifications());
    }

    @Test
    void updateProfileResetsEmailVerificationWhenEmailChanges() {
        User user = new User("owner", "test", "old@test.sk", UserRole.OWNER);
        user.startEmailVerification("123456", new Date(System.currentTimeMillis() + 60_000L));
        user.verifyEmail("123456", new Date());

        user.updateProfile("owner", "test", "new@test.sk", null, null);

        assertFalse(user.isEmailVerified());
        assertFalse(user.isEmailVerificationPending());
    }

    @Test
    void updateProfileRejectsInvalidPhone() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);

        RentalException ex = assertThrows(RentalException.class,
                () -> user.updateProfile("owner", "test", "owner@test.sk", "abc", null));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
        assertEquals("phone", ex.getField());
    }

    @Test
    void updateProfileRejectsInvalidSurname() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);

        RentalException ex = assertThrows(RentalException.class,
                () -> user.updateProfile("owner", "123", "owner@test.sk", "+421 900 000 000", null));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
        assertEquals("surname", ex.getField());
    }

    @Test
    void verifyEmailRejectsExpiredCode() {
        User user = new User("owner", "test", "owner@test.sk", UserRole.OWNER);
        user.startEmailVerification("123456", new Date(1000L));

        RentalException ex = assertThrows(RentalException.class,
                () -> user.verifyEmail("123456", new Date(2000L)));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
    }

    private void verify(User user) {
        user.startEmailVerification("123456", new Date(System.currentTimeMillis() + 60_000L));
        user.verifyEmail("123456", new Date());
    }
}
