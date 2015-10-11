CoiledCloud
===========

A project to explore the spring-cloud capabilities (http://projects.spring.io/spring-cloud/)

Build
-----

To build clone this project and run (from the project root directory)

```
mvn install
```

Configuration
-------------

The configuration for these projects is served via the `coiledcloud-config-server` project (using `spring-cloud-config` and this is in turn read from the repo at: https://github.com/codersparks/coiledcloud-config-repo, however this repo has been added as a submodule to the project under the folder `coiledcloud-config-repo`
