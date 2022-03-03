//
//  ViewAllTopRatedView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 15/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ViewAllTopRatedView: View {
    @StateObject var viewModel = ViewAllTopRatedViewModel(beersRepository: CraftieBeersRepository())
    
    var body: some View {
        BeersGridView(
            beers: viewModel.beers,
            shouldDisplayNextPage: viewModel.shouldDisplayNextPage,
            loadItems: {
                viewModel.load()
            },
            loadMoreContent: {
                viewModel.loadMoreContent()
            }
        )
    }
}


struct ViewAllTopRatedView_Previews: PreviewProvider {
    static var previews: some View {
        ViewAllTopRatedView()
    }
}
