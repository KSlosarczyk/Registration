package com.logandreg.KamilS.registration;

import com.logandreg.KamilS.appuser.AppUser;
import com.logandreg.KamilS.appuser.AppUserRole;
import com.logandreg.KamilS.appuser.AppUserService;
import com.logandreg.KamilS.registration.token.Token;
import com.logandreg.KamilS.registration.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final TokenService tokenService;

    public String register(UserRegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (isValidEmail){
            throw new IllegalStateException("Email is not valid!");
        }
        return appUserService.singUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public String confirmToken(String token){
        Token confirmationToken = tokenService.getToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token expired!");
        }

        tokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "Account confirmed";
    }
}
