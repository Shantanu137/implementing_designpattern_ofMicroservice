Spring Boot and Microservices
-----------------------------------------------

    Spring Boot is a spring framework module that offers auto-configuration.
    and thereby provides RAD - Rapiod Application Development.
    Spring Boot also has embeded servers.

    auto-config ---> Convention over coding...!

    @SpringBootApplication      =       @Configuration
                                        @ComponentScan
                                        @EnableAutoConfig
                                        @PropertySource


    Spring Starter Project can be created via
        1. STS Starter Project Wizard
        2. SpringInilizer -> http://start.spring.io
        3. Spring Boot CLI

    
    @SpringBootApplication
    public class MyApplication {
        public static void main(String a[]){
            SpringApplication.run(MyApplication.class);        
            /*
            * ApplicationContext is instantiated 
            * Context initilaization like componentscan, auto-config ...etc.,
            * Invoke all Spring Runners (if any)
            * Start the embeded server (if any)
            * Once Server is shut down, the context is destroyed and the app terminates
            */
        }
    }

    Spring Runner
        is any class that implements CommandLineRunner or ApplicationRunner

    #1
    ---------------------------------------------

        Develop a rest-api on Spring Boot 3.0
        Is to facilitate the CRUD operation on Contact Entity.
        Contact Entity has contactId, fullName, mailId, dateOfBirth as fields
        Spring Data JPA for ORM.
        Validation framework for Server Side Validations.
        Use Lombok if available.

    
    Micro-services
    --------------------------------------------

            Database        <----------->       Application [logic+UI]     <----------->  EndUser

        A webservice is any method that can be invoked through a request, the paramerter if any will be passed
        as request parameters or request bodya nd teh result is received through a response.

            PersisenceLayer                MiddleWare                   UI/UX

                                                                        Java-Swing-Stadnalone-App       <-->  EndUser
                                                                        Java-SprinWebMVC-App            <-->  EndUser
            Database        <----------->  WebService <----------->
                                                                        Java-Andriod-Mobile-App         <-->  EndUser   
                                                                        JavaScript-Angular-SPA          <-->  EndUser

        Web - Services      are of two ways to build:
            SOAP Web Services
            REST-ful Web Services / REST api

        The approach of building these middleware as a single unit of deployment is called Monolythical approach.
        All the modules of an application including all the entities, servoies, controllers are hosued
        together in a single deployemnt unit.

        This approach ahs three main limitations
            (a) lack of Scalability
            (b) lack of Interaperobility
            (c) lack of Adaptability

        Micro-services is an ecosystem of isolated independent web-services aimed at a single application
        that can correlate data and operations.

            (+) As the services are isolated into individual deployments, independent scaling is possible
            (+) As the services are independe, each of them can of any technology 
            (+) As and a new services has to be added to the ecossytem, the new service need not comply with the technologies
                of the existing services and can indipendently adopt to new standards and technologies

        Chanllenges To Build a MicrServices EcoSystem

            (-) Decomposition
            (-) Inter-Communication
            (-) Monitoring and Tuning
            (-) Distributed Transactions
            (-) Distributed Tracing        

        Micro-Services Design Patterns 

            Decomposition by Domain 
                We can identify the modules of a monolythic approach and conceder
                each module a micro-service.
                Based on the anticipated work load, furthur microservices can be merged or split.

            Decomposition by Sub-Domain 
                Identify the god-classes (classes that appear in most of the microservices)
                Define their bounded-context and avoid redudency.
                            
            Shared Database Pattern

                Having a single DB for all microservices
                in brown field apps

            Database Per Service Pattern

                Each microservice has its own database
                in all green field apps

            Discovery Service Pattern

                discovery-service
                    |
                    |- all microservices will register their address with discovery-service
                    |- the address are retrived from here by the needy microservices

            Data Aggregation Pattern

                Aggregation is about desiging a microservice that can collect info
                from other microservices analyze and aggreagate the data and pass the 
                aggregated data to the client, saving the client from making multiple requests
                for different parts of the data.

                the 'statement-microservice' is an example for this pattern.

            Client Side Component Pattern

                Each component of the UI/UX application can place
                their individual reqeusts to different microservices parellelly
                and should be receiving the resposnes as well parllelly.

            Distributed Tracing Design Pattern

                Tracing - Service

                    Whenever a request comes to any of the microservices in our app-ecosystem,
                    that request is given a unique ID and is reported to the Tracing-Service
                    every time, the request goes from one service to another service until
                    the final resposne is sent to the clinet. And the tracing-service
                    will record all the track of tehis request along with any performence
                    metrics and log info attached with the request.

            Load Balancing Desing Pattern

                load balalcing means mapping the incoming reqeusts to multiple instacnes of the 
                same microservice based on some (round-robin) algorithm.

                tools like Ribbon / Spring Cloud Load Balancer ..etc., are used to perform load 
                balancing.

            API Gateway Design Pattern

                gateway-service <------------(all reqs)--------------- any-client
                    |
                    | -> forward that request to the respective micro-service
                    | <- receives the response from that micro-service
                    |
                    |----------------------------------(response)---------> client

            Circuit - Breaker Design Pattern

                circuit-breaker-thrushold

                    when the first request could not reach a specific microservice (due to its down-time),
                    a fallback machanisim is triggered.

                    After that the circuit is made open (broken), means that the fallback machanisim
                    will address all the other consiquitive request targetting that microservice.

                    When a request to the sme micro-service is inbound after the thrushold, then the
                    circuit is half-closed, means that a new attemp to reach the microserivce is made,
                            }|- on successful contact, the circuit is closed
                             |- or if that microservice is still unavailable, the circuit continues to be open.

                    tools like Resiliance4j ..etc., are for the purpose.

            External-Cofiguaration Design Pattern

                    repository (github) [contains a list all config files of all microservice]
                        |
                        |                
                config-service
                            |<- when ever a microservice has to start, it will first send a fetech req the
                            |   the config-service
                            |
                            | the config-service will check for the cofnig file in the repo
                            |
                            |<- the config file is passed to the microservcei by the config-service
                            |
                            |<- wheever the config fiels are modified and pushed into the repo
                            |   the config service will automatically notify all the respective microservices
                                    and the microservices will receive the updated config-file and restart all by 
                                    themselves.

