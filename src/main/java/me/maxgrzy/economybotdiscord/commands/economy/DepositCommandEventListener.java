package me.maxgrzy.economybotdiscord.commands.economy;

import me.maxgrzy.economybotdiscord.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DepositCommandEventListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getAsTag().equals(event.getJDA().getSelfUser().getAsTag())) {
            String[] args = Utils.splitIntoArgs(event.getMessage().getContentRaw());
            if (args[0].equalsIgnoreCase(Utils.getGuildPrefix(event.getGuild().getId()) + "deposit")) {
                if (args.length > 1) {
                    if (!args[1].equalsIgnoreCase("all")) {
                        int amount;
                        try {
                            amount = Integer.parseInt(args[1]);
                            if (Utils.depositMoney(event.getGuild().getId(), event.getAuthor().getId(), false, amount)) {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Done!");
                                builder.setAuthor(event.getAuthor().getAsTag());
                                builder.setDescription("You deposited money to bank! (" + amount + Utils.getGuildCurrency(event.getGuild().getId()) + ")");
                                builder.setColor(new Color(0x00FF00));
                                builder.setFooter("Balance: " + Utils.getBalance(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()) +  ", bank: " + Utils.getBankAccount(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()));
                                event.getChannel().sendMessage(builder.build()).queue();
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle(":stop_sign: Error:");
                                builder.setDescription("**What happened?**\nCouldn't deposit money.\n\n**Cause:**\nYou don't have that much money to deposit. (or you typed a number smaller or equal to 0)\n\n**Solution:**\nType " + Utils.getGuildPrefix(event.getGuild().getId()) + "work or " + Utils.getGuildPrefix(event.getGuild().getId()) + "crime to get some more money.\n");
                                builder.setAuthor(event.getAuthor().getAsTag());
                                builder.setFooter("error code: 107");
                                builder.setColor(new Color(0xFF0000));
                                event.getChannel().sendMessage(builder.build()).queue();
                            }
                        } catch (NumberFormatException e) {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle(":stop_sign: Error:");
                            builder.setDescription("**What happened?**\nCouldn't deposit money.\n\n**Cause:**\nWrong [amount] argument.\n\n**Solution:**\nType " + Utils.getGuildPrefix(event.getGuild().getId()) + "deposit [amount/all]\n");
                            builder.setAuthor(event.getAuthor().getAsTag());
                            builder.setFooter("error code: 105");
                            builder.setColor(new Color(0xFF0000));
                            event.getChannel().sendMessage(builder.build()).queue();
                        }
                    } else {
                        int depositedMoney = Utils.getBalance(event.getGuild().getId(), event.getMember().getId());
                        if (Utils.depositMoney(event.getGuild().getId(), event.getMember().getId(), true, 0)) {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("Done!");
                            builder.setAuthor(event.getAuthor().getAsTag());
                            builder.setDescription("You deposited all your money to bank! (" + depositedMoney + Utils.getGuildCurrency(event.getGuild().getId()) + ")");
                            builder.setColor(new Color(0x00FF00));
                            builder.setFooter("Balance: " + Utils.getBalance(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()) +  ", bank: " + Utils.getBankAccount(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()));
                            event.getChannel().sendMessage(builder.build()).queue();
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle(":stop_sign: Error:");
                            builder.setDescription("**What happened?**\nCouldn't deposit money.\n\n**Cause:**\nYou don't have enough money to deposit to bank. (or you typed a number smaller or equal to 0)\n\n**Solution:**\nType " + Utils.getGuildPrefix(event.getGuild().getId()) + "work or " + Utils.getGuildPrefix(event.getGuild().getId()) + "crime to get some more money.\n");
                            builder.setAuthor(event.getAuthor().getAsTag());
                            builder.setFooter("error code: 106");
                            builder.setColor(new Color(0xFF0000));
                            event.getChannel().sendMessage(builder.build()).queue();
                        }
                    }
                } else {
                    int depositedMoney = Utils.getBalance(event.getGuild().getId(), event.getMember().getId());
                    if (Utils.depositMoney(event.getGuild().getId(), event.getMember().getId(), true, 0)) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Done!");
                        builder.setAuthor(event.getAuthor().getAsTag());
                        builder.setDescription("You deposited all your money to bank! (" + depositedMoney + Utils.getGuildCurrency(event.getGuild().getId()) + ")");
                        builder.setColor(new Color(0x00FF00));
                        builder.setFooter("Balance: " + Utils.getBalance(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()) +  ", bank: " + Utils.getBankAccount(event.getGuild().getId(), event.getMember().getId()) + Utils.getGuildCurrency(event.getGuild().getId()));
                        event.getChannel().sendMessage(builder.build()).queue();
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(":stop_sign: Error:");
                        builder.setDescription("**What happened?**\nCouldn't deposit money.\n\n**Cause:**\nYou don't have enough money to deposit to bank.\n\n**Solution:**\nType " + Utils.getGuildPrefix(event.getGuild().getId()) + "work or " + Utils.getGuildPrefix(event.getGuild().getId()) + "crime to get some more money.\n");
                        builder.setAuthor(event.getAuthor().getAsTag());
                        builder.setFooter("error code: 106");
                        builder.setColor(new Color(0xFF0000));
                        event.getChannel().sendMessage(builder.build()).queue();
                    }
                }
            }
        }
    }
}
