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
                ErrorView {
                    featuredViewModel.load()
                }
                .onDisappear {
                    featuredViewModel.cancel()
                }
            case .loading:
                ProgressView()
            case .success(let featuredBeer):
                BeerDetail(beer: featuredBeer)
                .onDisappear {
                    featuredViewModel.cancel()
                }
            
        }
    }
}

struct FeaturedBeerView_Previews: PreviewProvider {
    static var previews: some View {
        FeaturedBeerView()
    }
}
