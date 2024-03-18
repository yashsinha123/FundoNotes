package org.bridgelabz.fundoo.user.controller;

import org.bridgelabz.fundoo.user.dto.UserDto;
import org.bridgelabz.fundoo.response.Response;
import org.bridgelabz.fundoo.user.model.UserModel;
import org.bridgelabz.fundoo.user.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api")

public class UserController {
    @Autowired
    private UserServiceInterface service;
    @PostMapping("/User")
   public Mono<ResponseEntity<Mono<Response>>> RegisterUser(@RequestBody UserDto userDto)
    {
        Mono<Response> response = service.SaveUser(userDto);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }
    @GetMapping("/User")
    public Mono<ResponseEntity<Flux<UserModel>>> GetAllUser()
    {
        return service.GetUsers()
                .collectList()
                .map(users-> new ResponseEntity<>(Flux.fromIterable(users),HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/User/{id}")
    public Mono<ResponseEntity<UserModel>> GetUserbById(@PathVariable  int id)
    {
        return service.GetUserbyID(id)
                .map(userbyId-> new ResponseEntity<>(userbyId,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @DeleteMapping("/User/{id}")
    public Mono<ResponseEntity<Response>> DeleteById(@PathVariable int id ){
        return service.DeleteUserByid(id)
                .map(deleteuser -> new ResponseEntity<>(new Response(204,"deleted succesfully"),HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(new Response(404,"User not found"),HttpStatus.NOT_FOUND));

    }
    @GetMapping("/login")
    public Mono<Response> LoginByEmailAndPassword(@RequestBody UserDto userDto)
    {
        return  service.loginService(userDto);
    }
    @PostMapping ("/verifytoken/{token}")
    public Mono<ResponseEntity<Mono<Response>>> verifyToken(@PathVariable String token) throws UnsupportedEncodingException {
        Mono<Response> response = service.verifyUser(token);

                return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }
}
