//
//  FeaturedBeerViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 04/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class FeaturedBeerViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case success(Beer)
    }
    
    @Published private(set) var state = State.idle
    
    private let beersRepository: CraftieBeersRepository
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
    }
    
    func load() {
        state = .loading
        
        beersRepository.featuredBeer { data, error in
            if let featuredBeer = data {
                self.state = .success(featuredBeer)
            } else {
                self.state = .error
            }
        }
    }
}
