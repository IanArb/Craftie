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
    
    enum State {
        case idle
        case loading
        case error
        case empty
        case success(DiscoveryUiData)
    }
    
    @Published private(set) var state = State.idle
    
    private let beersRepository: CraftieBeersRepository
    private let breweriesRepository: CraftieBreweriesRepository
    
    init(beersRepository: CraftieBeersRepository,
        breweriesRepository: CraftieBreweriesRepository) {
        self.beersRepository = beersRepository
        self.breweriesRepository = breweriesRepository
    }
    
    func load() {
        state = .loading
        
        var beersList = [Beer]()
        var breweriesList = [Brewery]()
        
        beersRepository.beers { data, error in
            if let beers = data {
                beersList = beers
            } else {
                self.state = .error
            }
            
            self.breweriesRepository.breweries { data, error in
                if let breweries = data {
                    breweriesList = breweries
                } else {
                    self.state = .error
                }
                
                if (beersList.count > 0 && breweriesList.count > 0) {
                    let discoveryUiData = DiscoveryUiData(beers: beersList, breweries: breweriesList)
                    self.state = .success(discoveryUiData)
                } else {
                    self.state = .empty
                }
            }
        }
    }
    
    
}
