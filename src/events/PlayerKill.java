package events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import v3Freak.Messages;

public class PlayerKill implements Listener {

	Messages msg;

	boolean payLevel;
	int payProzent;

	boolean lvlProKill;
	int amountLvL;

	boolean brodcast;

	public PlayerKill(String values, Messages msg) {
		this.msg = msg;
		String[] temp = values.split(", ");

		payLevel = Boolean.valueOf(temp[0]);
		payProzent = Integer.valueOf(temp[1]);
		lvlProKill = Boolean.valueOf(temp[2]);
		amountLvL = Integer.valueOf(temp[3]);
		brodcast = Boolean.valueOf(temp[4]);
	}

	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		e.setDeathMessage("");
		if (e.getEntity().getKiller() instanceof Player) {
			Player killer = (Player) e.getEntity().getKiller();
			Player tot = e.getEntity();

			if (brodcast) {
				e.setDeathMessage(msg.getMessage("death")
						.replace("%tot", tot.getName())
						.replace("%killer", killer.getName()));
			} else {
				tot.sendMessage(msg.getMessage("prefix")
						+ msg.getMessage("deathby").replace("%killer",
								killer.getName()));
				killer.sendMessage(msg.getMessage("prefix")
						+ msg.getMessage("killed").replace("%tot",
								tot.getName()));
			}

			if (lvlProKill || payLevel) {
				int lvl = 0;

				if (payLevel) {
					lvl = getLevel(tot);
					tot.setLevel(tot.getLevel() - lvl);
					tot.sendMessage(msg.getMessage("prefix")
							+ msg.getMessage("levellost").replace("%lvl",
									lvl + ""));

				}

				if (lvlProKill) {
					lvl = lvl + amountLvL;
					killer.sendMessage(msg.getMessage("prefix")
							+ msg.getMessage("levelearn").replace("%lvl",
									lvl + ""));
					killer.setLevel(killer.getLevel() + lvl);
				} else {
					killer.sendMessage(msg.getMessage("prefix")
							+ msg.getMessage("levelearn").replace("%lvl",
									lvl + ""));
					killer.setLevel(killer.getLevel() + lvl);

				}
			}
		} else {
			e.getEntity().sendMessage(
					msg.getMessage("prefix") + msg.getMessage("deathNoPlayer"));
		}
	}

	private int getLevel(Player tot) {
		// TODO Auto-generated method stub
		int lvl = tot.getLevel();
		if (lvl != 0) {
			double topay = lvl / (payProzent / 100);
			return (int) topay;
		}
		return 0;
	}

}
