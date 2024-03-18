package org.bridgelabz.fundoo.label.service;


import org.bridgelabz.fundoo.label.dto.LabelDto;
import org.bridgelabz.fundoo.label.model.LabelModel;
import org.bridgelabz.fundoo.label.repo.LabelRepository;
import org.bridgelabz.fundoo.response.Response;
import org.bridgelabz.fundoo.util.UserToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@org.springframework.stereotype.Service
public class Service {
    @Autowired
    LabelRepository labelRepository;

    public Mono<ResponseEntity<Response>> createLabel(LabelDto label, int noteId, String token) {
        int userid = UserToken.tokenVerify(token);

        ModelMapper modelMapper = new ModelMapper();
        LabelModel labelDAO = modelMapper.map(label, LabelModel.class);
        labelDAO.setUserId(userid);
        labelDAO.setNoteId(noteId);
        return labelRepository.save(labelDAO).map(createdLabel -> new ResponseEntity<>(new Response(200, "Label created successfully", createdLabel), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

    }

    public Mono<ResponseEntity<? extends Object>> getAllLabels(String token) {
        int userid = UserToken.tokenVerify(token);
       return labelRepository.findAllByUserId(userid)
               .collectList()
               .flatMap(labelList -> {
                   if (!labelList.isEmpty()) {
                       return Mono.just(new ResponseEntity<>(Flux.fromIterable(labelList), HttpStatus.OK));
                   } else {
                       return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                   }
               });
    }

    public Mono<ResponseEntity<Object>> getLabelByNoteId(int noteId) {
        return labelRepository.findByNoteId(noteId)
                .collectList()
                .flatMap(labelList -> {
                    if (!labelList.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(labelList, HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                });

    }

    public Mono<ResponseEntity<Response>> updateLabel(int id, LabelModel label) {
        return labelRepository.findById((long) id)
                .flatMap(existingLabel -> {
                    existingLabel.setName(label.getName());
                    existingLabel.setUserId(label.getUserId());
                    return labelRepository.save(existingLabel) .map(updatedLabel -> new ResponseEntity<>(new Response(200, "Label updated successfully", updatedLabel), HttpStatus.OK))
                            .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Label not found"), HttpStatus.NOT_FOUND))
                            .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

                });
    }

    public Mono<ResponseEntity<Response>> deleteLabel(int id) {
        return labelRepository.deleteById((long) id) .then(Mono.just(new ResponseEntity<>(new Response(204, "Label deleted successfully"), HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Label not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
