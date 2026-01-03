# Photon-spring-boot-starter
Use Photon client in Spring Boot application.

![](https://img.shields.io/badge/Apache--2.0-green?style=for-the-badge)
![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

[![](https://jitpack.io/v/N3cr0s1s/photon-spring-boot-starter.svg)](https://jitpack.io/#N3cr0s1s/photon-spring-boot-starter)

---
## Table Of Contents

<!-- TOC -->
* [Photon-spring-boot-starter](#photon-spring-boot-starter)
  * [Table Of Contents](#table-of-contents)
  * [Usage](#usage)
    * [Configuration](#configuration)
  * [Getting Started](#getting-started)
    * [Maven](#maven)
    * [Gradle](#gradle)
<!-- TOC -->

---

## Usage

```java

@Autowired
private PhotonClient photonClient;


public void search() {
    PhotonResponse response = photonClient.search("Budapest", "en", 1);
}

public void reverseGeocode() {
    PhotonResponse response = photonClient.reverse(47.497913, 19.040236);
}
```

### Configuration

You can configure the base url and default language.

```properties
hu.necrocore.photon.base-url=https://photon.komoot.io
hu.necrocore.photon.default-lang=en
```

If you are using self hosted version:

```properties
hu.necrocore.photon.base-url=http://localhost:2322
hu.necrocore.photon.default-lang=en
```

---

## Getting Started

### Maven

Add to pom.xml

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Step 2. Add the dependency

```xml
<dependency>
    <groupId>com.github.N3cr0s1s</groupId>
    <artifactId>photon-spring-boot-starter</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Gradle

Add it in your root settings.gradle at the end of repositories:

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```
dependencies {
        implementation 'com.github.N3cr0s1s:photon-spring-boot-starter:0.0.2'
}
```
