package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class SignChange implements Listener {

	boolean changeColor;

	int changeProzent;

	public SignChange(String values) {
		String[] temp = values.split(", ");
		changeColor = Boolean.valueOf(temp[0]);
		changeProzent = Integer.valueOf(temp[1]);
		registerPermission();
	}

	private void registerPermission() {
		// TODO Auto-generated method stub
		List<Permission> perms = new ArrayList<>();
		String f = "sign";
		String pre = "freakout." + f + ".";

		perms.add(new Permission(pre + "color",
				"FreakOut Permission fuer " + f, PermissionDefault.OP));

		for (Permission p : perms) {
			Bukkit.getServer().getPluginManager().addPermission(p);
		}
	}

	Random r = new Random();

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		List<String> lines = new ArrayList<>();

		for (String line : e.getLines()) {
			if (changeColor
					&& e.getPlayer().hasPermission("freakout.sign.color")) {
				lines.add(ChatColor.translateAlternateColorCodes('&', line));
			} else {
				lines.add(line);
			}
		}

		if (r.nextInt(100) <= changeProzent - 1) {
			e.setLine(0, lines.get(r.nextInt(4)));
			e.setLine(1, lines.get(r.nextInt(4)));
			e.setLine(2, lines.get(r.nextInt(4)));
			e.setLine(3, lines.get(r.nextInt(4)));

		} else {
			e.setLine(0, lines.get(0));
			e.setLine(1, lines.get(1));
			e.setLine(2, lines.get(2));
			e.setLine(3, lines.get(3));
		}
	}
}
