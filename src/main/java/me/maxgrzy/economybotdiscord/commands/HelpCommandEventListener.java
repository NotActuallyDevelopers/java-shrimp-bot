package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HelpCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "help")) {
                if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Check your DMs!");
                    builder.setAuthor(event.getAuthor().getAsTag());
                    builder.setDescription("Help menu has been sent to you on direct messages.");
                    builder.setColor(new Color(0x00FF00));
                    event.getChannel().sendMessage(builder.build()).queue();

                    builder = new EmbedBuilder();
                    builder.setTitle("Help Menu:");
                    StringBuilder DMCommandsAsText = new StringBuilder();
                    for (String command : Utils.LISTOFCOMMANDSDM) {
                        DMCommandsAsText.append("\n - " + Utils.DMPREFIX + command);
                    }
                    StringBuilder guildCommandsAsText = new StringBuilder();
                    for (String command : Utils.LISTOFCOMMANDS) {
                        guildCommandsAsText.append("\n - " + Utils.getGuildPrefix(event.getGuild().getId()) + command);
                    }
                    builder.setDescription("**List of all the guild commands:** " + guildCommandsAsText.toString() + "\n\n**List of all the PM commands:** " + DMCommandsAsText.toString());
                    builder.setColor(new Color(0x00FF00));
                    builder.setFooter("Version: " + Utils.VERSION);
                    try {
                        event.getAuthor().openPrivateChannel().complete().sendMessage(builder.build()).queue();
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.DMPREFIX + "help")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Help Menu:");
                StringBuilder PMCommandsAsText = new StringBuilder();
                for (String command : Utils.LISTOFCOMMANDSDM) {
                    PMCommandsAsText.append("\n - " + Utils.DMPREFIX + command);
                }
                StringBuilder guildCommandsAsText = new StringBuilder();
                for (String command : Utils.LISTOFCOMMANDS) {
                    guildCommandsAsText.append("\n - " + Utils.DEFAULTPREFIX + command);
                }
                builder.setDescription("**List of all the PM commands:** " + PMCommandsAsText.toString() + "\n\n **List of all the guild commands:** " + guildCommandsAsText.toString());


                builder.setColor(new Color(0x00FF00));
                builder.setFooter("Version: " + Utils.VERSION);
                event.getChannel().sendMessage(builder.build()).queue();
            }
        }
    }
}
