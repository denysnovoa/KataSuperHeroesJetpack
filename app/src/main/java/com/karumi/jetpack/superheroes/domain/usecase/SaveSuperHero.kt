package com.karumi.jetpack.superheroes.domain.usecase

import android.support.annotation.WorkerThread
import com.karumi.jetpack.superheroes.data.repository.SuperHeroRepository
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class SaveSuperHero(private val superHeroesRepository: SuperHeroRepository) {
    @WorkerThread
    suspend operator fun invoke(superHero: SuperHero): SuperHero? =
        superHeroesRepository.save(superHero)
}