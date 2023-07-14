package dev.sshear.way4homes.fabric;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Way4Homes implements ModInitializer {
	public static final String MOD_ID = "way4homes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("init way4homes");
	}
}
