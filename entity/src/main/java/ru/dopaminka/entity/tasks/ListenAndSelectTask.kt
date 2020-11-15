package ru.dopaminka.entity.tasks

import ru.dopaminka.entity.common.Identity

class ListenAndSelectTask(id: Identity, image: String, sound: String, val wrongImage: String) :
    ListenTask(id, image, sound)