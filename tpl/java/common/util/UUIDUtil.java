package {{groupid}}.common.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String s=uuid.toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
	
	public static String getUUID(String message){
		UUID uuid = UUID.fromString(message);
		return uuid.toString();
	}

}
