student-api-test/
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── example
│   │               └── students
│   │                   └── StudentResource.java
│   ├── test
│   │   └── java
│   │       └── com
│   │           └── example
│   │               └── steps
│   │                   └── StudentStepDefinitions.java
│   ├── resources
│       └── features
│           └── student.feature
├── pom.xml





<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>student-api-test</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- MicroProfile dependencies (for the REST API) -->
        <dependency>
            <groupId>org.eclipse.microprofile</groupId>
            <artifactId>microprofile</artifactId>
            <version>3.3</version>
        </dependency>

        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.7.0</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.7.0</version>
            <scope>test</scope>
        </dependency>

        <!-- Cucumber -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>7.0.0</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>7.0.0</version>
            <scope>test</scope>
        </dependency>

        <!-- RestAssured -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>4.4.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Surefire Plugin for running JUnit and Cucumber tests -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
            </plugin>
        </plugins>
    </build>
</project>




package com.example.students;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("John Smith", "A", 2022));
        students.add(new Student("Jane Doe", "B", 2021));
        students.add(new Student("Sam Black", "A", 2022));
    }

    @GET
    public Response getStudents(@QueryParam("grade") String grade, 
                                @QueryParam("year") int enrollmentYear) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getGrade().equals(grade) && student.getEnrollmentYear() == enrollmentYear) {
                filteredStudents.add(student);
            }
        }
        return Response.ok(filteredStudents).build();
    }
}




package com.example.students;

public class Student {
    private String name;
    private String grade;
    private int enrollmentYear;

    public Student(String name, String grade, int enrollmentYear) {
        this.name = name;
        this.grade = grade;
        this.enrollmentYear = enrollmentYear;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }
}


Feature: Retrieve students by grade and enrollment year

  Scenario Outline: Retrieve students by grade and enrollment year
    Given the student information service is running
    When I retrieve students with grade "<grade>" and enrollment year "<year>"
    Then I should receive a list containing:
      | name       | grade | enrollmentYear |
      | <name1>    | <grade> | <year>        |
      | <name2>    | <grade> | <year>        |

  Examples:
    | grade | year | name1      | name2    |
    | A     | 2022 | John Smith | Sam Black|
    | B     | 2021 | Jane Doe   |          |






    package com.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import java.util.List;
import java.util.Map;

public class StudentStepDefinitions {

    private Response response;
    private final String BASE_URL = "http://localhost:8080/api/students"; // Adjust to your service URL

    @Given("the student information service is running")
    public void the_student_information_service_is_running() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("I retrieve students with grade {string} and enrollment year {string}")
    public void i_retrieve_students_with_grade_and_enrollment_year(String grade, String year) {
        response = RestAssured.given()
                .queryParam("grade", grade)
                .queryParam("year", year)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Then("I should receive a list containing:")
    public void i_should_receive_a_list_containing(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expectedStudents = dataTable.asMaps(String.class, String.class);
        List<Map<String, String>> actualStudents = response.jsonPath().getList("");

        for (Map<String, String> expectedStudent : expectedStudents) {
            Assert.assertTrue("Student should be in the list", 
                actualStudents.contains(expectedStudent));
        }
    }
}





------------crud


student-api-crud/
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── example
│   │               └── students
│   │                   ├── Student.java
│   │                   └── StudentResource.java
│   ├── test
│   │   └── java
│   │       └── com
│   │           └── example
│   │               └── steps
│   │                   └── StudentCrudStepDefinitions.java
│   ├── resources
│       └── features
│           └── student_crud.feature
├── pom.xml





<project xmlns="http://maven.apache.org/POM/4.0.0">
    <!-- Same as above -->
</project>




package com.example.students;

public class Student {
    private String id;
    private String name;
    private String grade;
    private int enrollmentYear;

    public Student(String id, String name, String grade, int enrollmentYear) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.enrollmentYear = enrollmentYear;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }
}




package com.example.students;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("1", "John Smith", "A", 2022));
        students.add(new Student("2", "Jane Doe", "B", 2021));
        students.add(new Student("3", "Sam Black", "A", 2022));
    }

    @GET
    public List<Student> getStudents() {
        return students;
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();

        return student.map(value -> Response.ok(value).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createStudent(Student student) {
        students.add(student);
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") String id, Student student) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                s.setName(student.getName());
                s.setGrade(student.getGrade());
                s.setEnrollmentYear(student.getEnrollmentYear());
                return Response.ok(s).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id) {
        students.removeIf(s -> s.getId().equals(id));
        return Response.noContent().build();
    }
}








Feature: CRUD operations on student resource

  Scenario: Create a new student
    Given the student information service is running
    When I create a new student with ID "4", name "Alice Blue", grade "C", and enrollment year 2023
    Then the student with ID "4" should be created

  Scenario: Retrieve an existing student
    Given the student information service is running
    When I retrieve student with ID "1"
    Then I should receive a student with name "John Smith" and grade "A"

  Scenario: Update an existing student
    Given the student information service is running
    When I update the student with ID "2" to have name "Jane Green" and grade "A+"
    Then the student with ID "2" should be updated

  Scenario: Delete an existing student
    Given the student information service is running
    When I delete the student with ID "3"
    Then the student with ID "3" should be deleted






    package com.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class StudentCrudStepDefinitions {

    private Response response;
    private final String BASE_URL = "http://localhost:8080/api/students";

    @Given("the student information service is running")
    public void the_student_information_service_is_running() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("I create a new student with ID {string}, name {string}, grade {string}, and enrollment year {int}")
    public void i_create_a_new_student_with_id_name_grade_and_enrollment_year(String id, String name, String grade, int year) {
        String studentJson = String.format("{\"id\": \"%s\", \"name\": \"%s\", \"grade\": \"%s\", \"enrollmentYear\": %d}",
                id, name, grade, year);

        response = given()
                .header("Content-Type", "application/json")
                .body(studentJson)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    @Then("the student with ID {string} should be created")
    public void the_student_with_id_should_be_created(String id) {
        Assert.assertEquals(id, response.jsonPath().getString("id"));
    }

    @When("I retrieve student with ID {string}")
    public void i_retrieve_student_with_id(String id) {
        response = given()
                .when()
                .get("/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Then("I should receive a student with name {string} and grade {string}")
    public void i_should_receive_a_student_with_name_and_grade(String name, String grade) {
        Assert.assertEquals(name, response.jsonPath().getString("name"));
        Assert.assertEquals(grade, response.jsonPath().getString("grade"));
    }

    @When("I update the student with ID {string} to have name {string} and grade {string}")
    public void i_update_the_student_with_id_to_have_name_and_grade(String id, String name, String grade) {
        String studentJson = String.format("{\"name\": \"%s\", \"grade\": \"%s\"}", name, grade);

        response = given()
                .header("Content-Type", "application/json")
                .body(studentJson)
                .when()
                .put("/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Then("the student with ID {string} should be updated")
    public void the_student_with_id_should_be_updated(String id) {
        Assert.assertEquals(id, response.jsonPath().getString("id"));
    }

    @When("I delete the student with ID {string}")
    public void i_delete_the_student_with_id(String id) {
        given()
                .when()
                .delete("/" + id)
                .then()
                .statusCode(204);
    }

    @Then("the student with ID {string} should be deleted")
    public void the_student_with_id_should_be_deleted(String id) {
        given()
                .when()
                .get("/" + id)
                .then()
                .statusCode(404); // Not found after deletion
    }
}







