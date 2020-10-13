package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AddToYourServerCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "addToYourServer")) {
                if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Check your DMs!");
                    builder.setAuthor(event.getAuthor().getAsTag());
                    builder.setDescription("Link has been sent to you on direct messages.");
                    builder.setColor(new Color(0x00FF00));
                    event.getChannel().sendMessage(builder.build()).queue();

                    builder = new EmbedBuilder();
                    builder.setTitle("Add this bot to your server!");
                    builder.setDescription("**Click on the link below to add this bot to your server!**\nhttps://discord.com/api/oauth2/authorize?client_id=756772375739236402&permissions=871459927&scope=bot");
                    builder.setColor(new Color(0x00FF00));
                    event.getAuthor().openPrivateChannel().complete().sendMessage(builder.build()).queue();
                }
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.DMPREFIX + "addToYourServer")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Add this bot to your server!");
                builder.setDescription("**Click on the link below to add this bot to your server!**\nhttps://discord.com/api/oauth2/authorize?client_id=756772375739236402&permissions=871459927&scope=bot");
                builder.setColor(new Color(0x00FF00));
                event.getChannel().sendMessage(builder.build()).queue();
            }
        }
    }
}
