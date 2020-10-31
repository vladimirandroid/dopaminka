package ru.dopaminka.usecases

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity

abstract class Repository<T : Entity> {
    abstract fun all(): List<T>
    abstract fun get(id: Identity): T?
    abstract fun get(ids: List<Identity>): List<T>
    abstract fun save(entity: T)
    abstract fun remove(id: Identity)
    abstract fun clear()
}