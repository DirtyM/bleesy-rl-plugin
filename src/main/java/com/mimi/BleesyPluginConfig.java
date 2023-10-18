package com.mimi;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bleesy plugin")
public interface BleesyPluginConfig extends Config
{

	enum OptionEnum
	{
		ALLBUTME,
		INCLUDELIST,
		EXCLUDELIST,
		OFF
	}
	@ConfigItem(
			position = 1,
			keyName = "mode",
			name = "verblezeringsmodus",
			description = "select which bleest mode you want"
	)
	default OptionEnum bleesymode() { return OptionEnum.ALLBUTME; }

	@ConfigItem(
			position = 2,
			keyName = "include_list",
			name = "includelist",
			description = "when includelist method is activated, only the usernames here will be bleesified. Separate names with a comma."
	)
	default String include_list() { return ""; }

	@ConfigItem(
			position = 2,
			keyName = "exclude_list",
			name = "excludelist",
			description = "when excludelist method is activated, only the usernames not in this list will be bleesified. Separate names with a comma."
	)
	default String exclude_list() { return ""; }


	@ConfigItem(
			position = 3,
			keyName = "teusjes_bool",
			name = "Teusjes warning",
			description = "when activated, the character will tell you when there is booze on the ground"
	)
	default boolean teusjes_bool() { return true; }



}
