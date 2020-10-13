package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ReloadCommandEventListener extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.DMPREFIX + "reload")) {
                if (Utils.listOfBotAdmins.contains(event.getAuthor().getId())) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Reloading...");
                    builder.setDescription("Reloading by remote request.");
                    builder.setColor(new Color(0x00FF00));
                    event.getChannel().sendMessage(builder.build()).queue();
                    Utils.reloadGuildData();
                    Utils.reloadBotAdmins();
                    System.out.println("Reloaded by an admin: " + event.getAuthor().getAsTag());
                }
            }
        }
    }
}
