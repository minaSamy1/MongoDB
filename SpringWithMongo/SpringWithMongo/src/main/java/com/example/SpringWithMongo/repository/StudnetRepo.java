package com.example.SpringWithMongo.repository;

import com.example.SpringWithMongo.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudnetRepo extends MongoRepository<Student,Integer> {
}
