//
//  SearchViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/08/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

class SearchViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case empty
        case success([Beer])
    }
    
    var subscription: Set<AnyCancellable> = []
    
    @Published private(set) var state = State.idle
    
    @Published var searchText: String = String()
    
    private let beersRepository: CraftieBeersRepository
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
        
        $searchText
            .debounce(for: .milliseconds(800), scheduler: RunLoop.main)
            .removeDuplicates()
            .map({ (string) -> String? in
                if string.count < 2 {
                    self.state = .empty
                    return nil
                }
                            
                return string
            })
            .compactMap{ $0 }
            .sink { (_) in
                
            } receiveValue: {
                [self] (searchField) in
                        query(keyword: searchField)
            }.store(in: &subscription)
    }
    
    func query(keyword: String) {
        state = .loading
        
        beersRepository.findBeersByKeyword(keyword: keyword) { data, error in
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
