//
//  ViewAllBreweriesView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 14/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ViewAllBreweriesView: View {
    @StateObject var viewModel = ViewAllBreweriesViewModel(breweriesRepository: CraftieBreweriesRepository())
    
    @State var uiTabarController: UITabBarController?
    
    var body : some View {
        let columns = [
            GridItem(.flexible()),
            GridItem(.flexible())
        ]

        ScrollView {
            LazyVGrid(columns: columns, spacing: 16) {
                ForEach(viewModel.breweries, id: \.self) { item in
                    VStack {
                        ImageView(withURL: item.imageUrl, contentMode: .fit)
                            .frame(width: 150, height: 150)
                        Spacer()
                        Text(item.name)
                    }
                }
                
                if viewModel.shouldDisplayNextPage {
                    nextPageView
                }
            }
            .onAppear {
                viewModel.load()
            }
            .padding(.horizontal)
        }
        .introspectTabBarController { (UITabBarController) in
            UITabBarController.tabBar.isHidden = true
            uiTabarController = UITabBarController
        }
        .onDisappear {
            uiTabarController?.tabBar.isHidden = false
        }
        
    }
    
    private var nextPageView: some View {
            HStack {
                Spacer()
                VStack {
                    ProgressView()
                    Text("Loading next page...")
                }
                Spacer()
            }
            .onAppear(perform: {
                viewModel.loadMoreContent()
            })
        }
    
}

struct ViewAllBreweriesView_Previews: PreviewProvider {
    static var previews: some View {
        ViewAllBreweriesView()
    }
}
