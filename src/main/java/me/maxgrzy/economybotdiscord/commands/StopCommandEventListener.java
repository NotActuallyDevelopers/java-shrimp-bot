package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StopCommandEventListener extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.DMPREFIX + "stop")) {
                if (Utils.listOfBotAdmins.contains(event.getAuthor().getId())) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Shutting down...");
                    builder.setDescription("Force stopping by remote request.");
                    builder.setColor(new Color(0x00FF00));
                    event.getChannel().sendMessage(builder.build()).complete();
                    System.out.println("Force stopping by an admin: " + event.getAuthor().getAsTag());
                    event.getJDA().shutdown();
                    System.out.println("Shutting down...");
                    System.exit(0);
                }
            }
        }
    }
}
