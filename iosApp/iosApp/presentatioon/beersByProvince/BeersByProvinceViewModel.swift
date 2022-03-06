//
//  BeersByProvinceViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 18/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class BeersByProvinceViewModel : ObservableObject {
    
    @Published public var beers: [Beer] = []
    
    var hasNextPage: Bool = false
    
    private let beersRepository: CraftieBeersRepository
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
    }
    
    func load(province: String) {
        beersRepository.beersProvincesData(province: province).watch { pagingData in
            guard let list = pagingData?.compactMap({ $0 as? Beer }) else {
                return
            }
            self.beers = list
            self.hasNextPage = self.beersRepository.beersByProvincePager(province: province).hasNextPage
        }
    }
    
    func loadMoreContent(province: String) {
        beersRepository.beersByProvincePager(province: province).loadNext()
    }
    
    public var shouldDisplayNextPage: Bool {
            return hasNextPage
        }
    
    
}
