package v3Freak;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {

	File f = new File("plugins/Fre4k0ut/messages.yml");

	Main main;

	public Messages(Main main) {
		copyDefaults();
		loadMessages();
		this.main = main;
	}

	HashMap<String, String> messages = new HashMap<>();

	private void loadMessages() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		for (String s : cfg.getKeys(false)) {
			messages.put(
					s.toLowerCase(),
					ChatColor.translateAlternateColorCodes('&',
							cfg.getString(s)));
		}
	}

	public String getMessage(String s) {
		return messages.get(s.toLowerCase());
	}

	private void copyDefaults() {

		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			cfg.addDefault("prefix", "&bFreak&cOut ");
			cfg.addDefault("disabledCommand",
					"&cDieser Command wurde gesperrt!");
			cfg.addDefault("unknownCMD", "§cDieser Command ist nicht bekannt!");
			cfg.addDefault("onlydirect",
					"§cDu darfst keine Plugingebundenen Befehle nutzen!");

			cfg.addDefault("join", "&8[&a&l+&8] &3PLAYER");
			cfg.addDefault("quit", "&8[&c&l-&8] &3PLAYER");
			cfg.addDefault("kick", "&8[&c&l-&8] &3PLAYER");
			cfg.addDefault("death",
					"&dDer Spieler &e%tot &dwurde von &e%killer &dgetötet!");
			cfg.addDefault("deathby", "&dDu wurdest von &c%killer &dgetötet!");
			cfg.addDefault("killed", "&dDu hast &c%tot &dgetötet!");
			cfg.addDefault("levellost", "&8Du hast &c%lvl &8verloren!");
			cfg.addDefault("levelearn", "&8Du hast &a%lvl &8bekommen!");
			cfg.addDefault("deathNoPlayer", "&dDu bist gestorben!");
			cfg.addDefault("enchant", "&cEnchanten wurde deaktiviert!");
			cfg.options().copyDefaults(true);
			cfg.options().header("Message File von FreakOut");
			cfg.options().copyHeader(true);
			cfg.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
