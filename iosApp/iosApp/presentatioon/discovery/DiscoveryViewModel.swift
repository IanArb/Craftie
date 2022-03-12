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
    
    private var handler: Task<(), Never>? = nil
    
    private var beersList = [Beer]()
    private var breweriesList = [Brewery]()
    
    init(beersRepository: CraftieBeersRepository,
        breweriesRepository: CraftieBreweriesRepository) {
        self.beersRepository = beersRepository
        self.breweriesRepository = breweriesRepository
    }
    
    func load() {
        state = .loading
    
        handler = Task {
            do {
                let beers = try await asyncFunction(for: beersRepository.beersNative())
                let breweries = try await asyncFunction(for: breweriesRepository.breweriesNative())
                let discoveryUiData = DiscoveryUiData(beers: beers, breweries: breweries)
                self.state = .success(discoveryUiData)
            } catch {
                self.state = .error
                print("Failed with error: \(error)")
            }
           
        }
        
    }
    
    func cancel() {
        handler?.cancel()
    }
    
    
}
