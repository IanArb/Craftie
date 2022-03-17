//
//  DiscoveryShimmerView.swift
//  iosApp
//
//  Created by Ian Arbuckle on 16/03/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct DiscoveryShimmerView: View {
    var body: some View {
        ZStack {
            ScrollView(showsIndicators: false) {
                VStack(alignment: .leading) {
                    ListShimmerView(title: "View all Breweries")
                        .padding(.bottom, 16)
                    Spacer()
                    FeaturedShimmerView()
                        .padding(.bottom, 16)
                    Spacer()
                    RectangeListShimmerView(title: "Top Rated")
                        .padding(.bottom, 16)
                    Spacer()
                    ListShimmerView(title: "Beer by Province")
                        .padding(.bottom, 16)
                    Spacer()
                    RectangeListShimmerView(title: "Newest Beers")
                        .padding(.bottom, 16)
                }
                .padding(16)
            }
            .navigationTitle(Text("Discovery"))
        }
        .background(Color(red: 248 / 255, green: 248 / 255, blue: 248 / 255))
    }
}

struct ListShimmerView: View {
    var title: String
    var body: some View {
        HStack {
            Text(title)
                .bold()
                .frame(alignment: .leading)
            Spacer()
            Text("View All").foregroundColor(Color.blue)
                .padding(.bottom, 4)
        }
        
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top, spacing: 10) {
                ForEach(1...10, id: \.self) {_ in
                    Circle()
                        .fill(Color(red: 211 / 225, green: 211 / 225, blue: 211 / 225))
                        .frame(width: 80, height: 80)
                        .redacted(reason: .placeholder)
                        .shimmer()
                }
            }
        }
    }
}

struct FeaturedShimmerView: View {
    var body: some View {
        Group {
            Text("Featured")
                .bold()
                .padding(.bottom, 8)
            ZStack(alignment: .bottomLeading) {
                ZStack(alignment: .top) {
                    Rectangle()
                        .foregroundColor(Color(red: 211 / 225, green: 211 / 225, blue: 211 / 225))
                        .cornerRadius(6.0)
                        .redacted(reason: .placeholder)
                        .frame(height: 250)
                        .shimmer()
                }
            }

        }
    }
}

struct RectangeListShimmerView: View {
    var title: String
    
    var body: some View {
        HStack {
            Text(title)
                .bold()
                .frame(alignment: .leading)
            Spacer()
            Text("View All").foregroundColor(Color.blue)
        }
        
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(alignment: .top) {
                ForEach(1...10, id: \.self) { _ in
                    VStack {
                        ZStack {
                            RoundedRectangle(cornerRadius: 10, style: .continuous)
                                .fill(Color.white)
                                .shadow(radius: 0.5)
                            HStack {
                                Rectangle()
                                    .fill(Color(red: 211 / 225, green: 211 / 225, blue: 211 / 225))
                                    .frame(width: 85, height: 150, alignment: .center)
                                    .redacted(reason: .placeholder)
                                    .shimmer()
                            }
                            .padding(16)
                        }
                        .padding(2)
                        .frame(height: 175)
                        
                        Text("Text to load before content")
                            .fontWeight(.medium)
                            .lineLimit(2)
                            .font(.system(size: 12))
                            .frame(width: 130)
                            .padding(.bottom, 4)
                            .redacted(reason: .placeholder)
                            .shimmer()
                    }
                }
            }
        }
    }
}

struct DiscoveryShimmerView_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryShimmerView()
    }
}
