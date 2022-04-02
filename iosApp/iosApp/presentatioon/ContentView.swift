import SwiftUI
import shared

struct ContentView: View {
    
    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Label("Home", systemImage: "house")
                }
            DiscoveryView()
                .tabItem {
                    Label("Discovery", systemImage: "map")
                }
            SearchView()
                .tabItem {
                    Label("Search", systemImage: "magnifyingglass")
                }
        }
    }
}

struct InternetConnectionErrorView: View {
    var body: some View {
        GenericErrorView(
            title: "No Internet Connection",
            message: "Oops.. please try to reconnect to the internet by going to your connection settings."
        )
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
