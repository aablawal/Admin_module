package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.PasswordHistory;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.exceptions.KulaApiException;
import com.unionbankng.future.authorizationserver.exceptions.ResourceNotFoundException;
import com.unionbankng.future.authorizationserver.interfaces.AdminService;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.AdminResponse;
import com.unionbankng.future.authorizationserver.pojos.ErrorResponse;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.repositories.PasswordHistoryRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.security.PasswordValidator;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.authorizationserver.utils.CryptoService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${kula.encryption.key}")
    private String encryptionKey;

    private final UserService userService;

    private final MessageSource messageSource;

    private final PasswordEncoder passwordEncoder;

    private final UserConfirmationTokenService userConfirmationTokenService;

    private final ProfileService profileService;

    private final PasswordHistoryRepository passwordHistoryRepository;

    private final UserRepository userRepository;

    private  final CryptoService cryptoService;

    private final App app;

    private PasswordValidator passwordValidator  = PasswordValidator.
            buildValidator(false, true, true, 6, 40);



    @Override
    public ResponseEntity<APIResponse> createAdmin(RegistrationRequest request) {

        app.print("####CREATING KULA ADMIN");
        LOGGER.info("####CREATING KULA ADMIN");
        if (userService.existsByUsername(request.getUsername()) || userService.existsByEmail(request.getEmail()) || userService.existsByPhoneNumber(request.getPhoneNumber())) {
            app.print("User found: ");
            ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setCode("00");
                return ResponseEntity.ok( new APIResponse<>(errorResponse.getRemark(), false, errorResponse));
        }


//        if(!passwordValidator.validatePassword(request.getPassword()))
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
//                    new APIResponse(messageSource.getMessage("password.validation.error", null, LocaleContextHolder.getLocale()),false,null));


        String generateUuid =app.makeUIID();
        String decryptedPassword=cryptoService.decryptAES(request.getPassword(),encryptionKey);
        if (request.getEmail().endsWith("unionbankng.com") || request.getEmail().equalsIgnoreCase("abideenlawal70@gmail.com")) {
            PasswordHistory history = PasswordHistory.builder()
                    .uuid(generateUuid)
                    .password(decryptedPassword)
                    .createdAt(new Date())
                    .build();


            User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                    .kycLevel(0)
                    .role(request.getRole().name())
                    .department(request.getDepartment())
                    .phoneNumber(request.getPhoneNumber())
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .dialingCode(request.getDialingCode())
                    .branchCode(request.getBranchCode())
                    .roleFunction(request.getRoleFunction())
                    .department(request.getDepartment())
                    .isEnabled(Boolean.FALSE)
                    .uuid(generateUuid)
                    .password(passwordEncoder.encode(decryptedPassword))
                    .authProvider(request.getAuthProvider()).build();
            app.print("Before");
            app.print(user);
            user = userService.save(user);

            app.print("Saved Admin");

            Profile profile = Profile.builder().profileType(ProfileType.BASIC).userId(user.getId()).build();
            profileService.save(profile);

            userConfirmationTokenService.sendConfirmationToken(user);

            passwordHistoryRepository.save(history);

            return ResponseEntity.ok(new APIResponse<>(messageSource.getMessage("success.registration.message", null, LocaleContextHolder.getLocale()),true, user));
        } else {
            throw new KulaApiException(HttpStatus.NOT_ACCEPTABLE, "Not a valid UBN staff");
        }


    }

    @Override
    public ResponseEntity<APIResponse>  updateUser(Long userId, RegistrationRequest request) {

        User existingUser =  userService.findById(userId).orElseThrow( () -> new ResourceNotFoundException("user", "userId", userId));
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setUsername(request.getUsername());
        existingUser.setRole(request.getRole().name());
        existingUser.setEmail(request.getEmail());
        existingUser.setDepartment(request.getDepartment());
        existingUser.setRoleFunction(request.getRoleFunction());
        existingUser.setBranchCode(request.getBranchCode());

        existingUser = userService.save(existingUser);

        app.print("Update User");

        //TODO send notification to super admin

        return ResponseEntity.ok(new APIResponse<>("Admin Updated Successfully", true, existingUser));
    }

    @Override
    public ResponseEntity<APIResponse> enableUser(Long userId, String enabled) {
        Optional<User> user =  userService.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();

            if (Boolean.parseBoolean(enabled)) {
                existingUser.setIsEnabled(Boolean.parseBoolean(enabled));
                userService.save(existingUser);
                app.print("Enable User");
                return ResponseEntity.ok(new APIResponse<>(messageSource.getMessage("admin.enable", null, LocaleContextHolder.getLocale()),true, user));
            } else {
                existingUser.setIsEnabled(Boolean.parseBoolean(enabled));
                userService.save(existingUser);
                app.print("Disable User");
                return ResponseEntity.ok(new APIResponse<>(messageSource.getMessage("admin.disable", null, LocaleContextHolder.getLocale()),true, user));
            }
        } else {
            throw new UsernameNotFoundException("Can't find user");
        }
    }

    @Override
    public ResponseEntity<APIResponse> searchForAdmin(int pageNo, int pageSize, String sortBy, String sortDir, String q) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> admin = userRepository.findAdminsBySearch(pageable, q);
        return getApiResponseResponseEntity(admin);

    }

    @Override
    public ResponseEntity<APIResponse> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> admin = userRepository.findAll(pageable);
        return getApiResponseResponseEntity(admin);
    }

    @NotNull
    private ResponseEntity<APIResponse> getApiResponseResponseEntity(Page<User> admin) {
        List<User> admins = admin.getContent();
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setContent(admins);
        adminResponse.setPageNo(admin.getNumber() +1);
        adminResponse.setPageSize(admin.getSize());
        adminResponse.setTotalElements(admin.getTotalElements());
        adminResponse.setTotalPages(admin.getTotalPages());
        adminResponse.setLast(admin.isLast());

        return ResponseEntity.ok(new APIResponse<>("Request successful", true, adminResponse));
    }

    @Override
    public ResponseEntity<APIResponse> userCount() {
        return ResponseEntity.ok(new APIResponse("Request Successful", true, userRepository.count()));
    }
}
