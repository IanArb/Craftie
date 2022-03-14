//
//  BeerDetailViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 24/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import MapKit
import KMPNativeCoroutinesAsync

class BeerDetailViewModel : ObservableObject {
    
    enum State {
        case idle
        case loading
        case error
        case success(Beer)
    }
    
    @Published private(set) var state = State.idle
    
    @Published var region: MKCoordinateRegion = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: 0.0, longitude: 0.0), span: MKCoordinateSpan(latitudeDelta: 0.05, longitudeDelta: 0.05))
    
    @Published var latLng: LatLng = LatLng(latitude: 0.0, longitude: 0.0)
    
    private let beersRepository: CraftieBeersRepository
    
    private let favouritesRepository: FavouritesRepository
    
    private var handler: Task<(), Never>? = nil
    
    init(beersRepository: CraftieBeersRepository, favouritesRepository: FavouritesRepository) {
        self.beersRepository = beersRepository
        self.favouritesRepository = favouritesRepository
    }
    
    func load(id: String) {
        state = .loading
        
        handler = Task {
            do {
                let beer = try await asyncFunction(for: beersRepository.findBeerNative(id: id))
                self.state = .success(beer)
                self.region = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: beer.breweryInfo.location.latLng.latitude, longitude: beer.breweryInfo.location.latLng.longitude), span: MKCoordinateSpan(latitudeDelta: 0.010, longitudeDelta: 0.010))
            } catch {
                self.state = .error
            }
        }
    }
    
    func save(beer: Beer) {
        favouritesRepository.saveBeer(beer: beer)
    }
    
    func cancel() {
        handler?.cancel()
    }
    
    
}
