Etl-Android
===========
[![Download](https://api.bintray.com/packages/vaibhavbhandula/Maven/Etl-Analytics/images/download.svg) ](https://bintray.com/vaibhavbhandula/Maven/Etl-Analytics/_latestVersion) [![License](http://img.shields.io/:license-apache-blue.svg)](LICENSE) [![Build Status](https://travis-ci.org/vaibhavbhandula/Etl-Analytics.svg?branch=master)](https://travis-ci.org/vaibhavbhandula/Etl-Analytics)

Data Capturing Library for Android.

This can be helpful in sending real time events. This Library send a JSON Array against a key defined by the user in the Api Body.

Download
--------

Maven:
```xml
<dependency>
    <groupId>com.noonEdu.nAnalytics</groupId>
    <artifactId>analytics</artifactId>
    <version>1.0.2</version>
    <type>pom</type>
</dependency>
```
or Gradle:
```groovy
implementation 'com.noonEdu.nAnalytics:analytics:1.0.2'
```
Project Setup
-------------

**Initialize the Library**

```java
NAnalytics.initialize(Context context, String url, RequestType type);
```
There are 3 parameters to pass:
* Application Context
* String Url - **Api Url for sending events**
* Request Type - **POST** or **PUT**

_This method throws an Exception if Url is Empty!!_

**Getting an Instance**

```java
NAnalytics analytics = NAnalytics.getInstance();
```
**Sending an Event**

```java
NAnalytics analytics = NAnalytics.getInstance();
analytics.logEvent(String key, String jsonData);
```
There are 2 parameters to pass:
* String Key - **Key in which array of object will be sent in the api**
* String jsonData - **JSON Object String**

_This method throws **WrongRequestMethodException** if the request method is not **PUT** or **POST** and **JSONException**_

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
