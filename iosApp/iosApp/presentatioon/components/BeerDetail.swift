//
//  BeerDetail.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 24/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BeerDetail: View {
    var beer: Beer
    
    @State var uiTabarController: UITabBarController?
    
    var body: some View {
        
        ScrollView {
            GeometryReader { geometry in
                VStack {
                    if geometry.frame(in: .global).minY <= 0 {
                        ZStack(alignment: .bottomLeading) {
                            ZStack(alignment: .top) {
                                if let brandImageUrl = beer.breweryInfo.brandImageUrl {
                                    ImageView(withURL: brandImageUrl, contentMode: .fit)
                                }
                               
                                Rectangle()
                                        .foregroundColor(.clear)
                                        .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                                    
                            }
                            Text(beer.name)
                                .bold()
                                .font(.title2)
                                .foregroundColor(.white)
                                .frame(maxWidth: 150, alignment: .bottom)
                                .padding(16)
                            
                        }.frame(width: geometry.size.width, height: geometry.size.height)
                        .offset(y: geometry.frame(in: .global).minY/9)
                        .clipped()
                    } else {
                        ZStack(alignment: .bottomLeading) {
                            ZStack(alignment: .top) {
                                if let brandImageUrl = beer.breweryInfo.brandImageUrl {
                                    ImageView(withURL: brandImageUrl, contentMode: .fit)
                                }
                                
                                Rectangle()
                                        .foregroundColor(.clear)
                                        .background(LinearGradient(gradient: Gradient(colors: [.clear, .black]), startPoint: .top, endPoint: .bottom))
                                    
                            }
                            Text(beer.name)
                                .bold()
                                .font(.title2)
                                .foregroundColor(.white)
                                .frame(maxWidth: 150, alignment: .bottom)
                                .padding(16)
                        }.frame(width: geometry.size.width, height: geometry.size.height + geometry.frame(in: .global).minY)
                        .clipped()
                        .offset(y: -geometry.frame(in: .global).minY)
                    }
                }
            }
            .frame(height: 320)
            VStack(alignment: .leading) {
                VStack {
                    Text(beer.description_)
                        .padding(16)
                }
            }
        }
        .introspectTabBarController { (UITabBarController) in
            UITabBarController.tabBar.isHidden = true
            uiTabarController = UITabBarController
        }
        .onDisappear {
            uiTabarController?.tabBar.isHidden = false
        }
        .edgesIgnoringSafeArea(.top)
        
    }
}
