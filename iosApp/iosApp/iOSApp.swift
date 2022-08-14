import SwiftUI
import shared
import Firebase

@main
struct iOSApp: App {
    
    private let networkReachability: NetworkReachability = NetworkReachability()
    
    private let baseUrlProvider: BaseUrlProvider = BaseUrlProvider()
    
    @ObservedObject var authenticationViewModel: AuthenticationViewModel

    init() {
        authenticationViewModel = AuthenticationViewModel(
            authenticationRepository: CraftieAuthenticationRepository(),
            settingsRepository: SettingsRepository(baseUrl: baseUrlProvider.resolveUrl())
        )
        if (networkReachability.checkConnection() == true) {
            KoinKt.doInitCraftie(baseUrl: baseUrlProvider.resolveUrl())
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
