[![CircleCI](https://circleci.com/gh/QVDev/FlickrDemo.svg?style=svg)](https://circleci.com/gh/QVDev/FlickrDemo)
[![codecov](https://codecov.io/gh/QVDev/FlickrDemo/branch/develop/graph/badge.svg)](https://codecov.io/gh/QVDev/FlickrDemo)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1b3ac4c69fa945b78c9b3bb9f0f260d8)](https://www.codacy.com/app/feedbackandroid/FlickrDemo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=QVDev/FlickrDemo&amp;utm_campaign=Badge_Grade)

# FlickrDemo
Demo application to search for images on Flickr using the Flickr-Api. Using retrofit, rxjava and jsonscheme2pojo

### Building with gradle
* ```./gradlew clean assembleDebug``` Assemble the debug version
* ```./gradlew test``` Will run the tests
* ```./gradlew jacocoTestDebugUnitTestReport``` Will run the test with jacoco test report

### Quality and CI
The badges on top of the README are generated by the following websites
* Circle-CI is used for Continues integration see https://circleci.com/gh/QVDev/FlickrDemo
* Codecov is used to report test coverage see https://codecov.io/gh/QVDev/FlickrDemo

### Testing
For unit testing the spock framework is being used see http://spockframework.org/spock/docs/1.1-rc-2/index.html using the spock framework the unit test are written in Groovy and is better structured with good Mock ability

### JSONSCHEME2POJO
For generating the models that will be parsed JSONSCHEME2POJO is being used see https://github.com/joelittlejohn/jsonschema2pojo
The generated models will generated in the build folder and thus not clutter the source folder

### Fresco
Fresco image loading library is used to load images efficiently see https://github.com/facebook/fresco

### Networking
For making API calls to Flickr retrofit in combination with rxjava for parsing the response to object GSON is being used
* https://github.com/ReactiveX/RxJava/wiki/The-RxJava-Android-Module
* https://github.com/google/gson
* http://square.github.io/retrofit/
