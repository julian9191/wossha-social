package com.wossha.social.wsCommands;

import java.io.IOException;
import com.wossha.msbase.commands.ICommandSerializer;

public abstract class WSCommandSerializer implements ICommandSerializer {

	public abstract WSCommand deserialize(String json) throws IOException;

}
