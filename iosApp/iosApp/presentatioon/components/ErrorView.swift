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
                .fill(Color.surfaceColor)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
            
            VStack {
                Text("No Results")
                    .font(.title)
                Text("There are no results at this time. Please try again")
                    .font(.body)
                Button(action: {
                    onClick()
                }) {
                    HStack {
                        Text("Try Again")
                            .fontWeight(.bold)
                    }
                    .padding(10)
                    
                }
                .frame(width: 300)
                .background(
                    RoundedRectangle(cornerRadius: 6.0, style: .continuous)
                        .fill(Color.surfaceColor)
                        .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
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

struct GenericErrorView: View {
    @State var uiTabarController: UITabBarController?
    
    var title: String
    var message: String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.surfaceColor)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/, y: 7)
            
            VStack {
                Text(title)
                    .font(.title)
                Text(message)
                    .font(.body)
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

struct InternetConnectionErrorView: View {
    var body: some View {
        GenericErrorView(
            title: "No Internet Connection",
            message: "Oops.. please try to reconnect to the internet by going to your connection settings."
        )
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView {
            
        }
    }
}
