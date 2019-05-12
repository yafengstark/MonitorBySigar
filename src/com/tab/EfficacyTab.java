package com.tab;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * @author tony
 * @date 2019/5/12 1:15
 */
public class EfficacyTab extends Tab {

    StringProperty amountProperty = new SimpleStringProperty();
    StringProperty companyProperty = new SimpleStringProperty();
    StringProperty clazzProperty = new SimpleStringProperty();
    StringProperty cpuRateProperty = new SimpleStringProperty();

    public EfficacyTab() {
        super("性能");
        setClosable(false);

        TabPane vTabPane = new TabPane();
        vTabPane.setSide(Side.LEFT);
        vTabPane.setTabMinWidth(30);
        vTabPane.setTabMaxWidth(30);
        vTabPane.setTabMinHeight(100);
        vTabPane.setTabMaxHeight(100);


        vTabPane.getTabs().addAll(
                createCPUTab(),
                createMemoryTab()
        );



        this.setContent(vTabPane);

    }

    private Tab createMemoryTab() {
        Tab memoryTab = new Tab();
        memoryTab.setGraphic(createTabHeader("内存", null));
        memoryTab.setClosable(false);
        return memoryTab;
    }

    private Tab createCPUTab() {
        BorderPane borderPane = new BorderPane();


        Label amount = new Label("CPU的总量MHz: ");
        Label amountValue = new Label();
        amountValue.textProperty().bindBidirectional(amountProperty);
        HBox amountBox = new HBox();
        amountBox.getChildren().addAll(
                amount, amountValue
        );

        Label company = new Label("厂家");
        Label companyValue = new Label();
        companyValue.textProperty().bindBidirectional(companyProperty);
        HBox companyBox = new HBox();
        companyBox.getChildren().addAll(
                company,
                companyValue
        );

        Label clazz = new Label("类别");
        Label clazzLabel = new Label("");
        clazzLabel.textProperty().bindBidirectional(clazzProperty);
        HBox clazzBox = new HBox();
        clazzBox.getChildren().addAll(
                clazz, clazzLabel
        );

        Label rate = new Label("实时利用率");
        Label rateLabel = new Label("");
        rateLabel.textProperty().bindBidirectional(cpuRateProperty);
        HBox rateBox = new HBox();
        rateBox.getChildren().addAll(
                rate, rateLabel
        );


        VBox contentBox = new VBox();
        contentBox.getChildren().addAll(
                amountBox,
                companyBox,
                clazzBox,
                rateBox
        );


        Tab cpuTab = new Tab();
        cpuTab.setGraphic(createTabHeader("CPU", null));
        cpuTab.setClosable(false);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
//        yAxis.forceZeroInRangeProperty().setValue(false);

        xAxis.setLabel("时间");
        xAxis.autoRangingProperty().setValue(true);

        final LineChart<String, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("CPU占用率");

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        Timer timer = new Timer();
        timer.schedule(new UpdateTask(lineChart,
                cpuRateProperty), 1000, 1000);

        initCpuMessage();

        borderPane.setTop(contentBox);
        borderPane.setCenter(lineChart);
        cpuTab.setContent(borderPane);

        return cpuTab;
    }

    private StackPane createTabHeader(String text, Node graphics) {
        return new StackPane(new Group(new Label(text, graphics)));
    }

    private void initCpuMessage(){


        try {
            Sigar sigar = new Sigar();
            CpuInfo infos[] = sigar.getCpuInfoList();
            CpuPerc[] cpuList = null;
            cpuList = sigar.getCpuPercList();

            amountProperty.set(infos[0].getMhz()+ "");
            companyProperty.set(infos[0].getVendor());
            clazzProperty.set(infos[0].getModel());


        } catch (SigarException e) {
            e.printStackTrace();
        }


    }

}
