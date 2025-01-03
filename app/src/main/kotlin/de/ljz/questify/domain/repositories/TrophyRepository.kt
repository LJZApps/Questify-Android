package de.ljz.questify.domain.repositories

import de.ljz.questify.domain.daos.TrophyEntityDao
import javax.inject.Singleton

@Singleton
class TrophyRepository (
    private val trophyDao: TrophyEntityDao
): BaseRepository() {

    fun getAllTrophies() = trophyDao.getAllTrophies()

}