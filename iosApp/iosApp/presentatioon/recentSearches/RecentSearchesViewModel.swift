//
//  RecentSearchesViewModel.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 15/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class RecentSearchesViewModel: ObservableObject {
    
    private let recentSearchesRepository: RecentSearchesRepository
    
    init(recentSearchesRepository: RecentSearchesRepository) {
        self.recentSearchesRepository = recentSearchesRepository
    }
    
    enum State {
        case success([RecentSearchUiData])
        case idle
        case empty
        case error
    }
    
    @Published private(set) var state = State.idle
    
    private var handler: Task<(), Never>? = nil
    private var saveHandler: Task<(), Never>? = nil
    private var removeHandler: Task<(), Never>? = nil
    private var removeAllHandler: Task<(), Never>? = nil
    
    func load() {
        handler = Task {
            do {
                let stream = asyncStream(for: recentSearchesRepository.findAllRecentSearchesNative())
                for try await searches in stream {
                    if (searches.isEmpty) {
                        self.state = .empty
                    } else {
                        self.state = .success(searches)
                    }
                }
            } catch {
                self.state = .error
            }
        }
    }
    
    func saveSearch(id: String, name: String) {
        saveHandler = Task {
            do {
                _ = try await asyncFunction(for: recentSearchesRepository.saveRecentSearchNative(beerId: id, beerName: name))
            } catch {
                self.state = .error
            }
        }
    }
    
    func removeAllRecentSearches() {
        removeAllHandler = Task {
            do {
                _ = try await asyncFunction(for: recentSearchesRepository.removeAllRecentSearchesNative())
            } catch {
                self.state = .error
            }
        }
    }
    
    func removeRecentSearch(id: String) {
        removeHandler = Task {
            do {
                _ = try await asyncFunction(for: recentSearchesRepository.removeRecentSearchNative(id: id))
            } catch {
                self.state = .error
            }
        }
    }
    
    func cancelSaveHandler() {
        saveHandler?.cancel()
    }
    
    func cancelRemoveAllHandler() {
        removeAllHandler?.cancel()
    }
    
    func cancelRemoveHandler() {
        removeHandler?.cancel()
    }
    
}
