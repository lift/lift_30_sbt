
Template Applications for Lift 3.0
=======
#Lift version 3.0 templates

This repository contains templates for Lift projects.

These templates offer a starting point for your Lift-based project.

##Getting started.

Most people will want to start with the `lift_basic` template. This template shows you how to use:

* `Mapper`, which is one way to connect to a database using **Lift**.
* `specs2`, to write unit tests (or spec tests).

##How to use them.

At your terminal, enter:

1. `git clone https://github.com/lift/lift_30_sbt.git`
2. `cd lift_30_sbt/lift_basic`
3. `./sbt`

       SBT will download a lot of stuff and after some time, you will see the sbt prompt `>`

4. `jetty:start`
5. Your Lift application is now running at [127.0.0.1:8080](http://127.0.0.1:8080)


##How to work with Lift.

Now that you have your first Lift application running, you may want to look around the code, change a few things and see what happens.

You can use any IDE you want, even a text editor, but one of the best IDE for Scala is IntelliJ IDEA with the scala plugin. We'll assume you already got that installed. If you need help, this is the [plugin page](http://confluence.jetbrains.net/display/SCA/Scala+Plugin+for+IntelliJ+IDEA)

1. Open Intellij, click on `File` -> `Open`
2. Navigate to the folder `lift_30_sbt/lift_basic` and click open.

       You will see a new dialog panel asking a few questions, leave them on the default settings and click **ok**. A new window will appear asking
       about which modules to include, leave them both checked and click **ok**.

       After a few minutes, you will be able to start making changes to the sample project.

       You can now navigate through the different files, and make changes as you wish. To see those changes take effect, you go back to `sbt` and restart `jetty` by doing `;jetty:stop;jetty:start`

## Getting help.     

Questions, feedback, etc. please join the conversation at https://groups.google.com/forum/#!forum/liftweb
