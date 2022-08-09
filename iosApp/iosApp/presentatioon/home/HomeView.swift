//
//  HomeView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeView: View {
    @ObservedObject var viewModel = HomeViewModel(
        favouritesRepository: FavouritesRepository()
    )
    
    @ObservedObject var favouriteBeersViewModel = BeersFavouritesViewModel(provincesCount: ProvincesCountRepository())
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack (alignment: .leading) {
                    switch (viewModel.state) {
                        case .idle:
                            Color.clear.onAppear(perform:
                                viewModel.loadFavourites
                            )
                        case .error:
                            FavouritesHeader(showClearAll: false) {
                                
                            }
                            EmptyFavouritesCard()
                        case .empty:
                            FavouritesHeader(showClearAll: false) {
                                
                            }
                            EmptyFavouritesCard()
                        case .success(let favourites):
                            FavouritesView(
                                beers: favourites,
                                onDeleteBeerClick: { beer in
                                    viewModel.deleteBeerById(id: beer.id)
                                },
                                onDeleteAllBeers: {
                                    viewModel.deleteAllBeers()
                                }
                            )
                        
                            switch (favouriteBeersViewModel.state) {
                                case .idle:
                                Color.clear.onAppear(perform: {
                                    favouriteBeersViewModel.fetchFavouriteBeersByProvince(favourites: favourites)
                                })
                                case .success(let beers):
                                    BeersTasted(favouriteBeers: beers)
                                case .error:
                                    Color.clear
                                
                            }
                    }
                }
                .navigationBarTitle(Text("Home"))
                .onDisappear(perform: {
                    viewModel.cancelSaveHandler()
                    viewModel.cancelRemoveHandler()
                })
                
            }
            .background(Color.backgroundColor)
        }
    }

}

struct FavouritesView: View {
    var beers: [FavouriteBeerUiData]
    var onDeleteBeerClick: (FavouriteBeerUiData) -> Void
    var onDeleteAllBeers: () -> Void
    
    var body: some View {
        FavouritesHeader(showClearAll: beers.count > 0) {
            onDeleteAllBeers()
        }
        FavouritesCard(beers: beers) { beer in
            onDeleteBeerClick(beer)
        }
    }
}

struct FavouritesCard : View {
    var beers: [FavouriteBeerUiData]
    var onDeleteBeerClick: (FavouriteBeerUiData) -> Void
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top, spacing: 8) {
                ForEach(beers, id: \.name) { beer in
                
                    NavigationLink(destination: SearchResultDetailView(id: beer.id)) {
                        VStack(alignment: .leading) {
                            VStack(alignment: .leading) {
                                Image(systemName: "xmark")
                                    .font(.system(size: 12))
                                    .padding(6)
                                    .onTapGesture {
                                        onDeleteBeerClick(beer)
                                    }
                            }
                            .background(
                                Circle().fill(Color(red: 242 / 255, green: 242 / 255, blue: 242 / 255))
                            )
                            VStack(alignment: .center) {
                                ImageView(withURL: beer.imageUrl, contentMode: .fit)
                                    .frame(width: 120, height: 160)
                                Text(beer.name)
                                    .lineLimit(1)
                                    .frame(width: 150)
                            }
                        }
                        .padding(16)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(Color.surfaceColor)
                                .frame(width: .infinity)
                        )
                    }
                    .buttonStyle(PlainButtonStyle())
                }
            }
        }
        .padding(16)
    }
}

struct FavouritesHeader : View {
    var showClearAll: Bool
    var onClearAllClick: () -> Void
    
    var body: some View {
        HStack {
            Image(systemName: "heart")
                .foregroundColor(.red)
                .padding(10)
            .background(
                Circle().fill(Color(red: 255 / 255, green: 227 / 255, blue: 222 / 255))
            )
            
            Text("Your Favourites")
                .fontWeight(.medium)
            
            Spacer()
            
            if (showClearAll) {
                Text("Clear All")
                    .fontWeight(.medium)
                    .foregroundColor(.blue)
                    .onTapGesture {
                        onClearAllClick()
                    }
            }
            
        }
        .padding(.top, 8)
        .padding(.leading, 12)
        .padding(.trailing, 12)
    }
}

struct EmptyFavouritesCard : View {
    var body: some View {
        VStack {
            VStack(alignment: .leading) {
                
                Text("You have no favourites. Why not go ahead and add some?")
                    .fontWeight(.light)
                    .padding(.top, 6)
            }
            .padding(24)
        }
        .background(
            RoundedRectangle(cornerRadius: 6)
                .fill(Color.surfaceColor)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
                .frame(width: .infinity)
                .padding(.leading, 12)
                .padding(.trailing, 12)
                .padding(.top, 4)
        )
    }
}

struct BeersTasted : View {
    var favouriteBeers: [FavouriteBeers]
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Favourites by province")
                .fontWeight(.medium)
                .padding(.top, 10)
                .padding(.leading, 16)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(alignment: .top, spacing: 8) {
                    ForEach(favouriteBeers, id: \.name) { favourite in
                        VStack(alignment: .center) {
                            ImageView(withURL: favourite.imageUrl, contentMode: .fit)
                                .frame(width: 80, height: 80)
                            Text(favourite.name)
                            let value = NSString(format: "%.0f", favourite.percentage) as String
                            Text(value + "%")
                        }
                        .padding(16)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(Color.surfaceColor)
                                .frame(width: .infinity)
                        )
                    }
                }
            }
            .padding(16)
        }
    }

}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
