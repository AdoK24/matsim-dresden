package org.matsim.population;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class PopulationModifier {
	public static void main(String[] args) {
		Config config = ConfigUtils.loadConfig("input/v1.0/dresden-v1.0-10pct.config.xml");
		Scenario scenario = ScenarioUtils.createScenario(config);

		Population population = scenario.getPopulation();
		for (Person person : scenario.getPopulation().getPersons().values()) {
			for (Plan plan : person.getPlans()) {
				for (PlanElement planElement : plan.getPlanElements()) {
					if (planElement instanceof Activity) {
						Activity activity = (Activity) planElement;
						activity.setLinkId(null);
					} else { // i.e. plan element is a leg
						Leg leg = (Leg) planElement;
						leg.setRoute(null);
					}
				}
			}
		}
		PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
		populationWriter.write("input/v1.0/plans_no_routes_no_links.xml");
	}
}
