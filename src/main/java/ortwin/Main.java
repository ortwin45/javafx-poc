package ortwin;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
public class Main extends Application {

    public static void main(String[] args) {
        OpenCV.loadShared();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        Image image = new Image("file:" + "/home/ortwin/temp/mountain.png");
        ImageView imageView = new ImageView();
        imageView.setY(10);
        imageView.setFitHeight(200);
        imageView.setImage(image);

        ImageView updatedImageView = new ImageView();
        updatedImageView.setY(300);
        updatedImageView.setFitHeight(200);
        updatedImageView.setImage(image);

        VBox dragTarget = new VBox();
        dragTarget.getChildren().add(imageView);
        dragTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dragTarget
                    && event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        dragTarget.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                String fileUri = db.getFiles().get(0).toString();
                Image newImage = new Image("file:" + fileUri);
                imageView.setImage(newImage);
                var openCvImage = getOpenCvImage(fileUri);
                updatedImageView.setImage(openCvImage);

                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);
            event.consume();
        });


        Group root = new Group();
        root.getChildren().add(dragTarget);
        //root.getChildren().add(imageView);
        root.getChildren().add(updatedImageView);

        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    private Image getOpenCvImage(String fileUri) {
        return loadImage(fileUri);
    }

    @SneakyThrows
    private Image loadImage(String fileUri) {
        //Reading the Image from the file and storing it in to a Matrix object
        Mat image = Imgcodecs.imread(fileUri);
        Core.bitwise_not(image, image);
        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", image, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
        //Displaying the image
        InputStream in = new ByteArrayInputStream(byteArray);
        Image fxImage = new Image(in);
        return fxImage;
    }
}