package net.HolyEnderman.HolyAway;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class IdleEvent implements Listener
{
  private HashMap<String, Integer> tasks;
  private HolyAway HolyAway;

  public void PlayerEvents()
  {
    this.tasks = new HashMap();
  }

  protected IdleEvent(HolyAway HolyAway) {
    this.HolyAway = HolyAway;
    HolyAway.getServer().getPluginManager().registerEvents(this, HolyAway);
  }

  public void onPlayerJoin(PlayerJoinEvent event)
  {
    if (event.getPlayer().hasPermission("kickidle.exempt")) return;
    if (this.tasks.containsKey(event.getPlayer().getName())) return;
    int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(HolyAway.getInstance(), new UpdateTask(event.getPlayer()), 20L * 30, 20L * 30);
    this.tasks.put(event.getPlayer().getName(), Integer.valueOf(id));
  }

  public void onPlayerQuit(PlayerQuitEvent event)
  {
    if (!this.tasks.containsKey(event.getPlayer().getName())) return;
    Bukkit.getScheduler().cancelTask(((Integer)this.tasks.get(event.getPlayer().getName())).intValue());
    this.tasks.remove(event.getPlayer().getName());
  }

  public class UpdateTask implements Runnable {
    private Player player;
    private Location lastLocation;

    public UpdateTask(Player player) {
      this.player = player;
      updatePlayer();
    }

    public void run()
    {
      if (this.player.getLocation().equals(this.lastLocation))
      {
        HolyAway.isAway = true;
        Bukkit.broadcastMessage(player.getName() + ChatColor.YELLOW + " is now afk " + ChatColor.BLUE + "(Away From Keyboard)");
        return;
      }
      else if ((!this.player.getLocation().equals(this.lastLocation)) && (HolyAway.isAway == true))
      {
    	  HolyAway.isAway = false;
          Bukkit.broadcastMessage(player.getName() + ChatColor.YELLOW + " is no longer afk.");
          return;
      }
      updatePlayer();
    }

    private void updatePlayer()
    {
      this.lastLocation = this.player.getLocation();
    }
  }
}