Decomposition by sub-domain

    budgettracking 
        profiles service
            AccountHolder Entity
                Long ahId
                String fullName
                String mobile
                String mailId

        txns service
            AccountHolder Entity
                Long ahId
                Double currentBalance
                Set<Txn> txns
            Txn           Entity
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate
                AccountHolder holder

        statement service
            AccountHolder Model
                Long ahId
                String fullName
                String mobile
                String mailId
                Double currentBalance

            Txn           Model
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate

            Statement     Model
                LocalDate start
                LocalDate end
                AccountHolder profile
                Set<Txn> txns
                totalCredit
                totalDebit
                statementBalance

Aggregator Pattern

    req for statement ------------> statement-service ---------------> profile service
                                                    <---account holder data---
                                                    --------------------> txns service
                                                    <----list of txns-------
                                     does the composition and computation
            <---statement obj-------  into statement obj

Discovery Service Design Pattern

                discovery-service
                (spring cloud netflix eureka discovery service)
                        ↑|
                    registration of urls 
                    and retrival of urls
                        |↓
            -------------------------------------
            |               |                   |
    profile-service     txns-service     statement-service

Api Gateway Pattern Design Pattern

    Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
       

Distributed Tracing

  Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
        (sleuth)          (sleuth)            (sleuth)
            |               |                      |
            -------------------------------------------
                        ↑↓
             distrubuted tracing service
                    (zipkin-server)

External Configuaration

  Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
        (sleuth)          (sleuth)            (sleuth)
            |               |                      |
            -------------------------------------------
                        ↑↓                      ↑↓
             distrubuted tracing service       configuaration-service 
                    (zipkin-server)         (spring cloud config service)
                                                    |
                                                    |
                                                    git-repo
                                                        profile.properties
                                                        txns.properties
                                                        statement.properties
                                                        gateway.properties

