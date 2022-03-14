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
import BottomSheetSwiftUI


enum BookBottomSheetPosition: CGFloat, CaseIterable {
    case middle = 0.4, bottom = 0.125, hidden = 0
}

struct SearchResultDetailView: View {
    var id: String
    
    @ObservedObject var viewModel = BeerDetailViewModel(beersRepository: CraftieBeersRepository(), favouritesRepository: FavouritesRepository())
    
    @ObservedObject var recentSearchesViewModel = RecentSearchesViewModel(recentSearchesRepository: RecentSearchesRepository())
    
    @ObservedObject var saveBeerRatingViewModel = SaveBeerRatingViewModel(craftieBeerRatingsRepository: CraftieBeerRatingsRepository())
    
    @State var uiTabarController: UITabBarController?
    
    @State var bottomSheetPosition: BottomSheetPosition = .hidden
    
    @State private var description: String = ""
    
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
                    SearchResultCard(beer: beer, viewModel: viewModel) {
                        bottomSheetPosition = .middle
                    }
                }
                .introspectTabBarController { (UITabBarController) in
                    UITabBarController.tabBar.isHidden = true
                    uiTabarController = UITabBarController
                }
                .onAppear {
                    recentSearchesViewModel.saveSearch(id: beer.id, name: beer.name)
                }
                .onDisappear {
                    saveBeerRatingViewModel.cancel()
                }
                .bottomSheet(
                    bottomSheetPosition: self.$bottomSheetPosition,
                    options: [.swipeToDismiss, .tapToDissmiss, .backgroundBlur(effect: .dark), .background(AnyView(Color.white))]
                ) {
                    VStack(spacing: 0) {
                        switch saveBeerRatingViewModel.state {
                            case .idle:
                                BeerReviewView(id: id, showError: false) { request in
                                    saveBeerRatingViewModel.saveRating(rating: request)
                                }
                            case .success:
                                Image(systemName: "checkmark.circle.fill")
                                    .foregroundColor(.green)
                                    .padding(.top, 16)
                                    .font(.system(size: 42))
                                Text("Successfully sent rating")
                                    .fontWeight(.medium)
                            case .error:
                                BeerReviewView(id: id, showError: true) { request in
                                    saveBeerRatingViewModel.saveRating(rating: request)
                                }
                                Spacer()
                                Text("There was an error sending your request, please try again.")
                                    .foregroundColor(.red)
                            case .loading:
                                VStack {
                                    ProgressView()
                                        .padding(.top, 16)
                                    Text("Sending rating..")
                                        .fontWeight(.medium)
                                }
                        }
                        
                    }
                    .padding([.horizontal, .top])
                }
            case .loading:
                ProgressView()
        }
    }
}

struct BeerReviewView: View {
    @State private var rating: Int?
    @State private var description: String = ""
    var id: String
    var showError: Bool
    var onDoneClick: (RatingRequest) -> Void
    
    var body: some View {
        Text("Select Rating")
            .padding(.bottom, 2)
                                        
        RatingView(rating: $rating)
            .padding(.bottom, 2)
        
        VStack(alignment: .leading) {
            Text("Enter description")
            ZStack {
                TextEditor(text: $description)
            }
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(Color.gray))
            .padding(.top, 4)
            .frame(height: 80)
            
        }
        .padding(.top, 16)
       
        CTAButton(text: "Done") {
            if let rating = self.rating {
                let ratingRequest = RatingRequest(
                    beerId: id,
                    authorName: "",
                    description: description,
                    rating: Double(rating))
                onDoneClick(ratingRequest)
            }
        }
        .padding(.top, 16)
        
        if (showError) {
            Text("There was an error sending your request, please try again.")
                .foregroundColor(.red)
        }
    }
}

struct SearchResultCard: View {
    var beer: Beer
    var viewModel: BeerDetailViewModel
    var onReviewClick: () -> Void
    
    var body: some View {
        VStack {
            ZStack(alignment: .top) {
                if let brandImageUrl = beer.breweryInfo.brandImageUrl {
                    ImageView(withURL: brandImageUrl, contentMode: .fill)
                        .frame(width: .infinity, height: 220)
                    
                    Rectangle()
                            .foregroundColor(.clear)
                            .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                        .frame(width: .infinity, height: 250)
                    
                    BeerDetailCard(beer: beer) {
                        viewModel.save(beer: beer)
                    } onReviewClick: {
                        onReviewClick()
                    }
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
    var onReviewClick: () -> Void
    
    @ObservedObject var saveBeerRatingViewModel = SaveBeerRatingViewModel(craftieBeerRatingsRepository: CraftieBeerRatingsRepository())
        
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
                        RatingResultView(beerId: beer.id)
                        CTAButton(text: "Review") {
                            onReviewClick()
                        }
                    }
                    .padding(.top, -180)
                    
                }
            }
            .padding(.top, 220)
    }
}
}

struct RatingResultView : View {
    var beerId: String
    
    @ObservedObject var saveBeerRatingViewModel = SaveBeerRatingViewModel(craftieBeerRatingsRepository: CraftieBeerRatingsRepository())
    
    var body: some View {
        
        switch saveBeerRatingViewModel.ratingUiState {
            case .success(let ratingResult):
            Rating(rating: Int(ratingResult.averageRating), reviews: Int(ratingResult.totalReviews), beerId: beerId)
            case .error:
                Rating(rating: 0, reviews: 0, beerId: beerId)
            case .loading:
                ProgressView()
            case .idle:
                Color.clear.onAppear(perform: {
                    saveBeerRatingViewModel.fetchRating(beerId: beerId)
                })
            }
    }
}

struct Rating : View {
    var rating: Int
    var reviews: Int
    var beerId: String
    
    var body: some View {
        let reviewsText = "Based on %d reviews"
        let formatText = String(format: reviewsText, reviews)
        
        RatingView(rating: .constant(rating))
        NavigationLink(destination: ViewAllRatingsView(beerId: beerId)) {
            Text(formatText)
                .font(.caption)
                .foregroundColor(Color.blue)
                .padding(.top, 2)
                .padding(.bottom, 16)
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
