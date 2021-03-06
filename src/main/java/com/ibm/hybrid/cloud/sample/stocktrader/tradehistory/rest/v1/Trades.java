package com.ibm.hybrid.cloud.sample.stocktrader.tradehistory.rest.v1;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.mongodb.MongoClient;
import com.ibm.json.java.JSONObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import com.ibm.hybrid.cloud.sample.stocktrader.tradehistory.mongo.MongoConnector;

@Path("/v1")
public class Trades {

    @Path("/example")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response example() {
        List<String> list = new ArrayList<>();
        //return a simple list of strings
        list.add("Congratulations, your application is up and running");
        return Response.ok(list.toString()).build();
    }

    //com.ibm.hybrid.cloud.sample.stocktrader.tradehistory
    //{ "owner":"John", "symbol":"IBM", "shares":3, "price":120, "when":"now", "comission":0  } 
    // URL: /tradeHistory/com.ibm.hybrid.cloud.sample.stocktrader.tradehistory.rest/v1/trade
    @Path("/trade")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getTrades() {
        //TODO: com.ibm.hybrid.cloud.sample.stocktrader.tradehistory.rest call to get data from Mongo API
        JSONObject json = new JSONObject();
        json.put("id", "testObject");
        return json;
    }

    @Path("/latestBuy")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject latestBuy() {
        MongoConnector mConnector = new MongoConnector();
        MongoClient mClient = mConnector.mongoClient;
        //TODO: com.ibm.hybrid.cloud.sample.stocktrader.tradehistory.rest call to get data from Mongo API
        JSONObject json = new JSONObject();
        long dbSize = mClient.getDatabase("test").getCollection("test_collection").count();
        int approxDbSize = Math.toIntExact(dbSize);

        FindIterable<Document> docs = mClient.getDatabase("test").getCollection("test_collection").find().skip(approxDbSize - 1);
        for (Document doc : docs) {
            json.put("trade", doc.toJson());
        }

        return json;
    }
}
