package me.maxgrzy.economybotdiscord;

import me.maxgrzy.economybotdiscord.commands.*;
import me.maxgrzy.economybotdiscord.commands.economy.DepositCommandEventListener;
import me.maxgrzy.economybotdiscord.commands.economy.WorkCommandEventListener;
import me.maxgrzy.economybotdiscord.eventlisteners.ReadyEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {
    private static JDA jda;
    public static void main(String[] args) throws Exception {
        try {
            FirebaseDBHandler.start();
            Utils.reloadGuildData();
            Utils.reloadBotAdmins();
            JDABuilder builder = JDABuilder.createDefault(Utils.TOKEN);
            builder.setBulkDeleteSplittingEnabled(false);
            builder.setActivity(Activity.watching(Utils.STATUS));
            jda = builder.build();
            jda.addEventListener(new ReadyEventListener(), new HelpCommandEventListener(), new NoCommandLikeThatListener(), new SupportCommandEventListener(), new SetPrefixCommandEventListener(), new ReloadCommandEventListener(), new StopCommandEventListener(), new AddToYourServerCommandEventListener(), new DepositCommandEventListener(), new WorkCommandEventListener());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
