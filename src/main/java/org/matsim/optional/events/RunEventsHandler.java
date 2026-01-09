package org.matsim.optional.events;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

import java.io.IOException;

public class RunEventsHandler {

    public static void main(String[] args) throws IOException {
        String eventFile = "output/output_events.xml.gz";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        LinkVolumeAnalyzer linkVolumeAnalyzer = new LinkVolumeAnalyzer();

        eventsManager.addHandler(linkVolumeAnalyzer);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);

        eventsReader.readFile(eventFile);

        linkVolumeAnalyzer.printResult();

        linkVolumeAnalyzer.writeChart("output/volume_diagram_link_6.png");

        linkVolumeAnalyzer.writeDataFile("output/volume_data_link.txt");

    }
}
