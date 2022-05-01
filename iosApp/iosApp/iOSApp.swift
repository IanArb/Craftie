import SwiftUI
import shared
import Firebase

@main
struct iOSApp: App {
    
    private let networkReachability: NetworkReachability = NetworkReachability()
    
    @ObservedObject var authenticationViewModel: AuthenticationViewModel = AuthenticationViewModel(
        authenticationRepository: CraftieAuthenticationRepository(),
        usernamePasswordProvider: UsernamePasswordProvider(),
        settingsRepository: SettingsRepository()
    )

    init() {
        if (networkReachability.checkConnection() == true) {
            KoinKt.doInitKoin()
            authenticationViewModel.login()
        }
    }
    
    var body: some Scene {
        WindowGroup {
            if (networkReachability.checkConnection() == false) {
                InternetConnectionErrorView()
            } else {
                switch authenticationViewModel.state {
                    case .idle:
                        Color.clear
                    case .success:
                        ContentView()
                    case .error:
                        ErrorView {
                            authenticationViewModel.login()
                        }
                }
            }
        }
    }
}
