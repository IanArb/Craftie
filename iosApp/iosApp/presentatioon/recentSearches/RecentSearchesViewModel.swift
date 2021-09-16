//
//  RecentSearchesViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 15/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class RecentSearchesViewModel: ObservableObject {
    
    private let recentSearchesRepository: RecentSearchesRepository
    
    init(recentSearchesRepository: RecentSearchesRepository) {
        self.recentSearchesRepository = recentSearchesRepository
    }
    
    enum State {
        case success([RecentSearchDb])
        case idle
        case empty
    }
    
    @Published private(set) var state = State.idle
    
    func load() {
        recentSearchesRepository.findAllRecentSearches(success: { data in
            if (data.isEmpty) {
                self.state = .empty
            } else {
                self.state = .success(data)
            }
        })
    }
    
    func saveSearch(id: String, name: String) {
        recentSearchesRepository.saveRecentSearch(beerId: id, beerName: name)
    }
    
    func removeAllRecentSearches() {
        recentSearchesRepository.removeAllRecentSearches()
    }
    
}
