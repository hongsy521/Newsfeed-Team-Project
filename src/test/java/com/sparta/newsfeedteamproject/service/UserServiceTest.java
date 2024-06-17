package com.sparta.newsfeedteamproject.service;

import com.sparta.newsfeedteamproject.dto.user.ProfileResDto;
import com.sparta.newsfeedteamproject.dto.user.SignupReqDto;
import com.sparta.newsfeedteamproject.dto.user.UpdateReqDto;
import com.sparta.newsfeedteamproject.dto.user.UserAuthReqDto;
import com.sparta.newsfeedteamproject.entity.Status;
import com.sparta.newsfeedteamproject.entity.User;
import com.sparta.newsfeedteamproject.exception.ExceptionMessage;
import com.sparta.newsfeedteamproject.repository.UserRepository;
import com.sparta.newsfeedteamproject.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private SignupReqDto signupReqDto;
    private UserAuthReqDto userAuthReqDto;
    private UpdateReqDto updateReqDto;
    private UserDetailsImpl userDetails;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        signupReqDto = new SignupReqDto();
        signupReqDto.setUsername("Hhongsy0521");
        signupReqDto.setPassword("Hhongsy0521!");
        signupReqDto.setName("hongsy");
        signupReqDto.setEmail("hongsy@gmail.com");
        signupReqDto.setUserInfo("한줄소개 아무거나");

        userAuthReqDto = new UserAuthReqDto();
        userAuthReqDto.setUsername("Hhongsy0521");
        userAuthReqDto.setPassword("Hhongsy0521!");

        updateReqDto = new UpdateReqDto();
        updateReqDto.setPassword("Hhongsy0521!");
        updateReqDto.setNewPassword("yahoo12345!");
        updateReqDto.setNewName("hong");
        updateReqDto.setNewUserInfo("야호");

        user = new User("Hhongsy0521", "Hhongsy0521!", "hongsy", "hongsy@gmail.com", "한줄소개 아무거나", Status.UNAUTHORIZED, LocalDateTime.now());
        user.setId(1L);
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    public void testSignup() {
        when(userRepository.findByUsername(signupReqDto.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(signupReqDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signupReqDto.getPassword())).thenReturn("encodedPassword");

        userService.signup(signupReqDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(signupReqDto.getUsername(), savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(signupReqDto.getName(), savedUser.getName());
        assertEquals(signupReqDto.getEmail(), savedUser.getEmail());
        assertEquals(signupReqDto.getUserInfo(), savedUser.getUserInfo());
        assertEquals(Status.UNAUTHORIZED, savedUser.getStatus());
    }

    @Test
    public void testSignupDuplicateUsername() {
        when(userRepository.findByUsername(signupReqDto.getUsername())).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signup(signupReqDto));

        assertEquals(ExceptionMessage.DUPLICATE_USERNAME.getExceptionMessage(), exception.getMessage());
    }

    @Test
    public void testWithdraw() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userAuthReqDto.getPassword(), user.getPassword())).thenReturn(true);

        userService.withdraw(user.getId(), userAuthReqDto, userDetails);

        assertEquals(Status.DEACTIVATE, user.getStatus());
    }

    @Test
    public void testGetProfile() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ProfileResDto profile = userService.getProfile(user.getId());

        assertEquals(user.getUsername(), profile.getUsername());
        assertEquals(user.getName(), profile.getName());
        assertEquals(user.getEmail(), profile.getEmail());
        assertEquals(user.getUserInfo(), profile.getUserInfo());
    }

    @Test
    public void testEditProfile() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(updateReqDto.getPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(updateReqDto.getNewPassword())).thenReturn("encodedNewPassword");

        ProfileResDto profile = userService.editProfile(user.getId(), updateReqDto, userDetails);

        assertEquals(updateReqDto.getNewName(), profile.getName());
        assertEquals(updateReqDto.getNewUserInfo(), profile.getUserInfo());
    }
}
