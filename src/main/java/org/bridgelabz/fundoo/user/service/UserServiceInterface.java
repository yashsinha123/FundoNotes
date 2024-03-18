package org.bridgelabz.fundoo.user.service;

import org.bridgelabz.fundoo.user.dto.UserDto;
import org.bridgelabz.fundoo.response.Response;
import org.bridgelabz.fundoo.user.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

public interface UserServiceInterface {
    Mono<Response> SaveUser(UserDto userDto);

    Flux<UserModel> GetUsers();

    Mono<UserModel> GetUserbyID(int id);

    Mono<Void> DeleteUserByid(int id);
    Mono<Response> loginService(UserDto userDto);

    Mono<Response> verifyUser(String token) throws UnsupportedEncodingException;
}
