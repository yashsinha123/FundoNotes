package org.bridgelabz.fundoo.user.service;

import org.bridgelabz.fundoo.user.dto.UserDto;
import org.bridgelabz.fundoo.user.exception.LoginException;
import org.bridgelabz.fundoo.response.Response;
import org.bridgelabz.fundoo.user.model.UserModel;
import org.bridgelabz.fundoo.user.repo.UserRepo;
import org.bridgelabz.fundoo.util.UserToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    UserRepo userRepo;
    @Autowired
    Environment environment;

    @Override
    public Mono<Response> SaveUser(UserDto userDto)  {
        return userRepo.findByEmail(userDto.getEmail())
                .collectList()
                .flatMap(existingUsers -> {
                    if (existingUsers.isEmpty()) {
                        // No user with the given email exists
                        ModelMapper modelMapper = new ModelMapper();
                        UserModel newUser = modelMapper.map(userDto, UserModel.class);
                        return userRepo.save(newUser)
                                .map(savedUser -> new Response(200, "User added successfully", savedUser));
                    } else {
                        // User with the given email already exists
                        return  Mono.error(new LoginException("user alreayd exists be unique"));
                    }
                });
        }


    @Override
    public Flux<UserModel> GetUsers() {
        return userRepo.findAll();
    }

    @Override
    public Mono<UserModel> GetUserbyID(int id) {
        return userRepo.findById(id);
    }

    @Override
    public Mono<Void> DeleteUserByid(int id) {
        return userRepo.findById(id)
                .flatMap(user ->{
                    return userRepo.delete(user);
                });

    }

    @Override
    public Mono<Response> loginService(UserDto userDto) {
        return userRepo.findAll()
                .filter(user -> user.getEmail().equals(userDto.getEmail()))
                .next()
                .flatMap(usermodel ->{
                    if(usermodel.getPassword().equals(userDto.getPassword())){
                        Object token = String.valueOf(UserToken.generateToken(usermodel.getId()));
                        String  message = "status.login.succes";
                        int successcode = Integer.parseInt("3");
                        return Mono.just(new Response(200, "user login successfully", token));
                    }
                    else{
                        return Mono.error(new LoginException("login failed please try again"));

                    }
                })
                .switchIfEmpty(Mono.error(new LoginException("user not found")));

    }

    @Override
    public Mono<Response> verifyUser(String token) throws UnsupportedEncodingException {
        int id = UserToken.tokenVerify(token);

        // tokenverify basicaaly gives userid ab use jo krna hai kroo
               return userRepo.findById(id)
                .map(user -> new Response(200, "User verified successfully", id))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}
