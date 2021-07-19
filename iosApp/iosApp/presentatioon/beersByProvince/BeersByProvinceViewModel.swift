//
//  BeersByProvinceViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 18/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class BeersByProvinceViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case empty
        case success([Beer])
    }
    
    @Published private(set) var state = State.idle
    
    private let beersRepository: CraftieBeersRepository
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
    }
    
    func load(province: String) {
        state = .loading
        
        beersRepository.findBeersByProvince(province: province) { data, error in
            if let beers = data {
                if (beers.isEmpty) {
                    self.state = .empty
                } else {
                    self.state = .success(beers)
                }
            } else {
                self.state = .error
            }
        }
    }
    
    
}
