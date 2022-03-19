//
//  ErrorView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 04/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct ErrorView: View {
    @State var uiTabarController: UITabBarController?
    
    var onClick: () -> Void
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
            
            VStack {
                Text("No Results")
                    .font(.title)
                    .foregroundColor(.black)
                Text("There are no results at this time. Please try again")
                    .font(.body)
                    .foregroundColor(.black)
                Button(action: {
                    onClick()
                }) {
                    HStack {
                        Text("Try Again")
                            .fontWeight(.bold)
                            .foregroundColor(.white)
                            .background(Color(red: 242 / 255, green: 153 / 255, blue: 74 / 255))
                            
                    }
                    .padding(10)
                    
                }
                .frame(width: 300)
                .background(
                    RoundedRectangle(cornerRadius: 6.0, style: .continuous)
                        .fill(Color.orange)
                )
                .padding(.bottom, 16)
            }
            .padding(20)
            .multilineTextAlignment(.center)
        }
        .frame(width: 400, height: 250)
        .introspectTabBarController { (UITabBarController) in
            UITabBarController.tabBar.isHidden = true
            uiTabarController = UITabBarController
        }
        .padding(20)
        .onDisappear {
            uiTabarController?.tabBar.isHidden = false
        }
    }
    
}

struct EmptyResultView: View {
    @State var uiTabarController: UITabBarController?
    
    var title: String
    var message: String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
            
            VStack {
                Text(title)
                    .font(.title)
                    .foregroundColor(.black)
                Spacer()
                Text(title)
                    .font(.body)
                    .foregroundColor(.black)
            }
        }
        .frame(width: 400, height: 250)
        .introspectTabBarController { (UITabBarController) in
            UITabBarController.tabBar.isHidden = true
            uiTabarController = UITabBarController
        }
        .padding(20)
        .onDisappear {
            uiTabarController?.tabBar.isHidden = false
        }
    }
    
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView {
            
        }
    }
}
