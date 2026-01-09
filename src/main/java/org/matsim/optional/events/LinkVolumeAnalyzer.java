package org.matsim.optional.events;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.utils.charts.XYScatterChart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LinkVolumeAnalyzer implements LinkLeaveEventHandler {

    private double[] volumeLink6 = new double[24];
    Id<Link> linkId6 = Id.createLinkId("6");

    BufferedWriter bufferedWriter;

    @Override
    public void handleEvent(LinkLeaveEvent event) {
        Id<Link> linkId = event.getLinkId();
        double time = event.getTime(); // Time in seconds after midnight

        int timeSlot = (int) (time/3600); //mit runden Klammern auf unteren integer runden

        if (linkId.equals(linkId6)) {
            volumeLink6[timeSlot]++;
        }
    }
    public void printResult() {
        for (int i = 0; i < 24; i++) {
            System.out.println("Link volume in link 6 from " + i + " until " + (i+1) + " o'clock is " + volumeLink6[i]);

        }
    }

    public void writeChart(String fileName){
        double[] hours = new double[24];
        for (double i = 0.0; i < 24; i++){
            hours[(int) i] = i;
        }

        XYScatterChart chart = new XYScatterChart("Traffic volumes", "Hour", "Volume");
        chart.addSeries("Link 6", hours, volumeLink6);

        chart.saveAsPng(fileName, 800, 600);

        System.out.println("Volume chart created and saved as " + fileName + ".");
    }

    public void writeDataFile (String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write("hour");
        bufferedWriter.write(";");
        bufferedWriter.write("volume");
        bufferedWriter.newLine();

        for (int i = 0; i < 23; i++){
            bufferedWriter.write(String.valueOf(i));
            bufferedWriter.write(";");
            bufferedWriter.write(String.valueOf(volumeLink6[i]));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        System.out.println("Volume data file created and saved as " + fileName + ".");
    }
}
