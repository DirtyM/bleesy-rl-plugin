package com.mimi;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class BleesyPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BleesyPluginConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Bleesy plugin is active!:D", null);
		}
	}


	@Subscribe
	void onItemSpawned(ItemSpawned itemSpawned){
		if (config.teusjes_bool()){
			int[] drankids = {ItemID.BEER, ItemID.WHISKY, ItemID.JUG_OF_WINE};
			if(ArrayUtils.contains(drankids,itemSpawned.getItem().getId())){
				client.getLocalPlayer().setOverheadCycle(200);
				client.getLocalPlayer().setOverheadText("oah, daar ligt een teusje, nie zievern!");
			}
		}

	}

	@Subscribe
	void onOverheadTextChanged(OverheadTextChanged overheadTextChanged){
		if(!config.bleesymode().equals(BleesyPluginConfig.OptionEnum.OFF)){
			if(config.bleesymode().equals(BleesyPluginConfig.OptionEnum.ALLBUTME)){
				if(!overheadTextChanged.getActor().equals(client.getLocalPlayer())){
					Actor actor = overheadTextChanged.getActor();
					actor.setOverheadText(getRandomOah()+", " +overheadTextChanged.getOverheadText()+getRandomNieZievernChance());
				}
			} else if (config.bleesymode().equals(BleesyPluginConfig.OptionEnum.INCLUDELIST)) {
				String[] includelist = config.include_list().split(",");
				Actor actor = overheadTextChanged.getActor();
				if(ArrayUtils.contains(includelist,actor.getName())){
					actor.setOverheadText(getRandomOah()+", " +overheadTextChanged.getOverheadText()+getRandomNieZievernChance());
				}

			} else if (config.bleesymode().equals(BleesyPluginConfig.OptionEnum.EXCLUDELIST)) {
				String[] excludelist = config.exclude_list().split(",");
				Actor actor = overheadTextChanged.getActor();
				if((!ArrayUtils.contains(excludelist,actor.getName()))&&(!overheadTextChanged.getActor().equals(client.getLocalPlayer()))){
					actor.setOverheadText(getRandomOah()+", " +overheadTextChanged.getOverheadText()+getRandomNieZievernChance());
				}
			}
		}

	}


//helper functions...
	
	public String getRandomNieZievernChance(){
		String returnstr="";
		Random random=new Random();
		int r = random.nextInt(100);
		if (r%20 == 0){
			returnstr=", nie zievern!";
		}
		if (r==20){
			returnstr=", geen geintjes!";
		}
		return returnstr;
	}


	public String getRandomOah(){
		String[] possibleOahs= new String[]{"Oah", "Ooah", "Oooaaah", "Oaah", "Ooooooah","Oh"};
		Random random = new Random();
		int index = random.nextInt(possibleOahs.length);
		return possibleOahs[index];
	}

	@Provides
	BleesyPluginConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BleesyPluginConfig.class);
	}
}
