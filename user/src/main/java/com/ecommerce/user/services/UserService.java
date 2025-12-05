package com.ecommerce.user.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.Role;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.RoleRepository;
import com.ecommerce.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
   // private final KeyCloakAdminService keyCloakAdminService;
//    private List<User> userList = new ArrayList<>();
//    private Long nextId = 1L;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

   public void addUser(UserRequest userRequest){
 //       user.setId(nextId++);
//        String token = keyCloakAdminService.getAdminAccessToken();
//        String keycloakUserId =
//                keyCloakAdminService.createUser(token, userRequest);
//
        User user = new User();
        updateUserFromRequest(user, userRequest);
//        user.setKeycloakId(keycloakUserId);
//
//        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(),
//                "USER", keycloakUserId);
        userRepository.save(user);
   }
   
   public String addUser(OAuth2User principal, String authBy) {
	   User user = new User();
	   try {
		   Map<String, Object> attributes = principal.getAttributes();

		   String fullName = (String) attributes.get("name");
		   if (fullName != null) {
		       String[] parts = fullName.split(" ");
		       user.setFirstName(parts[0]);
		       user.setLastName(parts.length > 1 ? parts[1] : "");
		   }

		   Object idObj = attributes.get("id");
		   if (idObj != null) {
		       user.setKeycloakId(idObj.toString());
		   }

		   Object locationObj = attributes.get("location");
		   user.setLocation(locationObj != null ? locationObj.toString() : null);

		   Object emailObj = attributes.get("email");
		   user.setEmail(emailObj != null ? emailObj.toString() : null);

		   Object loginObj = attributes.get("login");
		   user.setUsername(loginObj != null ? loginObj.toString() : null);

		   user.setAuthBy(authBy);
		   Optional<Role> role = roleRepository.findById((long) 2);
	       user.setRole(role.get());
	       userRepository.save(user);
		   
	   }catch(Exception e) {
		   e.printStackTrace();
		   return "Something wet wrong, user not created !!";
	   }
	   
	return "user created successfully !!";
   }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        // role
        Optional<Role> role = roleRepository.findById((long) 2);
        user.setRole(role.get());
        
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setKeyCloakId(user.getKeycloakId());
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}
