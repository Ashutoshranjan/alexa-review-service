package com.alexa.review.configuration;

import java.io.*;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import com.mongodb.client.MongoClients;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mongodb.MongoWriteException;
import org.apache.commons.io.IOUtils;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.StringRefAddr;

public class Mongo {

    @Value("${connection.string}")
    private String connectionString;

    public void connectMongoDb() throws IOException {

        com.mongodb.client.MongoClient client = MongoClients.create(connectionString);

        MongoDatabase database = client.getDatabase("reviewsdb");
        MongoCollection<org.bson.Document> coll = database.getCollection("reviewdetails");

        try {

            //drop previous import
            coll.drop();

            //Bulk Approach:
            int count = 0;
            int batch = 100;
            List<InsertOneModel<Document>> docs = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("/resources/alexa.json"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    docs.add(new InsertOneModel<>(Document.parse(line)));
                    count++;
                    if (count == batch) {
                        coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
                        docs.clear();
                        count = 0;
                    }
                }
            }

            if (count > 0) {
                BulkWriteResult bulkWriteResult=  coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
                System.out.println("Inserted" + bulkWriteResult);
            }

        } catch (MongoWriteException e) {
            System.out.println("Error");
        }

    }
}