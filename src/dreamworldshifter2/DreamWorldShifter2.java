/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamworldshifter2;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jia Yi
 */
public class DreamWorldShifter2 extends JavaPlugin implements Listener {

    World DreamWorld;
    World MainWorld;
    
    HashMap<Player, Location> Stored;
    HashMap<Player, Location> Stored2;

    public void onEnable() {
        
        Stored = new HashMap<>();
        Stored2 = new HashMap<>();
        DreamWorld = getServer().createWorld(new WorldCreator("Test"));
        MainWorld = getServer().createWorld(new WorldCreator("world"));
        getServer().getPluginManager().registerEvents(this, this);
        
    }
    
    public void onDisable() {
        
        Stored = null;
        Stored2 = null;
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("Dreamstore")) {
            Stored.put(p, p.getLocation());
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("Dreamtp")) {
            p.teleport(Stored.get(p));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("Dreamtpother")) {
            if (p.getWorld() != DreamWorld)
                p.teleport(DreamWorld.getSpawnLocation());
            else
                p.teleport(MainWorld.getSpawnLocation());
            return true;
        }
        return false;
    }
    
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        
        Player p = event.getPlayer();
        /*
        if (event.getPlayer().getWorld() != DreamWorld)
            event.getPlayer().teleport(DreamWorld.getSpawnLocation());
        else
            event.getPlayer().teleport(MainWorld.get);
        */
        
        if (p.getWorld() != DreamWorld) {
            Stored.put(p, p.getLocation());
            if (Stored2.get(p) == null)
                p.teleport(DreamWorld.getSpawnLocation());
            else
                p.teleport(Stored2.get(p));
        }
        else {
            Stored2.put(p, p.getLocation());
            if (Stored.get(p) == null)
                p.teleport(MainWorld.getSpawnLocation());
            else
                p.teleport(Stored.get(p));
        }
        
        /*p.sendMessage("Current Bed Spawn " + p.getBedSpawnLocation().getWorld().getName());
        if (Stored.get(p) != null)
            p.sendMessage("Stored Bed Spawn " + Stored.get(p).getWorld().getName());
        
        if (Stored.get(p) == null) {
            p.sendMessage("First " + p.getWorld().getName());
            Stored.put(p, event.getBed().getLocation());
            p.sendMessage("First " + p.getWorld().getName());
            if (p.getWorld() != DreamWorld) {
                p.sendMessage ("Teleported player to dreamworld.");
                p.teleport(DreamWorld.getSpawnLocation());
            }
            else {
                p.sendMessage ("Teleported player to the real world.");
                p.teleport(MainWorld.getSpawnLocation());
            }
        }
        
        else if (Stored.get(p) != null && Stored.get(p).getWorld() != p.getWorld()) {
            p.sendMessage("Current world: " + p.getWorld().getName());
            Location temp = Stored.get(p);
            p.sendMessage ("Teleported Location: " + temp.getWorld().getName());
            Stored.put(p, event.getBed().getLocation());
            p.sendMessage ("Placed Location " + Stored.get(p).getWorld().getName() + " into arraylist.");
            p.teleport(temp);
            p.setBedSpawnLocation(temp);
        }
        else
            p.sendMessage("Did nothing");
        */
    }
    
}
