//
//  ViewAllTopRatedViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 15/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ViewAllTopRatedViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case success([Beer])
    }
    
    @Published private(set) var state = State.idle
    
    private let beersRepository: CraftieBeersRepository
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
    }
    
    func load() {
        state = .loading
        
        beersRepository.beers { data, error in
            if let beers = data {
                self.state = .success(beers)
            } else {
                self.state = .error
            }
        }
    }
}
