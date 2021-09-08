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
    
    @ObservedObject var viewModel = HomeViewModel(databaseRepository: DatabaseRepository())
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading) {
                    switch viewModel.state {
                        case .idle:
                            Color.clear.onAppear(perform: viewModel.load)
                        case .empty:
                            FavouritesHeader(showClearAll: false) {
                                
                            }
                            EmptyFavouritesCard()
                        case .success(let beersDb):
                            FavouritesHeader(showClearAll: beersDb.count > 0) {
                                viewModel.deleteAllBeers()
                            }
                            FavouritesCard(beers: beersDb) { beersDb in
                                viewModel.deleteBeer(beer: beersDb)
                            }
                    }
                    BeersTasted()
                }
                    
                .navigationBarTitle(Text("Home"))
            }
            .background(Color(red: 248 / 255, green: 248 / 255, blue: 248 / 255))
        }
    }
}

struct FavouritesCard : View {
    var beers: [BeersDb]
    var onDeleteBeerClick: (BeersDb) -> Void
    
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
                                    .frame(width: 120, height: 150)
                                Text(beer.name)
                                    .lineLimit(1)
                                    .frame(width: 150)
                            }
                        }
                        .padding(16)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(Color.white)
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
                .fill(Color.white)
                .frame(width: .infinity)
                .padding(.leading, 12)
                .padding(.trailing, 12)
                .padding(.top, 4)
        )
    }
}

struct BeersTasted : View {
    var provinces = [
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FLeinster.png?alt=media&token=8cb29e1f-a9ad-46ee-8c8c-52b6737a22e9", name: "Leinster"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FConnaught.png?alt=media&token=32b6f284-d5ec-4ab4-b9e2-c71d15de999e", name: "Connaught"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FMunster.png?alt=media&token=ff179873-d7ac-45cf-a4de-358c70f64272", name: "Munster"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FUlster.png?alt=media&token=4ff562fe-4b5f-4a6f-aeff-fc770b0cf450", name: "Ulster")
    ]
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Beers Tasted")
                .fontWeight(.medium)
                .padding(.top, 10)
                .padding(.leading, 16)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(alignment: .top, spacing: 8) {
                    ForEach(provinces, id: \.name) { province in
                        VStack(alignment: .center) {
                            ImageView(withURL: province.imageUrl, contentMode: .fit)
                                .frame(width: 80, height: 80)
                            Text(province.name)
                            Text("25%")
                        }
                        .padding(16)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(Color.white)
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
