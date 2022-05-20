//
//  BeersFavouritesUseCase.swift
//  iosApp
//
//  Created by Ian Arbuckle on 15/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class BeersFavouritesViewModel : ObservableObject {
    
    let provincesCount: ProvincesCountRepository
    
    init(provincesCount: ProvincesCountRepository) {
        self.provincesCount = provincesCount
    }
    
    enum State {
        case success([FavouriteBeers])
        case error
        case idle
    }
    
    @Published private(set) var state = State.idle
    
    private var handler: Task<(), Never>? = nil
    
    func fetchFavouriteBeersByProvince(favourites: [FavouriteBeerUiData]) {
        handler = Task {
            do {
                let provinces = try await asyncFunction(for: provincesCount.provincesCountNative())
                
                let countLeinster = favourites.filter { $0.province == "Leinster" }.count
                let countMunster = favourites.filter { $0.province == "Munster" }.count
                let countUlster = favourites.filter { $0.province == "Ulster" }.count
                let countConnaught = favourites.filter {$0.province == "Connaught"}.count
                
                let leinster = FavouriteBeers(
                    name: provinces.leinster.name,
                    percentage: calculatePercentage(
                        value: Int(provinces.leinster.amount),
                        total: countLeinster
                    ),
                    imageUrl: provinces.leinster.imageUrl
                )
                
                let munster = FavouriteBeers(
                    name: provinces.munster.name,
                    percentage: calculatePercentage(
                        value: countMunster,
                        total: Int(provinces.munster.amount)
                    ),
                    imageUrl: provinces.munster.imageUrl
                )
                
                let ulster = FavouriteBeers(
                    name: provinces.ulster.name,
                    percentage: calculatePercentage(
                        value: countUlster,
                        total: Int(provinces.ulster.amount)
                    ),
                    imageUrl: provinces.ulster.imageUrl
                )
                
                let connaught = FavouriteBeers(
                    name: provinces.connaught.name,
                    percentage: calculatePercentage(
                        value: countConnaught,
                        total: Int(provinces.connaught.amount)
                    ),
                    imageUrl: provinces.connaught.imageUrl
                )
                
                self.state = .success([leinster, munster, connaught, ulster])
            } catch {
                self.state = .error
            }
        }
    }
    
    func calculatePercentage(value: Int, total: Int) -> Double {
        if (value == 0) {
            return 0.0
        }
        let result = (Double(total) / Double(value)) * 100
        return result.rounded()
    }
}
