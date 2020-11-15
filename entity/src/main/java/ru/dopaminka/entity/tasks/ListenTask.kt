package ru.dopaminka.entity.tasks

import ru.dopaminka.entity.common.Identity

open class ListenTask(id: Identity, val image: String, val sound: String) : Task(id)