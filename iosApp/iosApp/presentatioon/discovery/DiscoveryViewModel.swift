//
//  DiscoveryViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

class DiscoveryViewModel : ObservableObject {
    
    private let beersRepository: CraftieBeersRepository
    private let breweriesRepository: CraftieBreweriesRepository
    
    @Published var beers: [Beer] = []
    @Published var breweries: [Brewery] = []
    
    init(beersRepository: CraftieBeersRepository,
        breweriesRepository: CraftieBreweriesRepository) {
        self.beersRepository = beersRepository
        self.breweriesRepository = breweriesRepository
    }
    
    func beersList() {
        beersRepository.beers { data, error in
            if let beers = data {
                self.beers = beers
            }
        }
    }
    
    func breweriesList() {
        breweriesRepository.breweries { data, error in
            if let breweries = data {
                self.breweries = breweries
            }
        }
    }
    
    
}
