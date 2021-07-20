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
                                .fill(Color.white)
                                .shadow(radius: 0.5)
                            HStack {
                                ImageView(withURL: item.imageUrl)
                                    .frame(width: 85, height: 150, alignment: .center)
                            }
                            .padding(16)
                        }
                        .padding(2)
                        .frame(height: 175)
                        Spacer()
                        Text(item.name)
                    }
                }
            }
            .padding(.horizontal)
        }
    }
}
