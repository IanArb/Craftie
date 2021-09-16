//
//  SearchView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecentSearch {
    var name: String = ""
}

struct PopularStyle {
    var name: String = ""
}

struct PopularBrewery {
    var name: String = ""
}

struct SearchView: View {
    
    @ObservedObject var viewModel = SearchViewModel(beersRepository: CraftieBeersRepository())
    
    @State private var searchText = ""
    
    @State private var isEditing = false
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading) {
                VStack(alignment: .leading) {
                    VStack {
                        HStack {
                            TextField("Beer, brewery, flavour, type", text: $viewModel.searchText)
                                .padding(6)
                                .padding(.horizontal, 25)
                                .background(Color(.systemGray6))
                                .cornerRadius(8)
                                .overlay(
                                    HStack {
                                        Image(systemName: "magnifyingglass")
                                            .foregroundColor(.gray)
                                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                                            .padding(.leading, 8)
                                        
                                        if isEditing {
                                            Button(action: {
                                                viewModel.searchText = ""
                                            }) {
                                                Image(systemName: "multiply.circle.fill")
                                                    .foregroundColor(.gray)
                                                    .padding(.trailing, 8)
                                            }
                                        }
                                    }
                                )
                                .padding(.horizontal, 10)
                                .onTapGesture {
                                    self.isEditing = true
                                }
                            
                            if isEditing {
                                Button(action: {
                                    self.isEditing = false
                                    viewModel.searchText = ""
                                    UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                                }) {
                                    Text("Cancel")
                                }
                                .padding(.trailing, 10)
                                .transition(.move(edge: .trailing))
                                .animation(.default)
                            }
                        }
                    }
                    
                    switch viewModel.state {
                        case .idle:
                            PopularSearchesView()
                        case .loading:
                            ProgressView()
                        case .error:
                            ErrorView()
                        case .success(let beers):
                            SearchResults(beers: beers)
                        case .empty:
                            ErrorView()
                    }
                    
                    Spacer()
            }
            
            .navigationBarTitle(Text("Search"))
        }
        .background(Color(red: 248, green: 248, blue: 248))
        }
    }
}

struct PopularSearchesView : View {
    
    @ObservedObject var viewModel = RecentSearchesViewModel(recentSearchesRepository: RecentSearchesRepository())
    
    
    var body: some View {
        
        ScrollView(showsIndicators: false) {
            VStack(alignment: .leading) {
                Spacer()
                    .padding(.bottom, 10)
                
                switch viewModel.state {
                    case .idle:
                        Color.clear.onAppear(perform: {
                            viewModel.load()
                        })
                    case .empty:
                        StylesView()
                        Spacer()
                            .padding(.bottom, 4)
                    case .success(let recentSearches):
                        RecentSearchHeader {
                            viewModel.removeAllRecentSearches()
                        }
                        ForEach(recentSearches, id: \.name) { recentSearch in
                            Text(recentSearch.name)
                                .padding(.top, 2)
                            Divider()
                                .frame(maxWidth: 380)
                        }
                        Spacer()
                            .padding(.bottom, 4)
                        StylesView()
                }
                
                
            }
            .padding(.leading, 20)
        }
    }
}

struct RecentSearchHeader : View {
    var onClearAllClick: () -> Void
    
    var body: some View {
        HStack {
            Text("Recent Searches")
                .bold()
                .font(.title3)
                .padding(.bottom, 6)
            
            Spacer()
            
            Text("Clear All")
                .fontWeight(.medium)
                .foregroundColor(.blue)
                .onTapGesture {
                    onClearAllClick()
                }
                .padding(.trailing, 10)
        }
        
    }
}

struct StylesView : View {
    private var popularStyles = [
        PopularStyle(name: "Lager"),
        PopularStyle(name: "Stout"),
        PopularStyle(name: "India Pale Ale"),
        PopularStyle(name: "Pilsner"),
        PopularStyle(name: "Red Ale"),
        PopularStyle(name: "Dunkel")
    ]
    
    private var popularBreweries = [
        PopularStyle(name: "Five Lamps"),
        PopularStyle(name: "Rascals"),
        PopularStyle(name: "McGargles"),
        PopularStyle(name: "Wicklow Wolf")
    ]
    
    var body: some View {
        Text("Popular Styles")
            .bold()
            .font(.title3)
            .padding(.bottom, 2)
        ForEach(popularStyles, id: \.name) { style in
            Text(style.name)
                .padding(.top, 6)
            Divider()
                .frame(maxWidth: 380)
        }
        Spacer()
            .padding(.bottom, 4)
        Text("Popular Breweries")
            .bold()
            .font(.title3)
            .padding(.bottom, 6)
        ForEach(popularBreweries, id: \.name) { brewery in
            Text(brewery.name)
                .padding(.top, 2)
            Divider()
                .frame(maxWidth: 380)
        }
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
    }
}
