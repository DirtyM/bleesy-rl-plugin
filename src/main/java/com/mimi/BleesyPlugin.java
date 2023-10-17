package com.mimi;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

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
	private ExampleConfig config;

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
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Bleesy plugin is actief!:D", null);
		}
	}




	@Subscribe void onOverheadTextChanged(OverheadTextChanged overheadTextChanged){
		boolean blezerifySpeech=true;
		if (blezerifySpeech){
			if(!overheadTextChanged.getActor().equals(client.getLocalPlayer())){
				Actor actor = overheadTextChanged.getActor();
				actor.setOverheadText(getRandomOah()+", " +overheadTextChanged.getOverheadText()+getRandomNieZievernChance());
			}
		}

	}

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
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
