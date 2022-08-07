//
//  ViewAllTopRatedViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 15/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class ViewAllTopRatedViewModel : ObservableObject {
    
    @Published public var beers: [Beer] = []
    
    var hasNextPage: Bool = false
    
    private let beersRepository: CraftieBeersRepository
    
    private var handler: Task<(), Never>? = nil
    
    init(beersRepository: CraftieBeersRepository) {
        self.beersRepository = beersRepository
    }
    
    func load() {
        beersRepository.beersPagingData.watch { pagingData in
            guard let list = pagingData?.compactMap({ $0 as? Beer }) else {
                return
            }
            self.beers = list
            self.hasNextPage = self.beersRepository.beersPager.hasNextPage
        }
    }
    
    func loadMoreContent() {
        beersRepository.beersPager.loadNext()
    }
    
    public var shouldDisplayNextPage: Bool {
            return hasNextPage
        }
}
