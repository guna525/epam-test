# epam-test

This is a Simple Project to test Below given Rest Apis

GET all  http://swapi.co/api/planets
GET by {id} - http://swapi.co/api/planets/{id}
GET by Page number - http://swapi.co/api/planets/?page{pageNumber}


Please run as

mvn clean test 
-DsuiteXmlFile=src/main/resource/testng/RunGetApiTests.xml


or Run RunGetApiTests.xml as testNG suite.