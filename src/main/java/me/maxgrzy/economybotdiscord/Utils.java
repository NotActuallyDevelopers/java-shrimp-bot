package me.maxgrzy.economybotdiscord;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import me.maxgrzy.economybotdiscord.helperclasses.Entry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.util.*;

public class Utils {
    public static final String DMPREFIX = "//";
    public static final String DEFAULTPREFIX = "//";
    public static final String STATUS = "Type " + DMPREFIX + "help in direct messages.";
    public static final String TOKEN = "NzU3MjM4OTQyMzQ4MzQ1NDg1.X2dfuA.avOS_WbfaOZqqbBAMPr4cS6lcuA";
    public static final String[] LISTOFCOMMANDS = {"help", "support", "setPrefix", "addToYourServer"};
    public static final String[] LISTOFCOMMANDSECONOMY = {"work", "deposit"};
    public static final String[] LISTOFCOMMANDSDM = {"help", "support", "addToYourServer"};
    public static final String[] BOTADMINCOMMANDS = {"reload", "stop"};
    public static final String VERSION = "1.0.9";
    public static final String DEFAULTGUILDCURRENCYSYMBOL = "$";
    public static final int DEFAULTWORKMAXPAYOUT = 50;
    public static final int DEFAULTWORKMINPAYOUT = 20;
    public static final ArrayList<String> listOfBotAdmins = new ArrayList<>();
    private static final HashMap<String, String> guildPrefixes = new HashMap<>();
    private static final HashMap<Entry, Integer> userBalances = new HashMap<>();
    private static final HashMap<Entry, Integer> userBankAccounts = new HashMap<>();
    public static String getGuildPrefix(String guildID) {
        String prefix = guildPrefixes.get(guildID);
        if (prefix == null) {
            setGuildPrefix(guildID, DEFAULTPREFIX);
            prefix = DEFAULTPREFIX;
        }
        return prefix;
    }
    public static void setGuildPrefix(String guildID, String newPrefix) {
        guildPrefixes.put(guildID, newPrefix);
        FirebaseDBHandler.getRef("guilds/" + guildID + "/prefix").setValueAsync(newPrefix);
    }
    public static boolean canTalk(TextChannel channel, User author) {
        if (channel.canTalk()) {
            return true;
        } else {
            PrivateChannel privateChannel = author.openPrivateChannel().complete();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(":stop_sign: Error:");
            builder.setDescription("**What happened?**\nThe bot couldn't reply to your command.\n\n**Cause:**\nThe server administrators didn't give permissions to type on this channel.\n\n**Solution:**\n Ask server administration to give talk permissions to the bot on the channel you sent the command at.\n");
            builder.setFooter("error code: 101");
            builder.setColor(new Color(0xFF0000));
            privateChannel.sendMessage(builder.build()).complete();
            return false;
        }
    }

