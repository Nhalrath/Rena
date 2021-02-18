package net.crimsonite.rena.engine;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.crimsonite.rena.database.DBUsers;

public class RoleplayEngine {
	
	public static class CommenceBattle {
		
		private static String player;
		private static String enemy;
		private static int playerHP;
		private static int enemyHP;
		private static int playerMP;
		private static int enemyMP;
		private static int playerDef;
		private static int enemyDef;
		private static int playerAttk;
		private static int enemyAttk;
		private static int playerLevel;
		private static int playerExp;
		
		/**
		 * @param player -The Discord UID of the player.
		 * @param enemy -The name of the enemy.
		 * @return damage -The damage dealt by the player.
		 * @throws JsonProcessingException
		 * @throws IOException
		 */
		public static int attack(String player, String enemy) throws JsonProcessingException, IOException {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode enemyData = mapper.readTree(new File("./src/main/resources/rp_assets/enemy.json"));
			
			playerAttk = Integer.parseInt(DBUsers.getValueString(player, "Attk"));
			int enemyDef = enemyData.get(enemy).get("Def").asInt();
			int damage = playerAttk/(25/(25+enemyDef));
			
			return damage;
		}
		
		private static boolean checkExp(int level, int exp) {
			int nextLevel = level += 1;
			int requiredExpForNextLevel = 50*nextLevel*(nextLevel+1);
			
			if (exp >= requiredExpForNextLevel) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Handles the levelup of the player.
		 * 
		 * @param -The Discord UID of the player.
		 */
		public static void handleLevelup(String player) {
			playerLevel = Integer.parseInt(DBUsers.getValueString(player, "level"));
			playerExp = Integer.parseInt(DBUsers.getValueString(player, "exp"));
			
			boolean increment = checkExp(playerLevel, playerExp);
			
			if (increment) {
				while (increment) {
					playerLevel += 1;
					increment = checkExp(playerLevel, playerExp);
				}
				
				DBUsers.incrementValue(player, "level", playerLevel);
			}
		}
		
	}

}
