package ru.alsi.cpsmi.jee.web;

import ru.alsi.cpsmi.jee.model.Human;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Path("/human")
public class HumanSearch {
    private final String FILE_NAME = "users";
    private HashMap<Integer, Human> humanMap;

    public HashMap<Integer, Human> getHumanMap() {
        if(humanMap == null) {
            humanMap = new HashMap<>();
            ClassLoader cl = this.getClass().getClassLoader();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(cl.getResourceAsStream(FILE_NAME)));
                while (true) {
                    String tmp = br.readLine();
                    if (tmp == null) break;
                    String[] humanArr = tmp.split(",");
                    Human human = new Human();
                    human.setId(Integer.parseInt(humanArr[0]));
                    human.setName(humanArr[1]);
                    human.setZipCode(humanArr[2]);
                    human.setCity(humanArr[3]);
                    humanMap.put(human.getId(), human);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return humanMap;
    }


    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchREST(InputStream incomingData) {
        StringBuilder builder = new StringBuilder();
        StringBuilder json = new StringBuilder();
        String separator = "";
        json.append('[');
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println("Request: " + builder.toString());
        String name = builder.toString().split("=")[1];
        if(name != null && !name.isEmpty()) {
            for (Human h : getHumanMap().values()) {
                if(h.getName().contains(name)){
                    json.append(separator);
                    json.append(h.toString());
                    separator = ",";
                }
            }
        }
        json.append(']');
        System.out.println("Response: " + json.toString());

        return Response.status(200).entity(json.toString()).build();
    }



    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    public Response verifyRESTService(InputStream incomingData) {
        StringBuilder result = new StringBuilder();
        result.append("Human search service successfully started..<br>");
        result.append("Humans : ").append(getHumanMap().size()).append("<br>");
        for (Human h : getHumanMap().values()) {
            result.append(h.toString()).append("<br>");
        }
        return Response.status(200).entity(result.toString()).build();
    }
}
