//
//  ViewAllBreweriesViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 14/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ViewAllBreweriesViewModel : ObservableObject {
    
    @Published public var breweries: [Result] = []
    
    var hasNextPage: Bool = false
    
    private let breweriesRepository: CraftieBreweriesRepository
    
    init(breweriesRepository: CraftieBreweriesRepository) {
        self.breweriesRepository = breweriesRepository
    }
    
    func load() {
        breweriesRepository.breweriesPagingData.watch { pagingData in
            guard let list = pagingData?.compactMap({ $0 as? Result }) else {
                return
            }
            self.breweries = list
            self.hasNextPage = self.breweriesRepository.breweriesPager.hasNextPage
        }
    }
    
    func loadMoreContent() {
        breweriesRepository.breweriesPager.loadNext()
    }
    
    public var shouldDisplayNextPage: Bool {
            return hasNextPage
        }
}
