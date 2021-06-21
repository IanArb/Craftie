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
    
    var provinces: [String] = [
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FConnaught.png?alt=media&token=32b6f284-d5ec-4ab4-b9e2-c71d15de999e",
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FLeinster.png?alt=media&token=8cb29e1f-a9ad-46ee-8c8c-52b6737a22e9",
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FMunster.png?alt=media&token=ff179873-d7ac-45cf-a4de-358c70f64272",
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FUlster.png?alt=media&token=4ff562fe-4b5f-4a6f-aeff-fc770b0cf450"
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
                    EmptyView()
                case .success(let discoveryUiData):
                    ZStack {
                        ScrollView(showsIndicators: false) {
                            VStack(alignment: .leading) {
                                BreweriesView(uiData: discoveryUiData)
                                    .padding(.bottom, 16)
                                Spacer()
                                FeaturedView()
                                    .padding(.bottom, 16)
                                Spacer()
                                TopRatedView(uiData: discoveryUiData)
                                    .padding(.bottom, 16)
                                Spacer()
                                ProvincesView(provinces: provinces)
                            }
                            .padding(16)
                        }
                        .navigationBarTitle(Text("Discovery"))
                    }.background(Color(red: 248, green: 248, blue: 248))
            }
            
        }
    }
}

struct HeaderLinkView: View {
    var title: String
    var body: some View {
        HStack {
            Text(title)
                .bold()
                .frame(alignment: .leading)
            Spacer()
            Text("View All").foregroundColor(Color.blue)
        }
    }
}

struct BreweriesView: View {
    var uiData: DiscoveryUiData
    
    var body: some View {
        HeaderLinkView(title: "Breweries Nearby")
            .padding(.bottom, 8)
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
        ImageView(withURL: imageUrl)
            .frame(width: 80, height: 80)
    }
}

struct FeaturedView: View {
    var body: some View {
        Group {
            Text("Featured")
                .bold()
                .padding(.bottom, 8)
            ImageView(withURL: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery_brand%2FWicklow%20Wolf.png?alt=media&token=9d1713cb-1c20-45ff-b6f9-cf4e037bcbf1")
                .frame(maxWidth: .infinity)
        }
    }
}

struct TopRatedView: View {
    var uiData: DiscoveryUiData
    
    var body: some View {
        HeaderLinkView(title: "Top Rated")
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top) {
                ForEach(uiData.beers.prefix(3), id: \.id) { beer in
                    BeersView(imageUrl: beer.imageUrl)
                        .frame(height: 200)
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
                ImageView(withURL: imageUrl)
                    .frame(width: 85, height: 150, alignment: .center)
            }
            .padding(16)
        }
        .padding(2)
        .frame(height: 175)
    }
}

struct ProvincesView: View {
    var provinces: [String]
    var body: some View {
        Group {
            Text("Provinces")
                .bold()
                .padding(.bottom, 8)
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(alignment: .top, spacing: 16) {
                    ForEach(provinces, id: \.self) { imageUrl in
                        ProvincesCarouselView(imageUrl: imageUrl)
                    }
                }
            }
        }
    }
}

struct ProvincesCarouselView: View {
    var imageUrl: String
    var body: some View {
        ImageView(withURL: imageUrl)
            .frame(width: 80, height: 80)
    }
}

struct ErrorView: View {
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
            
            VStack {
                Text("Oops something went wrong, please try again.")
                    .font(.title)
                    .foregroundColor(.black)
            }
            .padding(20)
            .multilineTextAlignment(.center)
        }
        .frame(width: 450, height: 250)
    }
}

struct EmptyView: View {
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
