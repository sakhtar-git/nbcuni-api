# NBC QE Challenge

Testing Craft Demonstration Challenge - developed in Java,RestAssured, TestNG, Maven

## Getting Started
These instructions below will help you to set up the test automation to run on your local and verify the search API
https://youtu.be/I06P8jaR8G0 

### Install
* IDE - IntelliJ
* Maven
* Set up your pom file for dependencies - for RestAssured, TestNG, etc.

The automation framework designed to support API test is contained in a single Maven module.  The module is broken down as follows: 
```
    src
     |-- test
           |-- java
                 |-- com.nbcuni.api       
                     |-- common
                     |-- search
           |-- resources
                 |-- search
```

### Test Cases

* verifySearch    - Basic search to make sure API is working
* verifyWithIncorrectURI - API is returning proper status code 404 for any unknown resources
* verifyWithAllParameters - API is functional if all optional parameters are passed as query params
* verifyWithoutAnyParams - API is returning proper status code 400 for query param is not passed
* verifyWithInvalidParam - API is returning proper status code 400 for invalid query param
* verifyJsonResponseConformsToSchema - API response confirm to some schema definition for a valid request

Other test cases can be automated:
* Verify end year is always greater than start year "YYYY" numeric format 
    (Fail, expected error message with reason is not returned in the response )
* Verify the start/end year supports only "YYYY" numeric format 
    (Fail, expected error message with reason is not returned in the response )


### Running the tests

* You may directly run the test from Intellij
* For command line -  mvn clean test

### Review the test results
 
 The test report is generated inside target folder:
 * xml report - target/surefire-reports/testng-results.xml
 * html report - open in browser - "open target/surefire-reports/emailable-report.html"



