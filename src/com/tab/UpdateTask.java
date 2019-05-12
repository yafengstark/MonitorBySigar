package com.tab;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author tony
 * @date 2019/5/12 1:47
 */
public class UpdateTask extends TimerTask {


    private LineChart lineChart;

    private XYChart.Series[] seriess;

    private XYChart.Series amountSeries;

    private StringProperty cpuRateProperty = new SimpleStringProperty();

    public UpdateTask(LineChart lineChart, StringProperty cpuRateProperty){
        this.lineChart = lineChart;
        this.cpuRateProperty = cpuRateProperty;

        try {
            Sigar sigar = new Sigar();
            CpuInfo infos[] = sigar.getCpuInfoList();

            seriess = new XYChart.Series[infos.length];
            for(int i = 0; i < infos.length; i++){
                XYChart.Series series =  new XYChart.Series();
                series.setName("CPU " + i);

                seriess[i] = series;


            }

            amountSeries = new XYChart.Series();
            lineChart.getData().add(amountSeries);


        } catch (SigarException e) {
            e.printStackTrace();
        }
       ;

    }

    @Override
    public void run() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:MM:ss");
        String dateStr = simpleDateFormat.format(new Date());

        System.out.println(dateStr);

        try {
            Sigar sigar = new Sigar();
            CpuInfo infos[] = sigar.getCpuInfoList();

            CpuPerc[] cpuList = null;
            cpuList = sigar.getCpuPercList();

            double averay = 0.0;
            for(int i=0; i< cpuList.length; i++){
                System.out.println( cpuList[i].getCombined());

                averay = averay + cpuList[i].getCombined();
            }

            final double num = averay/4;

            Platform.runLater(() ->{
                cpuRateProperty.set(num+ "");
            });

            ObservableList<XYChart.Data>  list = amountSeries.getData();

            if(list.size() > 50){
                list.remove(0);
            }


            amountSeries.getData().add(new XYChart.Data(dateStr,averay/4 ));



        } catch (SigarException e) {
            e.printStackTrace();
        }






    }
}
