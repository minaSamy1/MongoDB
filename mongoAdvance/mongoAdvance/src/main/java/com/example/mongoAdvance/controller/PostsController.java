package com.example.mongoAdvance.controller;


import com.example.mongoAdvance.entity.Posts;
import com.example.mongoAdvance.entity.ProfileResult;
import com.example.mongoAdvance.repository.PostsRepo;
import org.bson.io.BsonOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;


@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsRepo repo;


    @Autowired
    private MongoTemplate template;

    @GetMapping("")
    public List<Posts> getAll() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return repo.findAll();
    }

    @GetMapping("/{exper}")
    public List<Posts> getByExperenice(@PathVariable int exper) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>Using MongoTemplate >>>>>>>>>>>>>>>>>>>>>>>>");
        Query query = new Query();
        Criteria criteria = Criteria.where("totalExper").gt(exper);
        query.addCriteria(criteria);
        return template.find(query, Posts.class);

//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        return  repo.getPostsByTotalExper(exper);
    }

    @GetMapping("/searchByProfile")
    public List<Posts> getByProfile(@RequestParam String profile) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>Using MongoTemplate >>>>>>>>>>>>>>>>>>>>>>>>");
        Query query = new Query();

        Criteria criteria = Criteria.where("profile").is(profile);
        query.addCriteria(criteria);
        return template.find(query, Posts.class);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        return  repo.searchByprofile(profile);
    }



    @GetMapping("/searchByProfileOrExper")
    public List<Posts> getByProfilOrExper(@RequestParam String profile, @RequestParam int exper) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>Using MongoTemplate >>>>>>>>>>>>>>>>>>>>>>>>");
        Query query = new Query();

        Criteria criteria = Criteria.where("profile").is(profile);
        Criteria criteria1 = criteria.where("totalExper").is(exper);
        query.addCriteria(new Criteria().orOperator(criteria, criteria1));
        return template.find(query, Posts.class);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        return repo.searchByProfileOrTotalExper(profile, exper);
    }


    @GetMapping("/searchByProfileINValues")
    public List<Posts> searchByProfileValues(@RequestParam String profile, @RequestParam String profile2) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>Using MongoTemplate >>>>>>>>>>>>>>>>>>>>>>>>");
        Query query = new Query();
        Criteria criteri = Criteria.where("profile").in(profile, profile2);
        query.addCriteria(criteri);

        return template.find(query, Posts.class);

//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        return repo.searchByProfileValue(profile, profile2);
    }


    @GetMapping("/sort")
    public List<Posts> findPostsSortedByTotalExper() {
        System.out.println(" >>>>>>>>>>>>>>>>>>>> MongoTemplate>>>>>>");


        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.by(Sort.Order.desc("totalExper"))),
                Aggregation.limit(3));
        AggregationResults aggregationResults = template.aggregate(aggregation, "Posts", Posts.class);
        return aggregationResults.getMappedResults();


        ///  System.out.println(">>>>>>>>>>>>>>>>>>>>>> @aggregation");
        /// return repo.findPostsSortedByTotalExper() ;


    }

    @GetMapping("/techMatching")
    public List<Posts> getPostsByTech(@RequestParam String tech) {

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("tech").is(tech)));
        AggregationResults aggregationResults = template.aggregate(aggregation, "Posts", Posts.class);
        return aggregationResults.getMappedResults();


        // return repo.getPostByTech(tech);
    }

    @GetMapping("/techAndExperRang")
    public List<Posts> getPostsPerTechAndExperRang(@RequestParam String tech) {
        System.out.println(" >>>>>>>>>>>>>>>>>>> ");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where("totalExper").gt(5).lt(10)
                                .and("tech").is(tech))
        );

        AggregationResults aggregationResults = template.aggregate(aggregation, "Posts", Posts.class);
        return aggregationResults.getMappedResults();
        //System.out.println(">>>>>>>>>>>>>>>>>>>>> @Aggregation >>>>>>>>>>>>");
        //return repo.getPostsPerTechAndExperienceRang(tech);
    }


    @GetMapping("/groupProfileSumExper")
    public List<ProfileResult> getMaxExperPerProfile() {

        System.out.println(">>>>>>>>>>>> Template>>>>>>>>>>>>>>>>>");
        // here  get all posts per profile where the totalExper >10  and get sum , min , max of totalExper , Finally get array of Technical
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("totalExper").gt(10)),
                Aggregation.group("profile").
                        sum("totalExper").as("SumExper").
                        max("totalExper").as("Max").
                        min("totalExper").as("Min").
                        addToSet("tech").as("technical")

        );
        AggregationResults aggregationResults = template.aggregate(aggregation, "Posts", ProfileResult.class);

        return aggregationResults.getMappedResults();
        // System.out.println(" >>>>>>>>>>>>>>>>>@Aggregation>>>>>>>>>>>>");
        //return repo.getTotalExperPerProfile() ;
    }

    /// ////////////////  CURD Operation by MongoTemplate ////////////////////

    @PutMapping("")
    public void UpdateByProfile(@RequestParam String profile, @RequestParam int totalExper) {


        Criteria criteria = Criteria.where("profile").is(profile);
        Update update = new Update();
        update.set("totalExper", totalExper);
        //  template.updateMulti(Query.query(criteria),update,Posts.class);
        template.updateFirst(Query.query(criteria),update,Posts.class);
        
//        List<Posts> p = repo.searchByprofile(profile);
//        System.out.println( " Find Documents "+p.size());
//        for (int i = 0; i < p.size(); i++) {
//
//            Posts current = p.get(i);
//            System.out.println(" Reading " + current.getProfile() + "  ");
//            System.out.println(" Reading " + current.getTotalExper() + "  ");
//            System.out.println(" Reading " + current.getTech().toString() + "  ");
//            current.setTotalExper(totalExper);
//            repo.save(current);
//
//        }


    }
}
