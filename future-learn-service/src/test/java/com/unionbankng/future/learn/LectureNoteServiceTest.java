package com.unionbankng.future.learn;

import com.unionbankng.future.learn.entities.LectureNote;
import com.unionbankng.future.learn.services.LectureNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LectureNoteServiceTest extends AbstractTest{

    @Autowired
    LectureNoteService lectureNoteService;

    @Test
    public void createLectureNoteTest() {

        LectureNote lectureNote = new LectureNote();
        lectureNote.setCourseId(1l);
        lectureNote.setDuration("00:20");
        lectureNote.setLectureId(1l);
        lectureNote.setNoteText("What are you talking about");
        lectureNote.setUserUUID("12333-444555-uuiiee9");


        lectureNoteService.save(lectureNote);


    }

    @Test
    public void findAllByCourseIdAndUserUUIDTest() {

        LectureNote lectureNote = new LectureNote();
        lectureNote.setCourseId(3l);
        lectureNote.setDuration("00:20");
        lectureNote.setLectureId(1l);
        lectureNote.setNoteText("What are you talking about");
        lectureNote.setUserUUID("12333-444555-uuiiee9");


        lectureNoteService.save(lectureNote);

        List<LectureNote> lectureNotes = lectureNoteService.findAllByCourseIdAndUserUUID(3l, "12333-444555-uuiiee9");


        assertEquals(1,lectureNotes.size());
    }

    @Test
    public void findAllByLectureIdAndUserUUID() {

        LectureNote lectureNote = new LectureNote();
        lectureNote.setCourseId(3l);
        lectureNote.setDuration("00:20");
        lectureNote.setLectureId(1l);
        lectureNote.setNoteText("What are you talking about");
        lectureNote.setUserUUID("12333-444555-uuiiee9");


        lectureNoteService.save(lectureNote);

        List<LectureNote> lectureNotes = lectureNoteService.findAllByLectureIdAndUserUUID(1l, "12333-444555-uuiiee9");


        assertTrue(lectureNotes.size() > 0);
    }
}
