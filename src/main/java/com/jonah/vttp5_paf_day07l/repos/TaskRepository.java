package com.jonah.vttp5_paf_day07l.repos;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class TaskRepository {
    @Autowired
    private MongoTemplate template;

/*     db.tasks.insert({
        name: 'Jogging',
        priority: 1
        to_bring: ['water', 'headphone'
    })
 */

 public void insertTask(){
    Document toInsert = new Document();
    toInsert.put("name", "Jogging");
    toInsert.put("priority",1);
    List<String> toBring = new ArrayList<>();
    toBring.add("water");
    toBring.add("snack");

    toInsert.put("to_bring", toBring);

    System.out.println("to insert " + toInsert.toJson());
    Document insertedDoc = template.insert(toInsert, "tasks");
    ObjectId id = insertedDoc.getObjectId("_id"); //this gets the primary key id of what you input
    System.out.println("the object id is " + id.toHexString());


    
 }


 public void insertTask2(){
    JsonArray jogWith = Json.createArrayBuilder()
    .add("fred")
    .add("banee")
    .build();

    JsonObject toInsert = Json.createObjectBuilder()
    .add("_id", UUID.randomUUID().toString().substring(0,8))
    .add("name", "CNY SHOPPING")
    .add("friends", jogWith)
    .add("venue", "starlightown")
    .add("priority",1)
    .build();

    String jsonStr = toInsert.toString(); //must convert the jsonObject to string, then use document.parse to make it a Document.

    Document docToInsert = Document.parse(jsonStr);

    Document result = template.insert(docToInsert, "tasks");

    System.out.println("the result is: " + result + "\n the id is" + result.getString("_id"));


    //to make a Document back to jsonObject
    String resultString = result.toJson();
    JsonReader r = Json.createReader(new StringReader(resultString));
    JsonObject jsonResult = r.readObject();


 }


 public void update() { 
    String id = "6790697c85832621c78105f8";

    Criteria criteria = Criteria.where("_id").is(id);
    Query query = Query.query(criteria);
    Update updateOps = new Update().set("time", "2pm").push("friends", "betty");

    UpdateResult result = template.updateFirst(query, updateOps, Document.class, "tasks");

    System.out.println("Matched " + result.getMatchedCount());
    System.out.println("modified " + result.getModifiedCount());
    System.out.println("Upsert id " + result.getUpsertedId());
 }


 public void searchComments(String... terms){
    TextCriteria criterial = TextCriteria.forDefaultLanguage()
    .matchingAny(terms)
    .caseSensitive(false);

    TextQuery query = (TextQuery)TextQuery.queryText(criterial)
    .includeScore("similarity_score")
    .sortByScore()
    .limit(5);



    template.find(query, Document.class, "comments")
    .stream()
    .forEach(d -> {System.out.println("THE FINDING IS" + d.toJson());
    });

 }


}
