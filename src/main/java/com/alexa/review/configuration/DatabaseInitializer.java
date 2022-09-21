package com.alexa.review.configuration;

import com.alexa.review.entities.Review;
import com.alexa.review.repositories.ReviewRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;


//@Component
public class DatabaseInitializer {
  /*  private Logger LOG = Logger.getLogger("DatabaseInitializer.class");

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void init() throws IOException {
        if (reviewRepository.findAll().isEmpty()) {
            LOG.info("Starting database and inserting data from json file...");
            //reviewRepository.insert(getReviews());
            getReviews();
            LOG.info(" Reviews inserted into the database.");
        }
    }

    public void getReviews() throws IOException {
        //List<Review> list = new ArrayList<>(0);
        Mongo mongo = new Mongo();
        mongo.connectMongoDb();
        


       list.add(Review.builder().productId("M20324").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("M20324").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("M20324").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("AC7836").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("AC7836").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("B42000").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("B42000").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("B42000").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("B42000").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("BB5476").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());
        list.add(Review.builder().productId("C77154").title("title").description("desc")
                .score(getRandomNumberBetween(0, 5)).build());

    //    return list;
    }

    public Integer getRandomNumberBetween(int minimum, int maximum) {
        Random rand = new Random();
        return minimum + rand.nextInt((maximum - minimum) + 1);
    }
    
    public String importTo(String collection, List<String> jsonLines) {
        List<Document> mongoDocs = generateMongoDocs(jsonLines);
        int inserts = insertInto(collection, mongoDocs);
        return inserts + "/" + jsonLines.size();
    }

    private int insertInto(String collection, List<Document> mongoDocs) {
        try {
            Collection<Document> inserts = mongo.insert(mongoDocs, collection);
            return inserts.size();
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof MongoBulkWriteException) {
                return ((MongoBulkWriteException) e.getCause())
                        .getWriteResult()
                        .getInsertedCount();
            }
            return 0;
        }
    }

    private List<Document> generateMongoDocs(List<String> jsonLines) {
    }*/
}
