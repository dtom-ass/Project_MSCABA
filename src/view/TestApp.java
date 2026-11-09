package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Clase de prueba para verificar la inicialización de JavaFX y componentes básicos.
 */
public class TestApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Project MSCABA - Panel de Prueba");

        // Componentes básicos
        Label lblTitle = new Label("Simulador Agroindustrial - JavaFX");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label lblStatus = new Label("Estado: Conectado correctamente al Modelo MVC");
        lblStatus.setStyle("-fx-text-fill: #666666;");

        Button btnAction = new Button("Probar Conexión");
        btnAction.setOnAction(e -> lblStatus.setText("¡Botón presionado con éxito!"));

        // Contenedor principal (Layout VBox)
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F4F4F4;");
        root.getChildren().addAll(lblTitle, lblStatus, btnAction);

        // Configuración de la escena y ventana
        Scene scene = new Scene(root, 450, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}