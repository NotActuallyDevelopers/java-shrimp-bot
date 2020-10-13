package me.maxgrzy.economybotdiscord.commands.economy;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class WorkCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "work")) {
                if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                    if (!Utils.isOnWorkCooldown(event.getGuild().getId(), event.getMember().getId())) {
                        Random random = new Random();
                        int rand = random.nextInt(Utils.getGuildMaxWorkPayout(event.getGuild().getId()) + 1 > 0 ? Utils.getGuildMaxWorkPayout(event.getGuild().getId()) + 1 : Utils.DEFAULTWORKMAXPAYOUT);
                        if (rand < Utils.getGuildMinWorkPayout(event.getGuild().getId())) {
                            rand += Utils.getGuildMinWorkPayout(event.getGuild().getId());
                        }
                        int money = Utils.addMoneyToBal(event.getGuild().getId(), event.getMember().getId(), rand);
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Your earnings:");
                        builder.setAuthor(event.getAuthor().getAsTag());
                        switch (random.nextInt(4)) {
                            case 0:
                                builder.setDescription("You worked hard and got " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                break;
                            case 1:
                                builder.setDescription("You worked as software engineer at Google and earned " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                break;
                            case 2:
                                builder.setDescription("You helped your granny with the spring cleaning and she awarded you " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                break;
                            case 3:
                                builder.setDescription("You coded a website for your friend and he gave you " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + " for that.");
                                break;
                        }
                        builder.setColor(new Color(0x00FF00));
                        builder.setFooter("Balance: " + Utils.getBalance(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()) +  ", bank: " + Utils.getBankAccount(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()));
                        event.getChannel().sendMessage(builder.build()).queue();
                    }
                }
            }
        }
    }
}
