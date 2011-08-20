package com.firestar.proofBan;

import uk.co.oliwali.HawkEye.HawkEye;

import me.taylorkelly.bigbrother.BigBrother;

import java.util.logging.Logger;

import com.firestar.mcbans.mcbans;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import de.diddiz.LogBlock.LogBlock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin {
	static private Logger log = Logger.getLogger("Minecraft");
	private PermissionHandler Permissions = null;
	private HawkEye hawk = null;
	private mcbans mcb = null;
	private LogBlock logb = null;
	private BigBrother bigb = null;
	public void onDisable() {
		Message("Disabled proofBan");
	}
	public void onEnable() {
		Message("Enabled proofBan");
		setupPermissions();
		setupLogging();
		setupMCBans();
	}
	public void setupLogging(){
		Plugin he = getServer().getPluginManager().getPlugin("HawkEye");
        if (he != null){
        	hawk = (HawkEye) he;
        	Message("Found HawkEye!");
        }
        Plugin lb = getServer().getPluginManager().getPlugin("LogBlock");
        if(lb!=null){
        	logb = (LogBlock) lb;
        	Message("Found LogBlock!");
        }
        Plugin bb = getServer().getPluginManager().getPlugin("BigBrother");
        if(bb!=null){
        	bigb = (BigBrother) bb;
        	Message("Found LogBlock!");
        }
	}
	public void setupMCBans() {
		Plugin test = getServer().getPluginManager().getPlugin("mcbans");
		if(mcb == null) {
		    if(test != null) {
		    	mcb = ((mcbans)test);
		    	Message("Found MCBans!");
		    } else {
		    	Message("MCBans not found, disabling!");
		    	getServer().getPluginManager().disablePlugin(this);
		    }
		}
	}
	public void setupPermissions() {
		Plugin test = getServer().getPluginManager().getPlugin("Permissions");
		if(Permissions == null) {
		    if(test != null) {
		    	Permissions = ((Permissions)test).getHandler();
		    	Message("Found Permission Bridge!");
		    } else {
		    }
		}
	}
	public boolean hasPerm(Player player){
		if(Permissions==null){
			if(player.hasPermission("proofBan")){
				return true;
			}else{
				return false;
			}
		}else{
			if(Permissions.has(player, "proofBan")){
				return true;
			}else{
				return false;
			}
		}
	}
	public mcbans mcbansPlugin(){
		return mcb;
	}
	public LogBlock logblockPlugin(){
		return logb;
	}
	public BigBrother bigbrotherPlugin(){
		return bigb;
	}
	public HawkEye hawkeyePlugin(){
		return hawk;
	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		thread cmdhandle = null;
		if(!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		if(args.length==0){
			return true;
		}
		cmdhandle = new thread(this,args[0],player.getName(),args[1],getReason(args,"",2));
		cmdhandle.start();
		return true;
	}
	public void Message(String msg){
		log.info("proofBan: "+msg);
	}
	private String getReason(String[] args, String reason, int start) {
        for (int x = start; x < args.length; x++) {
            reason += reason.equalsIgnoreCase("") ? args[x] : " " + args[x];
        }
        return reason;
    }
}