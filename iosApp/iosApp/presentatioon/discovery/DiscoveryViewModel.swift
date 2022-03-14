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
import KMPNativeCoroutinesAsync

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
    private let provincesRepository: CraftieProvincesRepository
    
    private var handler: Task<(), Never>? = nil
    
    private var beersList = [Beer]()
    private var breweriesList = [Brewery]()
    
    init(beersRepository: CraftieBeersRepository,
        breweriesRepository: CraftieBreweriesRepository,
         provincesRepository: CraftieProvincesRepository) {
        self.beersRepository = beersRepository
        self.breweriesRepository = breweriesRepository
        self.provincesRepository = provincesRepository
    }
    
    func load() {
        state = .loading
    
        handler = Task {
            do {
                let beers = try await asyncFunction(for: beersRepository.beersNative())
                let breweries = try await asyncFunction(for: breweriesRepository.breweriesNative())
                let featuredBeer = try await asyncFunction(for: beersRepository.featuredBeerNative())
                let provinces = try await asyncFunction(for: provincesRepository.provincesNative())
                let discoveryUiData = DiscoveryUiData(
                    beers: beers,
                    breweries: breweries,
                    featuredBeer: featuredBeer,
                    provinces: provinces
                )
                let hasBeers = beers.count > 0
                let hasBreweries = breweries.count > 0
                let hasProvinces = provinces.count > 0
                
                if (hasBeers && hasBreweries && hasProvinces) {
                    self.state = .success(discoveryUiData)
                } else {
                    self.state = .empty
                }
                
            } catch {
                self.state = .error
            }
           
        }
        
    }
    
    func cancel() {
        handler?.cancel()
    }
    
    
}
