//
//  ErrorView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 04/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct ErrorView: View {
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
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView()
    }
}
