package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DashboardView {

    private BorderPane mainLayout;
    
    private TextArea txtQueueStatus = new TextArea();
    private GridPane gridWarehouse = new GridPane();
    private Label lblCurrentInspector = new Label();
    private Label lblNotification = new Label();
    private TextArea txtReports = new TextArea();
    private Label lblAuditorHeader = new Label();

    // Campos del formulario (Sidebar)
    private TextField tfPlate = new TextField();
    private ComboBox<String> cbCategory = new ComboBox<>();
    private ComboBox<String> cbVehicle = new ComboBox<>();
    private TextField tfTare = new TextField();
    private TextField tfCargoWeight = new TextField();
    private ComboBox<Integer> cbPriority = new ComboBox<>();

    // Botones jerárquicos (Sidebar)
    private Button btnEnqueue = createSidebarButton("🚚 1. Registrar Camión", "#0284c7");
    private Button btnProcess = createSidebarButton("⚖️ 2. Procesar Báscula", "#059669");
    private Button btnUndo = createSidebarButton("🔄 3. Deshacer (Undo)", "#d97706");
    private Button btnAddInspector = createSidebarButton("👤 4. Reg. Inspector", "#7c3aed");
    private Button btnRefreshReports = createSidebarButton("📋 5. Ver Reportes", "#475569");

    public DashboardView(String auditorName) {
        buildUI(auditorName);
    }

    private void buildUI(String auditorName) {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f1f5f9;");

        // --- 1. HEADER (Superior) ---
        HBox headerBox = new HBox(15);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(12, 20, 12, 20));
        headerBox.setStyle("-fx-background-color: #0f172a;");

        Label lblTitle = new Label("🚚 SISTEMA MSCABA — DASHBOARD");
        lblTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        lblAuditorHeader.setText("👤 Auditor: " + auditorName);
        lblAuditorHeader.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");

        lblCurrentInspector.setStyle("-fx-text-fill: #34d399; -fx-font-weight: bold; -fx-font-size: 12px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerBox.getChildren().addAll(lblTitle, spacer, lblAuditorHeader, new Separator(javafx.geometry.Orientation.VERTICAL), lblCurrentInspector);
        mainLayout.setTop(headerBox);

        // --- 2. SIDEBAR LATERAL (Izquierda) ---
        VBox sidebar = createSidebarPanel();
        mainLayout.setLeft(sidebar);

        // --- 3. CENTRO (Dashboard Principal con alturas ajustadas) ---
        txtQueueStatus.setPrefHeight(180);
        txtQueueStatus.setStyle("-fx-font-family: 'Consolas', monospace; -fx-font-size: 11px;");
        txtQueueStatus.setEditable(false);
        VBox queueCard = createCard("📦 Cola de Espera (Prioridad)", txtQueueStatus);
        HBox.setHgrow(queueCard, Priority.ALWAYS);

        gridWarehouse.setHgap(15);
        gridWarehouse.setVgap(15);
        gridWarehouse.setAlignment(Pos.CENTER);
        VBox warehouseCard = createCard("🏭 Matriz de Bodega (3 Muelles)", gridWarehouse);
        warehouseCard.setAlignment(Pos.CENTER);
        HBox.setHgrow(warehouseCard, Priority.ALWAYS);

        HBox topCenterRow = new HBox(15, queueCard, warehouseCard);

        // Aumentamos el tamaño del panel de reportes para que luzca más amplio y cómodo
        txtReports.setPrefHeight(230);
        txtReports.setEditable(false);
        txtReports.setStyle("-fx-font-family: 'Consolas', monospace; -fx-font-size: 11px;");
        VBox reportsCard = createCard("📋 Historiales y Estadísticas del Sistema", txtReports);

        VBox centerContainer = new VBox(15, topCenterRow, reportsCard);
        centerContainer.setPadding(new Insets(15));

        ScrollPane centerScroll = new ScrollPane(centerContainer);
        centerScroll.setFitToWidth(true);
        centerScroll.setStyle("-fx-background: #f1f5f9; -fx-background-color: transparent;");
        mainLayout.setCenter(centerScroll);

        // --- 4. FOOTER (Notificaciones) ---
        HBox footerBox = new HBox();
        footerBox.setPadding(new Insets(10, 20, 10, 20));
        footerBox.setStyle("-fx-background-color: #e2e8f0;");
        lblNotification.setStyle("-fx-text-fill: #0369a1; -fx-font-weight: bold; -fx-font-size: 12px;");
        footerBox.getChildren().add(lblNotification);
        mainLayout.setBottom(footerBox);
    }

    private VBox createSidebarPanel() {
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(310);
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e1; -fx-border-width: 0 1 0 0;");

        Label lblSection = new Label("🎛️ Panel de Control");
        lblSection.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        VBox formBox = new VBox(6);
        tfPlate.setPromptText("Ej. ABC-123");
        
        cbCategory.getItems().addAll("Perecedero", "Grano", "Insumo");
        cbCategory.setValue("Perecedero");
        cbCategory.setMaxWidth(Double.MAX_VALUE);

        cbVehicle.getItems().addAll("Sencillo", "Doble Troque", "Tractocamion");
        cbVehicle.setValue("Sencillo");
        cbVehicle.setMaxWidth(Double.MAX_VALUE);

        tfTare.setEditable(false);
        tfTare.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #475569; -fx-font-weight: bold;");
        tfTare.setText("3.5");

        cbVehicle.setOnAction(e -> {
            String selected = cbVehicle.getValue();
            if ("Sencillo".equals(selected)) tfTare.setText("3.5");
            else if ("Doble Troque".equals(selected)) tfTare.setText("7.0");
            else if ("Tractocamion".equals(selected)) tfTare.setText("12.0");
        });

        tfCargoWeight.setPromptText("Ej. 8.5 (t de producto)");

        cbPriority.getItems().addAll(1, 2, 3);
        cbPriority.setValue(1);
        cbPriority.setMaxWidth(Double.MAX_VALUE);

        formBox.getChildren().addAll(
            new Label("Placa:"), tfPlate,
            new Label("Categoría Carga:"), cbCategory,
            new Label("Tipo Vehículo:"), cbVehicle,
            new Label("Tara Automática (tons):"), tfTare,
            new Label("Peso Carga / Producto (tons):"), tfCargoWeight,
            new Label("Prioridad (1 Alta):"), cbPriority
        );

        Separator sep = new Separator();

        VBox buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(
            btnEnqueue,
            btnProcess,
            new Separator(),
            btnUndo,
            btnAddInspector,
            new Separator(),
            btnRefreshReports
        );

        sidebar.getChildren().addAll(lblSection, formBox, sep, buttonsBox);
        return sidebar;
    }

    private VBox createCard(String titleText, javafx.scene.Node content) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-color: #e2e8f0; -fx-border-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 6, 0, 0, 2);");
        Label title = new Label(titleText);
        title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        card.getChildren().addAll(title, content);
        return card;
    }

    private Button createSidebarButton(String text, String hexColor) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + hexColor + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 9px 12px; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: derive(" + hexColor + ", -15%); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 9px 12px; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + hexColor + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 9px 12px; -fx-cursor: hand; -fx-alignment: CENTER_LEFT;"));
        return btn;
    }

    public Parent getRoot() { return mainLayout; }
    public TextField getTfPlate() { return tfPlate; }
    public ComboBox<String> getCbCategory() { return cbCategory; }
    public ComboBox<String> getCbVehicle() { return cbVehicle; }
    public TextField getTfTare() { return tfTare; }
    public TextField getTfCargoWeight() { return tfCargoWeight; }
    public ComboBox<Integer> getCbPriority() { return cbPriority; }

    public Button getBtnEnqueue() { return btnEnqueue; }
    public Button getBtnProcess() { return btnProcess; }
    public Button getBtnUndo() { return btnUndo; }
    public Button getBtnAddInspector() { return btnAddInspector; }
    public Button getBtnRefreshReports() { return btnRefreshReports; }

    public void setQueueStatusText(String text) { txtQueueStatus.setText(text); }
    public void setReportsText(String text) { txtReports.setText(text); }
    public void setNotification(String text) { lblNotification.setText(text); }
    public void setInspectorText(String text) { lblCurrentInspector.setText("🔄 Turno Inspector: " + text); }

    public void refreshWarehouseGrid(int[][] grid) {
        gridWarehouse.getChildren().clear();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                boolean isOccupied = (grid[r][c] != 0);
                Label cell = new Label(isOccupied ? "🔴 [ Ocupado ]" : "🟢 [  Libre  ]");
                cell.setStyle(isOccupied 
                    ? "-fx-background-color: #fee2e2; -fx-border-color: #ef4444; -fx-text-fill: #991b1b; -fx-padding: 10px; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-font-weight: bold; -fx-alignment: center;" 
                    : "-fx-background-color: #d1fae5; -fx-border-color: #10b981; -fx-text-fill: #065f46; -fx-padding: 10px; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-font-weight: bold; -fx-alignment: center;");
                gridWarehouse.add(cell, c, r);
            }
        }
    }
}