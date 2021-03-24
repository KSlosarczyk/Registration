package com.logandreg.KamilS.registration;

import com.logandreg.KamilS.appuser.AppUser;
import com.logandreg.KamilS.appuser.AppUserRole;
import com.logandreg.KamilS.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

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
}
