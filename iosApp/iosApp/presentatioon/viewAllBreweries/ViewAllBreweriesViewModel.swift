//
//  ViewAllBreweriesViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 14/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ViewAllBreweriesViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case success([Brewery])
    }
    
    @Published private(set) var state = State.idle
    
    private let breweriesRepository: CraftieBreweriesRepository
    
    init(breweriesRepository: CraftieBreweriesRepository) {
        self.breweriesRepository = breweriesRepository
    }
    
    func load() {
        state = .loading
        
        breweriesRepository.breweries { data, error in
            if let breweries = data {
                self.state = .success(breweries)
            } else {
                self.state = .error
            }
        }
    }
}
