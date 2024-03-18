package org.bridgelabz.fundoo.label.controller;

import org.bridgelabz.fundoo.label.dto.LabelDto;
import org.bridgelabz.fundoo.label.model.LabelModel;
import org.bridgelabz.fundoo.label.service.Service;
import org.bridgelabz.fundoo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/labels")

public class Controller {
    @Autowired
    private Service service;
    @PostMapping("/{noteId}/{token}")
    public Mono<ResponseEntity<Response>> createLabel(@RequestBody LabelDto label, @PathVariable int noteId, @PathVariable String token) {
        return service.createLabel(label,noteId,token);
    }

    @GetMapping("/all/{token}")
    public Mono<ResponseEntity<? extends Object>>  getAllLabels(@PathVariable String token) {
        return service.getAllLabels(token);

    }

    @GetMapping("/{noteId}")
    public Mono <ResponseEntity<Object>> getLabelById(@PathVariable int noteId) {
        return service.getLabelByNoteId(noteId);
    }



    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateLabel(@PathVariable int id, @RequestBody LabelModel labelModel) {
        return service.updateLabel(id, labelModel);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deleteLabel(@PathVariable int id) {
        return service.deleteLabel(id);
    }
}


