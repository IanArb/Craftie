//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class HomeViewModel : ObservableObject {
    
    private let favouritesRepository: FavouritesRepository
    
    init(
        favouritesRepository: FavouritesRepository
    ) {
        self.favouritesRepository = favouritesRepository
    }
    
    func loadFavourites() -> [BeersDb] {
        var list = [BeersDb]()
        favouritesRepository.findAllBeers( success: { data in
            list = data
        })
        return list
    }
    
    func deleteBeer(beer: BeersDb) {
        favouritesRepository.removeBeer(beer: beer)
    }
    
    func deleteAllBeers() {
        favouritesRepository.removeAll()
    }

}
