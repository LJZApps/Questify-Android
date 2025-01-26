package de.ljz.questify.domain.repositories.trophies

import de.ljz.questify.domain.daos.TrophyDao
import de.ljz.questify.domain.repositories.BaseRepository
import javax.inject.Singleton

@Singleton
class TrophyRepository (
    private val trophyDao: TrophyDao
): BaseRepository() {

    fun getAllTrophies() = trophyDao.getAllTrophies()

}