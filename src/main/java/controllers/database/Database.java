package controllers.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Database {
    public static final String path = ".database";

    private static Gson gson = new Gson();

    private static Path getTablePath(String tableName) {
        return Path.of(path, tableName + ".json").normalize();
    }

    public static <T> T read(String tableName, Class<T> classOfT) {
        Path path = getTablePath(tableName);
        if (!Files.exists(path)) {
            return null;
        }
        try {
            return gson.fromJson(Files.readString(path), classOfT);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void write(String tableName, T value, Class<T> classOfT) {
        Path path = getTablePath(tableName);
        try {
            Files.write(path, gson.toJson(value).getBytes(), StandardOpenOption.CREATE);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
