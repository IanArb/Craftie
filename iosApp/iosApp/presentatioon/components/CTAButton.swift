//
//  CTAButton.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 15/10/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct CTAButton: View {
    var text: String
    var onClick: () -> Void
    
    var body: some View {
        Button(action: {
            onClick()
        }) {
            HStack {
                Text(text)
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
   
}

struct CTAButton_Previews: PreviewProvider {
    static var previews: some View {
        CTAButton(text: "Review") {
            
        }
    }
}
