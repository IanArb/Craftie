//
//  BeersGridView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 18/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BeersGridView : View {
    var beers: [Beer]
    var shouldDisplayNextPage: Bool
    var loadItems: () -> Void
    var loadMoreContent: () -> Void
    
    @State var uiTabarController: UITabBarController?
    
    var body : some View {
        let columns = [
            GridItem(.flexible()),
            GridItem(.flexible())
        ]

        ScrollView {
            LazyVGrid(columns: columns, spacing: 20) {
                ForEach(beers, id: \.self) { item in
                    VStack {
                        ZStack {
                            RoundedRectangle(cornerRadius: 10, style: .continuous)
                                .fill(Color.surfaceColor)
                                .shadow(radius: 0.5)
                            HStack {
                                AsyncImage(
                                    url: URL(string: item.imageUrl),
                                    content: { image in
                                        image.resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .frame(width: 85, height: 150, alignment: .center)
                                    },
                                    placeholder: {
                                        ProgressView()
                                    }
                                )
                            }
                            .padding(16)
                        }
                        .padding(2)
                        .frame(height: 175)
                        Spacer()
                        Text(item.name)
                    }
                }
                
                if shouldDisplayNextPage {
                    PagerLoadingView {
                        loadMoreContent()
                    }
                }
            }
            .padding(.horizontal)
            .onAppear {
                loadItems()
            }
            
            
        }
        .introspectTabBarController { (UITabBarController) in
            UITabBarController.tabBar.isHidden = true
            uiTabarController = UITabBarController
        }
        .onDisappear {
            uiTabarController?.tabBar.isHidden = false
        }
    }
}
