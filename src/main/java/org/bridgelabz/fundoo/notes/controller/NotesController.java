package org.bridgelabz.fundoo.notes.controller;

import org.bridgelabz.fundoo.notes.dto.NoteDto;
import org.bridgelabz.fundoo.notes.service.NotesServiceImpl;
import org.bridgelabz.fundoo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notes")

public class NotesController {
    @Autowired
    NotesServiceImpl notesService;
    @PostMapping("/add/{token}")
    public Mono<ResponseEntity<Response>> addNotes(@RequestBody NoteDto noteDto, @PathVariable  String token) {
        return notesService.saveNote(noteDto,token);
    }
    @GetMapping("/Active/{token}")
    public Mono<ResponseEntity<Response>> findbyUseridandArchivedFalse(@PathVariable String token){
        return  notesService.findByUserIdAndArchivedFalse(token);
    }
    @GetMapping("/Archived/{token}")
    public Mono<ResponseEntity<Response>> findbyUseridandArchivedTrue(@PathVariable String token){
        return  notesService.findByUserIdAndArchivedTrue(token);
    }
    @GetMapping("/pin/{token}")
    Mono<ResponseEntity<Response>> pinNoteById(@PathVariable String token) {
        return notesService.pinNoteById(token);
    }

    @GetMapping("/unpin/{token}")
    public Mono<ResponseEntity<Response>> unpinNoteById(@PathVariable String token) {
        return notesService.unpinNoteById(token);
    }

    @GetMapping("/{token}/{noteid}")
    public Mono<ResponseEntity<Response>> getNoteById(@PathVariable String token,@PathVariable int noteid ) {
        return notesService.getNoteById(token,noteid);
    }
    @PutMapping("/{noteid}")
    public Mono<ResponseEntity<Response>> updateNoteById(@PathVariable int noteid, @RequestBody NoteDto noteDto) {
        return notesService.updateNoteById(noteid,noteDto);
    }

    @DeleteMapping("/{noteid}/{token}")
    public Mono<ResponseEntity<Response>> deleteNoteById(@PathVariable int noteid, @PathVariable String token) {
        return notesService.deleteNoteById(noteid,token);
    }

}
