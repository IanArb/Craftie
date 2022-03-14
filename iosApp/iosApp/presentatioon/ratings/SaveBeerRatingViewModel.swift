//
//  SaveBeerRatingViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 14/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class SaveBeerRatingViewModel : ObservableObject {
    
    private let craftieBeerRatingsRepository: CraftieBeerRatingsRepository
    
    @Published private(set) var state = State.idle
    
    @Published private(set) var ratingUiState = RatingUiState.idle
    
    enum State {
        case success
        case idle
        case error
        case loading
    }
    
    enum RatingUiState {
        case success(rating: RatingResult)
        case idle
        case error
        case loading
    }
    
    private var saveHandler: Task<(), Never>? = nil
    private var ratingsHandler: Task<(), Never>? = nil
    
    init(craftieBeerRatingsRepository: CraftieBeerRatingsRepository) {
        self.craftieBeerRatingsRepository = craftieBeerRatingsRepository
    }
    
    func saveRating(rating: RatingRequest) {
        self.state = State.loading
        
        saveHandler = Task {
            do {
                let result = try await asyncFunction(for: craftieBeerRatingsRepository.saveRatingNative(ratingRequest: rating))
                if !result.isEmpty {
                    self.state = State.success
                } else {
                    self.state = State.error
                }
            } catch {
                self.state = State.error
            }
            
            
        }
    }
    
    func fetchRating(beerId: String) {
        self.ratingUiState = RatingUiState.loading
        
        ratingsHandler = Task {
            do {
                let rating = try await asyncFunction(for: craftieBeerRatingsRepository.ratingNative(beerId: beerId))
                self.ratingUiState = RatingUiState.success(rating: rating)
            } catch {
                self.ratingUiState = RatingUiState.error
            }
        }
    }
    
    func cancel() {
        saveHandler?.cancel()
        ratingsHandler?.cancel()
    }
    
    
}
