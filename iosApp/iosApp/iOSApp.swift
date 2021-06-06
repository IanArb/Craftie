import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin(enableNetworkLogs: false)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
