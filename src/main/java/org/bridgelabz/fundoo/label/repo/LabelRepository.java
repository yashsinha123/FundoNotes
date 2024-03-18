package org.bridgelabz.fundoo.label.repo;


import org.bridgelabz.fundoo.label.model.LabelModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface LabelRepository extends R2dbcRepository<LabelModel, Long> {
    Flux<Object> findAllByUserId(int id);
    Flux<Object> findByNoteId(int id);

}
