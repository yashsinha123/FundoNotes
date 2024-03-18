package org.bridgelabz.fundoo.notes.service;

import org.bridgelabz.fundoo.notes.dto.NoteDto;
import org.bridgelabz.fundoo.notes.model.Note;
import org.bridgelabz.fundoo.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public interface NotesService {
    Mono<ResponseEntity<Response>> saveNote(NoteDto noteDto, String token);
    Mono<ResponseEntity<Response>> findByUserIdAndArchivedFalse(String token);
    Mono<ResponseEntity<Response>> findByUserIdAndArchivedTrue(String token);
    Mono<ResponseEntity<Response>> pinNoteById(String token);
    Mono<ResponseEntity<Response>> unpinNoteById(String token);

    Mono<ResponseEntity<Response>> getNoteById(String token, int noteId);

    Mono<ResponseEntity<Response>> updateNoteById(int NoteId, NoteDto noteDto);
    Mono<ResponseEntity<Response>> deleteNoteById(int noteId, String Token);

}
