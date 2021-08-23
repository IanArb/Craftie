//
//  DiscoveryItems.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DiscoveryView: View {
    
    @ObservedObject var discoveryViewModel = DiscoveryViewModel(beersRepository: CraftieBeersRepository(), breweriesRepository: CraftieBreweriesRepository())
    
    var provinces = [
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FLeinster.png?alt=media&token=8cb29e1f-a9ad-46ee-8c8c-52b6737a22e9", name: "Leinster"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FConnaught.png?alt=media&token=32b6f284-d5ec-4ab4-b9e2-c71d15de999e", name: "Connaught"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FMunster.png?alt=media&token=ff179873-d7ac-45cf-a4de-358c70f64272", name: "Munster"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FUlster.png?alt=media&token=4ff562fe-4b5f-4a6f-aeff-fc770b0cf450", name: "Ulster")
    ]
    
    
    var body: some View {
        NavigationView {
            switch discoveryViewModel.state {
                case .idle:
                    Color.clear.onAppear(perform: discoveryViewModel.load)
                case .loading:
                    ProgressView()
                case .error:
                    ErrorView()
                case .empty:
                    EmptyErrorView()
                case .success(let discoveryUiData):
                    ZStack {
                        ScrollView(showsIndicators: false) {
                            VStack(alignment: .leading) {
                                BreweriesView(uiData: discoveryUiData)
                                    .padding(.bottom, 16)
                                Spacer()
                                
                                let featuredBeer = discoveryUiData.beers.first { $0.isFeatured == true}
                                
                                FeaturedView(featuredBeer: featuredBeer!)
                                    .padding(.bottom, 16)
                                
                                Spacer()
                                TopRatedView(uiData: discoveryUiData)
                                    .padding(.bottom, 16)
                                Spacer()
                                ProvincesView(provinces: provinces)
                                    .padding(.bottom, 16)
                                Spacer()
                                NewestBeersView(beers: discoveryUiData.beers)
                                    .padding(.bottom, 16)
                            }
                            .padding(16)
                        }
                        .navigationBarTitle(Text("Discovery"))
                    }
                    .background(Color(red: 248 / 255, green: 248 / 255, blue: 248 / 255))
            }
            
        }
    }
}


struct BreweriesView: View {
    var uiData: DiscoveryUiData
    
    var body: some View {
        HStack {
            Text("Breweries Nearby")
                .bold()
                .frame(alignment: .leading)
            Spacer()
            NavigationLink(destination: ViewAllBreweriesView()) {
                Text("View All").foregroundColor(Color.blue)
            }
        }
        .buttonStyle(PlainButtonStyle())
        .padding(.bottom, 4)
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top, spacing: 10) {
                ForEach(uiData.breweries, id: \.id) { brewery in
                    BreweriesCarouselView(imageUrl: brewery.imageUrl)
                }
            }
        }
    }
}

struct BreweriesCarouselView: View {
    var imageUrl: String
    var body: some View {
        ImageView(withURL: imageUrl, contentMode: .fit)
            .frame(width: 80, height: 80)
    }
}

struct FeaturedView: View {
    var featuredBeer: Beer
    
    var body: some View {
        Group {
            Text("Featured")
                .bold()
                .padding(.bottom, 8)
            NavigationLink(destination: FeaturedBeerView()) {
                ZStack(alignment: .bottomLeading) {
                    ZStack(alignment: .top) {
                        ImageView(withURL: featuredBeer.breweryInfo.brandImageUrl, contentMode: .fit)
                            .frame(maxWidth: .infinity)
                        
                        Rectangle()
                                .foregroundColor(.clear)
                                .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                            .cornerRadius(6.0)
                    }
                    Text(featuredBeer.name)
                        .bold()
                        .font(.title2)
                        .foregroundColor(.white)
                        .frame(maxWidth: 150, alignment: .bottom)
                        .padding(16)
                }
                
            }
            .buttonStyle(PlainButtonStyle())

        }
    }
}

struct TopRatedView: View {
    var uiData: DiscoveryUiData
    
    var body: some View {
        NavigationLink(destination: ViewAllTopRatedView()) {
            HStack {
                Text("Top Rated")
                    .bold()
                    .frame(alignment: .leading)
                Spacer()
                Text("View All").foregroundColor(Color.blue)
            }
        }
        .buttonStyle(PlainButtonStyle())
        .padding(.bottom, 4)
        
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top) {
                ForEach(uiData.beers.prefix(3), id: \.id) { beer in
                    NavigationLink(destination: BeerDetailView(id: beer.id)) {
                        BeersView(imageUrl: beer.imageUrl)
                            .frame(height: 200)
                    }
                    .buttonStyle(PlainButtonStyle())
                }
            }
        }
    }
}

struct BeersView: View {
    var imageUrl: String
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 10, style: .continuous)
                .fill(Color.white)
                .shadow(radius: 0.5)
            HStack {
                ImageView(withURL: imageUrl, contentMode: .fit)
                    .frame(width: 85, height: 150, alignment: .center)
            }
            .padding(16)
        }
        .padding(2)
        .frame(height: 175)
    }
}

struct ProvincesView: View {
    var provinces: [ProvinceLocal]
    
    var body: some View {
        Group {
            Text("Provinces")
                .bold()
                .padding(.bottom, 4)
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(alignment: .top, spacing: 16) {
                    ForEach(provinces, id: \.name) { province in
                        NavigationLink(destination: BeersByProvinceView(provinceSelected: province.name)) {
                            ProvincesCarouselView(imageUrl: province.imageUrl)
                        }
                        .buttonStyle(PlainButtonStyle())
                    }
                }
            }
        }
    }
}

struct ProvincesCarouselView: View {
    var imageUrl: String
    var body: some View {
        ImageView(withURL: imageUrl, contentMode: .fit)
            .frame(width: 80, height: 80)
    }
}

struct NewestBeersView: View {
    var beers: [Beer]
    
    var body: some View {
        HStack {
            Text("Newest")
                .bold()
                .frame(alignment: .leading)
        }
        .padding(.bottom, 4)
        
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top) {
                ForEach(beers.prefix(3), id: \.id) { beer in
                    NavigationLink(destination: BeerDetailView(id: beer.id)) {
                        BeersView(imageUrl: beer.imageUrl)
                            .frame(height: 200)
                    }
                    .buttonStyle(PlainButtonStyle())
                }
            }
        }
    }
}

struct EmptyErrorView: View {
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
            
            VStack {
                Text("Oops there is no available data at this time")
                    .font(.title)
                    .foregroundColor(.black)
            }
            .padding(20)
            .multilineTextAlignment(.center)
        }
        .frame(width: 450, height: 250)
    }
}

struct DiscoveryView_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryView()
    }
}