package org.mule.miw.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Pokemon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String pokemon_name;
	private String pokeball;
	private String nature;
	private String type1;
	private String type2;
	private String original_trainer;
	private int ps;
	private int attack;
	private int defense;
	private int special_attack;
	private int special_defense;
	private int speed;
	private int ps_evs;
	private int attack_evs;
	private int defense_evs;
	private int special_attack_evs;
	private int special_defense_evs;
	private int speed_evs;

	private Trainer trainer;

	// Flags que modificaremos según la lógica de negocio

	private boolean legendary;
	private boolean illegal = false;
	private String illegal_reasoning = "";
	private int calificacion;
	private boolean valid_pokemon = false;

	public Pokemon(HashMap<String, String> arg0) {
		this.nickname = arg0.get("nickname");
		this.nickname = arg0.get("nickname");
		this.pokemon_name = arg0.get("pokemon_name");
		this.pokeball = arg0.get("pokeball");
		this.nature = arg0.get("nature");
		this.type1 = arg0.get("type1");
		if (arg0.get("type2").equalsIgnoreCase("none")) {
			this.type2 = null;
		} else {
			this.type2 = arg0.get("type2");
		}
		this.original_trainer = arg0.get("original_trainer");
		this.ps = Integer.parseInt(arg0.get("ps"));
		this.attack = Integer.parseInt(arg0.get("attack"));
		this.defense = Integer.parseInt(arg0.get("defense"));
		this.special_attack = Integer.parseInt(arg0.get("special_attack"));
		this.special_defense = Integer.parseInt(arg0.get("special_defense"));
		this.speed = Integer.parseInt(arg0.get("speed"));
		this.ps_evs = Integer.parseInt(arg0.get("ps_evs"));
		this.attack_evs = Integer.parseInt(arg0.get("attack_evs"));
		this.defense_evs = Integer.parseInt(arg0.get("defense_evs"));
		this.special_attack_evs = Integer.parseInt(arg0.get("special_attack_evs"));
		this.special_defense_evs = Integer.parseInt(arg0.get("special_defense_evs"));
		this.speed_evs = Integer.parseInt(arg0.get("speed_evs"));
	}

	public Pokemon(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6,
			String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14,
			String arg15, String arg16, String arg17, String arg18, Trainer arg19) {
		super();
		this.nickname = arg0;
		this.pokemon_name = arg1;
		this.pokeball = arg2;
		this.nature = arg3;
		this.type1 = arg4;
		if (arg5.equalsIgnoreCase("none")) {
			this.type2 = null;
		} else {
			this.type2 = arg5;
		}
		this.original_trainer = arg6;
		this.ps = Integer.parseInt(arg7);
		this.attack = Integer.parseInt(arg8);
		this.defense = Integer.parseInt(arg9);
		this.special_attack = Integer.parseInt(arg10);
		this.special_defense = Integer.parseInt(arg11);
		this.speed = Integer.parseInt(arg12);
		this.ps_evs = Integer.parseInt(arg13);
		this.attack_evs = Integer.parseInt(arg14);
		this.defense_evs = Integer.parseInt(arg15);
		this.special_attack_evs = Integer.parseInt(arg16);
		this.special_defense_evs = Integer.parseInt(arg17);
		this.speed_evs = Integer.parseInt(arg18);
		this.trainer = arg19;
	}

	public void comprobacionPokeball() {
		if (this.legendary && !this.pokeball.equals("cherishball")) {
			setIllegal(true);
			appendIllegalReasoning("Legendary pokémon with nickname " + getNickname()
					+ " hasn't been caught using the correct pokéball ( Expected a cherish ball but was a " + pokeball
					+ " )");
		}
	}

	public void comprobacionEntrenadorOriginal() {
		if (!trainer.getTrainer_id().equalsIgnoreCase(original_trainer)) {
			setIllegal(true);
			appendIllegalReasoning(
					"Pokémon with nickname " + getNickname() + " is not from the expected trainer ( Expected id: "
							+ original_trainer + " and was " + trainer.getTrainer_id() + " )");
		}
	}

	public void comprobacionTiposLegendarios() {
		String archivoConfiguracion = "C:\\Users\\javie\\AnypointStudio\\studio-workspace\\entrega\\src\\legendaries.properties";
		try (FileInputStream fis = new FileInputStream(archivoConfiguracion);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {

			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split("=");
				String nombrePokemon = partes[0].trim();
				if (nombrePokemon.equalsIgnoreCase(getPokemon_name())) {
					setLegendary(true);
					String[] types = partes[1].split(",");
					int needed_comprobations = types.length;
					for (String type : types) {
						if (type.equalsIgnoreCase(type1) || type.equalsIgnoreCase(type2)) {
							needed_comprobations -= 1;
						}
					}
					if (needed_comprobations != 0) {
						setIllegal(true);
						String expected_types = "";
						for (String type : types) {
							expected_types += type + " ";
						}
						appendIllegalReasoning("Legendary pokémon with nickname " + getNickname()
								+ " doesn't have the expected types ( " + expected_types + ")");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tratamientoPokemonIlegal() {
		this.pokemon_name = "MissingNo";
		this.type1 = "null";
		this.type2 = "null";
		this.ps = 0;
		this.attack = 0;
		this.defense = 0;
		this.speed = 0;
		this.special_attack = 0;
		this.special_defense = 0;
		this.resetEVS();
	}

	public void comprobacionEVS() {
		if (ps_evs + attack_evs + defense_evs + special_attack_evs + special_defense_evs + speed_evs > 504) {
			setIllegal(true);
			appendIllegalReasoning("Pokémon with nickname " + getNickname() + " has over than 504 evs");
		}
	}

	public void computarEVS() {
		setPs(getPs() + getPs_evs());
		setAttack(getAttack() + getAttack_evs());
		setDefense(getDefense() + getDefense_evs());
		setSpecial_attack(getSpecial_attack() + getSpecial_attack_evs());
		setSpecial_defense(getSpecial_defense() + getSpecial_defense_evs());
		setSpeed(getSpeed() + getSpeed_evs());
	}

	public void resetEVS() {
		this.setPs_evs(0);
		this.setAttack_evs(0);
		this.setDefense_evs(0);
		this.setSpeed_evs(0);
		this.setSpecial_attack_evs(0);
		this.setSpecial_defense_evs(0);
	}

	public void pokemonExists() {
		String archivoConfiguracion = "C:\\Users\\javie\\AnypointStudio\\studio-workspace\\entrega\\src\\pokemon.properties";
		try (FileInputStream fis = new FileInputStream(archivoConfiguracion);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().equalsIgnoreCase(pokemon_name)) {
					valid_pokemon = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateTrainer() {
		trainer.updateTotal_pokemon_stats(getAllStats());

	}

	public boolean isValid_pokemon() {
		return valid_pokemon;
	}

	public void setValid_pokemon(boolean valid_pokemon) {
		this.valid_pokemon = valid_pokemon;
	}

	public int getAllStats() {
		return ps + attack + defense + speed + special_attack + special_defense;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPokemon_name() {
		return pokemon_name;
	}

	public void setPokemon_name(String pokemon_name) {
		this.pokemon_name = pokemon_name;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getOriginal_trainer() {
		return original_trainer;
	}

	public void setOriginal_trainer(String original_trainer) {
		this.original_trainer = original_trainer;
	}

	public int getPs() {
		return ps;
	}

	public void setPs(int ps) {
		this.ps = ps;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpecial_attack() {
		return special_attack;
	}

	public void setSpecial_attack(int special_attack) {
		this.special_attack = special_attack;
	}

	public int getSpecial_defense() {
		return special_defense;
	}

	public void setSpecial_defense(int special_defense) {
		this.special_defense = special_defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPs_evs() {
		return ps_evs;
	}

	public void setPs_evs(int ps_evs) {
		this.ps_evs = ps_evs;
	}

	public int getAttack_evs() {
		return attack_evs;
	}

	public void setAttack_evs(int attack_evs) {
		this.attack_evs = attack_evs;
	}

	public int getDefense_evs() {
		return defense_evs;
	}

	public void setDefense_evs(int defense_evs) {
		this.defense_evs = defense_evs;
	}

	public int getSpecial_attack_evs() {
		return special_attack_evs;
	}

	public void setSpecial_attack_evs(int special_attack_evs) {
		this.special_attack_evs = special_attack_evs;
	}

	public int getSpecial_defense_evs() {
		return special_defense_evs;
	}

	public void setSpecial_defense_evs(int special_defense_evs) {
		this.special_defense_evs = special_defense_evs;
	}

	public int getSpeed_evs() {
		return speed_evs;
	}

	public void setSpeed_evs(int speed_evs) {
		this.speed_evs = speed_evs;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer arg0) {
		this.trainer = arg0;
	}

	public String getPokeball() {
		return pokeball;
	}

	public void setPokeball(String pokeball) {
		this.pokeball = pokeball;
	}

	public boolean isLegendary() {
		return legendary;
	}

	public void setLegendary(boolean legendary) {
		this.legendary = legendary;
	}

	public boolean isIllegal() {
		return illegal;
	}

	public void setIllegal(boolean illegal) {
		this.illegal = illegal;
	}

	public String getIllegal_reasoning() {
		return illegal_reasoning;
	}

	public void setIllegal_reasoning(String illegal_reasoning) {
		this.illegal_reasoning = illegal_reasoning;
	}

	public void appendIllegalReasoning(String text) {
		this.illegal_reasoning = getIllegal_reasoning() + " \n " + text;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public String generateReport() {
		StringBuilder str = new StringBuilder();
		str.append("\n -> OPERATION REGISTER POKEMON LOG: " + LocalDateTime.now().toString() + " OF TRAINER: " + trainer.getName()+  " - " + trainer.getTrainer_id());
		if (!this.valid_pokemon) {
			str.append("\n\t [INVALID POKEMON INFO] \n");
			str.append("\t Pokémon with nickname " + nickname + " doesn't exist \n");
		} else if (this.illegal) {
			str.append("\n\t [ILLEGAL POKEMON INFO] \n");
			str.append("\t Pokémon with nickname " + nickname
					+ " is an illegally modified Pokémon thus it was converted into a MissingNo\n");
			str.append("\t Reasons why the Pokémon is a modified one: " + illegal_reasoning + " \n");
			str.append("\t Pokémon species: " + pokemon_name + " \n");
			str.append("\t ATTACK: " + attack + " \n");
			str.append("\t DEFENSE: " + defense + " \n");
			str.append("\t SPECIAL_ATTACK: " + special_attack + " \n");
			str.append("\t SPECIAL_DEFENSE: " + special_defense + " \n");
			str.append("\t SPEED: " + speed + " \n");
		} else {
			str.append("\n\t [POKEMON INFO] \n");
			str.append("\t Nickname: " + nickname + " \n");
			str.append("\t Pokémon species: " + pokemon_name + " \n");
			str.append("\t Captured pokéball: " + pokeball + " \n");
			str.append("\t Original Trainer ID " + original_trainer + " \n");
			str.append("\t Nature: " + nature + " \n");
			str.append("\t Types: " + type1 + " " + type2 != null ? "" : type2 + " \n");
			str.append("\t PS: " + +ps + " \n");
			str.append("\t ATTACK: " + attack + " \n");
			str.append("\t DEFENSE: " + defense + " \n");
			str.append("\t SPECIAL_ATTACK: " + special_attack + " \n");
			str.append("\t SPECIAL_DEFENSE: " + special_defense + " \n");
			str.append("\t SPEED: " + speed + " \n");
			str.append("\t Legendary Pokémon?: " + legendary + " \n");
		}
		str.append("\t [END OF POKEMON INFO] \n");
		return str.toString();

	}

}
