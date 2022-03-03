//
//  BeersByProvinceView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 18/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BeersByProvinceView: View {
    var provinceSelected: String
    
    @ObservedObject var viewModel = BeersByProvinceViewModel(beersRepository: CraftieBeersRepository())
    
    var body: some View {
        BeersGridView(
            beers: viewModel.beers,
            shouldDisplayNextPage: viewModel.shouldDisplayNextPage,
            loadItems: {
                viewModel.load(province: provinceSelected)
            },
            loadMoreContent: {
                viewModel.loadMoreContent(province: provinceSelected)
            }
        )
    }
}

struct ProvinceEmptyStateView: View {
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
            
            VStack {
                Text("No beers found.")
                    .font(.title)
                    .foregroundColor(.black)
                Text("There are no beers available in this province at this time. Please try another province.")
                    .font(.body)
                    .foregroundColor(.black)
            }
            .padding(20)
            .multilineTextAlignment(.center)
        }
        .frame(width: 450, height: 250)
    }
}

struct BeersByProvinceView_Previews: PreviewProvider {
    static var previews: some View {
        BeersByProvinceView(provinceSelected: "Leinster")
    }
}
