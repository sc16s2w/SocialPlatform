A social platform based on springboot and springcloud only completes the back-end part, and then takes the front-end when there is time
<br>
Completed points:<br> 
1. Public class specification output, paging, and return status code<br> 
2. Article  Microservices: can publish articles, modify articles, delete articles, query all articles, query a single article, search for a certain article attribute, review articles, like <br> 
3. Comment microservice: because of the amount of comment data  Large, use nosql mongodb for storage, complete the addition, deletion and modification of comments, and query the comments of the specified article, the likes of the comments are the mongodbtemplate that comes with mongodb to complete the use of redis to avoid repeated user likes<br> 
4. Add  Gateway Service<br>
