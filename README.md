Sample Tsugi Application for Java
=================================

This is a sample Tsugi based webapp.  It depends on the basiclti-util library from Sakai and the https://github.com/csev/tsugi-j-lib

Install Tsugi
-------------

Then download and install MAMP and  the PHP Tsugi application and create its database use the 
default passwords for a dev environment.

    http://www.tsugi.org/

The default settings for this servlet expect that database to be up and
available (i.e. MAMP is running and MySQL is on port 8889)  You can connect this
to a different database by editing:

    src/main/resources/application.properties

But for the simple case - just install and get Tsugi running and then run 
this Java application.

Build The Library
-----------------

Then checkout and build https://github.com/csev/tsugi-j-lib and compile that jar:

    mvn install
    
Quick Run
---------
You can run the app in place to try it out without having to install and deploy a servlet container.

    mvn clean install spring-boot:run

The LTI launch URL is:

    http://localhost:8080/tsugi

You can launch to this using PHP Tsugi by going to 

    http://localhost:8888/tsugi/dev.php

Type in the dev "secret" and switch the launch URL to 

    http://localhost:8080/tsugi
    
And press "Launch" - it should look like <a href="https://raw.githubusercontent.com/csev/tsugi-j-sample/master/success.png" target="_blank">this</a>.

Customizing
-----------
Use the application.properties to control various aspects of the Spring Boot application (like setup your own database connection).
Use the logback.xml to adjust and control logging.

Debugging
---------
To enable the debugging port (localhost:8000) when using spring-boot:run, use the main profile: -Pdebug. Then you can attach any remote debugger (eclipse, intellij, etc.) to localhost:8000. NOTE that the application will pause until you connect the debugger to it.

    mvn clean install spring-boot:run -Pdebug
    
Git Details
-----------

If you forked my repo and want to grab the latest changes, do 
the following once:

    git remote add upstream https://github.com/csev/tsugi-j-sample

Then from time to time when you want to pull mods and update your fork:

    git pull upstream master
    git push origin master

History
-------

This is a fork of azeckoski/lti\_starter which was originally based on my PHP Tsugi code.



