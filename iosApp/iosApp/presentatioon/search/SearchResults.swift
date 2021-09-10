//
//  SearchResults.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/08/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import Introspect

struct SearchResults: View {
    var beers: [Beer]

    
    var body: some View {
        ScrollView(showsIndicators: false) {
            ForEach(beers, id: \.id) { beer in
                ResultsCard(beer: beer)
            }
        }
        .padding(.top, 20)
        .padding(.bottom, 20)
        .background(Color(red: 248, green: 248, blue: 248))
        
    }
}

struct ResultsCard: View {
    var beer: Beer
    
    var body: some View {
        NavigationLink(destination: SearchResultDetailView(id: beer.id)) {
            ZStack(alignment: .leading) {
                RoundedRectangle(cornerRadius: 6, style: .continuous)
                    .fill(Color.white)
                    .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
                    .frame(width: .infinity)
                    .padding(.leading, 20)
                    .padding(.trailing, 20)
                    .padding(.top, 4)
                    
                
                HStack {
                    
                    VStack(alignment: .leading) {
                        if (beer.imageUrl == "{placeholder}") {
                            ImageView(withURL: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FElevation_pale_ale.png?alt=media&token=e5fbe476-dfeb-41ac-87d8-c2698099313c", contentMode: ContentMode.fit)
                                .frame(height: 120)
                        } else {
                            ImageView(withURL: beer.imageUrl, contentMode: ContentMode.fit)
                                .frame(height: 120)
                        }
                    }
                    .padding(.top, 4)
                    .padding(.leading, 45)
                    .padding(.trailing, 30)
                    
                    VStack(alignment: .leading) {
                        Text(beer.style)
                            .font(.caption)
                            .foregroundColor(Color.gray)
                        Text(beer.name)
                            .font(.title3)
                            .fontWeight(.medium)
                            .fixedSize(horizontal: false, vertical: true)
                        Text(beer.breweryInfo.name)
                            .font(.caption)
                            .foregroundColor(Color.gray)
                        RatingView(rating: 4)
                            .padding(.top, 10)
                        Text("Based on 300 reviews")
                            .font(.caption)
                            .foregroundColor(Color.gray)
                            
                    }
                    .padding(.top, 20)
                    .padding(.bottom, 20)
                    
                }
                
            }
            .padding(.bottom, 6)
        }
        .buttonStyle(PlainButtonStyle())
        
        
    }
}
