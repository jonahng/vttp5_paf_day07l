package com.jonah.vttp5_paf_day07l.repos;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

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


}
