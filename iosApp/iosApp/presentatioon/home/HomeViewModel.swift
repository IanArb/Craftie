//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class HomeViewModel : ObservableObject {
    
    enum State {
        case idle
        case empty
        case success([BeersDb])
    }
    
    @Published private(set) var state = State.idle
    
    private let databaseRepository: DatabaseRepository
    
    init(databaseRepository: DatabaseRepository) {
        self.databaseRepository = databaseRepository
    }
    
    func load() {
        databaseRepository.findAllBeers( success: { data in
            if (data.isEmpty) {
                self.state = .empty
            } else {
                self.state = .success(data)
            }
        })
        
    }
    
    func deleteBeer(beer: BeersDb) {
        databaseRepository.removeBeer(beer: beer)
    }
    
    func deleteAllBeers() {
        databaseRepository.removeAll()
    }
}
