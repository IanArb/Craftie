//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class HomeViewModel : ObservableObject {
    
    private let favouritesRepository: FavouritesRepository
    
    init(
        favouritesRepository: FavouritesRepository
    ) {
        self.favouritesRepository = favouritesRepository
    }
    
    enum State {
        case success([FavouriteBeerUiData])
        case empty
        case error
        case idle
    }
    
    @Published private(set) var state = State.idle
    
    private var handler: Task<(), Never>? = nil
    private var saveHandler: Task<(), Never>? = nil
    private var removeHandler: Task<(), Never>? = nil
    private var removeAllHandler: Task<(), Never>? = nil
    
    func loadFavourites() {
        handler = Task {
            do {
                let stream = asyncStream(for: favouritesRepository.findAllBeersNative())
                for try await beers in stream {
                    if (beers.isEmpty) {
                        self.state = .empty
                    } else {
                        self.state = .success(beers)
                    }
                }
            } catch {
                self.state = .error
            }
        }
    }
    
    func deleteBeerById(id: String) {
        removeHandler = Task {
            do {
                _ = try await asyncFunction(for: favouritesRepository.removeBeerNative(id: id))
            } catch {
                self.state = .error
            }
        }
    }
    
    func deleteAllBeers() {
        removeAllHandler = Task {
            do {
                _ = try await asyncFunction(for: favouritesRepository.removeAllNative())
            } catch {
                self.state = .error
            }
        }
    }
    
    func cancelSaveHandler() {
        saveHandler?.cancel()
    }
    
    func cancelRemoveHandler() {
        removeHandler?.cancel()
        removeAllHandler?.cancel()
    }

}
