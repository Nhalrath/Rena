/*
 * Copyright (C) 2020-2021  Nhalrath
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.crimsonite.rena.commands.moderation;

import net.crimsonite.rena.commands.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class UnbanCommand extends Command {

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		Member author = event.getMember();
		MessageChannel channel = event.getChannel();
		
		try {
			if (author.hasPermission(Permission.BAN_MEMBERS)) {	
				User user = event.getJDA().retrieveUserById(args[1]).complete();
				
				if (args.length <= 2) {
					event.getGuild().unban(user).complete();
					channel.sendMessageFormat("**Successfully unbanned %s!**", user.getName()).queue();
				}
				else {
					String reason = args[2];
					event.getGuild().unban(user).reason(reason).complete();
					channel.sendMessageFormat("**Successfully unbanned %s! Reason: %s**", user.getName(), reason).queue();
				}
			}
			else {
				channel.sendMessage("**Sorry, but you dont have the permission to do that.\nRequired: BAN_MEMBERS permission**").queue();
			}
		}
		catch (IllegalArgumentException ignored) {
			channel.sendMessage("**That is not a valid ID.**").queue();
		}
		catch (ErrorResponseException ignored) {
			channel.sendMessage("**That user isn't blacklisted from your server, maybe they does not exist at all.**").queue();
		}
	}
	
	@Override
	public String getCommandName() {
		return "unban";
	}

	@Override
	public boolean isOwnerCommand() {
		return false;
	}

	@Override
	public long cooldown() {
		return 5;
	}

}
