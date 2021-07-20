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
    @ObservedObject var viewAllTopRatedViewModel = ViewAllTopRatedViewModel(beersRepository: CraftieBeersRepository())
    
    var body: some View {
        switch viewAllTopRatedViewModel.state {
                case .idle:
                    Color.clear.onAppear(perform: viewAllTopRatedViewModel.load)
                case .error:
                    ErrorView()
                case .loading:
                    ProgressView()
                case .success(let beers):
                    BeersGridView(beers: beers)
        }
    }
}


struct ViewAllTopRatedView_Previews: PreviewProvider {
    static var previews: some View {
        ViewAllTopRatedView()
    }
}
