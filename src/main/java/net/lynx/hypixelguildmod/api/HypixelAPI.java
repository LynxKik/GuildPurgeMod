package net.lynx.hypixelguildmod.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.lynx.hypixelguildmod.handler.ConfigurationHandler;
import net.lynx.hypixelguildmod.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static net.lynx.hypixelguildmod.Utility.HYPIXEL_API;

/**
 * Class that parses JSON
 * from {api.hypixel.net}
 * Written in Java 8 Streams and GSON
 */
public class HypixelAPI {
    public static String KEY = ConfigurationHandler.getAPIKey();
    public static String guildID = ConfigurationHandler.getGuildID();
    private static Gson gson;

    public HypixelAPI() {
        gson = new Gson();
    }

    public HashMap<String, Long> getPlayerBasedOnLastLoginDays(int days) {
        return getLastLoginTimes()
                .entrySet()
                .stream()
                .filter(e -> TimeUnit.MILLISECONDS.toDays(Math.abs(e.getValue() - System.currentTimeMillis())) > days)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, HashMap::new));
    }

    private HashMap<String, Long> getLastLoginTimes() {
        if (checkKey()) {
            return new HashMap<>();
        }
        return getMembersFromGuild().parallelStream()
                .filter(m -> m.getRank().equals("Member"))
                .map(m -> getReader(HYPIXEL_API + "player?key=" + KEY + "&uuid=" + m.getUuid()))
                .map(e -> gson.fromJson(e, JsonObject.class))
                .map(e -> e.getAsJsonObject("player"))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(e -> e.get("uuid").getAsString(), e -> e.get("lastLogin").getAsLong(), (x, y) -> y, HashMap::new));
    }

    private List<GuildMember> getMembersFromGuild() {
        if (checkKey()) {
            return new ArrayList<>();
        }
        List<GuildMember> guildMembers = new ArrayList<>();
        gson.fromJson(getReader(HYPIXEL_API + "guild?key=" + KEY + "&id=" + getGuildID()), JsonObject.class)
                .entrySet()
                .parallelStream()
                .filter(e -> e.getKey().equals("guild"))
                .map(e -> e.getValue().getAsJsonObject())
                .map(e -> e.getAsJsonArray("members"))
                .flatMap(jsonElements -> StreamSupport.stream(jsonElements.spliterator(), false))
                .forEach(jsonObject -> guildMembers.add(gson.fromJson(jsonObject.toString(), GuildMember.class)));
        return guildMembers;
    }

    private String getGuildID() {
        if (checkKey()) {
            return "";
        }
        if (guildID.isEmpty()) {
            InputStreamReader data = getReader(HYPIXEL_API + "findGuild?key=" + KEY + "&byUuid=" + Utility.getPlainUUID);
            gson.fromJson(data, JsonObject.class).entrySet()
                    .parallelStream()
                    .filter(e -> e.getKey().equals("guild"))
                    .forEach(e -> ConfigurationHandler.setGuildID(e.getValue().getAsString()));
        }
        return guildID;
    }

    public boolean checkKey() {
        return KEY.isEmpty();
    }

    private InputStreamReader getReader(String aUrl) {
        try {
            URLConnection conn = new URL(aUrl).openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/4.76");
            return new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InputStreamReader(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        });
    }
}
