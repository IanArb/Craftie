//
//  FeaturedBeerView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 04/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct FeaturedBeerView: View {
    @ObservedObject var featuredViewModel = FeaturedBeerViewModel(beersRepository: CraftieBeersRepository())

    
    var body: some View {
        switch featuredViewModel.state {
            case .idle:
                Color.clear.onAppear(perform: featuredViewModel.load)
            case .error:
                ErrorView()
            case .loading:
                ProgressView()
            case .success(let featuredBeer):
                Featured(featuredBeer: featuredBeer)
            
        }
    }
}

struct Featured: View {
    var featuredBeer: Beer
    var body: some View {
        ScrollView {
            GeometryReader { geometry in
                VStack {
                    if geometry.frame(in: .global).minY <= 0 {
                        ZStack(alignment: .bottomLeading) {
                            ZStack(alignment: .top) {
                                ImageView(withURL: featuredBeer.breweryInfo.brandImageUrl)
                                    .frame(maxWidth: .infinity)
                                
                                Rectangle()
                                        .foregroundColor(.clear)
                                        .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                                    
                            }
                            Text(featuredBeer.name)
                                .bold()
                                .font(.title2)
                                .foregroundColor(.white)
                                .frame(maxWidth: 150, alignment: .bottom)
                                .padding(16)
                        }.frame(width: geometry.size.width, height: geometry.size.height)
                        .offset(y: geometry.frame(in: .global).minY/9)
                        .clipped()
                    } else {
                        ZStack(alignment: .bottomLeading) {
                            ZStack(alignment: .top) {
                                ImageView(withURL: featuredBeer.breweryInfo.brandImageUrl)
                                    .frame(maxWidth: .infinity)
                                
                                Rectangle()
                                        .foregroundColor(.clear)
                                        .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                                    
                            }
                            Text(featuredBeer.name)
                                .bold()
                                .font(.title2)
                                .foregroundColor(.white)
                                .frame(maxWidth: 150, alignment: .bottom)
                                .padding(16)
                        }.frame(width: geometry.size.width, height: geometry.size.height + geometry.frame(in: .global).minY)
                        .clipped()
                        .offset(y: -geometry.frame(in: .global).minY)
                    }
                }
            }
            .frame(height: 250)
            VStack(alignment: .leading) {
                VStack {
                    Text(featuredBeer.description_)
                        .padding(16)
                }
            }
        }
        .edgesIgnoringSafeArea(.top)
        
    }
}

struct FeaturedBeerView_Previews: PreviewProvider {
    static var previews: some View {
        FeaturedBeerView()
    }
}
