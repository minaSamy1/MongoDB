package com.example.mongoAdvance.repository;

import com.example.mongoAdvance.entity.Posts;
import com.example.mongoAdvance.entity.ProfileResult;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepo extends MongoRepository<Posts, String> {
    @Query("{ totalExper :{$gt:?0}} ")    //    db.Posts.find( { totalExper :{$gt:9}} )
    public List<Posts> getPostsByTotalExper(int exper);
//    @Query("{ totalExper :{$gt: :exper}} ")    //    db.Posts.find( { totalExper :{$gt:9}} )
//    public List<Posts> getPostsByTotalExper(@Param("exper") int exper);

    @Query("{ profile:?0}")
    ///  db.Posts.find( { profile:"java"})
    public List<Posts> searchByprofile(String profileName);

    @Query("  { $or: [ {profile:?0} , { totalExper:?1 }  ]   }")
    // db.posts.find (   { $or: [  {profile: "java"} , { totalExper:10} ]  }   )
    public List<Posts> searchByProfileOrTotalExper(String profile, int exper);

    @Query(" { profile : {   '$in' :[ ?0 , ?1 ]  }  } ")
    //db.posts.find(   profile :  {   $in : [ "java", " Mobile developer" ]  }  } )
    public List<Posts> searchByProfileValue(String profile1, String profile2);


    //------------------------------ Aggregation -----------------------------------------

    // @Aggregation(pipeline = {   "{ $sort: { totalExper :-1 }"})
    @Aggregation(pipeline = {
            "{ $sort: { totalExper: -1 } }",
            "{ $limit : 3 }"
    })        // db.Posts.aggregate( [     {  $sort:{  totalExper:-1} }  , { $limit:3}  ])

    public List<Posts> findPostsSortedByTotalExper();


    @Aggregation(pipeline = {
            "{ $match : { tech :?0}}"
    })
    // db.Posts.aggregate( [{ $match : {  tech: "aws" }    }] )
    public List<Posts> getPostByTech(String tech);


    @Aggregation(pipeline = {
            "{ $match : { 'totalExper' : { $gte: 5, $lt: 18 }, 'tech' : ?0 } }"
    })      // db.posts.aggregate(  [{ $match : { totalExper :{  $gte : 5 , $lt :10 }  , tech :"Node.js" }  } ] )
    public List<Posts> getPostsPerTechAndExperienceRang(String tech);

    // for each Profile give me ( Sum >> of TotalExper , Min Exper , Max Exper  , array of Technical skills
    @Aggregation(pipeline = {
            "{ $group : {  _id : '$profile' ," +
                    " SumExper:  {$sum : '$totalExper'} " +
                    ", Max:{ $max : $totalExper'} ," +
                    " min: { $min :'$totalExper'} ,  " +
                    "technical : { $addToSet : '$tech'} } }"

    })
    public List<ProfileResult> getTotalExperPerProfile();


}
