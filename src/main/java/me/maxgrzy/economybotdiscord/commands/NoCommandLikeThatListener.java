package me.maxgrzy.economybotdiscord.commands;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;

public class NoCommandLikeThatListener extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().length() <= Utils.DMPREFIX.length() || !Arrays.asList(Utils.LISTOFCOMMANDSDM).contains(event.getMessage().getContentRaw().substring(Utils.DMPREFIX.length() < event.getMessage().getContentRaw().length() ? Utils.DMPREFIX.length() : 0))) {
                if (!Utils.commandByAdmin(event)) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(":stop_sign: Error:");
                    builder.setDescription("**Cause:**\n No command like that!\n\n**Solution:**\n Type " + Utils.DMPREFIX + "help for list of all the PM commands");
                    builder.setFooter("error code: 100");
                    builder.setColor(new Color(0xFF0000));
                    MessageEmbed embed = builder.build();
                    event.getChannel().sendMessage(embed).queue();
                }
            }
        }
    }
}
