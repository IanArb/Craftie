//
//  RatingView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/08/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct RatingView: View {
    @Binding var rating: Int?

    var body: some View {
        HStack {
            ForEach(1...5, id: \.self) { index in
                Image(systemName: self.starType(index: index))
                    .foregroundColor(Color.orange)
                    .onTapGesture {
                        self.rating = index
                    }
            }
        }
    }
    
    private func starType(index: Int) -> String {
        if let rating = self.rating {
            return index <= rating ? "star.fill" : "star"
        } else {
            return "star"
        }
    }
}

struct StaticRatingView: View {
    var rating: Int
    
    var body: some View {
        HStack {
            ForEach(1...5, id: \.self) { index in
                Image(systemName: starType(index: index))
                    .foregroundColor(Color.orange)
            }
        }
    }
    
    func starType(index: Int) -> String {
        if (index <= rating) {
            return "star.fill"
        } else {
            return "star"
        }
    }
}
