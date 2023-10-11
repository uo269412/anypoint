package org.mule.miw.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Trainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trainer_id;
	private String name;
	private int age;
	private boolean former_competitor;
	private int total_pokemon_stats = 0;
	private int total_pokemon_count = 0;

	public Trainer(HashMap<String, String> arg0) {
		this.trainer_id = arg0.get("trainer_id");
		this.name = arg0.get("name");
		this.age = Integer.parseInt(arg0.get("age"));
		this.former_competitor = Boolean.valueOf(arg0.get("former_competitor"));
	}

	public Trainer(String arg0, String arg1, String arg2, String arg3) {
		super();
		this.trainer_id = arg0;
		this.name = arg1;
		this.age = Integer.parseInt(arg2);
		this.former_competitor = Boolean.valueOf(arg3);
	}

	public String getTrainer_id() {
		return trainer_id;
	}

	public void setTrainer_id(String trainer_id) {
		this.trainer_id = trainer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isFormer_competitor() {
		return former_competitor;
	}

	public void setFormer_competitor(boolean former_competitor) {
		this.former_competitor = former_competitor;
	}

	public int getTotal_pokemon_stats() {
		return total_pokemon_stats;
	}

	public void setTotal_pokemon_stats(int total_pokemon_stats) {
		this.total_pokemon_stats = total_pokemon_stats;
	}

	public void updateTotal_pokemon_stats(int arg0) {
		this.total_pokemon_count +=1;
		this.total_pokemon_stats += arg0;
	}

	public String generateReport() {
		StringBuilder str = new StringBuilder();
		str.append("\n -> OPERATION REGISTER TRAINER LOG: " + LocalDateTime.now().toString() + " OF TRAINER: " + getName()+  " - " + getTrainer_id());
		str.append("\\n [TRAINER STATUS] \n");
		str.append("Trainer ID: " + trainer_id + " \n");
		str.append("Name: " + name + " \n");
		str.append("Age: " + age + " \n");
		str.append("Already a competitor? " + former_competitor + " \n");
		str.append("Total number of registered Pokémon: " + total_pokemon_count + " \n");
		str.append("Average Pokémon stats: " + total_pokemon_stats/total_pokemon_count + " \n");
		str.append("[END OF TRAINER REPORT] \n");
		return str.toString();
	}
	


}
