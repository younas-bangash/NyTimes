# Android MVVM Base Architecture for Enterprise Mobile Application using Architectural Components

# Highlights

1. MVVM Architectural pattern
2. Offline Support
3. Unit test demonstration using JUnit and Mockito
4. UI unit test demonstration using Espresso
5. Gradle scripts for running sonarqube static code analysis, code coverage, etc.
6. Use AndroidX for Android Support Libraries
7. Use of The Navigation component library
8. JSoup for HTML Parsing

The application has been built with **offline support**. It has been designed using **Android Architecture components** with **Room** for offline data caching. The application is built in such a way that when ever there is a service call, the result will be stored in local database.

The whole application is built based on the MVVM architectural pattern.

# Application Architecture
![alt text](https://cdn-images-1.medium.com/max/1600/1*OqeNRtyjgWZzeUifrQT-NA.png)

The main advatage of using MVVM, there is no two way dependency between ViewModel and Model unlike MVP. Here the view can observe the datachanges in the viewmodel as we are using LiveData which is lifecycle aware. The viewmodel to view communication is achieved through observer pattern (basically observing the state changes of the data in the viewmodel).

# Navigation Graph
<img src="/screenshots/4.png" width="500" height="615" alt="Home"/>

# Overview of Offline Architecture
Consider the following diagram, which shows how all the modules should interact with one another in the app:
![alt text](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

# Screenshots
<img src="/screenshots/1.png" width="346" height="615" alt="Home"/>
<img src="/screenshots/2.png" width="346" height="615" alt="Home"/>
<img src="/screenshots/3.png" width="346" height="615" alt="Home"/>

# Programming Practices Followed
a) Android Architectural Components <br/>
b) Dagger 2 for Dependency Injection <br/>
c) MVVM <br/>
d) Retrofit with Okhttp <br/>
e) Room for data caching <br/>
f) JUnit and Mockito for Unit testing <br/>
d) Repository pattern <br/>

# How to build ?

Open terminal and type the below command to generate debug build <br/>

``` ./gradlew assembleDebug ```

Open terminal and type the below command to generate release build <br/>

``` ./gradlew assembleRelease ```

# How to generate Sonarqube report ?

Open gradle.properties and update the below line with the sonarqube server url

```systemProp.sonar.host.url=http://localhost:9000```

Before running the sonarqube job, make sure the project version has been updated in the build.gradle. On every run, increment the version by 1.<br/>

```
            property "sonar.sources", "src/main"
            property "sonar.projectName", "Project Name" // Name of your project
            property "sonar.projectVersion", "1.0.0" // Version of your project
            property "sonar.projectDescription", "Description"
```

For running the sonarqube job, type the below command in the terminal. <br/>

```./gradlew sonarqube assembleDebug```

<br/>

# How to generate code coverage report ?

Open terminal and type the following command

```./gradlew clean jacocoTestReport```

The coverage report will be generated on the following path.

``` app/build/reports ```