# JavaFX

## Packaging Step 1 : JLink

Jlink is used to create a smaller JRE with only the required modules. However, JLink cannot create a limited set 
of modules with there are _automatic_ modules as dependencies. Using the maven plugin, these automatic modules
can be upgraded to explicit modules. 

Then use the following command:
````
jlink --add-modules hellofx --module-path ./target/javafx-poc-1.0.0-SNAPSHOT.jar:./target/modules/opencv-4.5.1-2.jar:./target/modules/lombok-1.18.22.jar:./target/modules/slf4j-api-1.8.0-beta4.jar:./target/modules/slf4j-simple-1.8.0-beta4.jar:./target/modules/javafx-fxml-16-linux.jar:./target/modules/javafx-controls-16-linux.jar:./target/modules/javafx-base-16-linux.jar:./target/modules/javafx-graphics-16-linux.jar --output target/jlink-image --launcher helloFxApp=hellofx/ortwin.Main

jpackage --runtime-image target/jlink-image/ --module hellofx/ortwin.Main
````
````
cd target/jlink-image/bin
./helloFxApp
````

