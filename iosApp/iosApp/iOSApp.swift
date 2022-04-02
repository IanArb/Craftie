import SwiftUI
import shared
import Firebase

@main
struct iOSApp: App {
    
    private let networkReachability: NetworkReachability = NetworkReachability()

    init() {
        if (networkReachability.checkConnection() == true) {
            KoinKt.doInitKoin()
        }
    }
    
    var body: some Scene {
        WindowGroup {
            if (networkReachability.checkConnection() == false) {
                InternetConnectionErrorView()
            } else {
                ContentView()
            }
        }
    }
}
