package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDItem implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		// TODO Auto-generated method stub
		if (cs instanceof Player) {
			Player p = (Player) cs;
			p.sendMessage(Item(p.getItemInHand()));
		}
		return false;
	}

	private String Item(ItemStack i) {
		// TODO Auto-generated method stub
		String s = i.getType() + ", " + i.getAmount() + ", "
				+ i.getDurability() + ", ";
		if (i.getItemMeta().getDisplayName() == null) {
			s = s + "null";
		} else {
			s = s + i.getItemMeta().getDisplayName();

		}
		if (i.getItemMeta().getLore() == null) {
			s = s + ", null";

		} else {
			s = s + ", " + i.getItemMeta().getDisplayName();

		}
		return s;
	}

}
