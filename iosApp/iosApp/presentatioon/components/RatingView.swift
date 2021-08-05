//
//  RatingView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 02/08/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct RatingView: View {
    
    var rating: Int

    var body: some View {
        HStack {
            ForEach(1..<(5 + 1), id: \.self) { index in
                Image(systemName: self.starType(index: index))
                    .foregroundColor(Color.orange)
            }
        }
    }
    
    private func starType(index: Int) -> String {
        if (index <= rating) {
            return "star.fill"
        } else {
            return "star"
        }
    }
}
