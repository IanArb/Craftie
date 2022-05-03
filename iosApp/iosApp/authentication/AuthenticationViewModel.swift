//
//  AuthenticationViewModel.swift
//  iosApp
//
//  Created by Ian Arbuckle on 01/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class AuthenticationViewModel : ObservableObject {
    
    enum State {
        case idle
        case success
        case error
    }
    
    @Published private(set) var state = State.idle
    
    private let authenticationRepository: CraftieAuthenticationRepository
    private let usernamePasswordProvider: UsernamePasswordProvider
    private let settingsRepository: SettingsRepository
    private var handler: Task<(), Never>? = nil
    
    init(authenticationRepository: CraftieAuthenticationRepository,
         usernamePasswordProvider: UsernamePasswordProvider,
         settingsRepository: SettingsRepository
    ) {
        self.authenticationRepository = authenticationRepository
        self.usernamePasswordProvider = usernamePasswordProvider
        self.settingsRepository = settingsRepository
    }
    
    func login() {
        handler = Task {
            do {
                let username = usernamePasswordProvider.username()
                let password = usernamePasswordProvider.password()
                
                let result = try await asyncFunction(for: authenticationRepository.loginNative(username: username, password: password))
                
                if (result.token != "") {
                    settingsRepository.clear()
                    settingsRepository.saveToken(token: result.token)
                    self.state = .success
                } else {
                    self.state = .error
                }
            } catch {
                self.state = .error
            }
            
        }
        
    }
}
