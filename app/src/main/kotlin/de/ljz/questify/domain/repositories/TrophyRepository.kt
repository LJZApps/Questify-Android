package de.ljz.questify.domain.repositories

import de.ljz.questify.domain.daos.TrophyDao
import javax.inject.Singleton

@Singleton
class TrophyRepository (
    private val trophyDao: TrophyDao
): BaseRepository() {

    fun getAllTrophies() = trophyDao.getAllTrophies()

}