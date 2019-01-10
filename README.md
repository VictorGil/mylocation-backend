# MyLocation backend application

Main microservice (API gateway) of MyLocation Java application based on [Vert.x](https://vertx.io/) which receives live location updates from [mylocation-android](https://github.com/VictorGil/mylocation-android) and then sends the location data to [mylocation-frontend](https://github.com/VictorGil/mylocation-frontend) using the [SockJS event bus bridge](https://vertx.io/docs/vertx-web/java/#_sockjs_event_bus_bridge).  
Other microservices which are part of the same application:  
[mylocation-last_known_location-persistence](https://github.com/VictorGil/mylocation-last_known_location-persistence)  
[mylocation-last_known_location](https://github.com/VictorGil/mylocation-last_known_location)  