Implementing Budget-tracker
                                        
    Step#1  implementing decomposed services and do inter-service communication and aggregator
        in.bta:bta-profiles
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
                mysq1:mysql-connector-java
                org.springframework.boot:spring-boot-starter-data-jpa
            configuaration
                spring.application.name=profiles
                server.port=9100

                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username=root
                spring.datasource.password=root
                spring.datasource.url=jdbc:mysql://localhost:3306/bapsDB?createDatabaseIfNotExist=true
                spring.jpa.hibernate.ddl-auto=update

        in.bta:bta-txns
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
                mysq1:mysql-connector-java
                org.springframework.boot:spring-boot-starter-data-jpa
            configuaration
                spring.application.name=txns
                server.port=9200

                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username=root
                spring.datasource.password=root
                spring.datasource.url=jdbc:mysql://localhost:3306/batxnsDB?createDatabaseIfNotExist=true
                spring.jpa.hibernate.ddl-auto=update

        in.bta:bta-statement
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
            configuaration
                spring.application.name=statement
                server.port=9300

    Step#2  implementing discovery service and client side load balancing
        in.bta:bta-discovery
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-server
            configuaration
                @EnableEurekaServer    on Application class

                spring.application.name=discovery
                server.port=9000

                eureka.instance.hostname=localhost
                eureka.client.registerWithEureka=false
                eureka.client.fetchRegistry=false
                eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
                eureka.server.waitTimeInMsWhenSyncEmpty=0

        in.bta:bta-profiles
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false

        in.bta:bta-txns
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false

        in.bta:bta-statement
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false    

    Step 3: Implement API Gateway Design Pattern
        in.bta:bta-gateway
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-starter-gateway
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                @EnableDiscoveryClient          on Application class

                spring.application.name=gateway
                server.port=9999

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.gateway.discovery.locator.enabled=true
                spring.cloud.gateway.discovery.locator.lower-case-service-id=true
                
        in.bta:bta-discovery
        in.bta:bta-profiles
        in.bta:bta-txns
        in.bta:bta-statement
              
    Step 4: Implement Distributed Tracing Design Pattern
          in.bta:bta-discovery
          
          in.bta:bta-gateway
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*
       
        in.bta:bta-profiles
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*

        in.bta:bta-txns
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*

        in.bta:bta-statement
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*

        tracing-service
            zipkin-server
                https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec 
                
                java -jar zipkin-server.jar

    Step 5: Implement Circuit Breaker Design Pattern
        in.bta:bta-discovery  
        in.bta:bta-gateway
        in.bta:bta-profiles
        in.bta:bta-txns
            dependencies
                ++org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j
            
            configuaration
                resilience4j.circuitbreaker.configs.default.registerHealthIndicator=true
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInClosedState=4
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInHalfOpenState=2
                resilience4j.circuitbreaker.configs.default.automaticTransitionFromOpenToHalfOpenEnabled=true
                resilience4j.circuitbreaker.configs.default.waitDurationInOpenState= 20s
                resilience4j.circuitbreaker.configs.default.failureRateThreshold= 50
                resilience4j.circuitbreaker.configs.default.eventConsumerBufferSize= 10

        in.bta:bta-statement
           dependencies
                ++org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j
            
            configuaration
                resilience4j.circuitbreaker.configs.default.registerHealthIndicator=true
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInClosedState=4
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInHalfOpenState=2
                resilience4j.circuitbreaker.configs.default.automaticTransitionFromOpenToHalfOpenEnabled=true
                resilience4j.circuitbreaker.configs.default.waitDurationInOpenState= 20s
                resilience4j.circuitbreaker.configs.default.failureRateThreshold= 50
                resilience4j.circuitbreaker.configs.default.eventConsumerBufferSize= 10

    Step 6: External Configuaration Design Pattern
        inTheWorkSpace> md bt-props-repo
            //and then create these files in this directory
                // gateway.properties
                // profiles.properties
                // txns.properties
                // statement.properties
                // move the content of 'application.properties' of each microservice into these respective files
                
            inTheWorkSpace> cd bt-props-repo
            inTheWorkSpace\bt-props-repo> git init           
            inTheWorkSpace\bt-props-repo> git add .
            inTheWorkSpace\bt-props-repo> git commit -m "all service properties"
        
        in.bta:bta-discovery
        in.bta:bta-config
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-config-server
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
            
            configuaration  
                @EnableDiscoveryClient
                @EnableConfigServer             on Application class

                spring.application.name=config
                server.port=9090

                spring.cloud.config.server.git.uri=file:///local/git/repo/path

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5
        
        in.bta:bta-gateway
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=gateway
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/                    
        
        in.bta:bta-profiles
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=profiles
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/   

        in.bta:bta-txns
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=txns
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/   

        in.bta:bta-statement
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=statement
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/   

