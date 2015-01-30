package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import v3Freak.Messages;

public class PlayerEnchant implements Listener {

	Messages msg;

	boolean denyEnchant;

	public PlayerEnchant(String values, Messages msg) {
		this.msg = msg;

		String[] temp = values.split(", ");
		denyEnchant = Boolean.valueOf(temp[0]);
	}

	@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		if (denyEnchant) {
			e.setCancelled(true);
			e.getEnchanter().sendMessage(
					msg.getMessage("prefix") + msg.getMessage("enchant"));
		}
	}

}
