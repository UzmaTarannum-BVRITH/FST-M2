package liveProject;

import static org.hamcrest.CoreMatchers.*;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GitHubRestAssuredProject {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp() {
        reqSpec=new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();

        sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCKv7ATrWnNgkuIiTGlySiitGjjv/EkMMwLRFM13hkes3w0i55b8I2/4pj/SHfJcanRhRAFuqvClsoZqX9ObJP4oxqqG6vgez2DyPTyWynxhayQZAtLr5kyfJUvlyoZArglASPYaBl2cMP7pn5Fd2zaUFWwtbrYiSM+lbO8qA7F+mWdLMt0N4dDHvOP976fC6HT863paqQZ2vv4EqZ+kyXgRPNsu3845xF+TXiXJdUlG0l834vo7olf8HEquWNHk2beSI8mPzU7qrApwvyTBSq+i/y76UArfq/9GsXzFrJfxXdiaIOLnPnqxY5DXMzwgZy7vOPyJbxbjpN9IQF9am4V";
    }



    @Test(priority=1)
    public void postKey() {
        // Create JSON request
        String reqBody = "{\"title\": \"RestAssuredAPIKey\",  \"key\": \""+sshKey+"\" }";

        Response response=given().spec(reqSpec).body(reqBody).when().post("/user/keys");
        String resBody= response.getBody().asPrettyString();
        System.out.println(resBody);
        System.out.println(response.getStatusCode());
        sshKeyId=response.then().extract().path("id");
        System.out.println(sshKeyId);

        Assert.assertEquals(response.getStatusCode(), 201, "Correct status code is not returned");

    }


    @Test(priority=2)
    public void getKey() {
        Response response =
                given().spec(reqSpec).when()
                        .get("/user/keys");
        System.out.println(response.asPrettyString());


        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");

    }

    @Test(priority=3)
    public void deleteKey() {
        Response response =
                given().spec(reqSpec)// Set headers
                        .when()
                        .delete("/user/keys/"+sshKeyId); // Send DELETE request

        System.out.println(response.asPrettyString());


        Assert.assertEquals(response.getStatusCode(), 204, "Correct status code is not returned");


    }
}
