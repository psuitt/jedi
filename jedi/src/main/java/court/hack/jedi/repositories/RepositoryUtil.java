package court.hack.jedi.repositories;

import java.util.UUID;


public final class RepositoryUtil {
	
	public static String createUniqueId() {
		final UUID id1 = UUID.randomUUID();
		final UUID id2 = UUID.randomUUID();
		String hexString = Integer.toHexString(id1.hashCode()).toUpperCase() + Integer.toHexString(id2.hashCode()).toUpperCase();
		while (hexString.length() < 13) {
			UUID id3 = UUID.randomUUID();			
			hexString = hexString + Integer.toHexString(id3.hashCode()).toUpperCase();
		}		
		hexString = hexString.substring(0, 13);
		return hexString;
	}
}