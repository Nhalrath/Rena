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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import com.sun.management.OperatingSystemMXBean;

import net.crimsonite.rena.commands.Command;
import net.crimsonite.rena.commands.info.HelpCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StatusReportCommand extends Command {

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		JDA jda = event.getJDA();
		MessageChannel channel = event.getChannel();
		
		OperatingSystemMXBean operatingSystem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
		
		long freeMemory = operatingSystem.getFreeMemorySize() / (1024 * 1024);
		long heapMemoryUsage = memory.getHeapMemoryUsage().getMax() / (1024 * 1024);
		long nonHeapMemoryUsage = memory.getNonHeapMemoryUsage().getMax() / (1024 * 1024);
		long numberOfCommands = HelpCommand.getCommandCount();
		long shards = jda.getShardInfo().getShardTotal();
		long timesCommandUsed = Command.getTimesCommandUsed();
		long totalMemory = operatingSystem.getTotalMemorySize() / (1024 * 1024);
		long usedMemory = totalMemory - freeMemory;
		long threads = Thread.activeCount();
		double cpuLoad = operatingSystem.getCpuLoad();
		
		channel.sendMessageFormat(
				"```yml\n" +
				"******************************\n" +
				"** System Reports for Debug **\n" +
				"******************************\n\n" +
				"Shards: %d\n" +
				"Command Count: %d\n" +
				"Times Command Used: %d\n\n" +
				"Threads : %d\n" +
				"CPU Usage: %.2f%%\n" +
				"Total Memory: %dmb\n" +
				"Used memory: %dmb\n" +
				"Available Memory: %dmb\n" +
				"Max Heap Memory: %dmb\n" +
				"Max Non-Heap Memory: %dmb\n" +
				"```",
				
				shards,
				numberOfCommands,
				timesCommandUsed,
				threads,
				cpuLoad,
				totalMemory,
				usedMemory,
				freeMemory,
				heapMemoryUsage,
				nonHeapMemoryUsage
				)
				.queue();
	}
	
	@Override
	public String getCommandName() {
		return "reports";
	}

	@Override
	public boolean isOwnerCommand() {
		return true;
	}

	@Override
	public long cooldown() {
		return 0;
	}

}
