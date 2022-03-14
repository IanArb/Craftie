//
//  ViewAllRatingsViewModel.swift
//  iosApp
//
//  Created by Ian Arbuckle on 04/11/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

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
    
    private var handler: Task<(), Never>? = nil
    
    init(ratingsRepository: CraftieBeerRatingsRepository) {
        self.ratingsRepository = ratingsRepository
    }
    
    func load(beerId: String) {
        self.state = .loading
        
        handler = Task {
            do {
                let ratings = try await asyncFunction(for: ratingsRepository.ratingsByBeerIdNative(beerId: beerId))
                if (ratings.count > 0) {
                    self.state = .success(ratings)
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
