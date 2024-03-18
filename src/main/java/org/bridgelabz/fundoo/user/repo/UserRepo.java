package org.bridgelabz.fundoo.user.repo;


import org.bridgelabz.fundoo.user.model.UserModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface UserRepo extends R2dbcRepository<UserModel,Integer> {

    Flux<Object> findByEmail(String email);

    Flux<Object> existsByEmail(String email);
}
