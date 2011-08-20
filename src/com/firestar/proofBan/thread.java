package com.firestar.proofBan;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;



import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.QueryParams.BlockChangeType;


public class thread extends Thread {
	private String player;
	private main plugin = null;
	private String admin;
	private String world;
	private String reason;
	public thread(main g, String playerName, String adminName, String World, String Reason){
		plugin = g;
		player = playerName;
		admin = adminName;
		world = World;
		reason = Reason;
	}
	public void run(){
		if(plugin.logblockPlugin()!=null){
			QueryParams params = new QueryParams(plugin.logblockPlugin());
			params.setPlayer(player);
			params.bct = BlockChangeType.CREATED;
			params.limit = -1;
			params.minutes = 1440;
			params.world = plugin.getServer().getWorld(world);
			params.needDate = true;
			params.needType = true;
			params.needData = true;
			params.needPlayer = true;
			ArrayList<HashMap<String,String>> g = new ArrayList<HashMap<String,String>>();
			HashMap<String,String> tmp = null;
			try {
				for (BlockChange bc : plugin.logblockPlugin().getBlockChanges(params)){
					tmp = new HashMap<String,String>();
					tmp.put("d", String.valueOf(bc.date));
					tmp.put("y", String.valueOf(bc.loc.getY()));
					tmp.put("x", String.valueOf(bc.loc.getX()));
					tmp.put("z", String.valueOf(bc.loc.getZ()));
					tmp.put("p", String.valueOf(bc.playerName));
					tmp.put("t", String.valueOf(bc.type));
					tmp.put("r", String.valueOf(bc.replaced));
					g.add(tmp);
				}
				HashMap<String,String> items = new HashMap<String,String>();
				items.put("exec", "evidence");
				items.put("player", player);
				items.put("admin", admin);
				items.put("changes", g.toString());
				String derp = urlparse(items);
				URL url = new URL("http://72.10.39.172/v2/"+plugin.mcbansPlugin().api_key);
	    	    URLConnection conn = url.openConnection();
	    	    conn.setConnectTimeout(15000);
	    	    conn.setReadTimeout(15000);
	    	    conn.setDoOutput(true);
	    	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    	    wr.write(derp);
	    	    wr.flush();
				wr.close();
	    	    Player f = plugin.getServer().getPlayer(admin);
	    	    if(f!=null){
	    	    	f.sendMessage("["+ChatColor.DARK_AQUA+"Proof"+ChatColor.WHITE+"]"+ChatColor.LIGHT_PURPLE+" Report Sent!");
	    	    }
	    	    plugin.mcbansPlugin().mcb_handler.ban( player, admin, reason, "g" );
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(plugin.hawkeyePlugin()!=null){
			/*if(plugin.getServer().getPlayer(admin)!=null){
				SearchParser parser = new SearchParser();
				parser.player = plugin.getServer().getPlayer(admin);
				parser.radius = 5;
				parser.actions = Arrays.asList({DataType.BLOCK_BREAK, DataType.BLOCK_PLACE});
				parser.parseLocations();
	
				//Call search function
				performSearch(new hawkeSearch(plugin.getServer().getPlayer(admin)), parser, SearchDir.DESC);
			}*/
		}else if(plugin.bigbrotherPlugin()!=null){
			
		}
	}
	public String urlparse(HashMap<String,String> items){
		String data = "";
		try {
			for ( Map.Entry<String, String> entry : items.entrySet() ){
				String key = entry.getKey();
				String val = entry.getValue();
				if(data.equals("")){
					data = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
				}else{
					data += "&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
		}
		return data;
	}
	
}