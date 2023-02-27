package games.project.motron.stockage.sql;

import java.util.List;

public interface Stockage<T> {
    void create(T element);

    void update(T element);

    void deleteByLogin(String login);

    T getByLogin(String login);

    List<T> getAll();
}
