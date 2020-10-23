package me.maxgrzy.economybotdiscord.commands.economy;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class CrimeCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "crime")) {
                if (Utils.canTalk(event.getChannel(), event.getAuthor())) {
                    if (!Utils.isOnCrimeCooldown(event.getGuild().getId(), event.getMember().getId())) {
                        if (new Random().nextBoolean()) {
                            Random random = new Random();
                            int rand = random.nextInt(Utils.getGuildMaxCrimePayout(event.getGuild().getId()) + 1 > 0 ? Utils.getGuildMaxCrimePayout(event.getGuild().getId()) + 1 : Utils.DEFAULTWORKMAXPAYOUT);
                            if (rand < Utils.getGuildMinCrimePayout(event.getGuild().getId())) {
                                rand += Utils.getGuildMinCrimePayout(event.getGuild().getId());
                            }
                            Utils.addMoneyToBal(event.getGuild().getId(), event.getMember().getId(), rand);
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("Your earnings:");
                            builder.setAuthor(event.getAuthor().getAsTag());
                            // TODO messages
                            switch (random.nextInt(4)) {
                                case 0:
                                    builder.setDescription("You committed a crime and got " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                                case 1:
                                    builder.setDescription("TODO " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                                case 2:
                                    builder.setDescription("TODO " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                                case 3:
                                    builder.setDescription("TODO " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                            }
                            builder.setColor(new Color(0x00FF00));
                            builder.setFooter("Balance: " + Utils.getBalance(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()) +  ", bank: " + Utils.getBankAccount(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()));
                            event.getChannel().sendMessage(builder.build()).queue();
                        } else {
                            Random random = new Random();
                            int rand = random.nextInt(Utils.getCrimeMaxLoss(event.getGuild().getId()) + 1);
                            if (rand < Utils.getGuildMinCrimeLoss(event.getGuild().getId())) {
                                rand += Utils.getGuildMinCrimeLoss(event.getGuild().getId());
                            }
                            Utils.subMoneyToBal(event.getGuild().getId(), event.getMember().getId(), rand);
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("Your earnings:");
                            builder.setAuthor(event.getAuthor().getAsTag());
                            // TODO messages
                            switch (random.nextInt(4)) {
                                case 0:
                                    builder.setDescription("You committed a crime and got caught, so you lost " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                                case 1:
                                    builder.setDescription("TODO " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                                case 2:
                                    builder.setDescription("TODO " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                                case 3:
                                    builder.setDescription("TODO " + rand + Utils.getGuildCurrency(event.getGuild().getId()) + ".");
                                    break;
                            }
                            builder.setColor(new Color(0xFF0000));
                            builder.setFooter("Balance: " + Utils.getBalance(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()) +  ", bank: " + Utils.getBankAccount(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()));
                            event.getChannel().sendMessage(builder.build()).queue();
                        }
                    }
                }
            }
        }
    }
}
