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

package net.crimsonite.rena.commands.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.crimsonite.rena.Rena;

public class ShutdownCommand extends Command{
	
	final static Logger logger = LoggerFactory.getLogger(Rena.class);
	
	public ShutdownCommand() {
		this.name = "shutdown";
		this.hidden = true;
		this.ownerCommand = true;
		this.guildOnly = false;
	}
	
	protected void execute(CommandEvent event) {
		event.reactWarning();
		logger.warn("Shutting down...");
		
		event.getJDA().shutdown();
		logger.info("Successfully closed all connections!");
	}

}
