package me.vik1395.TNTIgniter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Main extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		
		wgd = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if ((wgd != null))
        {
            System.out.println("[RandMan] hooked into WorldGuard.");
            wgc = true;
        }
        
		getLogger().info("TNTAutoIgniter has started successfully");
		getLogger().info("Created by Vik1395");
	}
	
	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent bpe)
	{
		Block bl = bpe.getBlockPlaced();
		Player p = bpe.getPlayer();
		
		if(bl.getType().equals(Material.TNT))
		{
			if(p.hasPermission("TNTAutoIgniter.disabled"))
			{
				
			}
			else if(p.hasPermission("TNTAutoIgniter.special"))
			{
				if(!p.isSneaking())
				{
					AutoIgnite(bpe);
				}
			}
			else
			{
				AutoIgnite(bpe);
			}
		}
	}
	
	private void AutoIgnite(BlockPlaceEvent bpe)
	{
		if(wgc==true)
        {
        	WorldGuardPlugin wg = (WorldGuardPlugin) wgd;
            Location loc = bpe.getBlock().getLocation();
            int num = bpe.getPlayer().getItemInHand().getAmount();
			Material typ = bpe.getPlayer().getItemInHand().getType();
			
			if(typ.equals(Material.TNT))
			{
				ItemStack is1 = new ItemStack(Material.TNT, num-1);
				bpe.getPlayer().setItemInHand(is1);
			}
			
    		if(wg.canBuild(bpe.getPlayer(), loc))
    		{
    			loc.getBlock().setType(Material.AIR);
    			loc.setY(loc.getY() + 0.9f);
    			loc.setX(loc.getX() + 0.5f);
    			loc.setZ(loc.getZ() + 0.5f);
    			loc.getWorld().spawn(loc, TNTPrimed.class);
    			
    		}
        	else
        	{
        		ItemStack is2 = new ItemStack(Material.TNT, num);
				bpe.getPlayer().setItemInHand(is2);
        	}
            
            
        }
		
		else
		{
			Location loc = bpe.getBlock().getLocation();
			loc.getBlock().setType(Material.AIR);
			loc.setY(loc.getY() + 0.9f);
			loc.setX(loc.getX() + 0.5f);
			loc.setZ(loc.getZ() + 0.5f);
			loc.getWorld().spawn(loc, TNTPrimed.class);
		}
	}
	private Plugin wgd;
    private boolean wgc = false;
}
