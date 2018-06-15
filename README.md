# Missing Scoop

  IoT Based Smart Kitchen Containers that track grocery consumption and continuously provide insights into kitchen cabinets. We propose a system where containers are equipped with smart devices using load sensors and ESP32 microcontroller which captures the weight of the container continuously and uploads that data to cloud with the help of MQTT client and AWS IoT configuration. This weight states of the container is monitored to identify if the grocery weight goes below a threshold and notify the user about the time to purchase. The application also provides visualization of current weight of the grocery and enables easy tracking. Furthermore, the application also houses a nutrition tracking system that encourages healthy eating habits. Based on historical data and daily consumption a nutrition pattern can be observed and users can make eat food in moderation. This nutrition tracking also helps monitoring malnutrition in children and tracking unhealthy eating habits in adults.

- Demo
  <br>[Missing Scoop Android and AWS IoT App Working Demo](https://youtu.be/yH8PBcnbjS8)
  
- System Architecture

<img src="https://user-images.githubusercontent.com/1582196/41492266-d7fdf0e0-70b2-11e8-9942-598084a791f4.png" width="500" height="300">

- System Design

<img src="https://user-images.githubusercontent.com/1582196/41492279-f26eb96e-70b2-11e8-9fa6-de97a1615ae5.png" width="500" height="300">

- IoT Setup

<img src="https://user-images.githubusercontent.com/1582196/41492303-29e5a6aa-70b3-11e8-84ba-d3e6ea7211e5.png" width="500" height="300"> <img src="https://user-images.githubusercontent.com/1582196/41492308-380a8804-70b3-11e8-921e-a05410272cfd.png" width="500" height="300">

- Application Screenshots

### Deployment in local
- Clone this project
- Run command "mvn clean install" on the folder where pom.xml is present
- To execute the project from eclipse Right click on the project -> Run as Java Application
- To execute on command line run the command java -jar "jar name"
