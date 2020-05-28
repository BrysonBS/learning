package cn.tacos.tacocloud.repository.impl;

public interface BaseRepository<T> {
    Iterable<T> findAll();
    T findOne(String id);
    boolean save(T t);
}
