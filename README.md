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
![image](https://user-images.githubusercontent.com/1582196/50266914-5c6e5800-03da-11e9-864a-ad65ec9dedb9.png)
![image](https://user-images.githubusercontent.com/1582196/50266917-61330c00-03da-11e9-97d1-a43096520100.png)
![image](https://user-images.githubusercontent.com/1582196/50266924-66905680-03da-11e9-9ec0-d1c2533c3033.png)
![image](https://user-images.githubusercontent.com/1582196/50266929-6a23dd80-03da-11e9-9722-883d08d613c1.png)
![image](https://user-images.githubusercontent.com/1582196/50266939-6f812800-03da-11e9-8b23-f195372a7d59.png)
![image](https://user-images.githubusercontent.com/1582196/50266943-7445dc00-03da-11e9-9ab4-dd51b138f897.png)
![image](https://user-images.githubusercontent.com/1582196/50266947-7871f980-03da-11e9-99e6-7ba75d26e12e.png)
![image](https://user-images.githubusercontent.com/1582196/50266954-7c9e1700-03da-11e9-9a8f-8a0e3874e425.png)
![image](https://user-images.githubusercontent.com/1582196/50266959-80319e00-03da-11e9-9e62-cb55b7925d90.png)
![image](https://user-images.githubusercontent.com/1582196/50266962-845dbb80-03da-11e9-9c50-05472f0d0091.png)
![image](https://user-images.githubusercontent.com/1582196/50266965-87f14280-03da-11e9-95a9-52e819d5116f.png)
![image](https://user-images.githubusercontent.com/1582196/50266978-90497d80-03da-11e9-80b0-e2f83fa85e91.png)
![image](https://user-images.githubusercontent.com/1582196/50266986-963f5e80-03da-11e9-8684-0a691a9f370a.png)
![image](https://user-images.githubusercontent.com/1582196/50266996-9a6b7c00-03da-11e9-8309-c86f2c3e6235.png)
![image](https://user-images.githubusercontent.com/1582196/50267003-9f303000-03da-11e9-846b-ce70249c4654.png)
![image](https://user-images.githubusercontent.com/1582196/50267010-a22b2080-03da-11e9-9d99-41106dd0e332.png)

### Deployment in local
- Clone this project
- Run command "mvn clean install" on the folder where pom.xml is present
- To execute the project from eclipse Right click on the project -> Run as Java Application
- To execute on command line run the command java -jar "jar name"
