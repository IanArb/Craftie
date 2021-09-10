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
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
            
            VStack {
                Text("Oops something went wrong, please try again.")
                    .font(.title)
                    .foregroundColor(.black)
            }
            .padding(20)
            .multilineTextAlignment(.center)
        }
        .frame(width: 450, height: 250)
        .introspectTabBarController { (UITabBarController) in
            UITabBarController.tabBar.isHidden = true
            uiTabarController = UITabBarController
        }
        .onDisappear {
            uiTabarController?.tabBar.isHidden = false
        }
    }
    
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView()
    }
}
