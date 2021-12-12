# TRiBot scripting gradle template

## Adding a new script
1) Add a new directory under `scripts`
2) Open `settings.gradle.kts` under the root directory
3) Add an include for your new script like: `include("scripts:my-script-name")`
4) Add a `src` directory inside your new script directory
5) Add a `scripts` directory inside your new script src directory
6) Refresh gradle within your IDE

## Adding a new library
1) Add a new directory under `libraries`
2) Open `settings.gradle.kts` under the root directory
3) Add an include for your new library like: `include("libraries:my-library-name")`
4) Add a `src` directory inside your new library directory
5) Add a `scripts` directory inside your new library src directory
6) Refresh gradle within your IDE
7) Add the new library dependency to any script in the build.gradle.kts file within your script module. Add this inside
the dependency block: `implementation(project(":libraries:my-library-name"))`

## Other dependencies
If you are planning on running your script on the TRiBot repository, you cannot add custom dependencies to your 
script that are not included in TRiBot. 

If you are running locally, add jars to your .tribot/thirdparty folder and 
this (and TRiBot) will pick them up automatically. Perform a gradle refresh in your IDE after adding.

## Features
* Compile scripts for TRiBot using the "build" gradle task (goes to correct location)
* Delete your compiled scripts using the "clean" gradle task (deletes correct files)
* Pack your scripts into a zip to upload to the repository using repoPackage (or use repoPackageAll to package them all)
* Upload your scripts to the repository (see the section below)
* (IntelliJ only) Debug your scripts through a remote debug config named "Debug TRiBot". This will launch tribot and
attach a remote debugger so that you can step through your scripts.
* Update repo scripts
* Lombok automatically configured
* JavaFX automatically configured
* Allatori annotations automatically configured

## Repository Updating
### Update your script on the TRiBot Repository:
1) Put the script's repository ID in the script's corresponding gradle.properties file with the key repoId. Ex. 
   `repoId=1000` . This can take a comma separated list of ids, if you have multiple variants.
2) Run the `repoUpdate` task in your script gradle project. (or run `repoUpdateAll` in the root project to update 
   every script)
3) You can optionally log in every time you want to upload (note logging in once is scoped to the Gradle daemon), or 
   login once and save the login info. Note that if you choose this second option to save your login info, please 
   ensure your machine is secure. Don't allow people to take your saved info.

### Versioning
The version to use is found by the following rules:
* The task will always use the `scriptVersion` property if set
* Otherwise, if there is a `scriptVersionIncrement` property, increment the current script version by that 
* However, if there is a `scriptBaseVersion` property that is greater than the current version, use that instead of 
  incrementing (to allow going from 1.33 -> 2.0, for example)
* Finally, if no other rule matched, default to the same version the script was before