//
//  FeaturedBeerViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 04/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class FeaturedBeerViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case success(Beer)
    }
    
    @Published private(set) var state = State.idle
    
    private let beersRepository: CraftieBeersRepository
    
    private var handler: Task<(), Never>? = nil
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
    }
    
    func load() {
        state = .loading
    
        handler = Task {
            do {
                let featuredBeer = try await asyncFunction(for: beersRepository.featuredBeerNative())
                self.state = .success(featuredBeer)
            } catch {
                self.state = .error
            }
        }
    }
    
    func cancel() {
        handler?.cancel()
    }
}
