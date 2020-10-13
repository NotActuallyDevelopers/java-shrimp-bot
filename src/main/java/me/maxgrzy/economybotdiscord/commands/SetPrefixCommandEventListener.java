package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SetPrefixCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            String[] args = Utils.splitIntoArgs(event.getMessage().getContentRaw());
            if (args[0].equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "setPrefix")) {
                if (event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                        if (args.length == 2) {
                            if (args[1].length() <= 5) {
                                Utils.setGuildPrefix(event.getGuild().getId(), args[1]);
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Done!");
                                builder.setDescription("The prefix has been set to: " + args[1]);
                                builder.setColor(new Color(0x00FF00));
                                builder.setAuthor(event.getAuthor().getAsTag());
                                event.getChannel().sendMessage(builder.build()).queue();
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle(":stop_sign: Error:");
                                builder.setDescription("**What happened?**\nIncompatible prefix.\n\n**Cause:**\nThe [prefix] argument is too long.\n\n**Solution:**\nMake your prefix shorter or equal to 5 characters\n");
                                builder.setAuthor(event.getAuthor().getAsTag());
                                builder.setFooter("error code: 103");
                                builder.setColor(new Color(0xFF0000));
                                event.getChannel().sendMessage(builder.build()).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle(":stop_sign: Error:");
                            builder.setDescription("**What happened?**\nIncorrectly typed command.\n\n**Cause:**\nYou didn't give [prefix] argument or you gave too many arguments.\n\n**Solution:**\nType correctly next time. (example: \"" + Utils.getGuildPrefix(event.getGuild().getId()) + "setprefix !\")\n");
                            builder.setAuthor(event.getAuthor().getAsTag());
                            builder.setFooter("error code: 104");
                            builder.setColor(new Color(0xFF0000));
                            event.getChannel().sendMessage(builder.build()).queue();
                        }
                    }
                } else {
                    if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(":stop_sign: Error:");
                        builder.setDescription("**What happened?**\nYou can't perform this command.\n\n**Cause:**\nYou don't have MANAGE_SERVER permission.\n\n**Solution:**\n Ask server owner to give you the permission.\n");
                        builder.setAuthor(event.getAuthor().getAsTag());
                        builder.setFooter("error code: 102");
                        builder.setColor(new Color(0xFF0000));
                        event.getChannel().sendMessage(builder.build()).queue();
                    }
                }

            }
        }
    }
}
