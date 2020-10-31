package ru.dopaminka.persistence

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository

class InMemoryRepositoryImpl<T : Entity> : Repository<T>() {

    private val entities: MutableMap<Identity, T> = mutableMapOf()

    override fun get(id: Identity): T? {
        return entities[id]
    }

    override fun save(entity: T) {
        entities[entity.id] = entity
    }

    override fun get(ids: List<Identity>): List<T> {
        return entities.filterKeys { ids.contains(it) }.values.toList()
    }

    override fun remove(id: Identity) {
        entities.remove(id)
    }

    override fun clear() {
        entities.clear()
    }

    override fun all(): List<T> {
        return entities.values.toList()
    }
}