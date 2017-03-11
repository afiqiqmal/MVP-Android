# MVP-Android
This is example of MVP Android

MVP (Model View Presenter) pattern is a derivative from the well known MVC (Model View Controller), which for a while now is gaining importance in the development of Android applications. There are more and more people talking about it, but yet very few reliable and structured information. That is why I wanted to use this blog to encourage the discussion and bring all our knowledge to apply it in the best possible way to our projects.

##What is MVP?
- The MVP pattern allows separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen. 
- Ideally the MVP pattern would achieve that same logic might have completely different and interchangeable views.
- MVP is not an architectural pattern, it’s only responsible for the presentation layer


![Alt text](https://davidguerrerodiaz.files.wordpress.com/2015/10/18289.jpg "MVC vs MVP")

##Why use MVP?
- In Android we have a problem arising from the fact that Android activities are closely coupled to both interface and data access mechanisms. For an application to be easily extensible and maintainable we need to define well separated layers. Instead of retrieving the same data from a database, we need to do it from a web service? We would have to redo our entire view .
MVP makes views independent from our data source. 
- We divide the application into at least three different layers, which let us test them independently. With MVP we are able to take most of logic out from the activities so that we can test it without using instrumentation tests.

##How to implement MVP for Android?<br>
- Well, this is where it all starts to become more diffuse. There are many variations of MVP and everyone can adjust the pattern idea to their needs and the way they feel more comfortable. 
- The pattern varies depending basically on the amount of responsibilities that we delegate to the presenter.
- And, there is no “standard” way to implement it BUT difficult to setup and easier to use.

###The presenter
The presenter is responsible to act as the middle man between view and model. It retrieves data from the model and returns it formatted to the view. But unlike the typical MVC, it also decides what happens when you interact with the view.

###The View
The view, usually implemented by an Activity (it may be a Fragment, a View… depending on how the app is structured), will contain a reference to the presenter. Presenter will be ideally provided by a dependency injector such as Dagger, but in case you don’t use something like this, it will be responsible for creating the presenter object. The only thing that the view will do is calling a method from the presenter every time there is an interface action (a button click for example).

###The model
In an application with a good layered architecture, this model would only be the gateway to the domain layer or business logic.




<br>
##Dependencies use in this project
- RxAndroid
- RxJava2
- Retrofit2
- Dagger
- Butterknife
- Ice Pick
- Dexter (Permission)


This project already setup firebase analytics. just replace the <b>google-service.json</b> with your json file.
<br>
<br>
Create gradle.properties (if not exist), put
```gradle
SMART_URL_API_PROD = "YOUR API URL"
```

##Sample
Only one Sample of MVP provided which in splash folder<br>
- SplashActivity - Activity
- SplashConnector - View
- SplashManager - Model
- SplashPresenter - Presenter
