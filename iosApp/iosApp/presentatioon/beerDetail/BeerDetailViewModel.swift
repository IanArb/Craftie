//
//  BeerDetailViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 24/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class BeerDetailViewModel : ObservableObject {
    
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
    
    func load(id: String) {
        state = .loading
        
        beersRepository.findBeer(id: id) { data, error in
            if let beer = data {
                self.state = .success(beer)
            } else {
                self.state = .error
            }
        }
    }
}
