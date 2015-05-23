Sample Tsugi Application for Java
=================================

This is a sample Tsugi based webapp.  It depends on the basiclti-util library from Sakai and the https://github.com/csev/tsugi-java-lib

Build
-----
I will clean this process up later.  The basiclti-util jar file is not hosted anywhere and until I fix that you need
to compile Sakai source to get that file in your local .m2 repo.  So use these scripts to checkout and compile Sakai.

    https://github.com/csev/sakai-scripts

You don't need to deploy or run Sakai - just compile it so we get the one jar we need.

Then download and install MAMP and  the PHP Tsugi application and create its database use the default passwords for a dev 
environment.

    http://www.tsugi.org/

Then checkout and build https://github.com/csev/tsugi-java-lib and compile that jar:

    mvn install
    
Once you have done that, the sample application can be compiled and turned into a war

    mvn clean install

I know what you are thinking - this is a lot of work.  Yes for now, since I want to keep all these projects in sync
in terms of sharing library code.  Also I want to focus on the Java Tsugi runtime rather than building all the key management stuff in Java right now.  And ultimately I want to be able to go back and forth between Java and PHP and make sure
both Tsug libraries can work together on a shared database.  And I want the basic-lti util code to also only be one place
so I can share that code between Sakai and Java Tsugi.

Eventually all these bits will be nicely factored.  But in the early development days - I want to keep them near each other.

Database
--------

This is expecting that PHP Tsugi already is running, the Tsugi database is running on
localhost:8889 using the default account, password, and database name and that the tables already exist.
If you want to change this, edit the file

    src/main/resources/application.properties

Quick Run
---------
You can run the app in place to try it out without having to install and deploy a servlet container.

    mvn clean install spring-boot:run

The LTI launch URL is:

    http://localhost:8080/tsugi

You can launch to this from any LMS.  For now it just dumps its data out.  I still am working on the library code
and oce I fignigh that I will make the application more interesting.

Customizing
-----------
Use the application.properties to control various aspects of the Spring Boot application (like setup your own database connection).
Use the logback.xml to adjust and control logging.

Debugging
---------
To enable the debugging port (localhost:8000) when using spring-boot:run, use the main profile: -Pdebug. Then you can attach any remote debugger (eclipse, intellij, etc.) to localhost:8000. NOTE that the application will pause until you connect the debugger to it.

    mvn clean install spring-boot:run -Pdebug
