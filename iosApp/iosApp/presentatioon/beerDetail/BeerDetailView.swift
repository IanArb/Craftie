//
//  BeerDetailView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 24/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BeerDetailView: View {
    var id: String
    
    @ObservedObject var viewModel = BeerDetailViewModel(beersRepository: CraftieBeersRepository())
    
    var body: some View {
        switch viewModel.state {
        case .idle:
            Color.clear.onAppear(perform: {
                viewModel.load(id: id)
            })
        case .error:
            ErrorView()
        case .loading:
            ProgressView()
        case .success(let beer):
            BeerDetail(beer: beer)
        }
    }
}

struct BeerDetailView_Previews: PreviewProvider {
    static var previews: some View {
        BeerDetailView(id: "id")
    }
}
