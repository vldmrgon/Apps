#### 1) Description

This project implements the basic functionality of the web service with two endpoints, according to the assignment.

* Used in the development process: Java 13, Spring, Spring Boot


* The service implements the following components: Controller, HandlerException, Repository, Services, Model, CsvParser
  with Validation
  

* Pagination


* Written tests using JUnit and Mockito


* Deployment with Dockerfile

#### 2) Improved functionality (in the case of infinite time):

* Improvement performance (Implement service caching, Implement .csv parallel processing technology)

* Create a universal Repository for any object in the repository.

```
interface IRepository<T, K> {

void loadFromCsv(String filePath, CsvRecordMapper<T> recordMapper, Function<CSVRecord, K> keyMapper);

Optional<T> getById(K id);

List<T> findAll();

List<T> findAll(int page, int size);
}
```
For this, need to create a objectResolver for T-obj processing  





