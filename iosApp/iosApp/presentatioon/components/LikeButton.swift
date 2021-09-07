//
//  LikeButton.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 04/09/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI


struct LikeButton : View {
    var onFavouriteClick: () -> Void
    @State var isPressed = false
    var body: some View {
        ZStack {
            Image(systemName: "heart.fill")
                .opacity(isPressed ? 1 : 0)
                .scaleEffect(isPressed ? 1.0 : 0.1)
                .animation(.linear)
            Image(systemName: "heart")
        }.font(.system(size: 20))
            .onTapGesture {
                self.isPressed.toggle()
                onFavouriteClick()
        }
        .foregroundColor(isPressed ? .red : .gray)
    }
}

struct LikeButton_Previews: PreviewProvider {
    static var previews: some View {
        LikeButton() {
            
        }
    }
}
