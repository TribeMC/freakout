package events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import v3Freak.Main;

public class WorldWeather implements Listener {
	int randomSekonds;
	Plugin plugin;

	public WorldWeather(String values, Main main) {
		String[] temp = values.split(", ");
		randomSekonds = Integer.valueOf(temp[0]);
		plugin = main;

		weatherChanger();
	}

	private void weatherChanger() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				
				for (World w : Bukkit.getWorlds()) {
					w.setThundering(r.nextBoolean());
					w.setStorm(r.nextBoolean());
				}
			}
		}, randomSekonds * 20, randomSekonds * 20);		
	}

	final Random r = new Random();
}
