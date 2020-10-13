package me.maxgrzy.economybotdiscord;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;

public class FirebaseDBHandler {
    private static FirebaseDatabase fbDb;
    public static void start() throws Exception {
        InputStream serviceAccount = FirebaseDBHandler.class.getResourceAsStream("/economy-bot-discord-firebase-adminsdk-3alfc-9764ea1b87.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://economy-bot-discord.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
        fbDb = FirebaseDatabase.getInstance();
    }
    public static DatabaseReference getRef(String path) {
        return fbDb.getReference(path);
    }
}
