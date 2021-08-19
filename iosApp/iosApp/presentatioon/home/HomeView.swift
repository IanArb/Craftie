//
//  HomeView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        NavigationView {
            ScrollView {
                FavouritesCard()
                BeersTasted()
                    
                .navigationBarTitle(Text("Home"))
            }
            .background(Color(red: 248 / 255, green: 248 / 255, blue: 248 / 255))
        }
    }
}

struct FavouritesCard : View {
    var body: some View {
        VStack {
            VStack(alignment: .leading) {
                HStack() {
                    Image(systemName: "heart")
                        .foregroundColor(.red)
                        .padding(10)
                    .background(
                        Circle().fill(Color(red: 255 / 255, green: 227 / 255, blue: 222 / 255))
                    )
                    
                    Text("Your Favourites")
                        .fontWeight(.medium)
                    
                }
                .padding(.top, 6)
                
                Text("You have no favourites. Why not go ahead and add some?")
                    .fontWeight(.light)
                    .padding(.top, 6)
            }
            .padding(20)
        }
        .background(
            RoundedRectangle(cornerRadius: 6)
                .fill(Color.white)
                .frame(width: .infinity)
                .padding(.leading, 8)
                .padding(.trailing, 8)
                .padding(.top, 16)
        )
    }
}

struct BeersTasted : View {
    var provinces = [
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FLeinster.png?alt=media&token=8cb29e1f-a9ad-46ee-8c8c-52b6737a22e9", name: "Leinster"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FConnaught.png?alt=media&token=32b6f284-d5ec-4ab4-b9e2-c71d15de999e", name: "Connaught"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FMunster.png?alt=media&token=ff179873-d7ac-45cf-a4de-358c70f64272", name: "Munster"),
        ProvinceLocal(imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FUlster.png?alt=media&token=4ff562fe-4b5f-4a6f-aeff-fc770b0cf450", name: "Ulster")
    ]
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Beers Tasted")
                .fontWeight(.medium)
                .padding(.top, 10)
                .padding(.leading, 16)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(alignment: .top, spacing: 8) {
                    ForEach(provinces, id: \.name) { province in
                        VStack(alignment: .center) {
                            ImageView(withURL: province.imageUrl, contentMode: .fit)
                                .frame(width: 80, height: 80)
                            Text(province.name)
                            Text("25%")
                        }
                        .padding(16)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(Color.white)
                                .frame(width: .infinity)
                        )
                    }
                }
            }
            .padding(16)
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
