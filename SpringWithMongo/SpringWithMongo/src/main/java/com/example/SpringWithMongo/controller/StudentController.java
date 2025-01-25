package com.example.SpringWithMongo.controller;

import com.example.SpringWithMongo.entity.DatabaseSequence;
import com.example.SpringWithMongo.entity.Student;
import com.example.SpringWithMongo.repository.StudnetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private StudnetRepo studnetRepo;

    @PostMapping("")
    public void addStudnet(@RequestBody Student student) {
        System.out.println(" Studnet >>> " + student.toString());
        System.out.println(" Studnet Name>>> " + student.getFirstName());
        System.out.println(" Studnet Address>>> " + student.getAddress());
       student.setRno(generateSequence(Student.SEQUENCE_NAME));

        studnetRepo.save(student);
    }

    @GetMapping("")
    public List<Student> getAllStudents() {


        return studnetRepo.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentsById(@PathVariable int id) {


        return studnetRepo.findById(id).get();
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {


        studnetRepo.deleteById(id);
    }


    @PutMapping()
    public void updateStudent(@RequestBody Student student) {
        Student s = studnetRepo.findById(student.getRno()).orElse(null);
        System.out.println("Update" + s.toString());
        if (s != null) {
            s.setAge(student.getAge());
            s.setFirstName(student.getFirstName());
            s.setAddress(student.getAddress());

            studnetRepo.save(s);
        }
    }


    public Integer generateSequence(String seqName) {

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }
//
//            Student temp= new Student();
//            temp.setAddress(student.getAddress());
//            temp.setFirstName(student.getFirstName());
//            temp.setAge(student.getAge());
//            temp

}
