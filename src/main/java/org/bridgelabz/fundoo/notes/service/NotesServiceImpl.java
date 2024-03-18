package org.bridgelabz.fundoo.notes.service;

import org.bridgelabz.fundoo.notes.dto.NoteDto;
import org.bridgelabz.fundoo.notes.model.Note;
import org.bridgelabz.fundoo.notes.repo.NotesRepo;
import org.bridgelabz.fundoo.response.Response;
import org.bridgelabz.fundoo.util.UserToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NotesServiceImpl implements  NotesService {
    @Autowired
    NotesRepo notesRepo;

    @Override
    public Mono<ResponseEntity<Response>> saveNote(NoteDto noteDto, String token) {
        int id = UserToken.tokenVerify(token);
        ModelMapper modelMapper = new ModelMapper();
        Note noteDAO = modelMapper.map(noteDto, Note.class);
        noteDAO.setId(id);

        return this.notesRepo.save(noteDAO)
                .flatMap(savedNote -> Mono.just(new ResponseEntity<>(new Response(200, "Note saved successfully"), HttpStatus.OK)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<Response>> findByUserIdAndArchivedFalse(String token) {
        int userid = UserToken.tokenVerify(token);
        return notesRepo.findByIdAndArchivedFalse(userid)
                .collectList()
                .flatMap(notesList -> {
                    if (notesList.isEmpty()) {
                        // Return 404 Not Found if no notes are found
                        return Mono.error(new RuntimeException("USER NOT EXISTS OR ALL NOTES ARE EMPTY"));
                    } else {
                        // Return 200 OK with the retrieved notes
                        String response = notesList.toString();
                        return Mono.just(new ResponseEntity<>(new Response(200, "notes that are not archived retrieved here", response), HttpStatus.OK));

                    }
                });

    }

    @Override
    public Mono<ResponseEntity<Response>> findByUserIdAndArchivedTrue(String token) {
        int id = UserToken.tokenVerify(token);
        return notesRepo.findByIdAndAndArchivedTrue(id)
                .collectList()
                .flatMap(notesList -> {
                    if (notesList.isEmpty()) {
                        // Return 404 Not Found if no notes are found
                        return Mono.error(new RuntimeException("USER NOT EXISTS OR ALL NOTES ARE EMPTY"));
                    } else {
                        // Return 200 OK with the retrieved notes
                        String response = notesList.toString();
                        return Mono.just(new ResponseEntity<>(new Response(200, "notes that are  archived retrieved here", response), HttpStatus.OK));

                    }
                });

    }

    @Override
    public Mono<ResponseEntity<Response>> pinNoteById(String token) {
        int id = UserToken.tokenVerify(token);
        return notesRepo.findByIdAndPinnedTrue(id)
                .collectList()
                .flatMap(notesList -> {
                    if (notesList.isEmpty()) {
                        // Return 404 Not Found if no notes are found
                        return Mono.error(new RuntimeException("USER NOT EXISTS OR ALL NOTES ARE EMPTY"));
                    } else {
                        // Return 200 OK with the retrieved notes
                        String response = notesList.toString();
                        return Mono.just(new ResponseEntity<>(new Response(200, "notes that are pinned retrieved here", response), HttpStatus.OK));

                    }
                });

    }

    @Override
    public Mono<ResponseEntity<Response>> unpinNoteById(String token) {
        int id = UserToken.tokenVerify(token);
        return notesRepo.findByIdAndPinnedFalse(id)
                .collectList()
                .flatMap(notesList -> {
                    if (notesList.isEmpty()) {
                        // Return 404 Not Found if no notes are found
                        return Mono.error(new RuntimeException("USER NOT EXISTS OR ALL NOTES ARE EMPTY"));
                    } else {
                        // Return 200 OK with the retrieved notes
                        String response = notesList.toString();
                        return Mono.just(new ResponseEntity<>(new Response(200, "notes that are unpinned retrieved here", response), HttpStatus.OK));

                    }
                });

    }

    @Override
    public Mono<ResponseEntity<Response>> getNoteById(String token, int Noteid) {
        int userId = UserToken.tokenVerify(token);
        return notesRepo.findByIdAndNoteId(userId, Noteid)
                .collectList()
                .map(note -> (new ResponseEntity<>(new Response(200, "Note found", note), HttpStatus.OK)))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<Response>> updateNoteById(int NoteId, NoteDto noteDto) {
        return notesRepo.findById(NoteId)
                .flatMap(existingNote -> {
                    // Update existing note properties with data from NoteDto
                    existingNote.setTitle(noteDto.getTitle());
                    existingNote.setNote(noteDto.getNote());
                    existingNote.setArchived(noteDto.isArchived());
                    existingNote.setPinned(noteDto.isPinned());
                    existingNote.setTrash(noteDto.isPinned());
                    // Save the updated note
                    return notesRepo.save(existingNote)
                            .map(updatedNote -> new ResponseEntity<>(new Response(200, "Note updated successfully", updatedNote), HttpStatus.OK));
                })
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<Response>> deleteNoteById(int noteId, String token) {
        int userid = UserToken.tokenVerify(token);
        return notesRepo.findByIdAndNoteId(userid, noteId)
                .next()
                .flatMap(existingNote -> notesRepo.deleteById(noteId)
                        .then(Mono.just(new ResponseEntity<>(new Response(200, "Note deleted successfully"), HttpStatus.OK))))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

}
