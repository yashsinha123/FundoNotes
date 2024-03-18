package org.bridgelabz.fundoo.notes.repo;

import org.bridgelabz.fundoo.notes.model.Note;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotesRepo extends R2dbcRepository<Note,Integer> {
    Flux<Note> findByIdAndArchivedFalse(int userId);

    Flux<Note>findByIdAndAndArchivedTrue (int userId);

    Flux<Note>findByIdAndPinnedFalse(int userId);

    Flux<Note>findByIdAndPinnedTrue(int userId);

    Flux<Note>findByIdAndNoteId(int userId, int notesId);

}


