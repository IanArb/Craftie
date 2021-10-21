//
//  SaveBeerRatingViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 14/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class SaveBeerRatingViewModel : ObservableObject {
    
    private let craftieBeerRatingsRepository: CraftieBeerRatingsRepository
    
    @Published private(set) var state = State.idle
    
    enum State {
        case success
        case idle
        case error
        case loading
    }
    
    init(craftieBeerRatingsRepository: CraftieBeerRatingsRepository) {
        self.craftieBeerRatingsRepository = craftieBeerRatingsRepository
    }
    
    func saveRating(rating: RatingRequest) {
        self.state = State.loading
        craftieBeerRatingsRepository.saveRating(ratingRequest: rating) { data, error in
            if data != nil {
                self.state = State.success
            } else {
                self.state = State.error
            }
        }
    }
    
    
}
