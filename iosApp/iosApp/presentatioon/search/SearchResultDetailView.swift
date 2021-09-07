//
//  SearchResultDetailView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 12/08/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import MapKit

struct SearchResultDetailView: View {
    var id: String
    
    @ObservedObject var viewModel = BeerDetailViewModel(beersRepository: CraftieBeersRepository(), databaseRepository: DatabaseRepository())
    
    var body: some View {
        switch viewModel.state {
            case .idle:
                Color.clear.onAppear(perform: {
                    viewModel.load(id: id)
                })
            case .error:
                ErrorView()
            case .success(let beer):
                ScrollView(showsIndicators: false) {
                    SearchResultCard(beer: beer, viewModel: viewModel)
                }
            case .loading:
                ProgressView()
        }
    }
}

struct SearchResultCard: View {
    var beer: Beer
    var viewModel: BeerDetailViewModel
    
    var body: some View {
        VStack {
            ZStack(alignment: .top) {
                ImageView(withURL: beer.breweryInfo.brandImageUrl, contentMode: .fill)
                    .frame(width: .infinity, height: 220)
                
                Rectangle()
                        .foregroundColor(.clear)
                        .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                    .frame(width: .infinity, height: 250)
                
                BeerDetailCard(beer: beer) {
                    viewModel.save(beer: beer)
                }
            }
            
            BeerDescription(beer: beer)
            
            BreweryDescription(breweryInfo: beer.breweryInfo)
            
            BreweryLocation(location: beer.breweryInfo.location, viewModel: viewModel)
            
        }
        .padding(.bottom , 20)
    }
}

struct BeerDetailCard: View {
    var beer: Beer
    var onFavouriteClick: () -> Void
        
    var body: some View {
        VStack(alignment: .center) {
            ZStack(alignment: .center) {
                
                RoundedRectangle(cornerRadius: 6, style: .continuous)
                    .fill(Color.white)
                    .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
                    .frame(width: .infinity, height: 275)
                    .padding(.leading, 20)
                    .padding(.trailing, 20)
                    .padding(.top, 4)
    
                
                VStack(alignment: .center) {
                    HStack {
                        VStack {
                            Text(beer.type)
                                .font(.footnote)
                                .padding(8)
                        }
                        .background(
                            RoundedRectangle(cornerRadius: 6.0, style: .continuous)
                                .fill(Color(red: 242 / 255, green: 201 / 255, blue: 76 / 255))
                        )
                        .padding(.leading, 36)
                        
                        Spacer()
                        
                        VStack(spacing: 10) {
                            LikeButton {
                                onFavouriteClick()
                            }
                            .padding(10)
                        }
                        .background(
                            Circle().fill(Color(red: 242 / 255, green: 242 / 255, blue: 242 / 255))
                        )
                        .padding(.trailing, 36)
                    
                    }
                    .padding(.top, 20)
                    
                    VStack {
                        ImageView(withURL: beer.imageUrl, contentMode: ContentMode.fit)
                            .frame(width: 100, height: 215)
                        
                        Text(beer.breweryInfo.name)
                            .font(.caption)
                            .foregroundColor(Color.gray)
                        Text(beer.name)
                            .font(.title3)
                            .fontWeight(.medium)
                            .fixedSize(horizontal: false, vertical: true)
                        RatingView(rating: 4)
                            .padding(.top, 6)
                        Text("Based on 300 reviews")
                            .font(.caption)
                            .foregroundColor(Color.gray)
                            .padding(.top, 2)
                            .padding(.bottom, 16)
                        Button(action: {
                            
                        }) {
                            HStack {
                                Text("REVIEW")
                                    .fontWeight(.bold)
                                    .foregroundColor(.white)
                                    .background(Color(red: 242 / 255, green: 153 / 255, blue: 74 / 255))
                                    
                            }
                            .padding(10)
                            
                        }
                        .frame(width: 300)
                        .background(
                            RoundedRectangle(cornerRadius: 6.0, style: .continuous)
                                .fill(Color.orange)
                        )
                        .padding(.bottom, 16)
                    }
                    .padding(.top, -180)
                    
                }
            }
            .padding(.top, 220)
    }
}
}

struct BeerDescription: View {
    var beer: Beer
    
    var body: some View {
        VStack {
            VStack(alignment: .leading) {
                Text("Description")
                    .fontWeight(.medium)
                    .font(.system(size: 16))
                    .padding(.top, 16)
                    
                Text(beer.description_)
                    .fontWeight(.light)
                    .font(.system(size: 12))
                    .padding(.top, 1)
                    .fixedSize(horizontal: false, vertical: true)
            }
            .padding(.leading, 36)
            .padding(.trailing, 36)
            
            HStack {
                Text("ABV: " + "\(beer.abv.value)" + "\(beer.abv.unit)")
                    .fontWeight(.medium)
                Spacer()
                if (beer.ibu?.value != nil) {
                    Text("IBU: " + "\(beer.ibu!.value)")
                        .fontWeight(.medium)
                }
            }
            .padding(.top, 16)
            .padding(.bottom, 24)
            .padding(.leading, 100)
            .padding(.trailing, 100)
        }
        .background(
            RoundedRectangle(cornerRadius: 6)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: 0.0, y: 7)
                .frame(width: .infinity)
                .padding(.leading, 20)
                .padding(.trailing, 20)
        )
    }
}

struct BreweryDescription: View {
    var breweryInfo: BreweryInfo
    
    var body: some View {
        VStack {
            VStack(alignment: .leading) {
                Text("About Brewery")
                    .fontWeight(.medium)
                    .font(.system(size: 16))
                    .padding(.top, 16)
                    
                Text(breweryInfo.description_)
                    .fontWeight(.light)
                    .font(.system(size: 12))
                    .padding(.top, 1)
            }
            .padding(.bottom, 16)
            .padding(.leading, 36)
            .padding(.trailing, 36)
        }
        .background(
            RoundedRectangle(cornerRadius: 6)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: 0.0, y: 7)
                .frame(width: .infinity)
                .padding(.leading, 20)
                .padding(.trailing, 20)
        )
    }
}

struct BreweryLocation: View {
    var location: Location
    var viewModel: BeerDetailViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            VStack(alignment: .leading) {
                Text("Location")
                    .fontWeight(.medium)
                    .font(.system(size: 16))
                    .padding(.top, 16)
                    
                Text(location.address)
                    .fontWeight(.light)
                    .font(.system(size: 12))
                    .padding(.top, 1)
                    .fixedSize(horizontal: false, vertical: true)
                
            }
            .padding(.bottom, 16)
            .padding(.leading, 36)
            .padding(.trailing, 36)
            
            MapView(viewModel: viewModel)
                .frame(width: .infinity, height: 150)
                .padding(.leading, 20)
                .padding(.trailing, 20)
            
        }
        .background(
            RoundedRectangle(cornerRadius: 6)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: 0.0, y: 7)
                .frame(width: .infinity)
                .padding(.leading, 20)
                .padding(.trailing, 20)
        )
    }
}

struct MapView : View {
    @ObservedObject var viewModel: BeerDetailViewModel
    
    var body: some View {
        let pointOfInterests = [
            AnnotatedItem(coordinate: .init(latitude: viewModel.latLng.latitude, longitude: viewModel.latLng.longitude))
        ]
        
        Map(coordinateRegion: $viewModel.region, annotationItems: pointOfInterests) { item in
            MapMarker(coordinate: item.coordinate)
        }
    }
}

struct AnnotatedItem: Identifiable {
    let id = UUID()
    var coordinate: CLLocationCoordinate2D
}
