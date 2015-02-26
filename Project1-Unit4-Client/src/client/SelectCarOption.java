package client;

import java.util.ArrayList;
import java.util.Scanner;

import model.Automobile;
import model.Automobile.OptionSet;

public class SelectCarOption {

	public void configureAudo(Automobile auto) {
		System.out
				.println("#Configuration Opitons for " + auto.getModel() + ":");
		System.out.println(auto.toString());
		for (OptionSet set : auto.getOpetionSetList()) {
			System.out.println("#Enter your choice for " + set.getName() + ":");
			System.out.println("1 for " + set.getOption().get(0).getName());
			System.out.println("2 for " + set.getOption().get(1).getName());
			Scanner input = new Scanner(System.in);
			String choice = input.nextLine();
			switch (choice) {
			case "1":
				auto.setOptionChoice(set.getName(), set.getOption().get(0)
						.getName());
				break;
			case "2":
				auto.setOptionChoice(set.getName(), set.getOption().get(1)
						.getName());
				break;
			}
		}
		System.out.println("#[Done]Option Set Successfully!\n");
		System.out.println("#Here is your car's information: ");
		System.out.println("Model: " + auto.getModel());
		System.out.println("Make: " + auto.getMake());
		System.out.println("Base Price: " + auto.getBasePrice());
		for (OptionSet set : auto.getOpetionSetList()) {
			System.out.println(set.getName() + ":");
			System.out.println("-" + auto.getOptionChoice(set.getName())
					+ ": $" + auto.getOptionChoicePrice(set.getName()));
		}
		System.out.println("Totoal Price: "
				+ (auto.getTotalPrice() + auto.getBasePrice()));
	}
}
