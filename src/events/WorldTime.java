package events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import v3Freak.Main;

public class WorldTime implements Listener {

	int randomSekonds;
	Plugin plugin;

	public WorldTime(String values, Main main) {
		String[] temp = values.split(", ");
		randomSekonds = Integer.valueOf(temp[0]);
		plugin = main;

		timeChanger();
	}

	final Random r = new Random();

	private void timeChanger() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.addPotionEffect(new PotionEffect(
							PotionEffectType.BLINDNESS, 15, 9));
				}
				for (World w : Bukkit.getWorlds()) {
					w.setTime(r.nextLong());
				}
			}
		}, randomSekonds * 20, randomSekonds * 20);
	}
}
