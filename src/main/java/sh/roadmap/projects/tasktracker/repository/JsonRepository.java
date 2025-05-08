package sh.roadmap.projects.tasktracker.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonRepository<T, ID> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Path filePath;
    private final Class<T> type;
    private final Function<T, ID> idGetter;

    public JsonRepository(String filePath, Class<T> type, Function<T, ID> idGetter) {
        this.filePath = Paths.get(filePath);
        this.type = type;
        this.idGetter = idGetter;
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        createDataFile();
    }

    private void createDataFile() {
        try {
            if (Files.exists(filePath)) {
                backupExistedDataFile();
            }
            Files.deleteIfExists(filePath);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, "[]".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data file", e);
        }
    }

    private void backupExistedDataFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String backupFileName = filePath.getFileName().toString()
                .replace(".json", "-" + timestamp + ".json");
        Path backupPath = filePath.resolveSibling(backupFileName);
        Files.copy(filePath, backupPath);
    }

    public synchronized List<T> findAll() {
        try {
            byte[] bytes = Files.readAllBytes(filePath);
            return mapper.readValue(bytes, mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public synchronized Optional<T> findById(ID id) {
        return findAll().stream()
                .filter(entity -> Objects.equals(idGetter.apply(entity), id))
                .findFirst();
    }

    public synchronized void save(T entity) {
        List<T> entities = findAll();
        ID entityId = idGetter.apply(entity);
        entities.removeIf(e -> Objects.equals(idGetter.apply(e), entityId));
        entities.add(entity);
        writeAll(entities);
    }

    public synchronized void deleteById(ID id) {
        List<T> entities = findAll();
        entities.removeIf(e -> Objects.equals(idGetter.apply(e), id));
        writeAll(entities);
    }

    private void writeAll(List<T> entities) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), entities);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON file", e);
        }
    }
}
