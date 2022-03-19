//
//  ViewAllRatingsView.swift
//  iosApp
//
//  Created by Ian Arbuckle on 04/11/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import Introspect

struct ViewAllRatingsView: View {
    
    var beerId: String
    
    @ObservedObject var viewModel: ViewAllRatingsViewModel = ViewAllRatingsViewModel(ratingsRepository: CraftieBeerRatingsRepository())
    
    
    var body: some View {
        switch viewModel.state {
            case .idle:
                Color.clear.onAppear(perform: {
                    viewModel.load(beerId: beerId)
                })
            case .success(let ratings):
                RatingsGridView(ratings: ratings)
                .onDisappear {
                    viewModel.cancel()
                }
            case .error:
                ErrorView {
                    viewModel.load(beerId: beerId)
                }
                .onDisappear {
                    viewModel.cancel()
                }
            case .loading:
                ProgressView()
            case .empty:
                EmptyView()
                .onDisappear {
                    viewModel.cancel()
                }
            }
    }
}

struct RatingsGridView: View {
    var ratings: [RatingResponse]
    
    @State var uiTabarController: UITabBarController?
    
    var body: some View {
        let columns = [
            GridItem(.flexible()),
            GridItem(.flexible())
        ]
        
        ScrollView {
            LazyVGrid(columns: columns, spacing: 20) {
                ForEach(ratings, id: \.self) { item in
                    VStack(alignment: .leading) {
                        ZStack {
                            RoundedRectangle(cornerRadius: 6, style: .continuous)
                                .fill(Color.white)
                                .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
                                .frame(width: .infinity)
                                .padding(.leading, 10)
                                .padding(.trailing, 10)
                                .padding(.top, 4)
                            
                            VStack(alignment: .leading) {
                                StaticRatingView(rating: Int(item.rating))
                                    .padding(.bottom, 2)
                                
                                let authorName = item.authorName ?? "Annoymous"
                                
                                if (authorName.isEmpty) {
                                    AuthorView(authorName: "Annoymous")
                                        .padding(.bottom, 6)
                                } else {
                                    AuthorView(authorName: authorName)
                                        .padding(.bottom, 6)
                                }
                                
                                
                                Text(item.description_ ?? "")
                            }
                            .padding(16)
                            
                        }
                        .padding(2)
                        
                        
                    }
                }
            }
            .padding(.horizontal)
            .introspectTabBarController { (UITabBarController) in
                UITabBarController.tabBar.isHidden = true
                uiTabarController = UITabBarController
            }
        }
    }
}

struct AuthorView : View {
    var authorName: String
    
    var body: some View {
        HStack {
            Image(systemName: "person")
            Text(authorName)
                .fontWeight(Font.Weight.medium)
                .padding(.bottom, 2)
        }
    }
}

struct ViewAllRatingsView_Previews: PreviewProvider {
    static var previews: some View {
        ViewAllRatingsView(beerId: "1")
    }
}
