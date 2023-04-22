package controllers.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Database {
    public static final String DATABASE_ROOT = ".database";
    private static Gson gson = new Gson();

    public static void init() throws IOException {
        Files.createDirectories(Path.of(DATABASE_ROOT));
    }

    private static Path getTablePath(String tableName) {
        return Path.of(DATABASE_ROOT, tableName + ".json").normalize();
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

    public static void delete(String tableName) {
        Path path = getTablePath(tableName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            // TODO: how should we handle errors?
            e.printStackTrace();
        }
    }
}
