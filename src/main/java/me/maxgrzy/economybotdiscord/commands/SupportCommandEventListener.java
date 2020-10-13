package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SupportCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "support")) {
                if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Check your DMs!");
                    builder.setAuthor(event.getAuthor().getAsTag());
                    builder.setDescription("Support menu has been sent to you on direct messages.");
                    builder.setColor(new Color(0x00FF00));
                    event.getChannel().sendMessage(builder.build()).queue();

                    builder = new EmbedBuilder();
                    builder.setTitle("Support:");
                    builder.setDescription("**Join our support server:**\nhttps://discord.gg/9tPwkkJ");
                    builder.setColor(new Color(0x00FF00));
                    event.getAuthor().openPrivateChannel().complete().sendMessage(builder.build()).queue();
                }
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.DMPREFIX + "support")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Support:");
                builder.setDescription("**Join our support server:**\nhttps://discord.gg/9tPwkkJ");
                builder.setColor(new Color(0x00FF00));
                event.getAuthor().openPrivateChannel().complete().sendMessage(builder.build()).queue();
            }
        }
    }
}