    public static void reloadGuildData() {
        guildPrefixes.clear();
        userBalances.clear();
        userBankAccounts.clear();
        FirebaseDBHandler.getRef("guilds/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int balance = 0;
                int bank = 0;
                for (DataSnapshot guildIDSnapshot : dataSnapshot.getChildren()) {
                    guildPrefixes.put(guildIDSnapshot.getKey(), guildIDSnapshot.child("prefix").getValue().toString());
                    for (DataSnapshot userEconomyDataSnapshot : guildIDSnapshot.child("economy/users").getChildren()) {
                        try {
                            balance = Integer.parseInt(userEconomyDataSnapshot.child("balance").getValue().toString());
                            bank = Integer.parseInt(userEconomyDataSnapshot.child("bank_account").getValue().toString());
                        } catch (NumberFormatException e) {
                            balance = 0;
                            bank = 0;
                            setBalance(guildIDSnapshot.getKey(), userEconomyDataSnapshot.getKey(), balance);
                            setBankAccount(guildIDSnapshot.getKey(), userEconomyDataSnapshot.getKey(), bank);
                        } finally {
                            userBalances.put(new Entry(guildIDSnapshot.getKey(), userEconomyDataSnapshot.getKey()), balance);
                            userBankAccounts.put(new Entry(guildIDSnapshot.getKey(), userEconomyDataSnapshot.getKey()), bank);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error occurred while fetching prefixes for guilds: " + databaseError.getMessage());
                System.exit(-1);
            }
        });
    }
    public static void reloadBotAdmins() {
        listOfBotAdmins.clear();
        FirebaseDBHandler.getRef("admins/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adminIDSnapshot : dataSnapshot.getChildren()) {
                    listOfBotAdmins.add(adminIDSnapshot.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error occurred while fetching bot admins: " + databaseError.getMessage());
            }
        });

    }
    public static String[] splitIntoArgs(String text) {
        return text.split(" ");
    }
    public static boolean commandByAdmin(PrivateMessageReceivedEvent event) {
        return !(event.getMessage().getContentRaw().length() <= Utils.DMPREFIX.length()) && listOfBotAdmins.contains(event.getAuthor().getId()) && Arrays.asList(BOTADMINCOMMANDS).contains(event.getMessage().getContentRaw().substring(DMPREFIX.length() < event.getMessage().getContentRaw().length() ? DMPREFIX.length() : 0));
    }
    public static int getGuildMaxWorkPayout(String guildID) {
        return DEFAULTWORKMAXPAYOUT;
    }
    public static String getGuildCurrency(String guildID) {
        return DEFAULTGUILDCURRENCYSYMBOL;
    }
    public static boolean isOnWorkCooldown(String guildID, String userID) {
        return false;
    }
    public static int addMoneyToBal(String guildID, String userID, int amount) {
        setBalance(guildID, userID, getBalance(guildID, userID) + amount);
        return getBalance(guildID, userID);
    }
    public static int getBalance(String guildID, String userID) {
        int balance = 0;
        if (userBalances.containsKey(new Entry(guildID, userID))) {
            balance = userBalances.get(new Entry(guildID, userID));
        } else {
            setBalance(guildID, userID, 0);
        }
        return balance;
    }
    public static int getBankAccount(String guildID, String userID) {
        int bank = 0;
        if (userBankAccounts.containsKey(new Entry(guildID, userID))) {
            bank = userBankAccounts.get(new Entry(guildID, userID));
        } else {
            setBankAccount(guildID, userID, 0);
        }
        return bank;
    }
    public static int getGuildMinWorkPayout(String guildID) {
        return DEFAULTWORKMINPAYOUT;
    }
    public static void setBankAccount(String guildID, String userID, int amount) {
        userBankAccounts.put(new Entry(guildID, userID), amount);
        FirebaseDBHandler.getRef("guilds/" + guildID + "/economy/users/" + userID + "/bank_account").setValueAsync(amount);
    }
    public static void setBalance(String guildID, String userID, int amount) {
        userBalances.put(new Entry(guildID, userID), amount);
        FirebaseDBHandler.getRef("guilds/" + guildID + "/economy/users/" + userID + "/balance").setValueAsync(amount);
    }
    public static boolean depositMoney(String guildID, String userID, boolean all, int amount) {
        if (all) {
            if (getBalance(guildID, userID) <= 0) {
                return false;
            } else {
                setBankAccount(guildID, userID, getBankAccount(guildID, userID) + getBalance(guildID, userID));
                setBalance(guildID, userID, 0);
                return true;
            }
        } else {
            if (amount > getBalance(guildID, userID)) {
                return false;
            } else {
                if (amount <= 0) {
                    return false;
                } else {
                    setBankAccount(guildID, userID, getBankAccount(guildID, userID) + amount);
                    setBalance(guildID, userID, getBalance(guildID, userID) - amount);
                    return true;
                }
            }
        }
    }

    public static int getCrimeMaxLoss(String guildID) {
        return 50;
    }

    public static int getGuildMinCrimePayout(String guildID) {
        return 10;
    }

    public static int getGuildMaxCrimePayout(String guildID) {
        return 50;
    }

    public static boolean isOnCrimeCooldown(String guildID, String userID) {
        return false;
    }

    public static int getGuildMinCrimeLoss(String guildID) {
        return 10;
    }

    public static int subMoneyToBal(String guildID, String userID, int amount) {
        setBalance(guildID, userID, getBalance(guildID, userID) - amount);
        return getBalance(guildID, userID);
    }
}
