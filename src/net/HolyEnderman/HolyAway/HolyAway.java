package net.HolyEnderman.HolyAway;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HolyAway extends JavaPlugin {
	public boolean isAway = false;
	public String awayReason = null;
	private static Plugin instance;
	
	@Override
	public void onEnable()
	{
		getLogger().info("HolyAway has been started!");
		instance = this;
		new IdleEvent(this);
	}
	@Override
	public void onDisable()
	{
		//Stuff will stop here
	}
	public Plugin getInstance()
	{
	    return instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("afk")) || (cmd.getName().equalsIgnoreCase("away"))) {
			if (isAway == false)
			{
				isAway = true;
				sender.sendMessage(ChatColor.DARK_GRAY + "You are now away.");
				if (args.length >= 1)
				{
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length; i++)
					{
						sb.append(args[i]).append(" ");
					}
					String awayReason = sb.toString().trim();
					Bukkit.broadcastMessage(sender.getName() + ChatColor.YELLOW + " is now afk " + ChatColor.BLUE + "(" + awayReason + ")");
				}
				else
				{
					Bukkit.broadcastMessage(sender.getName() + ChatColor.YELLOW + " is now afk " + ChatColor.BLUE + "(Away from Keyboard)"); 
				}
			}
			else if (isAway == true)
			{
				isAway = false;
				sender.sendMessage(ChatColor.DARK_GRAY + "You are no longer away.");
				Bukkit.broadcastMessage(sender.getName() + ChatColor.YELLOW + " is no longer afk");
			}
		}
		return true;
	}
}
