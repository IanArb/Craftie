package com.craftie.data.useCase

import com.craftie.data.model.Beer
import org.koin.core.component.KoinComponent

class CraftieFilterUseCase : KoinComponent {

    fun filterByCreationDate(results: List<Beer>) : List<Beer> {
        return results.sortedBy { it.creationDate }
    }

}