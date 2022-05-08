package ua.com.foxminded.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.error.ErrorDescriptor;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.service.interfaces.TeacherService;

@RestController
public class TeachersRestController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    TeacherService teacherService;

    Logger logger = LoggerFactory.getLogger("SampleLogger");

    @GetMapping(value = "/teachers", 
            produces = { "application/json" })
    public ResponseEntity<List<TeacherDto>> findAllTeachers() {

        List<TeacherDto> teachers = teacherService.findAllTeacher();

        return teachers != null && !teachers.isEmpty()
                ? new ResponseEntity<>(teachers, HttpStatus.OK)
                : new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not found Teacher "),
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/teacher/{uuid}", 
            produces = { "application/json" })
    public ResponseEntity<TeacherDto> showTeacher(@PathVariable("uuid") String uuid) {

        TeacherDto teacher = teacherService.findTeacher(UUID.fromString(uuid));
        
        return (teacher != null && teacher.getIdPerson() != null)
                ? new ResponseEntity<>(teacher, HttpStatus.OK)
                        : new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not found Teacher "),
                                HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/teacher",
            produces = {"application/json"},
            consumes = {"application/json"})
    public ResponseEntity createTeacher(@Valid @RequestBody TeacherDto teacher,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ErrorDescriptor(bindingResult, "teacher"), HttpStatus.BAD_REQUEST) ;
        }
        
       if (teacherService.addTeacher(teacher)) {
           return new ResponseEntity<>(HttpStatus.CREATED);
       }

       return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not create Teacher "),
               HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/teacher/{uuid}" ,
            produces = {"application/json"},
            consumes = {"application/json"})  
    public ResponseEntity editTeacher(@PathVariable(name = "uuid") String uuid,
            @Valid @RequestBody TeacherDto teacher,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ErrorDescriptor(bindingResult, "teacher"), HttpStatus.BAD_REQUEST) ;
        }
        
        TeacherDto teacherCheck = teacherService.findTeacher(UUID.fromString(uuid));
        if (teacherCheck.getIdPerson()==null) {
            return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find teacher with id  " + uuid),
                    HttpStatus.NOT_FOUND);
        }
        teacherService.editTeacher(teacher, UUID.fromString(uuid));
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/teacher/{uuid}", 
            produces = {"application/json"})
    public ResponseEntity deleteTeacher(@PathVariable("uuid") String uuid) {
        
        TeacherDto teacherCheck = teacherService.findTeacher(UUID.fromString(uuid));
        if (teacherCheck.getIdPerson()==null) {
            return  new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find teacher with id  " + uuid),
                    HttpStatus.NOT_FOUND);
        }
        
        teacherService.deleteTeacher(UUID.fromString(uuid));

         teacherCheck = teacherService.findTeacher(UUID.fromString(uuid));
        if (teacherCheck.getIdPerson()!=null) {
            return  new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_IMPLEMENTED).setMessage("Do not delete with id  " + uuid),
                    HttpStatus.NOT_IMPLEMENTED);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
