package ru.dopaminka.entity.tasks

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.common.Identity

class LetterListeningTask(id: Identity, val letter: Alphabet.Letter) : Task(id)