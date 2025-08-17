package com.example.wini.domain.note.repository;

import com.example.wini.domain.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>, NoteCustomRepository {}
