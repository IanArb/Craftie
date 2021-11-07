//
//  ViewAllRatingsViewModel.swift
//  iosApp
//
//  Created by Ian Arbuckle on 04/11/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ViewAllRatingsViewModel : ObservableObject {
    
    private let ratingsRepository: CraftieBeerRatingsRepository
    
    @Published private(set) var state = State.idle
    
    enum State {
        case success([RatingResponse])
        case idle
        case error
        case loading
        case empty
    }
    
    init(ratingsRepository: CraftieBeerRatingsRepository) {
        self.ratingsRepository = ratingsRepository
    }
    
    func load(beerId: String) {
        self.state = .loading
        
        ratingsRepository.ratingsByBeerId(beerId: beerId) { data, error in
            if let ratings = data {
                
                if (ratings.isEmpty) {
                    self.state = .empty
                    return
                }
                
                if (!ratings.isEmpty) {
                    self.state = .success(ratings)
                } else {
                    self.state = .error
                }
            }
        }
    }
    
    
}
