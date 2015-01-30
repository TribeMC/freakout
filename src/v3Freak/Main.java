package v3Freak;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	ConsoleCommandSender cs = Bukkit.getConsoleSender();

	Messages msg = new Messages(this);

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getConfig().options().copyHeader(true);
		saveConfig();

		if (!registerEvents()) {
			cs.sendMessage("§cFEHLER: §eEvents konnten §cnicht §eregistriert werden!");
		}
		if (!registerCommands()) {
			cs.sendMessage("§cFEHLER: §eCommands konnten §cnicht §eregistriert werden!");
		} else {
			cs.sendMessage("§eCommands §aerfolgreich §eregistriert!");
		}

		if (!addRecipes()) {
			cs.sendMessage("§cFEHLER: §eRezepte konnten §cnicht §eregistriert werden!");

		}

		cs.sendMessage("§a§lDONE: §ePlugin wurde geladen!");
	}

	@SuppressWarnings({ "deprecation", "unused" })
	private boolean addRecipes() {
		for (String s : getConfig().getStringList("Recipes.Furnace")) {
			String[] temp = s.split(":from:");
			ItemStack result = new ItemStack(Material.getMaterial(Integer
					.valueOf(temp[0])));

			FurnaceRecipe fr = new FurnaceRecipe(result,
					Material.getMaterial(Integer.valueOf(temp[1])));
			Bukkit.addRecipe(fr);
		}
		for (String s : getConfig().getStringList("Recipes.Craft")) {
			String[] temp = s.split(":from:");
			String[] wb = temp[1].split(", ");
			ShapelessRecipe rz = new ShapelessRecipe(Item(temp[0]));

		}
		return true;
	}

	private ItemStack Item(String s) {
		// TODO Auto-generated method stub
		String[] t = s.split(", ");
		ItemStack i = new ItemStack(Material.getMaterial(t[0]),
				Integer.valueOf(t[1]), Short.valueOf(t[2]));
		ItemMeta im = i.getItemMeta();
		if (!t[3].equals("null")) {
			im.setDisplayName(t[3]);
		}
		if (!t[4].equals("null")) {
			List<String> lore = new ArrayList<>();
			for (String lores : t[4].split("ads")) {
				lore.add(lores);
			}
			im.setLore(lore);
		}
		i.setItemMeta(im);
		return i;
	}

	@SuppressWarnings("unused")
	private String Item(ItemStack i) {
		// TODO Auto-generated method stub
		String s = i.getType() + ", " + i.getAmount() + ", "
				+ i.getDurability() + ", " + i.getItemMeta().getDisplayName()
				+ ", " + i.getItemMeta().getLore().toString();

		return s;
	}

	private boolean registerCommands() {
		// TODO Auto-generated method stub
		getCommand("item").setExecutor(new commands.CMDItem());
		getCommand("item").setTabCompleter(new commands.TabItem());
		return true;
	}

	private boolean registerEvents() {
		int events = 0;
		if (isEnabled("TPonDamage")) {
			getServer().getPluginManager().registerEvents(
					new events.TeleportOnDemage(getValues("TPonDamage")), this);
			events++;
		}
		if (isEnabled("RandomFall")) {
			getServer().getPluginManager().registerEvents(
					new events.RandomFall(getValues("RandomFall")), this);
			events++;
		}
		if (isEnabled("BlockPlace")) {
			getServer().getPluginManager().registerEvents(
					new events.BlockPlace(getValues("BlockPlace")), this);
			events++;
		}
		if (isEnabled("BlockBreak")) {
			getServer().getPluginManager().registerEvents(
					new events.BlockBreak(getValues("BlockBreak")), this);
			events++;
		}
		if (isEnabled("CommandManagement")) {
			getServer().getPluginManager().registerEvents(
					new events.CommandManagement(
							getValues("CommandManagement"), msg), this);
			events++;
		}
		if (isEnabled("SignChange")) {
			getServer().getPluginManager().registerEvents(
					new events.SignChange(getValues("SignChange")), this);
			events++;
		}

		if (isEnabled("Join")) {
			getServer().getPluginManager().registerEvents(
					new events.Join(getValues("Join"), msg), this);
			events++;
		}
		if (isEnabled("Quit")) {
			getServer().getPluginManager().registerEvents(
					new events.Quit(getValues("Quit"), msg), this);
			events++;
		}
		if (isEnabled("Kick")) {
			getServer().getPluginManager().registerEvents(
					new events.Kick(getValues("Kick"), msg), this);
			events++;
		}
		if (isEnabled("BettEnter")) {
			getServer().getPluginManager().registerEvents(
					new events.BettEnter(getValues("BettEnter")), this);
			events++;
		}
		if (isEnabled("BettLeave")) {
			getServer().getPluginManager().registerEvents(
					new events.BettLeave(getValues("BettLeave"), msg), this);
			events++;
		}
		if (isEnabled("EntityBurn")) {
			getServer().getPluginManager().registerEvents(
					new events.EntityBurn(getValues("EntityBurn"), msg), this);
			events++;
		}
		if (isEnabled("Kill")) {
			getServer().getPluginManager().registerEvents(
					new events.PlayerKill(getValues("Kill"), msg), this);
			events++;
		}
		if (isEnabled("Enchant")) {
			getServer().getPluginManager().registerEvents(
					new events.PlayerEnchant(getValues("Enchant"), msg), this);
			events++;
		}
		if (isEnabled("Time")) {
			getServer().getPluginManager().registerEvents(
					new events.WorldTime(getValues("Time"), this), this);
			events++;
		}
		if (isEnabled("Weather")) {
			getServer().getPluginManager().registerEvents(
					new events.WorldWeather(getValues("Weather"), this), this);
			events++;
		}
		cs.sendMessage("§eEs wurden §c" + events
				+ " §eEvents §aerfolgreich §eregistriert!");
		return true;
	}

	private String getValues(String s) {
		return getConfig().getString(s + ".values");
	}

	private boolean isEnabled(String s) {
		return getConfig().getBoolean(s + ".enabled");
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
}
