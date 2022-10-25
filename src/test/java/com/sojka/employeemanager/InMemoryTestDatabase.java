package com.sojka.employeemanager;

import com.sojka.employeemanager.employee.domain.DomainObject;
import com.sojka.employeemanager.employee.dto.SampleEmployee;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTestDatabase<T extends DomainObject> implements SampleEmployee {

    private final Map<Integer, T> objects = new HashMap<>();

    @SafeVarargs
    private InMemoryTestDatabase(T... data) {
        for (int i = 0; i < data.length; i++) {
            objects.put(i, data[i]);
        }
    }

    @SafeVarargs
    public static <T extends DomainObject> InMemoryTestDatabase<T> of(T... data) {
        return new InMemoryTestDatabase<>(data);
    }

    public List<T> findAllObjects() {
        return List.copyOf(objects.values());
    }

    public Optional<T> findObjectById(String number) {
        return Optional.ofNullable(objects.get(Integer.parseInt(number)));
    }

    public T saveObject(T object) {
        objects.put(objects.size(), object);
        return objects.get(objects.size()-1);
    }

    public boolean exists(String objectId) {
        return objects.values().stream()
                .anyMatch(object -> object.getObjectId().equals(objectId));
    }

    public List<T> saveAllObjects(List<T> objects) {
        List<T> saved = new ArrayList<>();
        for (T object : objects) {
            if (this.objects.containsValue(object)) {
                T duplicate = this.objects.values().stream()
                        .filter(e -> e.getObjectId().equals(object.getObjectId()))
                        .findFirst().orElseThrow();
                throw new DuplicateKeyException(duplicate.toString());
            }
            this.objects.put(this.objects.size(), object);
            saved.add(object);
        }
        return saved;
    }

    public void remove(String objectId) {
        int key = 100;
        for (Map.Entry<Integer, T> entry : objects.entrySet()) {
            if (entry.getValue().getObjectId().equals(objectId)) {
                key = entry.getKey();
                break;
            }
        }
        if (key != 100)
            objects.remove(key);
    }
}